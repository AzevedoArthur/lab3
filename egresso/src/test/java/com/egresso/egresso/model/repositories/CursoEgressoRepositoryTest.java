package com.egresso.egresso.model.repositories;

import java.util.List;
import java.util.Optional;

import com.egresso.egresso.model.entities.CursoEgresso; // Interface
import com.egresso.egresso.model.entities.CursoEgressoKey; // Interface
import com.egresso.egresso.model.entities.Curso; // Interface
import com.egresso.egresso.model.entities.Egresso; // Interface

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@ActiveProfiles("test")
public class CursoEgressoRepositoryTest{
    @Autowired
    CursoEgressoRepository repository;
    @Autowired
    EgressoRepository egressoRepository;
    @Autowired
    CursoRepository cursoRepository;
}