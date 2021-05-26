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

    private Measurement measurement1 = new Measurement(3077929,1622050364L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement2 = new Measurement(3077928,1622050364L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement3 = new Measurement(3077927,1622050364L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement4 = new Measurement(3077926,1622050364L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement5 = new Measurement(3077925,1622050364L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement6 = new Measurement(3077929,1622050365L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);
    private Measurement measurement7 = new Measurement(3077929,1622050366L,"Clouds","overcast clouds",11.97F,11.34F,12.28F,10.66F,1014,81,5.26F,221);

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
}
