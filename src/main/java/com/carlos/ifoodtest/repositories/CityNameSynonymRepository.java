package com.carlos.ifoodtest.repositories;

import com.carlos.ifoodtest.models.CityNameSynonym;
import org.springframework.data.repository.CrudRepository;

public interface CityNameSynonymRepository extends CrudRepository<CityNameSynonym, Long> {
    CityNameSynonym findBySynonym(String synonym);
}
