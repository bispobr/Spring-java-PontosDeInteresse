package spring.gps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gps.dto.PontosAtualizacaoDTO;
import spring.gps.dto.PontosInteresseDTO;
import spring.gps.model.PontosInteresse;
import spring.gps.repository.PontosInteresseRepository;
import spring.gps.service.PontosProximoService;

import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/pontosInteresse")
public class PontosInteresseController {
    @Autowired
    private PontosInteresseRepository pontosInteresseRepository;

    @Autowired
    private PontosProximoService pontosProximoService;

    @PostMapping
    @Operation(description = "Endpoint responsável por cadastrar novos Pontos de interesse")
    @ApiResponse(responseCode = "200", description = "Transação criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity CriarPontosInteresse( @Valid @RequestBody PontosInteresseDTO pontos){
        pontosInteresseRepository.save(new PontosInteresse(pontos));
        log.info("Requisição recebida ponto de interesse " + pontos.nome());
        return ResponseEntity.ok().build();
    }

    @PutMapping
    @Operation(description = "Endpoint responsável por atualizar Ponto de Interesse")
    @ApiResponse(responseCode = "200", description = "POI atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "POI não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity atualizarPonto (@Valid @RequestBody PontosAtualizacaoDTO pontos ){
        log.info("Solicitação de Atualização de POI recebida");
        Optional<PontosInteresse> optionalPontos = pontosInteresseRepository.findById(pontos.id());
         if (optionalPontos.isPresent()){
             PontosInteresse pontoEncontrado = optionalPontos.get();
             pontoEncontrado.setNome(pontos.nome()); ;
             pontoEncontrado.setX(pontos.x());
             pontoEncontrado.setY(pontos.y());

             PontosInteresse pontoAtualizado = pontosInteresseRepository.save(pontoEncontrado);
             log.info("POI: " + pontoAtualizado.getNome()+ "atualizado com sucesso");

             return ResponseEntity.ok(pontoAtualizado);
         } else {
             log.info("POI Não encontrado na base de dados");
             return ResponseEntity.notFound().build();
         }
    }

    @DeleteMapping ("/remover/{id}")
    @Transactional
    @Operation(description = "Endpoint responsável por remover POI")
    @ApiResponse(responseCode = "200", description = "POI removido com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "POI não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity removerPonto ( @PathVariable Long id){
        log.info("Solicitação de remoção de POI recebida");
        Optional<PontosInteresse> optionalPontos = pontosInteresseRepository.findById(id);
        if (optionalPontos.isPresent()){
            pontosInteresseRepository.deleteById(id);
            log.info("POI removido com sucesso");
            return ResponseEntity.ok().build();
        } else {
            log.info("POI Não encontrado na base de dados");
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping
    @Operation(description = "Endpoint responsável por retornar todos os Pontos de interesse")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<List<PontosInteresse>> retornarPontos (){
        List<PontosInteresse>listaPontosInteresse = pontosInteresseRepository.findAll();
        log.info("Listando todos os pontos de interesse" );
        return ResponseEntity.ok(listaPontosInteresse);
    }

    @GetMapping("/proximos")
    @Operation(description = "Endpoint responsável por retornar todos os Pontos de interesse proximos")
    @ApiResponse(responseCode = "200", description = "Lista interesse proximo retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<List<PontosInteresse>> retornarPontosProximos (@RequestParam("x")Long x,@RequestParam("y")Long y,@RequestParam(value ="dmax", required = false,defaultValue = "10")Long dmax){
        log.info("Requsição de listagem de  todos os pontos de interesse proximos recebida" );
        List<PontosInteresse>  filtradosPontos = pontosProximoService.pontosProximos(x,y,dmax);
        return ResponseEntity.ok(filtradosPontos);
    }
}
