package spring.gps.service;

import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import spring.gps.dto.PontosRequisicaoDTO;
import spring.gps.dto.PontosRespostaDTO;
import spring.gps.mapper.PontosInteresseMapper;
import spring.gps.model.PontosInteresse;
import spring.gps.repository.PontosInteresseRepository;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



class PontosProximoServiceTest {

    @Mock
    PontosInteresseRepository repository;

    @Mock
    PontosInteresseMapper mapper;

    @InjectMocks
    PontosProximoService service;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void Salvar_DeveChamarRepositoryComEntidadeConvertida() {
        PontosRequisicaoDTO dto = new PontosRequisicaoDTO("Shopping", 10L, 20L);
        PontosInteresse entidade = new PontosInteresse(201L,"Shopping", 10L, 20L);

        when(mapper.paraPontoInteresse(dto)).thenReturn(entidade);

        service.Salvar(dto);

        verify(repository).save(entidade);
    }

    @Test
    void atualizarPonto_QuandoEncontrado_DeveAtualizarERetornarDTO() {

        Long id = 1L;
        PontosRequisicaoDTO dto = new PontosRequisicaoDTO("Mercado", 15L, 25L);
        PontosInteresse existente = new PontosInteresse(id,"Antigo", 5L, 5L);
        PontosInteresse atualizado = new PontosInteresse(id,"Mercado", 15L, 25L);


        PontosRespostaDTO respostaDTO = new PontosRespostaDTO(id,"Mercado", 15L, 25L);

        when(repository.findById(id)).thenReturn(Optional.of(existente));
        when(repository.save(any())).thenReturn(atualizado);
        when(mapper.paraRespostaDTO(atualizado)).thenReturn(respostaDTO);

        ResponseEntity<PontosRespostaDTO> response = service.atualizarPonto(id, dto);

        assertEquals(HttpStatus.OK ,response.getStatusCode());
        assertEquals("Mercado", response.getBody().nome());
    }

    @Test
    void atualizarPonto_QuandoNaoEncontrado_DeveRetornar404() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<PontosRespostaDTO> response = service.atualizarPonto(99L, new PontosRequisicaoDTO("Teste", 1L, 2L));

        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
    }

    @Test
    void removerPonto_QuandoEncontrado_DeveRemoverERetornar200() {
        Long id = 1L;
        PontosInteresse ponto = new PontosInteresse(id,"Parque", 10L, 10L);
        ponto.setId(id);

        when(repository.findById(id)).thenReturn(Optional.of(ponto));

        ResponseEntity<Void> response = service.removerPonto(id);

        verify(repository).deleteById(id);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void removerPonto_QuandoNaoEncontrado_DeveRetornar404() {
        when(repository.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = service.removerPonto(99L);

        assertEquals(HttpStatus.NOT_FOUND ,response.getStatusCode());
    }

    @Test
    void retornarListaPontos_DeveRetornarListaDTO() {
        List<PontosInteresse> entidades = List.of(
                new PontosInteresse(1L,"Parque", 10L, 20L),
                new PontosInteresse(2L,"Museu", 5L, 5L)
        );
        List<PontosRespostaDTO> dtos = List.of(
                new PontosRespostaDTO(1L,"Parque", 10L, 20L),
                new PontosRespostaDTO(2L,"Museu", 5L, 5L)
        );

        when(repository.findAll()).thenReturn(entidades);
        when(mapper.paraRespostaList(entidades)).thenReturn(dtos);

        List<PontosRespostaDTO> resultado = service.retornarListaPontos();

        assertEquals(2, resultado.size());
        assertEquals("Parque", resultado.getFirst().nome());
    }

    @Test
    void pontosProximos_DeveFiltrarPorDistancia() {
        long x = 10L, y = 10L, dmax = 5L;
        List<PontosInteresse> encontrados = List.of(
                new PontosInteresse(1L,"A", 12L, 10L),
                new PontosInteresse(2L,"B", 15L, 15L)
        );
        List<PontosInteresse> esperadosFiltrados = List.of(encontrados.getFirst());
        List<PontosRespostaDTO> resposta = List.of(new PontosRespostaDTO(1L,"A", 12L, 10L));

        when(repository.findPontosProximos(5L, 15L, 5L, 15L)).thenReturn(encontrados);
        when(mapper.paraRespostaList(any())).thenReturn(resposta);

        List<PontosRespostaDTO> resultado = service.pontosProximos(x, y, dmax);

        assertEquals(1, resultado.size());
        assertEquals("A", resultado.getFirst().nome());
    }

    @Test
    void distanciaEuclidiana_DeveRetornarValorCorreto() {
        double distancia = service.distanciaEuclidiana(0L, 0L, 3L, 4L);
        assertEquals(5.0, distancia);
    }

}