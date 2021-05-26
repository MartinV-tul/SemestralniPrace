package cz.tul;

import cz.tul.Main;
import cz.tul.data.Country;
import cz.tul.data.Town;
import cz.tul.service.CountryService;
import cz.tul.service.TownService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private TownService townService;

    private Country country1 = new Country(1,"Česká Republika");
    private Country country2 = new Country(2,"Slovensko");


    @Before
    public void init() {
        //townService.deleteAllTowns();
        countryService.deleteAllCountries();
    }

    @Test
    public void testCreateCountry(){
        countryService.create(country1);
        countryService.create(country2);

        Town town1 = new Town(1,"Praha",country1);
        Town town2 = new Town(2,"Brno",country1);
        Town town3 = new Town(3,"Liberec",country1);
        Town town4 = new Town(4,"Bratislava",country2);
        Town town5 = new Town(5,"Kosice",country2);
        Town town6 = new Town(6,"Trencin",country2);

        townService.create(town1);
        townService.create(town2);
        townService.create(town3);
        townService.create(town4);
        townService.create(town5);
        townService.create(town6);

        List<Town> towns = townService.getTownsByCountry(country1);
        System.out.println(towns);

        countryService.deleteCountry(country2);
    }

}
