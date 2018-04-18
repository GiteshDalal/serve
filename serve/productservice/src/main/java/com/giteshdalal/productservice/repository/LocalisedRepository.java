package com.giteshdalal.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.productservice.model.Localised;

@Repository
public interface LocalisedRepository extends JpaRepository<Localised, Long> {

}