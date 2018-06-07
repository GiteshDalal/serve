package com.giteshdalal.productservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import com.giteshdalal.productservice.model.LocalisedEntry;
import com.giteshdalal.productservice.model.LocalisedEntryId;

@Repository
public interface LocalisedEntryRepository extends JpaRepository<LocalisedEntry, LocalisedEntryId>, QuerydslPredicateExecutor<LocalisedEntry> {

}