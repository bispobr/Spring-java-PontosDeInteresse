package spring.gps.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import spring.gps.dto.PontosRespostaDTO;
import spring.gps.dto.PontosRequisicaoDTO;
import spring.gps.repository.PontosInteresseRepository;
import spring.gps.service.PontosProximoService;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/pontosInteresse")
public class PontosInteresseController {
    @Autowired
    private PontosInteresseRepository pontosInteresseRepository;

    @Autowired
    private PontosProximoService pontosProximoService;

    @PostMapping("/cadastro")
    @Operation(description = "Endpoint responsável por cadastrar novos Pontos de interesse")
    @ApiResponse(responseCode = "201", description = "Novo Ponto de interesse  criada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> CriarPontosInteresse( @Valid @RequestBody PontosRequisicaoDTO pontos){
        log.info("Requisição para criação de ponto de interesse recebida ");
        pontosProximoService.Salvar(pontos);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/{id}")
    @Operation(description = "Endpoint responsável por atualizar Ponto de Interesse")
    @ApiResponse(responseCode = "200", description = "POI atualizado com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição, dados enviados não atendem os requisitos")
    @ApiResponse(responseCode = "404", description = "POI não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<PontosRespostaDTO> atualizarPonto (@PathVariable("id")Long id, @Valid @RequestBody PontosRequisicaoDTO pontos ){
        log.info("Requisição para atualizar  ponto de interesse recebida ");
        return pontosProximoService.atualizarPonto(id, pontos);
    }

    @DeleteMapping ("/{id}")
    @Transactional
    @Operation(description = "Endpoint responsável por remover POI")
    @ApiResponse(responseCode = "200", description = "POI removido com sucesso")
    @ApiResponse(responseCode = "404", description = "POI não encontrado")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public ResponseEntity<Void> removerPonto ( @PathVariable Long id){
        log.info("Solicitação de remoção de POI recebida");
        return pontosProximoService.removerPonto(id);
    }


    @GetMapping("/listagem")
    @Operation(description = "Endpoint responsável por retornar todos os Pontos de interesse")
    @ApiResponse(responseCode = "200", description = "Lista retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<List<PontosRespostaDTO>> retornarPontos (){
        log.info("Solicitação de Listagem  de POIs recebida");
        return ResponseEntity.ok(pontosProximoService.retornarListaPontos());
    }

    @GetMapping("/proximos")
    @Operation(description = "Endpoint responsável por retornar todos os Pontos de interesse proximos")
    @ApiResponse(responseCode = "200", description = "Lista interesse proximo retornada com sucesso")
    @ApiResponse(responseCode = "400", description = "Erro de Requisição")
    @ApiResponse(responseCode = "500", description = "Erro interno")
    public  ResponseEntity<List<PontosRespostaDTO>> retornarPontosProximos (@RequestParam("x")Long x, @RequestParam("y")Long y, @RequestParam(value ="dmax", required = false,defaultValue = "10")Long dmax){
        log.info("Requsição de listagem de  todos os pontos de interesse proximos recebida" );
        return ResponseEntity.ok(pontosProximoService.pontosProximos(x,y,dmax));
    }
}
