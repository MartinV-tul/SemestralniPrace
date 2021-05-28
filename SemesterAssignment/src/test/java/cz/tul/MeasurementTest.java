package cz.tul;

import cz.tul.data.Measurement;
import cz.tul.service.MeasurementService;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class MeasurementTest {
    @Autowired
    private MeasurementService measurementService;

    private Measurement measurement1 = new Measurement(3077929,1622050369L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"prague","CZ");
    private Measurement measurement2 = new Measurement(3077929,1622050368L,"Clouds","overcast clouds",5.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"prague","CZ");
    private Measurement measurement3 = new Measurement(3077929,1622050367L,"Clouds","overcast clouds",3.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"prague","CZ");
    private Measurement measurement4 = new Measurement(3077929,1622050366L,"Clouds","overcast clouds",8.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"prague","CZ");
    private Measurement measurement5 = new Measurement(3077929,1621050365L,"Clouds","overcast clouds",20.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"prague","CZ");
    private Measurement measurement6 = new Measurement(3077928,1622050364L,"Clouds","overcast clouds",7.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"brno","CZ");
    private Measurement measurement7 = new Measurement(3077928,1622050363L,"Clouds","overcast clouds",9.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221,"brno","CZ");

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

        double d = measurementService.oneDayAverage(1623050364L,3077929);
        System.out.println(d);
    }
}
