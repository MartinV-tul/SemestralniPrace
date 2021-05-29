package cz.tul;

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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CountryTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private TownService townService;

    private Country country1 = new Country("CZ","Česká Republika");
    private Country country2 = new Country("SK","Slovensko");


    @Before
    public void init() {
        //townService.deleteAllTowns();
        countryService.deleteAllCountries();
    }

    @Test
    public void testCreateCountry(){
        countryService.saveOrUpdate(country1);
        countryService.saveOrUpdate(country2);

        Town town1 = new Town(1,"Praha",country1);
        Town town2 = new Town(2,"Brno",country1);
        Town town3 = new Town(3,"Liberec",country1);
        Town town4 = new Town(4,"Bratislava",country2);
        Town town5 = new Town(5,"Kosice",country2);
        Town town6 = new Town(6,"Trencin",country2);

        townService.saveOrUpdate(town1);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);

        List<Town> towns = townService.getTownsByCountryCode(country1.getCode());
        System.out.println(towns);

        countryService.deleteCountry(country2);
    }

}
