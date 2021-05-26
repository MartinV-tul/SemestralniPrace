package cz.tul.repositories;

import cz.tul.data.Country;
import cz.tul.data.Town;
import cz.tul.data.TownID;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public interface TownRepository extends CrudRepository<Town, Integer> {

    @Query(value = "select * from town where country_id = ?1",nativeQuery = true)
    public List<Town> findByCountryId(Integer country_id);

    @Transactional
    @Modifying
    @Query("delete from Town as t where t.id=:id")
    public void deleteTownById(@Param("id") Integer id);

    @Transactional
    @Modifying
    @Query("delete from Town as t where t.country.id=:country_id")
    public void  deleteByCountryName(@Param("country_id") Integer country_id);
}
