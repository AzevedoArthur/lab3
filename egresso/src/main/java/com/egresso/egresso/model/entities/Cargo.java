package com.egresso.egresso.model.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="cargo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cargo {
    @Id
    @Column(name="id_cargo")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_cargo;

    @Column(name="nome")
    private String nome;

    @Column(name="descricao")
    private String descricao;
}