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


    // public Long getId() {
    //     return this.id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getDescricao() {
    //     return this.descricao;
    // }

    // public void setDescricao(String descricao) {
    //     this.descricao = descricao;
    // }

}