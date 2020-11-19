package ru.digital.league.manualfias.db.dao.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.digital.league.manualfias.db.dao.FiasAddressDao;
import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.db.entity.FiasAddressExtension;
import ru.digital.league.manualfias.db.entity.HouseEntity;

import java.sql.Types;
import java.util.Collections;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class FiasAddressDaoImpl implements FiasAddressDao {

	private final JdbcTemplate jdbcTemplate;

	private final static RowMapper<AddressEntity> ADDRESS_ENTITY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(AddressEntity.class);

	private final static RowMapper<HouseEntity> HOUSE_ENTITY_ROW_MAPPER = BeanPropertyRowMapper.newInstance(HouseEntity.class);

	private final static RowMapper<FiasAddressExtension> FIAS_ADDRESS_EXTENSION_ROW_MAPPER = BeanPropertyRowMapper.newInstance(FiasAddressExtension.class);

	@Value("${fias.schema.name}")
	private String FIAS_SCHEMA_NAME;


	@Override
	public void createOrUpdateHouse(HouseEntity entity, String address) {
		if (entity.getHouseGuid() == null) {
			log.error("NULL guid for house {}, HouseEntity {}", address, entity);
		}
		jdbcTemplate.update("INSERT INTO " + FIAS_SCHEMA_NAME + ".tbfias_extension(guid, address_text, ao_level, region_code, live_status) " +
						"VALUES (?, ?, 100, ?, 1) ON CONFLICT ON CONSTRAINT unique_guid_status DO UPDATE SET address_text = ?, region_code = ?," +
						"live_status = 1, ao_level = 100",
				new Object[]{entity.getHouseGuid(), address, entity.getRegionCode(), address, entity.getRegionCode()},
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.VARCHAR});
	}

	@Override
	public void createOrUpdateAddress(AddressEntity entity, String address) {
		if (entity.getAoGuid() == null) {
			log.error("NULL guid for address {}, AddressEntity {}", address, entity);
		}
		jdbcTemplate.update("INSERT INTO " + FIAS_SCHEMA_NAME + ".tbfias_extension(guid, address_text, ao_level, region_code, live_status) " +
						"VALUES (?, ?, ?, ?, 1) ON CONFLICT ON CONSTRAINT unique_guid_status DO UPDATE SET address_text = ?, ao_level = ?, " +
						"region_code = ?",
				new Object[]{entity.getAoGuid(), address, entity.getAoLevel(), entity.getRegionCode(), address, entity.getAoLevel(), entity.getRegionCode()},
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR, Types.VARCHAR, Types.INTEGER, Types.VARCHAR});
	}

	@Override
	public int setLiveStatusInactive(AddressEntity entity) {
		if (entity == null) {
			return 0;
		}
		boolean needsUpdate = jdbcTemplate.query("SELECT * FROM " + FIAS_SCHEMA_NAME + ".tbfias_extension WHERE live_status = 0 AND guid = ?",
				new Object[]{entity.getAoGuid()}, FIAS_ADDRESS_EXTENSION_ROW_MAPPER).isEmpty();
		if (needsUpdate) {
			log.info("Setting live status = 0 for Address {}", entity.getAoGuid());
			return jdbcTemplate.update("UPDATE " + FIAS_SCHEMA_NAME + ".tbfias_address_extension SET live_status = 0 WHERE guid = ?",
					new Object[]{entity.getAoGuid()}, new int[]{Types.VARCHAR});
		}
		return 0;
	}

	@Override
	public int setLiveStatusInactive(HouseEntity entity) {
		if (entity == null) {
			return 0;
		}
		boolean needsUpdate = jdbcTemplate.query("SELECT * FROM " + FIAS_SCHEMA_NAME + ".tbfias_extension WHERE live_status = 0 AND guid = ?",
				new Object[]{entity.getHouseGuid()}, FIAS_ADDRESS_EXTENSION_ROW_MAPPER).isEmpty();
		if (needsUpdate) {
			log.info("Setting live status = 0 for House {}", entity.getHouseGuid());
			return jdbcTemplate.update("UPDATE " + FIAS_SCHEMA_NAME + ".tbfias_house_extension SET live_status = 0 WHERE guid = ?",
					new Object[]{entity.getHouseGuid()}, new int[]{Types.VARCHAR});
		}
		return 0;
	}

	@Override
	public AddressEntity findParent(String parentGuid) {
		if (parentGuid == null) {
			return null;
		}
		List<AddressEntity> resultList = jdbcTemplate.query("SELECT * FROM " + FIAS_SCHEMA_NAME + ".tbfias_address_obj WHERE ao_guid = ? AND live_status = 1 LIMIT 1",
				new Object[]{parentGuid}, ADDRESS_ENTITY_ROW_MAPPER);
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	@Override
	public List<AddressEntity> findChildren(String guid) {
		if (guid == null) {
			return Collections.emptyList();
		}
		return jdbcTemplate.query("SELECT * FROM " + FIAS_SCHEMA_NAME + ".tbfias_address_obj WHERE parent_guid = ? AND live_status = 1",
				new Object[]{guid}, ADDRESS_ENTITY_ROW_MAPPER);
	}

	@Override
	public List<HouseEntity> findHousesByAddress(String addressGuid) {
		if (addressGuid == null) {
			return Collections.emptyList();
		}
		return jdbcTemplate.query("SELECT ao_guid, start_date, end_date, house_num, house_guid, build_num, struc_num, region_code FROM " + FIAS_SCHEMA_NAME + ".tbfias_house " +
						"WHERE ao_guid = ? AND current_date BETWEEN start_date AND end_date " +
						"GROUP BY house_id, house_guid, ao_guid, start_date, end_date, build_num, struc_num, region_code " +
						"HAVING start_date = MAX(start_date)",
				new Object[]{addressGuid}, HOUSE_ENTITY_ROW_MAPPER);
	}

	@Override
	public FiasAddressExtension findAddressExtension(String addressGuid) {
		if (addressGuid == null) {
			return null;
		}
		List<FiasAddressExtension> resultList = jdbcTemplate.query("SELECT * FROM " + FIAS_SCHEMA_NAME + ".tbfias_address_extension WHERE guid = ? LIMIT 1",
				new Object[]{addressGuid}, FIAS_ADDRESS_EXTENSION_ROW_MAPPER);
		return resultList.isEmpty() ? null : resultList.get(0);
	}

	@Override
	public boolean notExistsLiveAddress(String guid) {
		return jdbcTemplate.query("SELECT ao_guid FROM " + FIAS_SCHEMA_NAME + ".tbfias_address_obj WHERE ao_guid = ? AND live_status = 1",
				new Object[]{guid}, ADDRESS_ENTITY_ROW_MAPPER).isEmpty();
	}

	@Override
	public boolean notExistsLiveHouse(String houseGuid) {
		return jdbcTemplate.query("SELECT house_guid FROM " + FIAS_SCHEMA_NAME + ".tbfias_house " +
				"WHERE house_guid = ? AND current_date BETWEEN start_date AND end_date",
				new Object[]{houseGuid}, HOUSE_ENTITY_ROW_MAPPER).isEmpty();
	}
}
