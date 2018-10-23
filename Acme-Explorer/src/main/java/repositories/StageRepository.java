package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Stage;

@Repository
public interface StageRepository extends JpaRepository<Stage, Integer> {
	
	@Query("select t.stages from Trip t where t.id=?1")
	Collection<Stage> findByTrip(int tripId);

}
