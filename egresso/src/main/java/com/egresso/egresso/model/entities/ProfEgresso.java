package com.egresso.egresso.model.entities;

import java.sql.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="prof_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfEgresso {
    @Id
    @Column(name="id_prof_egresso")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="empresa")
    private String empresa;

    @Column(name="descricao")
    private String descricao;

    @Column(name="data_registro")
    private Date data_registro;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="egresso_id")
    private Egresso egresso;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="cargo_id")
    private Cargo cargo;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="faixa_salario_id")
    private FaixaSalario faixaSalario;

}