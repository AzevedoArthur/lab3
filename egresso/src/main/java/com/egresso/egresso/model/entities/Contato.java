package com.egresso.egresso.model.entities;

import java.util.Set;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="contato")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Contato {
    @Id
    @Column(name="id_contato")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="nome")
    private String nome;

    @Column(name="url_logo")
    private String url_logo;

    @ManyToMany(mappedBy = "contatos")
    private Set<Egresso> egressos;


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

    // public String getUrl_logo() {
    //     return this.url_logo;
    // }

    // public void setUrl_logo(String url_logo) {
    //     this.url_logo = url_logo;
    // }

    // public Set<Egresso> getEgressos() {
    //     return this.egressos;
    // }

    // public void setEgressos(Set<Egresso> egressos) {
    //     this.egressos = egressos;
    // }

}