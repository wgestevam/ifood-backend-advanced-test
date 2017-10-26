package com.carlos.ifoodtest.repositories;

import com.carlos.ifoodtest.models.City;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends CrudRepository<City, Long> {

    @Query("select c from City c join c.cityNameSynonyms as cns where cns.synonym = ?1")
    City findBySynonym(String synonym);

    City findByExternalId(Integer externaID);

    @Query(value = "select * from city c where st_distance(c.location::::geography, ST_SetSRID(ST_MakePoint( ?1, ?2),4326)::::geography)/1000 < 15", nativeQuery = true)
    List<City> listCitiesNear(  Double lon, Double lat);

}
