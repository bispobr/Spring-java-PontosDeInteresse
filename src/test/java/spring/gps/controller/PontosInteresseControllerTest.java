package spring.gps.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import spring.gps.dto.PontosRequisicaoDTO;
import spring.gps.dto.PontosRespostaDTO;
import spring.gps.repository.PontosInteresseRepository;
import spring.gps.service.PontosProximoService;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class PontosInteresseControllerTest {

    @Mock
    private PontosInteresseRepository pontosInteresseRepository;

    @Mock
    private PontosProximoService pontosProximoService;

    @InjectMocks
    private PontosInteresseController pontosInteresseController;

    private ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @BeforeEach
    void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(pontosInteresseController).build();
    }

    @Test
    void deveCriarPontoInteresseComSucesso() throws Exception {
        PontosRequisicaoDTO dto = new PontosRequisicaoDTO("Padaria", 5L, 10L);

        mockMvc.perform(post("/pontosInteresse/cadastro")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated());

        Mockito.verify(pontosProximoService).Salvar(any());
    }

    @Test
    void deveAtualizarPontoInteresseComSucesso() throws Exception {
        Long id = 1L;
        PontosRequisicaoDTO dto = new PontosRequisicaoDTO("Restaurante", 6L, 9L);
        PontosRespostaDTO resposta = new PontosRespostaDTO(id, "Restaurante", 6L, 9L);

        Mockito.when(pontosProximoService.atualizarPonto(eq(id), any())).thenReturn(ResponseEntity.ok(resposta));

        mockMvc.perform(put("/pontosInteresse/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nome").value("Restaurante"));
    }

    @Test
    void deveRemoverPontoInteresseComSucesso() throws Exception {
        Long id = 1L;

        Mockito.when(pontosProximoService.removerPonto(id)).thenReturn(ResponseEntity.ok().build());

        mockMvc.perform(delete("/pontosInteresse/" + id))
                .andExpect(status().isOk());
    }

    @Test
    void deveListarTodosOsPontos() throws Exception {
        List<PontosRespostaDTO> lista = List.of(new PontosRespostaDTO(1L, "Supermercado", 2L, 3L));

        Mockito.when(pontosProximoService.retornarListaPontos()).thenReturn(lista);

        mockMvc.perform(get("/pontosInteresse/listagem"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Supermercado"));
    }

    @Test
    void deveListarPontosProximos() throws Exception {
        List<PontosRespostaDTO> lista = List.of(new PontosRespostaDTO(1L, "Padaria", 1L, 1L));

        Mockito.when(pontosProximoService.pontosProximos(1L, 1L, 10L)).thenReturn(lista);

        mockMvc.perform(get("/pontosInteresse/proximos")
                        .param("x", "1")
                        .param("y", "1")
                        .param("dmax", "10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nome").value("Padaria"));
    }

}