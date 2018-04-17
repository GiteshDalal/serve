package com.giteshdalal.counterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.counterservice.model.Localised;

@Repository
public interface LocalisedRepository extends JpaRepository<Localised, Long> {

}
