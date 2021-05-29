package cz.tul;


import cz.tul.data.Country;
import cz.tul.data.Town;
import cz.tul.service.CountryService;
import cz.tul.service.TownService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {Main.class})
public class TownTest {

    @Autowired
    private CountryService countryService;

    @Autowired
    private TownService townService;

    private static Country country1 = new Country("CZ","Česká Republika");
    private static Country country2 = new Country("SK","Slovensko");

    private Town town1 = new Town(1,"Praha",country1);
    private Town town2 = new Town(2,"Brno",country1);
    private Town town3 = new Town(3,"Liberec",country1);

    private Town town4 = new Town(4,"Bratislava",country2);
    private Town town5 = new Town(5,"Košice",country2);
    private Town town6 = new Town(6,"Trenčín",country2);

    public void init(){
        countryService.deleteAllCountries();
    }

    @Test
    public void testGetByCountry(){
        countryService.saveOrUpdate(country1);
        countryService.saveOrUpdate(country2);

        townService.saveOrUpdate(town1);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);

        List<Town> towns1 = townService.getTowns();
        assertEquals("Should be six retrieved towns.", 6, towns1.size());
        List<Town> towns2 = townService.getTownsByCountryCode(country1.getCode());
        assertEquals("Should be three retrieved towns.", 3, towns2.size());
        List<Town> towns3 = townService.getTownsByCountryCode(country2.getCode());
        assertEquals("Should be three retrieved towns.", 3, towns3.size());

        assertEquals("Should be country1.", country1, towns2.get(0).getCountry());
        assertEquals("Should be country1.", country1, towns2.get(1).getCountry());
        assertEquals("Should be country1.", country1, towns2.get(2).getCountry());

        assertEquals("Should be country2.", country2, towns3.get(0).getCountry());
        assertEquals("Should be country2.", country2, towns3.get(1).getCountry());
        assertEquals("Should be country2.", country2, towns3.get(2).getCountry());
    }

    @Test
    public void testDelete(){
        countryService.saveOrUpdate(country1);
        countryService.saveOrUpdate(country2);

        townService.saveOrUpdate(town1);
        townService.saveOrUpdate(town2);
        townService.saveOrUpdate(town3);
        townService.saveOrUpdate(town4);
        townService.saveOrUpdate(town5);
        townService.saveOrUpdate(town6);

        townService.deleteTown(town4);

        List<Town> towns1 = townService.getTowns();
        assertEquals("Should be five retrieved towns.", 5, towns1.size());
        List<Town> towns2 = townService.getTownsByCountryCode(country1.getCode());
        assertEquals("Should be three retrieved towns.", 3, towns2.size());
        List<Town> towns3 = townService.getTownsByCountryCode(country2.getCode());
        assertEquals("Should be two retrieved towns.", 2, towns3.size());

        townService.deleteByCountryCode(country2.getCode());
        List<Town> towns4 = townService.getTowns();
        assertEquals("Should be three retrieved towns.", 3, towns4.size());
    }

}
