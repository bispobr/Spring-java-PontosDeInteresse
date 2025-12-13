package spring.gps.mapper;

import org.springframework.stereotype.Component;
import spring.gps.dto.PontosRespostaDTO;
import spring.gps.dto.PontosRequisicaoDTO;
import spring.gps.model.PontosInteresse;

import java.util.List;

@Component
public class PontosInteresseMapper {

    public PontosInteresse paraPontoInteresse (PontosRequisicaoDTO dto){
     PontosInteresse pontosInteresse =  new PontosInteresse();
     pontosInteresse.setNome(dto.nome());
     pontosInteresse.setX(dto.x());
     pontosInteresse.setY(dto.y());
     return pontosInteresse;
    }

    public PontosRespostaDTO paraRespostaDTO (PontosInteresse pontosInteresse){
        return new PontosRespostaDTO(pontosInteresse.getId(), pontosInteresse.getNome(), pontosInteresse.getX(), pontosInteresse.getY());
    }

    public List<PontosRespostaDTO> paraRespostaList (List<PontosInteresse> lista){
        return lista.stream().map(l ->{
            return new PontosRespostaDTO(l.getId(), l.getNome(), l.getX(), l.getY());
        }).toList();
    }
}
