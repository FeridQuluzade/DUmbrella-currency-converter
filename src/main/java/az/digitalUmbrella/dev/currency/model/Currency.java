package az.digitalUmbrella.dev.currency.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@ToString
@Entity
public class Currency extends BaseEntity {

    private String code;
    private String name;
    private String nominal;
    private BigDecimal value;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Currency)) return false;
        Currency currency = (Currency) o;
        return Objects.equals(getCode(), currency.getCode()) &&
                Objects.equals(getValue(), currency.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCode(), getValue());
    }

}
