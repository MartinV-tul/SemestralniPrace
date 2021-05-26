package cz.tul.service;


import cz.tul.data.Country;
import cz.tul.data.Town;
import cz.tul.repositories.TownRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class TownService {

    @Autowired
    private TownRepository townRepository;

    public List<Town> getTowns(){
        return StreamSupport.stream(townRepository.findAll().spliterator(), false).collect(Collectors.toList());
    }

    public void create(Town town){
        townRepository.save(town);
    }


    public List<Town> getTownsByCountry(Country country){
        if(country == null)return null;

        List<Town> towns = townRepository.findByCountryId(country.getId());
        if(towns.size() == 0) return null;

        return towns;
    }

    @Transactional
    public void deleteTown(Town town){
        townRepository.deleteTownById(town.getId());
    }

    public void deleteAllTowns(){
        townRepository.deleteAll();
    }
}
