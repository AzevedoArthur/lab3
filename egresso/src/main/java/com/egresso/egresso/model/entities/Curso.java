package com.egresso.egresso.model.entities;


import java.util.Set;

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
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="nivel")
    private String nivel;
    
    @OneToMany(mappedBy = "curso",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<CursoEgresso> cursantes;
}