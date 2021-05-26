package cz.tul.repositories;

import cz.tul.data.Measurement;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface MeasurementRepository extends MongoRepository<Measurement,Long> {
    List<Measurement> findAllByTownIdOrderByTsDesc(int townId);

    Measurement findFirstByTownIdOrderByTsDesc(int townId);

    void deleteAllByTownId(int townId);
}
