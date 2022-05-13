package com.egresso.egresso.model.entities;

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
}