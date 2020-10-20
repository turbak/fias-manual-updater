package ru.digital.league.manualfias.db.dao;

import org.springframework.data.repository.CrudRepository;
import ru.digital.league.manualfias.db.entity.HouseEntity;

public interface HouseEntityDao extends CrudRepository<HouseEntity, String> {
}
