package cz.tul.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document(collection = "measurement")
public class Measurement {
    public Integer getTownId() {
        return townId;
    }

    public void setTownId(Integer townId) {
        this.townId = townId;
    }

    public Long getTs() {
        return ts;
    }

    public void setTs(Long ts) {
        this.ts = ts;
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

    public Float getTemperature() {
        return temperature;
    }

    public void setTemperature(Float temperature) {
        this.temperature = temperature;
    }

    public Float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(Float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public Float getMaximalTemperature() {
        return maximalTemperature;
    }

    public void setMaximalTemperature(Float maximalTemperature) {
        this.maximalTemperature = maximalTemperature;
    }

    public Float getMinimalTemperature() {
        return minimalTemperature;
    }

    public void setMinimalTemperature(Float minimalTemperature) {
        this.minimalTemperature = minimalTemperature;
    }

    public Integer getPressure() {
        return pressure;
    }

    public void setPressure(Integer pressure) {
        this.pressure = pressure;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }

    public Float getWindSpeed() {
        return windSpeed;
    }

    public void setWindSpeed(Float windSpeed) {
        this.windSpeed = windSpeed;
    }

    public Integer getWindDegree() {
        return windDegree;
    }

    public void setWindDegree(Integer windDegree) {
        this.windDegree = windDegree;
    }

    public Measurement(){

    }
    public Measurement(Integer townId, Long ts, String main, String description, Float temperature, Float feelsLike, Float maximalTemperature, Float minimalTemperature, Integer pressure, Integer humidity, Float windSpeed, Integer windDegree,String townName, String countryName) {
        this.townId = townId;
        this.ts = ts;
        this.main = main;
        this.description = description;
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.maximalTemperature = maximalTemperature;
        this.minimalTemperature = minimalTemperature;
        this.pressure = pressure;
        this.humidity = humidity;
        this.windSpeed = windSpeed;
        this.windDegree = windDegree;
        this.countryName = countryName;
        this.townName = townName;
        this.id = createMeasurementId(townId,ts);
    }

    @Id
    Long id;

    Integer townId;
    Long ts;
    String main;
    String description;
    String townName;
    String countryName;
    Float temperature;
    Float feelsLike;
    Float maximalTemperature;
    Float minimalTemperature;
    Integer pressure;
    Integer humidity;
    Float windSpeed;
    Integer windDegree;

    public static Long createMeasurementId(int townID,long timeStamp){
        return timeStamp*100000000 + townID;
    }

    @Override
    public String toString() {
        return "Measurement ["+ts+", "+main+", "+description+", "+temperature+", "+feelsLike+", "+maximalTemperature+", "+minimalTemperature+", "+pressure+", "+humidity+", "+windSpeed+", "+windDegree+"]";
    }


}
