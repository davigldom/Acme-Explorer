
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Curriculum;

@Repository
public interface CurriculumRepository extends JpaRepository<Curriculum, Integer> {

	//B-level----------------------------------------------------------------

	//Requirement 30.1 and 31.1
	@Query("select c from Curriculum c where c.ranger.id=?1")
	Curriculum findByRangerId(int rangerId);
	
	@Query("select c from Curriculum c where c.personalRecord.id=?1")
	Curriculum findByPersonalRecordId(int personalRecordId);
	
	@Query("select c.ticker from Curriculum c where c.ticker like CONCAT('%',?1,'%')")
	Collection<String> findTickersLike(String ticker);

}
