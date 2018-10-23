
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.LegalText;

@Repository
public interface LegalTextRepository extends JpaRepository<LegalText, Integer> {

	//C-Level--------------------------------------------------------------------
	//An actor who is authenticated as an administrator must be able to:
	//Display a dashboard with the following information:

	// A table with the number of times that each legal text’s been referenced:
	@Query("select l, l.trips.size from LegalText l group by l.id")
	Collection<Object[]> timesLegalTextReferenced();

	@Query("select l from LegalText l where l.definitive=1")
	Collection<LegalText> findAllFinal();

}
