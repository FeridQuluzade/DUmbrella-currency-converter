package az.digitalUmbrella.dev.currency.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class CurrencyDetail extends BaseEntity {

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "currency_detail_id")
    @ToString.Exclude
    private List<Currency> currencies;
    private String type;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CurrencyDetail)) return false;
        CurrencyDetail that = (CurrencyDetail) o;
        return Objects.equals(getCurrencies(), that.getCurrencies()) &&
                Objects.equals(getType(), that.getType());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCurrencies(), getType());
    }

}
