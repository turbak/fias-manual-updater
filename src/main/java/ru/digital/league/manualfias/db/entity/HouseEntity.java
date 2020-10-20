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
@Table(name = "tbfias_house", schema = "fias")
public class HouseEntity {

    @Id
    @Column(name = "house_id")
    private String houseId;
    @Column(name = "postal_code")
    private String postalCode;
    @Column(name = "region_code")
    private String regionCode;
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
    @Column(name = "house_num")
    private String houseNum;
    @Column(name = "est_status")
    private Integer estStatus;
    @Column(name = "build_num")
    private String buildNum;
    @Column(name = "struc_num")
    private String strucNum;
    @Column(name = "str_status")
    private Integer strStatus;
    @Column(name = "house_guid")
    private String houseGuid;
    @Column(name = "ao_guid")
    private String aoGuid;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;
    @Column(name = "stat_status")
    private Integer statStatus;
    @Column(name = "norm_doc")
    private String normDoc;
    @Column(name = "counter")
    private Integer counter;
    @Column(name = "cadnum")
    private String cadNum;
    @Column(name = "divtype")
    private Integer divType;
}
