package spring.gps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontosRespostaDTO( Long id,  String nome,  Long x,  Long y) {
}
