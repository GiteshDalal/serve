package com.giteshdalal.counterservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.giteshdalal.counterservice.model.LocalisedEntry;
import com.giteshdalal.counterservice.model.LocalisedEntryId;

@Repository
public interface LocalisedEntryRepository extends JpaRepository<LocalisedEntry, LocalisedEntryId> {

}
