package az.digitalUmbrella.dev.currency.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class CurrencyCurs extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "currency_curs_id")
    @ToString.Exclude
    @Column(nullable = false)
    private List<CurrencyDetail> currencyDetails;

    @Column(nullable = false)
    private LocalDate refreshDate;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyCurs)) return false;
        CurrencyCurs that = (CurrencyCurs) o;
        return Objects.equals(getCurrencyDetails(),
                that.getCurrencyDetails()) && Objects.equals(getRefreshDate(),
                that.getRefreshDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencyDetails(), getRefreshDate());
    }

    public boolean isVacation(LocalDate date) {
        return !date.equals(refreshDate);
    }

}
