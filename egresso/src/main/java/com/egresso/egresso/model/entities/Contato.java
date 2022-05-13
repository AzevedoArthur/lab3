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
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="url_logo")
    private String url_logo;

    @ManyToMany(mappedBy = "contatos")
    private Set<Egresso> egressos;
}