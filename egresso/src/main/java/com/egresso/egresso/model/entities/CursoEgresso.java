package com.egresso.egresso.model.entities;

import java.sql.Date;

import javax.persistence.*;
import lombok.*;

@Entity
@Table(name="curso_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoEgresso {
    @EmbeddedId
    CursoEgressoKey id;

    @ManyToOne
    @MapsId("curso_id")
    @JoinColumn(name="curso_id")
    private Curso curso;

    @ManyToOne
    @MapsId("egresso_id")
    @JoinColumn(name="egresso_id")
    private Egresso egresso;

    @Column(name="data_inicio")
    private Date data_inicio;

    @Column(name="data_conclusao")
    private Date data_conclusao;
}