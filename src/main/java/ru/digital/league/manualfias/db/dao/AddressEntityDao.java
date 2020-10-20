package ru.digital.league.manualfias.db.dao;

import org.springframework.data.repository.CrudRepository;
import ru.digital.league.manualfias.db.entity.AddressEntity;

public interface AddressEntityDao extends CrudRepository<AddressEntity, String> {
}
