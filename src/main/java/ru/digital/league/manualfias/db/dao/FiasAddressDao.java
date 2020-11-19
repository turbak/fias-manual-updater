package ru.digital.league.manualfias.db.dao;

import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.db.entity.FiasAddressExtension;
import ru.digital.league.manualfias.db.entity.HouseEntity;

import java.util.List;

public interface FiasAddressDao {

	void createOrUpdateHouse(HouseEntity entity, String address);

	void createOrUpdateAddress(AddressEntity entity, String address);

	int setLiveStatusInactive(AddressEntity entity);

	int setLiveStatusInactive(HouseEntity entity);

	AddressEntity findParent(String parentGuid);

	List<AddressEntity> findChildren(String guid);

	List<HouseEntity> findHousesByAddress(String addressGuid);

	FiasAddressExtension findAddressExtension(String addressGuid);

	boolean notExistsLiveAddress(String guid);

	boolean notExistsLiveHouse(String houseGuid);

}
