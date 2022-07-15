package az.digitalUmbrella.dev.currency.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@ToString
@Getter
@Setter
@Table(name = "roles")
@Entity
public class Role extends BaseEntity {

    @Column
    private String name;

    @Column
    private String description;

}
