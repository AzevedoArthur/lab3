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
    
    @ManyToOne
    @JoinColumn(name="egresso_id")
    private Egresso egresso;
    
    @ManyToOne
    @JoinColumn(name="cargo_id")
    private Cargo cargo;
    
    @ManyToOne
    @JoinColumn(name="faixa_salario_id")
    private FaixaSalario faixa_salario;


    // public Long getId() {
    //     return this.id;
    // }

    // public void setId(Long id) {
    //     this.id = id;
    // }

    // public String getEmpresa() {
    //     return this.empresa;
    // }

    // public void setEmpresa(String empresa) {
    //     this.empresa = empresa;
    // }

    // public String getDescricao() {
    //     return this.descricao;
    // }

    // public void setDescricao(String descricao) {
    //     this.descricao = descricao;
    // }

    // public Date getData_registro() {
    //     return this.data_registro;
    // }

    // public void setData_registro(Date data_registro) {
    //     this.data_registro = data_registro;
    // }

    // public Egresso getEgresso() {
    //     return this.egresso;
    // }

    // public void setEgresso(Egresso egresso) {
    //     this.egresso = egresso;
    // }

    // public Cargo getCargo() {
    //     return this.cargo;
    // }

    // public void setCargo(Cargo cargo) {
    //     this.cargo = cargo;
    // }

    // public FaixaSalario getFaixa_salario() {
    //     return this.faixa_salario;
    // }

    // public void setFaixa_salario(FaixaSalario faixa_salario) {
    //     this.faixa_salario = faixa_salario;
    // }

}