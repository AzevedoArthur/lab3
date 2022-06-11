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

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
        name="contato_egresso", 
        joinColumns = @JoinColumn(name="egresso_id"),
        inverseJoinColumns = @JoinColumn(name="contato_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Contato> contatos;

    @OneToMany(mappedBy = "egresso",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Depoimento> depoimentos;
    // public void setDepoimentos(Set<Depoimento> novosDepoimentos) {
    //     for (Depoimento d : novosDepoimentos){
    //         d.setEgresso(this);
    //     }
    // }
    
    @OneToMany(mappedBy = "egresso",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<CursoEgresso> cursos;
    // public void setCursos(Set<CursoEgresso> novosCursos) {
    //     for (CursoEgresso c : novosCursos){
    //         c.setEgresso(this);
    //     }
    // }
    
    @OneToMany(mappedBy = "egresso",
                cascade = CascadeType.ALL,
                orphanRemoval = true)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<ProfEgresso> profissoes;
    // public void setProfissoess(Set<ProfEgresso> novosProfissoess) {
    //     for (ProfEgresso p : novosProfissoess){
    //         p.setEgresso(this);
    //     }
    // }

}