package com.egresso.egresso.model.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="faixa_salario")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaixaSalario {
    @Id
    @Column(name="id_faixa_salario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="descricao")
    private String descricao;
}