package cz.tul.service;

import cz.tul.data.Measurement;
import cz.tul.repositories.MeasurementRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class MeasurementService {

    @Autowired
    private MeasurementRepository measurementRepository;

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
}
