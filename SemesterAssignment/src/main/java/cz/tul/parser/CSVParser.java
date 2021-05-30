package cz.tul.parser;

import cz.tul.data.Measurement;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class CSVParser {
    public StringBuilder createCSV(List<Measurement> measurements){
        StringBuilder csv = new StringBuilder("_class,_id,countryCode,createdAt,description,feelsLike,humidity,main,maximalTemperature,minimalTemperature,pressure,temperature,townId,townName,ts,windDegree,windSpeed");
        for (Measurement measurement:measurements) {
            csv.append("\n");
            csv.append(measurement.toString());
        }
        return csv;
    }

    public List<Measurement> createList(BufferedReader reader){
        List<Measurement> measurements = new ArrayList<>();
        try{
            reader.readLine();
            while (reader.ready()){
                String line = reader.readLine();
                String[] lines = line.split(",");
                if(lines.length != 17) return measurements;
                String code = lines[2];
                String description = lines[4];
                Double feelsLike = Double.parseDouble(lines[5]);
                Integer humidity = Integer.parseInt(lines[6]);
                String main = lines[7];
                Double maxTemp = Double.parseDouble(lines[8]);
                Double minTemp = Double.parseDouble(lines[9]);
                Integer pressure = Integer.parseInt(lines[10]);
                Double temp = Double.parseDouble(lines[11]);
                Integer townId = Integer.parseInt(lines[12]);
                String townName = lines[13];
                Long ts = Long.parseLong(lines[14]);
                Integer windDegree = Integer.parseInt(lines[15]);
                Integer windSpeed = Integer.parseInt(lines[16]);
                Measurement measurement = new Measurement(townId,ts,main,description,temp,feelsLike,maxTemp,minTemp,pressure,humidity,windSpeed,windDegree,townName,code);
                measurements.add(measurement);
            }
            return measurements;
        }catch (Exception e){
            return null;
        }
    }

}
