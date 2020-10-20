package ru.digital.league.manualfias.xml.pojo;

import lombok.Data;
import ru.digital.league.manualfias.xml.adapter.LocalDateAdapter;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "House")
public class House {

    @XmlAttribute(name = "POSTALCODE")
    private String postalCode;

    @XmlAttribute(name = "REGIONCODE")
    private String regionCode;

    @XmlAttribute(name = "IFNSFL")
    private String ifnsfl;

    @XmlAttribute(name = "TERRIFNSFL")
    private String terrIfnsfl;

    @XmlAttribute(name = "IFNSUL")
    private String ifnsul;

    @XmlAttribute(name = "TERRIFNSUL")
    private String terrIfnsul;

    @XmlAttribute(name = "OKATO")
    private String okato;

    @XmlAttribute(name = "OKTMO")
    private String oktmo;

    @XmlAttribute(name = "UPDATEDATE")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate updateDate;

    @XmlAttribute(name = "HOUSENUM")
    private String houseNum;

    @XmlAttribute(name = "ESTSTATUS")
    private Integer estStatus;

    @XmlAttribute(name = "BUILDNUM")
    private String buildNum;

    @XmlAttribute(name = "STRUCNUM")
    private String strucNum;

    @XmlAttribute(name = "STRSTATUS")
    private Integer strStatus;

    @XmlAttribute(name = "HOUSEID")
    private String houseId;

    @XmlAttribute(name = "HOUSEGUID")
    private String houseGuid;

    @XmlAttribute(name = "AOGUID")
    private String aoGuid;

    @XmlAttribute(name = "STARTDATE")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlAttribute(name = "ENDDATE")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate endDate;

    @XmlAttribute(name = "STATSTATUS")
    private Integer statStatus;

    @XmlAttribute(name = "NORMDOC")
    private String normDoc;

    @XmlAttribute(name = "COUNTER")
    private Integer counter;

    @XmlAttribute(name = "CADNUM")
    private String cadNum;

    @XmlAttribute(name = "DIVTYPE")
    private Integer divType;

}
