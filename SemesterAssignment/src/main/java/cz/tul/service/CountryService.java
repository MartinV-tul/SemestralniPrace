package cz.tul.service;

import cz.tul.data.Country;
import cz.tul.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public void create(Country country){
        countryRepository.save(country);
    }

    public boolean exists(Integer id){
        return countryRepository.existsById(id);
    }

    public List<Country> getAllCountries(){
        return StreamSupport.stream(countryRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }

    public void deleteAllCountries(){
        countryRepository.deleteAll();
    }
}
