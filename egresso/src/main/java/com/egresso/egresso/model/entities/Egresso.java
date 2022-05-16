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

    // public Long getId() {
    //     return this.id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getNome() {
    //     return this.nome;
    // }

    // public void setNome(String nome) {
    //     this.nome = nome;
    // }

    // public String getEmail() {
    //     return this.email;
    // }

    // public void setEmail(String email) {
    //     this.email = email;
    // }

    // public String getCpf() {
    //     return this.cpf;
    // }

    // public void setCpf(String cpf) {
    //     this.cpf = cpf;
    // }

    // public String getResumo() {
    //     return this.resumo;
    // }

    // public void setResumo(String resumo) {
    //     this.resumo = resumo;
    // }

    // public String getUrl_foto() {
    //     return this.url_foto;
    // }

    // public void setUrl_foto(String url_foto) {
    //     this.url_foto = url_foto;
    // }

    // public Set<Contato> getContatos() {
    //     return this.contatos;
    // }

    // public void setContatos(Set<Contato> contatos) {
    //     this.contatos = contatos;
    // }

}