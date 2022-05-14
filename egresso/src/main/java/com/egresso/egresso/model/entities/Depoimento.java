package com.egresso.egresso.model.entities;

import java.sql.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="depoimento")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Depoimento {
    @Id
    @Column(name="id_depoimento")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name="texto")
    private String texto;

    @Column(name="data")
    private Date data;
    
    @ManyToOne
    @JoinColumn(name="egresso_id")
    private Egresso egresso;


    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTexto() {
        return this.texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Date getData() {
        return this.data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Egresso getEgresso() {
        return this.egresso;
    }

    public void setEgresso(Egresso egresso) {
        this.egresso = egresso;
    }

}