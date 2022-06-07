package com.egresso.egresso.model.entities;

import java.util.Set;

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
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="descricao")
    private String descricao;
    
    @OneToMany(mappedBy = "cargo",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    private Set<ProfEgresso> ocupantes;
}