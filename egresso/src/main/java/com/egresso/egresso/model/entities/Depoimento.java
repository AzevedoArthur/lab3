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
    private Long id_depoimento;

    @Column(name="texto")
    private String texto;

    @Column(name="data")
    private Date data;
    
    @ManyToOne
    @JoinColumn(name="egresso_id")
    private Egresso egresso;
}