package com.egresso.egresso.model.entities;

import java.sql.Date;

import javax.persistence.*;

import com.egresso.egresso.model.entities.CursoEgressoKey.CursoEgressoKeyBuilder;

import lombok.*;

@Entity
@Table(name="curso_egresso")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CursoEgresso {
    @EmbeddedId
    private CursoEgressoKey id;
    // Getter customizado para a chave composta, garante que a chave apropriada ao estado atual do objeto vai ser retornada.
    public CursoEgressoKey getId() {
        if (id == null) { // Verifica se a chave é nula, se sim verifica se isso é apropriado à situação
            if (curso == null && egresso == null) { // Se a entidade não tem curso nem egresso registrados, a chave nula está correta.
                id = null;
            } else if (curso.getId() == null && egresso.getId() == null) { // Se o curso e egresso registrados na entidade não possuem id, a chave nula está correta.
                id = null;
            } else { // Fora destes cenários, a chave não deveria estar nula: pelo menos um dos elementos "curso_id" e "egresso_id" deveria estar na chave.
                CursoEgressoKeyBuilder builder = CursoEgressoKey.builder();
                if (curso != null) // Verifica se o elemento 'curso_id' é válido.
                    if (curso.getId() != null)
                        builder = builder.curso_id(curso.getId()); // Se for válido, adiciona à chave.
                if (egresso != null) // Verifica se o elemento 'egresso_id' é válido.
                    if (egresso.getId() != null)
                        builder = builder.egresso_id(egresso.getId()); // Se for válido, adiciona à chave.
                id = builder.build(); // Cria a nova chave, ela pode ser parcial (apenas "curso_id" ou apenas "egresso_id") ou completa.
            }
        }
        return id; // Retorna a chave devidamente atualizada.
    }

    @ManyToOne
    @MapsId("curso_id")
    @JoinColumn(name="curso_id")
    private Curso curso;
    // Setter customizado para curso que mantém a chave atualizada com eventuais mudanças
    public void setCurso(Curso curso) {
        if (id == null) {
            id = CursoEgressoKey.builder().curso_id(curso.getId()).build();
        } else {
            id.setCurso_id(curso.getId());
        }
    }

    @ManyToOne
    @MapsId("egresso_id")
    @JoinColumn(name="egresso_id")
    // Setter customizado para egresso que mantém a chave atualizada com eventuais mudanças
    private Egresso egresso;
    public void setEgresso(Egresso egresso) {
        if (id == null) {
            id = CursoEgressoKey.builder().curso_id(egresso.getId()).build();
        } else {
            id.setCurso_id(egresso.getId());
        }
    }

    @Column(name="data_inicio")
    private Date data_inicio;

    @Column(name="data_conclusao")
    private Date data_conclusao;
}