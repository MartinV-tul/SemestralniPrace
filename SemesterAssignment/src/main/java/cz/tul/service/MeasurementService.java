package cz.tul.service;

import cz.tul.data.Measurement;
import cz.tul.repositories.MeasurementRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.query.Criteria;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Measurement> getAllMeasurementsOfTown(int townId){
        return measurementRepository.findAllByTownIdOrderByTsDesc(townId);
    }

    public Measurement getLastMeasurementOfTown(int townId){
        return  measurementRepository.findFirstByTownIdOrderByTsDesc(townId);
    }

    public void saveMeasurement(Measurement measurement){
        measurementRepository.save(measurement);
    }

    public void saveAllMeasurements(List<Measurement> measurements){
        measurementRepository.saveAll(measurements);
    }

    public void deleteAllMeasurements(){
        measurementRepository.deleteAll();
    }

    public void deleteAllMeasurementsOfTown(int townId){
        measurementRepository.deleteAllByTownId(townId);
    }

    public void deleteMeasurement(Measurement measurement){
        measurementRepository.delete(measurement);
    }

    public List<Measurement> getAllMeasurements(){
        return measurementRepository.findAll();
    }

    public double oneDayAverage(Long currentTime,Integer townId ){
        return averageOverTime(currentTime - 86400,townId);
    }

    public double oneWeekAverage(Long currentTime,Integer townId ){
        return averageOverTime(currentTime - 604800,townId);
    }

    public double twoWeeksAverage(Long currentTime,Integer townId ){
        return averageOverTime(currentTime - 1209600,townId);
    }

    private double averageOverTime(Long endTime, Integer townId){
        Aggregation aggregation = Aggregation.newAggregation(Aggregation.match(Criteria.where("townId").is(townId).and("ts").gte(endTime)),Aggregation.group().avg("temperature").as("average"));
        Document document = mongoTemplate.aggregate(aggregation,"measurement", String.class).getRawResults();
        Object[] values = document.values().toArray();
        ArrayList list = (ArrayList) values[0];
        if(list.size() == 0) return 0;
        Document document1 = (Document)list.get(0);
        return document1.getDouble("average");
    }
}
