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
@XmlRootElement(name = "Object")
public class AddressObject {

    @XmlAttribute(name = "AOGUID")
    private String aoGuid;

    @XmlAttribute(name = "FORMALNAME")
    private String formalName;

    @XmlAttribute(name = "REGIONCODE")
    private String regionCode;

    @XmlAttribute(name = "AUTOCODE")
    private String autoCode;

    @XmlAttribute(name = "AREACODE")
    private String areaCode;

    @XmlAttribute(name = "CITYCODE")
    private String cityCode;

    @XmlAttribute(name = "CTARCODE")
    private String ctarCode;

    @XmlAttribute(name = "PLACECODE")
    private String placeCode;

    @XmlAttribute(name = "STREETCODE")
    private String streetCode;

    @XmlAttribute(name = "EXTRCODE")
    private String extrCode;

    @XmlAttribute(name = "SEXTCODE")
    private String sextCode;

    @XmlAttribute(name = "OFFNAME")
    private String offName;

    @XmlAttribute(name = "POSTALCODE")
    private String postalCode;

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

    @XmlAttribute(name = "SHORTNAME")
    private String shortName;

    @XmlAttribute(name = "AOLEVEL")
    private Integer aoLevel;

    @XmlAttribute(name = "PARENTGUID")
    private String parentGuid;

    @XmlAttribute(name = "AOID")
    private String aoId;

    @XmlAttribute(name = "PREVID")
    private String prevId;

    @XmlAttribute(name = "NEXTID")
    private String nextId;

    @XmlAttribute(name = "CODE")
    private String code;

    @XmlAttribute(name = "PLAINCODE")
    private String plainCode;

    @XmlAttribute(name = "ACTSTATUS")
    private Integer actStatus;

    @XmlAttribute(name = "CENTSTATUS")
    private Integer centStatus;

    @XmlAttribute(name = "OPERSTATUS")
    private Integer operStatus;

    @XmlAttribute(name = "CURRSTATUS")
    private Integer currStatus;

    @XmlAttribute(name = "STARTDATE")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate startDate;

    @XmlAttribute(name = "ENDDATE")
    @XmlJavaTypeAdapter(value = LocalDateAdapter.class)
    private LocalDate endDate;

    @XmlAttribute(name = "NORMDOC")
    private String normDoc;

    @XmlAttribute(name = "LIVESTATUS")
    private Integer liveStatus;

}
