package cz.tul;


import cz.tul.data.Country;
import cz.tul.data.Measurement;
import cz.tul.data.Town;
import cz.tul.service.CountryService;
import cz.tul.service.MeasurementService;
import cz.tul.service.TownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableJpaRepositories("cz.tul.repositories")
@EnableMongoRepositories("cz.tul.repositories")
public class Main {

    @Bean
    public CountryService countryService(){
        return new CountryService();
    }

    @Bean
    public MeasurementService measurementService(){return new MeasurementService();}



    public static void main(String[] args){
        SpringApplication app = new SpringApplication(Main.class);
        ApplicationContext context = app.run(args);
    }
}
