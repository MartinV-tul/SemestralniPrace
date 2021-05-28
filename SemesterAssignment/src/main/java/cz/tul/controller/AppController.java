package cz.tul.controller;


import cz.tul.data.Country;
import cz.tul.data.Town;
import cz.tul.service.CountryService;
import cz.tul.service.MeasurementService;
import cz.tul.service.TownService;
import org.dom4j.rule.Mode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @PostMapping("/saveCountry")
    public ModelAndView saveCountry(@ModelAttribute("country") Country country){
        if(!countryService.exists(country.getCode())){
            countryService.create(country);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }

    @PostMapping("/saveTown")
    public ModelAndView saveTown(@ModelAttribute("town") Town town,@RequestParam String code, @RequestParam String countryName){
        Country country = new Country(code,countryName);
        town.setCountry(country);
        if(!townService.exists(town.getId())){
            townService.create(town);
        }
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/showTownsOfCountry?code="+code+"&name="+countryName);
        return modelAndView;
    }

    @GetMapping("/showTownsOfCountry")
    public ModelAndView showTownsOfCountry(@RequestParam String code,@RequestParam String name, Model model){
        List<Town> towns = townService.getTownsByCountryCode(code);
        model.addAttribute("towns",towns);
        String text = "Towns of " + name;
        model.addAttribute("text",text);
        model.addAttribute("countryName",name);
        model.addAttribute("code",code);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("towns_of_country");
        return modelAndView;
    }

    private class TownMeasurement{
        public TownMeasurement(Integer id, String name, String main, String description, Float actualTemperature, Float windSpeed) {
            this.id = id;
            this.name = name;
            this.main = main;
            this.description = description;
            this.actualTemperature = actualTemperature;
            this.windSpeed = windSpeed;
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

        public Float getActualTemperature() {
            return actualTemperature;
        }

        public void setActualTemperature(Float actualTemperature) {
            this.actualTemperature = actualTemperature;
        }

        public Float getOneDayAverageTemperature() {
            return oneDayAverageTemperature;
        }

        public void setOneDayAverageTemperature(Float oneDayAverageTemperature) {
            this.oneDayAverageTemperature = oneDayAverageTemperature;
        }

        public Float getOneWeekAverageTemperature() {
            return oneWeekAverageTemperature;
        }

        public void setOneWeekAverageTemperature(Float oneWeekAverageTemperature) {
            this.oneWeekAverageTemperature = oneWeekAverageTemperature;
        }

        public Float getTwoWeeksAverageTemperature() {
            return twoWeeksAverageTemperature;
        }

        public void setTwoWeeksAverageTemperature(Float twoWeeksAverageTemperature) {
            this.twoWeeksAverageTemperature = twoWeeksAverageTemperature;
        }

        public Float getWindSpeed() {
            return windSpeed;
        }

        public void setWindSpeed(Float windSpeed) {
            this.windSpeed = windSpeed;
        }

        private Integer id;
        private String name;
        private String main;
        private String description;
        private Float actualTemperature;
        private Float oneDayAverageTemperature;
        private Float oneWeekAverageTemperature;
        private Float twoWeeksAverageTemperature;
        private Float windSpeed;

    }
}
