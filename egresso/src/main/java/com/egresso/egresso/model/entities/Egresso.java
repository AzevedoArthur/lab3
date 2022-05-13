package com.egresso.egresso.model.entities;

import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Egresso {
    @Id
    @Column(name="id_egresso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="email")
    private String email;

    @Column(name="cpf")
    private String cpf;

    @Column(name="resumo")
    private String resumo;

    @Column(name="url_foto")
    private String url_foto;

    @ManyToMany
    @JoinTable(
        name="contato_egresso", 
        joinColumns = @JoinColumn(name="egresso_id"),
        inverseJoinColumns = @JoinColumn(name="contato_id"))
    private Set<Contato> contatos;
}