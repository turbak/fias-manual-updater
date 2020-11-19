package ru.digital.league.manualfias.db.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "tbfias_address_extension", schema = "fias_reserve")
public class FiasAddressExtension extends FiasExtension {
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
	@Column(name = "version_id")
	private Long versionId;
	@Column(name = "region_code")
	private String regionCode;
	@Column(name = "live_status")
	private Integer liveStatus;
}
