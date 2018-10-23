
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.SurvivalClass;

@Repository
public interface SurvivalClassRepository extends JpaRepository<SurvivalClass, Integer> {

	@Query("select t.survivalClasses from Application a join a.trip t where a.explorer.id=?1 and a.trip.startDate>CURRENT_DATE and a.status=3 and a.trip.id = t.id group by t.id")
	Collection<SurvivalClass> findAvailableToEnrol(int explorerId);
}
