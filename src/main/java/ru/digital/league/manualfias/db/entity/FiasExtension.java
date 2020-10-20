package ru.digital.league.manualfias.db.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@Data
@MappedSuperclass
public abstract class FiasExtension {

    @Id
    @Column(name = "guid")
    private String guid;

    @Column(name = "address_text")
    private String addressText;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "ao_level")
    private Integer aoLevel;

    @Column(name = "region_code")
    private String regionCode;

    @Column(name = "live_status")
    private Integer liveStatus;

}
