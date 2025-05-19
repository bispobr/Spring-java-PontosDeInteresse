package spring.gps.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record PontosInteresseDTO(@NotBlank String nome, @NotNull Long x, @NotNull Long y) {
}
