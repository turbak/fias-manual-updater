package ru.digital.league.manualfias.service.address;

import org.springframework.stereotype.Service;
import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.db.entity.HouseEntity;

@Service
public interface FiasExtensionUpdateService {

	AddressEntity updateEntity(AddressEntity entity);

	HouseEntity updateEntity(HouseEntity entity);

}
