package ru.finam.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import ru.finam.backend.model.entities.FinanceInstrumentEntity;

import java.util.List;
import java.util.Optional;


@Repository
public interface FinanceInstrumentRepository extends
    JpaRepository<FinanceInstrumentEntity, Integer>{

}