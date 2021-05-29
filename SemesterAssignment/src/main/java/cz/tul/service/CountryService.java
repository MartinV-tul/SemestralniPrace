package cz.tul.service;

import cz.tul.data.Country;
import cz.tul.repositories.CountryRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class CountryService {

    @Autowired
    private CountryRepository countryRepository;

    public void saveOrUpdate(Country country){
        countryRepository.save(country);
    }

    public boolean exists(String id){
        return countryRepository.existsById(id);
    }

    public List<Country> getAllCountries(){
        return StreamSupport.stream(countryRepository.findAll().spliterator(),false).collect(Collectors.toList());
    }

    public String getCountryName(String code){
        Optional<Country> optionalCountry = countryRepository.findById(code);
        Country country = optionalCountry.get();
        return country.getCountryName();
    }

    public void deleteCountry(Country country){
        countryRepository.delete(country);
    }

    public void deleteCountryByCode(String code){
        countryRepository.deleteById(code);
    }

    public void deleteAllCountries(){
        countryRepository.deleteAll();
    }
}
