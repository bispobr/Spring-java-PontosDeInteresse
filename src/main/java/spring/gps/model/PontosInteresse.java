package spring.gps.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import spring.gps.dto.PontosInteresseDTO;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "pontos_interesse")
public class PontosInteresse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long x;
    private Long y;

    public PontosInteresse(PontosInteresseDTO ponto) {
        this.nome = ponto.nome();
        this.x = ponto.x();
        this.y = ponto.y();
    }
}
