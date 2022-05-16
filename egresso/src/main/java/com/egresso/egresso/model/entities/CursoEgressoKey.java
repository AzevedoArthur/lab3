package com.egresso.egresso.model.entities;

import java.io.Serializable;

import javax.persistence.*;
import lombok.*;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoEgressoKey implements Serializable {
    @Column(name="egresso_id")
    private Long egresso_id;

    @Column(name="curso_id")
    private Long curso_id;
}