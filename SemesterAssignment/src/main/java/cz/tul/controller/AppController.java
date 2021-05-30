package cz.tul.controller;


import cz.tul.data.Country;
import cz.tul.data.Measurement;
import cz.tul.data.Town;
import cz.tul.parser.CSVParser;
import cz.tul.service.CountryService;
import cz.tul.service.MeasurementService;
import cz.tul.service.TownService;
import cz.tul.thread.UpdateThread;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.util.MimeType;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@RestController
public class AppController {

    @Autowired
    private CountryService countryService;

    @Autowired
    private TownService townService;

    @Autowired
    private MeasurementService measurementService;

    @GetMapping("/")
    public ModelAndView viewHomePage(Model model){
        model.addAttribute("countries",countryService.getAllCountries());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }

    @GetMapping("/setUpdateTime")
    public String setUpdateTime(@RequestParam Integer time){
        if(time >=5000){
            UpdateThread.updateTime = time;
            return "Update time successfully set to: " + time+"ms.";
        }
        UpdateThread.updateTime = 5000;
        return  "Update time lower than 5000ms is not allowed. Update time set to 5000ms.";
    }

    @GetMapping("/setExpirationTime")
    public String setExpirationTime(@RequestParam Integer time){
        if(time>=3600){
            measurementService.changeExpirationTime(time);
            return "Expiration time successfully set to: "+time+"s.";
        }
        measurementService.changeExpirationTime(3600);
        return "Expiration time lower then 1 hour is not allowed. Expiration time set to 3600s.";
    }

    @GetMapping("/downloadMeasurementsOfCountry")
    public ResponseEntity<Object> downloadAllMeasurementsOfCountry(@RequestParam String code){
        CSVParser csvParser = new CSVParser();
        StringBuilder csv = csvParser.createCSV(measurementService.getAllMeasurementsOfCountry(code));
        String fileName = "C:\\csvDownload/measurements_"+code+".csv";
        return downloadMeasurements(fileName,csv);
    }

    @GetMapping("/downloadMeasurementsOfTown")
    public ResponseEntity<Object> downloadAllMeasurementsOfTown(@RequestParam Integer townId,@RequestParam String name){
        CSVParser csvParser = new CSVParser();
        StringBuilder csv = csvParser.createCSV(measurementService.getAllMeasurementsOfTown(townId));
        String fileName = "C:\\csvDownload/measurements_"+name+".csv";
        return downloadMeasurements(fileName,csv);
    }

    @GetMapping("/downloadAllMeasurements")
    public ResponseEntity<Object> downloadAllMeasurements(){
        CSVParser csvParser = new CSVParser();
        StringBuilder csv = csvParser.createCSV(measurementService.getAllMeasurements());
        String fileName = "C:\\csvDownload/measurements.csv";
        return downloadMeasurements(fileName,csv);
    }

    private ResponseEntity<Object> downloadMeasurements(String fileName,StringBuilder csv){
        FileWriter writer = null;
        try {
            writer = new FileWriter(fileName);
            writer.write(csv.toString());
            writer.flush();

            File file = new File(fileName);
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Disposition",String.format("attachment;filename=\"%s\"",file.getName()));
            headers.add("Cache-Control","no-cache, no-store, must-revalidate");
            headers.add("Pragma","no-cache");
            headers.add("Expires","0");
            ResponseEntity<Object> responseEntity = ResponseEntity.ok().headers(headers).contentLength(file.length()).contentType(MediaType.parseMediaType("text/csv")).body(resource);
            return responseEntity;
        }catch (Exception e){
            return new ResponseEntity<>("ERROR OCCURRED", HttpStatus.OK);
        }
        finally {
            try {
                if(writer != null) writer.close();
            }
            catch (Exception e){

            }
        }
    }

    @PostMapping("/upload")
    public String upload(@RequestParam(value = "file")MultipartFile file){
        if(file.isEmpty()){
            return "no file selected";
        }

        try {
            InputStream inputStream = file.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            CSVParser csvParser = new CSVParser();
            List<Measurement> measurements = csvParser.createList(reader);
            for (Measurement measurement:measurements) {
                String code = measurement.getCountryCode();
                Integer townId = measurement.getTownId();
                String name = measurement.getTownName();
                Country country = new Country(code,"Country_"+code);
                if(!countryService.exists(code)){
                    countryService.saveOrUpdate(country);
                }
                Town town = new Town(townId,name,country);
                if (!townService.exists(townId)) {
                    townService.saveOrUpdate(town);
                }
            }
            measurementService.saveAllMeasurements(measurements);
        }catch (Exception e)
        {

        }
        return "file selected";
    }


    @GetMapping("/newCountryForm")
    public ModelAndView newCountryForm(Model model){
        Country country = new Country();
        model.addAttribute(country);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_country");
        return modelAndView;
    }

    @GetMapping("/newTownForm")
    public ModelAndView newTownForm(@RequestParam String code,@RequestParam String countryName, Model model){
        Town town = new Town();
        model.addAttribute("town",town);
        model.addAttribute("code",code);
        model.addAttribute("countryName",countryName);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("new_town");
        return modelAndView;
    }

    @GetMapping("/deleteCountry")
    public ModelAndView deleteCountry(@RequestParam String code){
        measurementService.deleteAllMeasurementsOfCountry(code);
        countryService.deleteCountryByCode(code);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/saveCountry")
    public ModelAndView saveCountry(@ModelAttribute("country") Country country){
        if(countryService.exists(country.getCode())){
            List<Town> towns = townService.getTownsByCountryCode(country.getCode());
            country.setTowns(towns);
        }
        countryService.saveOrUpdate(country);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/saveTown")
    public ModelAndView saveTown(@ModelAttribute("town") Town town,@RequestParam String code, @RequestParam String countryName){
        Country country = new Country(code,countryName);
        town.setCountry(country);
        if(townService.exists(town.getId())){
            measurementService.updateTownNameOfMeasurement(town.getName(),town.getId());
        }
        townService.saveOrUpdate(town);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/showTownsOfCountry?code="+code+"&name="+countryName);
        return modelAndView;
    }

    @GetMapping("/deleteTown")
    public ModelAndView deleteTown(@RequestParam String code, @RequestParam String countryName, @RequestParam Integer townId){
        townService.deleteTownById(townId);
        measurementService.deleteAllMeasurementsOfTown(townId);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/showTownsOfCountry?code="+code+"&name="+countryName);
        return modelAndView;
    }

    @GetMapping("/showTownsOfCountry")
    public ModelAndView showTownsOfCountry(@RequestParam String code,@RequestParam String name, Model model){
        List<Town> towns = townService.getTownsByCountryCode(code);
        List<TownMeasurement> townMeasurements = new ArrayList<>();
        if(towns!=null){
            for (Town town:towns) {
                townMeasurements.add(new TownMeasurement(town));
            }
        }
        model.addAttribute("towns",townMeasurements);
        String text = "Towns of " + name;
        model.addAttribute("text",text);
        model.addAttribute("countryName",name);
        model.addAttribute("code",code);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("towns_of_country");
        return modelAndView;
    }

    private class TownMeasurement{
        public TownMeasurement(Town town) {
            this.id = town.getId();
            this.name = town.getName();
            Measurement measurement = measurementService.getLastMeasurementOfTown(town.getId());
            if(measurement != null){
                this.actualTemperature = roundNumber(measurement.getTemperature());
                this.oneDayAverageTemperature = roundNumber(measurementService.oneDayAverage(measurement.getTs(),town.getId()));
                this.oneWeekAverageTemperature = roundNumber(measurementService.oneWeekAverage(measurement.getTs(),town.getId()));
                this.twoWeeksAverageTemperature = roundNumber(measurementService.twoWeeksAverage(measurement.getTs(),town.getId()));
                this.humidity = measurement.getHumidity().toString();
                this.windSpeed = measurement.getWindSpeed().toString();
                this.main = measurement.getMain();
                this.description = measurement.getDescription();
                this.pressure = measurement.getPressure().toString();
            }
            else{
                this.actualTemperature = "N/A";
                this.oneDayAverageTemperature = "N/A";
                this.oneWeekAverageTemperature = "N/A";
                this.twoWeeksAverageTemperature = "N/A";
                this.humidity = "N/A";
                this.windSpeed = "N/A";
                this.main = "N/A";
                this.description = "N/A";
                this.pressure = "N/A";
            }
        }

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getActualTemperature() {
            return actualTemperature;
        }

        public void setActualTemperature(String actualTemperature) {
            this.actualTemperature = actualTemperature;
        }

        public String getOneDayAverageTemperature() {
            return oneDayAverageTemperature;
        }

        public void setOneDayAverageTemperature(String oneDayAverageTemperature) {
            this.oneDayAverageTemperature = oneDayAverageTemperature;
        }

        public String getOneWeekAverageTemperature() {
            return oneWeekAverageTemperature;
        }

        public void setOneWeekAverageTemperature(String oneWeekAverageTemperature) {
            this.oneWeekAverageTemperature = oneWeekAverageTemperature;
        }

        public String getTwoWeeksAverageTemperature() {
            return twoWeeksAverageTemperature;
        }

        public void setTwoWeeksAverageTemperature(String twoWeeksAverageTemperature) {
            this.twoWeeksAverageTemperature = twoWeeksAverageTemperature;
        }

        public String getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(String windSpeed) {
            this.windSpeed = windSpeed;
        }


        public String getHumidity() {
            return humidity;
        }

        public void setHumidity(String humidity) {
            this.humidity = humidity;
        }

        public String getPressure() {
            return pressure;
        }

        public void setPressure(String pressure) {
            this.pressure = pressure;
        }


        private Integer id;
        private String name;
        private String main;
        private String description;
        private String actualTemperature;
        private String oneDayAverageTemperature;
        private String oneWeekAverageTemperature;
        private String twoWeeksAverageTemperature;
        private String windSpeed;
        private String humidity;
        private String pressure;

        private String roundNumber(double d){
            Float roundedNumber = (float) Math.round(d*10)/10;
            return roundedNumber.toString();
        }
    }
}
