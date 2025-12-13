package spring.gps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontosRequisicaoDTO(@NotBlank String nome, @NotNull Long x, @NotNull Long y) {
}
