package cz.tul;


import cz.tul.Main;
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

    private static Country country1 = new Country(1,"Česká Republika");
    private static Country country2 = new Country(2,"Slovensko");

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
        countryService.create(country1);
        countryService.create(country2);

        townService.create(town1);
        townService.create(town2);
        townService.create(town3);
        townService.create(town4);
        townService.create(town5);
        townService.create(town6);

        List<Town> towns1 = townService.getTowns();
        assertEquals("Should be six retrieved towns.", 6, towns1.size());
        List<Town> towns2 = townService.getTownsByCountry(country1);
        assertEquals("Should be three retrieved towns.", 3, towns2.size());
        List<Town> towns3 = townService.getTownsByCountry(country2);
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
        countryService.create(country1);
        countryService.create(country2);

        townService.create(town1);
        townService.create(town2);
        townService.create(town3);
        townService.create(town4);
        townService.create(town5);
        townService.create(town6);

        townService.deleteTown(town4);

        List<Town> towns1 = townService.getTowns();
        assertEquals("Should be five retrieved towns.", 5, towns1.size());
        List<Town> towns2 = townService.getTownsByCountry(country1);
        assertEquals("Should be three retrieved towns.", 3, towns2.size());
        List<Town> towns3 = townService.getTownsByCountry(country2);
        assertEquals("Should be two retrieved towns.", 2, towns3.size());

        countryService.deleteCountry(country2);
        List<Town> towns4 = townService.getTowns();
        assertEquals("Should be three retrieved towns.", 3, towns4.size());
    }

}
