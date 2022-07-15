package az.digitalUmbrella.dev.currency.client.model;

import lombok.Data;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@Data
@XmlRootElement(name = "ValCurs")
@XmlAccessorType(XmlAccessType.FIELD)
public class ValCurs {

    @XmlElement(name = "ValType")
    private List<ValType> valTypes;

    @XmlAttribute(name = "Date")
    private String date;

    @XmlAttribute(name = "Name")
    private String name;

    @XmlAttribute(name = "Description")
    private String description;

}