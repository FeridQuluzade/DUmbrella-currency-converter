package az.digitalUmbrella.dev.currency.repository;

import az.digitalUmbrella.dev.currency.model.Currency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Long> {

    List<Currency> findAllByCode(String code);

}
