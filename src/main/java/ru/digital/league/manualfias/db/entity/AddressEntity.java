package ru.digital.league.manualfias.db.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
@Table(name = "tbfias_address_obj", schema = "fias_reserve")
public class AddressEntity {

    @Id
    @Column(name = "ao_id")
    private String aoId;
    @Column(name = "ao_guid")
    private String aoGuid;
    @Column(name = "formal_name")
    private String formalName;
    @Column(name = "region_code")
    private String regionCode;
    @Column(name = "auto_code")
    private String autoCode;
    @Column(name = "area_code")
    private String areaCode;
    @Column(name = "city_code")
    private String cityCode;
    @Column(name = "ctar_code")
    private String ctarCode;
    @Column(name = "place_code")
    private String placeCode;
    @Column(name = "street_code")
    private String streetCode;
    @Column(name = "extr_code")
    private String extrCode;
    @Column(name = "sext_code")
    private String sextCode;
    @Column(name = "off_name")
    private String offName;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "ifnsfl")
    private String ifnsfl;
    @Column(name = "terrifnsfl")
    private String terrIfnsfl;
    @Column(name = "ifnsul")
    private String ifnsul;
    @Column(name = "terrifnsul")
    private String terrIfnsul;
    @Column(name = "okato")
    private String okato;
    @Column(name = "oktmo")
    private String oktmo;
    @Column(name = "update_date")
    private LocalDate updateDate;
    @Column(name = "short_name")
    private String shortName;
    @Column(name = "ao_level")
    private Integer aoLevel;
    @Column(name = "parent_guid")
    private String parentGuid;
    @Column(name = "prev_id")
    private String prevId;
    @Column(name = "next_id")
    private String nextId;
    @Column(name = "code")
    private String code;
    @Column(name = "plain_code")
    private String plainCode;
    @Column(name = "act_status")
    private Integer actStatus;
    @Column(name = "cent_status")
    private Integer centStatus;
    @Column(name = "oper_status")
    private Integer operStatus;
    @Column(name = "curr_status")
    private Integer currStatus;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "norm_doc")
    private String normDoc;
    @Column(name = "live_status")
    private Integer liveStatus;
}
