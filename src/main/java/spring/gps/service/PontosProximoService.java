package spring.gps.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spring.gps.model.PontosInteresse;
import spring.gps.repository.PontosInteresseRepository;

import java.util.List;
@Slf4j
@Service
public class PontosProximoService {

    @Autowired
    PontosInteresseRepository pontosInteresseRepository;

    public List<PontosInteresse> pontosProximos(Long x,long y, long dmax){
        log.info("Calculando Pontos próximos");
        long xMin = x - dmax;
        long xMax = x + dmax;
        long yMin = y - dmax;
        long yMax = y + dmax;

        List<PontosInteresse>  filtradosPontos = pontosInteresseRepository.findPontosProximos(xMin,xMax,yMin,yMax).stream().filter(p -> distanciaEuclidiana(x,y, p.getX(), p.getY()) <=dmax).toList();
        return filtradosPontos;
    }


    public double distanciaEuclidiana(Long x1,Long y1,Long x2,Long y2){
        log.info("calculando distancia Euclidiana" );
        return Math.hypot(x2 - x1,y2 - y1);
    }

}
