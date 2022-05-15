package com.egresso.egresso.model.entities;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="curso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @Column(name="id_curso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="nome")
    private String nome;

    @Column(name="nivel")
    private String nivel;


    // public long getId() {
    //     return this.id;
    // }

    // public void setId(long id) {
    //     this.id = id;
    // }

    // public String getNome() {
    //     return this.nome;
    // }

    // public void setNome(String nome) {
    //     this.nome = nome;
    // }

    // public String getNivel() {
    //     return this.nivel;
    // }

    // public void setNivel(String nivel) {
    //     this.nivel = nivel;
    // }

}