package spring.gps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import spring.gps.dto.PontosRespostaDTO;
import spring.gps.dto.PontosRequisicaoDTO;
import spring.gps.mapper.PontosInteresseMapper;
import spring.gps.model.PontosInteresse;
import spring.gps.repository.PontosInteresseRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class PontosProximoService {

    @Autowired
    PontosInteresseRepository pontosInteresseRepository;

    @Autowired
    PontosInteresseMapper mapper;

    public  void Salvar (PontosRequisicaoDTO requisicao){
        log.info("POI Salvo");
        pontosInteresseRepository.save(mapper.paraPontoInteresse(requisicao));
    }

    public  ResponseEntity<PontosRespostaDTO> atualizarPonto(Long id, PontosRequisicaoDTO pontos){
        Optional<PontosInteresse> optionalPontos = pontosInteresseRepository.findById(id);
        if (optionalPontos.isPresent()){
            PontosInteresse pontoEncontrado = optionalPontos.get();
            pontoEncontrado.setNome(pontos.nome()); ;
            pontoEncontrado.setX(pontos.x());
            pontoEncontrado.setY(pontos.y());

            PontosInteresse pontoAtualizado = pontosInteresseRepository.save(pontoEncontrado);

            log.info("POI: atualizado com sucesso");

            return ResponseEntity.ok(mapper.paraRespostaDTO(pontoAtualizado));
        } else {
            log.info("POI Não encontrado na base de dados");
            return ResponseEntity.notFound().build();
        }
    }

    public  ResponseEntity<Void> removerPonto (Long id){
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

    public  List<PontosRespostaDTO> retornarListaPontos(){
        log.info("Listando todos os pontos de interesse" );
        return mapper.paraRespostaList(pontosInteresseRepository.findAll());
    }

    public List<PontosRespostaDTO> pontosProximos(Long x, long y, long dmax){
        log.info("Calculando Pontos próximos");
        long xMin = x - dmax;
        long xMax = x + dmax;
        long yMin = y - dmax;
        long yMax = y + dmax;

        return mapper.paraRespostaList(pontosInteresseRepository.findPontosProximos(xMin,xMax,yMin,yMax).stream().filter(p -> distanciaEuclidiana(x,y, p.getX(), p.getY()) <=dmax).toList());
    }

    public double distanciaEuclidiana(Long x1,Long y1,Long x2,Long y2){
        log.info("calculando distancia Euclidiana" );
        return Math.hypot(x2 - x1,y2 - y1);
    }
}
