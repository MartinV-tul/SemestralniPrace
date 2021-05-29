package cz.tul;

import com.fasterxml.jackson.databind.JsonSerializer;
import cz.tul.data.Measurement;
import cz.tul.parser.JsonParser;
import cz.tul.service.MeasurementService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeasurementTest {
    @Autowired
    private MeasurementService measurementService;

    private Measurement measurement1 = new Measurement(3067696,1622050369L,"Clouds","overcast clouds",11.97,11.34,12.28,10.66,1014,81,5,221,"prague","CZ");
    private Measurement measurement2 = new Measurement(3067696,1622050370L,"Clouds","overcast clouds",5.97,11.34,12.28,10.66,1014,81,5,221,"prague","CZ");
    private Measurement measurement3 = new Measurement(3067696,1622050367L,"Clouds","overcast clouds",3.97,11.34,12.28,10.66,1014,81,5,221,"prague","CZ");
    private Measurement measurement4 = new Measurement(3067696,1621550366L,"Clouds","overcast clouds",8.97,11.34,12.28,10.66,1014,81,5,221,"prague","CZ");
    private Measurement measurement5 = new Measurement(3067696,1621050365L,"Clouds","overcast clouds",20.97,11.34,12.28,10.66,1014,81,5,221,"prague","CZ");
    private Measurement measurement6 = new Measurement(3078610,1622050364L,"Clouds","overcast clouds",7.97,11.34,12.28,10.66,1014,81,5,221,"brno","CZ");
    private Measurement measurement7 = new Measurement(3078610,1622050363L,"Clouds","overcast clouds",9.97,11.34,12.28,10.66,1014,81,5,221,"brno","CZ");

    @Before
    public void init(){
        measurementService.deleteAllMeasurements();
    }

    @Test
    public void testSave(){
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        measurements.add(measurement3);
        measurements.add(measurement4);
        measurements.add(measurement5);
        measurements.add(measurement6);
        measurements.add(measurement7);

        measurementService.saveAllMeasurements(measurements);

        List<Measurement> measurements1 = measurementService.getAllMeasurements();
        assertEquals("Seven measurements should have been saved and retrieved",7,measurements1.size());

    }

    @Test
    public void testCalculateAverage(){
        List<Measurement> measurements = new ArrayList<>();
        measurements.add(measurement1);
        measurements.add(measurement2);
        measurements.add(measurement3);
        measurements.add(measurement4);
        measurements.add(measurement5);
        measurements.add(measurement6);
        measurements.add(measurement7);

        measurementService.saveAllMeasurements(measurements);

        double d = measurementService.oneDayAverage(1622050364L,3077929);
        System.out.println(d);
    }

    @Test
    public void testGetWeatherData(){
        String urlString = "https://api.openweathermap.org/data/2.5/group?id=524901,703448,2643743,3067696,3077929,3071961,3078610,3068799,3077318&appid=ab9635a72d36ec4ce25bd37c0f200ba9&units=metric";
        try {
            StringBuilder result = new StringBuilder();
            URL url = new URL(urlString);
            URLConnection conn = url.openConnection();
            BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while((line = rd.readLine()) != null){
                result.append(line);
            }
            rd.close();
            JsonParser parser = new JsonParser();
            ArrayList<Measurement> measurements = parser.getMeasurementsFromJson(result);
            System.out.println(measurements);
        }
        catch (Exception e){
            System.out.println(e.toString());
        }

    }
}
