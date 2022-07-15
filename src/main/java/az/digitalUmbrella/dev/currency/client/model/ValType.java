package az.digitalUmbrella.dev.currency.client.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.List;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class ValType {

    @XmlElement(name = "Valute")
    private List<Valute> valutes;

    @XmlAttribute(name = "Type")
    private String type;

}