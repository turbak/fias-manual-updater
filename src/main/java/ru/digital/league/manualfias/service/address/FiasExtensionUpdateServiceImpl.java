package ru.digital.league.manualfias.service.address;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.digital.league.manualfias.db.dao.FiasAddressDao;
import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.db.entity.FiasAddressExtension;
import ru.digital.league.manualfias.db.entity.HouseEntity;

import java.time.LocalDate;

@Slf4j
@RequiredArgsConstructor
@Service
public class FiasExtensionUpdateServiceImpl implements FiasExtensionUpdateService {

	private final FiasAddressDao fiasAddressDao;

	public AddressEntity updateEntity(AddressEntity entity) {
		if (entity.getLiveStatus() == 1) {
			String address = buildAddressText(entity);
			if (needsUpdate(entity, address)) {
				updateAddressTextForEachChild(address, entity);
			}
		} else if (fiasAddressDao.notExistsLiveAddress(entity.getAoGuid())) {
			setLiveStatusInactiveForAllChildren(entity);
		}
		return entity;
	}

	private boolean needsUpdate(AddressEntity entity, String address) {
		FiasAddressExtension fiasAddressExtension = fiasAddressDao.findAddressExtension(entity.getAoGuid());
		if (fiasAddressExtension == null) {
			return true;
		}
		if (!address.equals(fiasAddressExtension.getAddressText())) {
			return true;
		}
		if (entity.getRegionCode() != null && !entity.getRegionCode().equals(fiasAddressExtension.getRegionCode())) {
			return true;
		}
		if (entity.getAoLevel() != null && !entity.getAoLevel().equals(fiasAddressExtension.getAoLevel())) {
			return true;
		}
		if (entity.getLiveStatus() != null && !entity.getLiveStatus().equals(fiasAddressExtension.getLiveStatus())) {
			return true;
		}
		log.debug("No need to update entity {}", entity);
		return false;
	}

	public HouseEntity updateEntity(HouseEntity entity) {
		if (LocalDate.now().isBefore(entity.getEndDate())) {
			FiasAddressExtension extension = fiasAddressDao.findAddressExtension(entity.getAoGuid());
			if (extension == null) {
				log.warn("No address in fias extension for house {}", entity);
				return entity;
			}
			String address = buildHouseAddress(entity, extension.getAddressText());
			fiasAddressDao.createOrUpdateHouse(entity, address);
		} else if (fiasAddressDao.notExistsLiveHouse(entity.getHouseGuid())) {
			fiasAddressDao.setLiveStatusInactive(entity);
		}
		return entity;
	}

	private String buildAddressText(AddressEntity entity) {
		StringBuilder addressText = new StringBuilder();

		addressText.append(entity.getShortName()).append(entity.getShortName().endsWith(".") ? " " : ". ")
				.append(entity.getFormalName());

		while (entity != null) {
			StringBuilder dynamicAddress = new StringBuilder();
			String parentGuid = entity.getParentGuid();
			entity = fiasAddressDao.findParent(parentGuid);
			if (entity != null) {
				if (!entity.getShortName().endsWith(".")) {
					dynamicAddress.append(entity.getShortName()).append(". ");
				} else {
					dynamicAddress.append(entity.getShortName()).append(" ");
				}
				dynamicAddress.append(entity.getFormalName()).append(", ");
				addressText.insert(0, dynamicAddress);
			}
		}
		return addressText.toString();
	}

	private void updateAddressTextForEachChild(String addressText, AddressEntity entity) {
		log.debug("Updating address {} with guid {}", addressText, entity.getAoGuid());
		fiasAddressDao.createOrUpdateAddress(entity, addressText);
		updateHouses(addressText, entity.getAoGuid());
		fiasAddressDao.findChildren(entity.getAoGuid()).forEach(child -> {
			StringBuilder childAddress = new StringBuilder();
			childAddress.append(addressText)
					.append(", ")
					.append(child.getShortName())
					.append(child.getShortName().endsWith(".") ? " " : ". ")
					.append(child.getFormalName());
			updateAddressTextForEachChild(childAddress.toString(), child);
		});
	}

	private void updateHouses(String addressText, String addressGuid) {
		fiasAddressDao.findHousesByAddress(addressGuid).forEach(house -> {
			String houseAddress = buildHouseAddress(house, addressText);
			log.debug("House Address : {}", houseAddress);
			fiasAddressDao.createOrUpdateHouse(house, houseAddress);
		});
	}

	private String buildHouseAddress(HouseEntity houseEntity, String addressText) {
		StringBuilder houseAddress = new StringBuilder();
		houseAddress.append(addressText).append(", ");
		if (houseEntity.getHouseNum() != null) {
			houseAddress.append(houseEntity.getHouseNum());
		}
		if (houseEntity.getBuildNum() != null) {
			houseAddress.append('к').append(houseEntity.getBuildNum());
		}
		if (houseEntity.getStrucNum() != null) {
			houseAddress.append('с').append(houseEntity.getStrucNum());
		}
		return houseAddress.toString();
	}

	private void setLiveStatusInactiveForAllChildren(AddressEntity entity) {
		log.debug("Setting live status to 0 for Address entity {}", entity.getAoGuid());
		fiasAddressDao.setLiveStatusInactive(entity);
		fiasAddressDao.findHousesByAddress(entity.getAoGuid()).forEach(fiasAddressDao::setLiveStatusInactive);
		fiasAddressDao.findChildren(entity.getAoGuid()).forEach(this::setLiveStatusInactiveForAllChildren);
	}
}
