
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.CreditCard;

@Repository
public interface CreditCardRepository extends JpaRepository<CreditCard, Integer> {

	@Query("select cc from CreditCard cc where cc.explorer.id = ?1")
	Collection<CreditCard> findByExplorerId(int explorerId);

	@Query("select cc from CreditCard cc where cc.sponsor.id = ?1")
	CreditCard findBySponsorId(int sponsorId);

	@Query("select cc.number from CreditCard cc")
	Collection<String> findAllNumbers();
}
