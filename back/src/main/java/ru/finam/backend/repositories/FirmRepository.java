package ru.finam.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ru.finam.backend.model.entities.FirmEntity;

@Repository
public interface FirmRepository extends JpaRepository<FirmEntity, Integer> {

}