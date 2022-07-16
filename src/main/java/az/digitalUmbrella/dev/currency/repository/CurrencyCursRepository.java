package az.digitalUmbrella.dev.currency.repository;

import az.digitalUmbrella.dev.currency.model.CurrencyCurs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface CurrencyCursRepository extends JpaRepository<CurrencyCurs, Long> {

    Optional<CurrencyCurs> findCurrencyCursByRefreshDate(LocalDate date);

}
