package ru.digital.league.manualfias.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.digital.league.manualfias.db.entity.HouseEntity;
import ru.digital.league.manualfias.xml.pojo.House;

import java.time.Instant;

@Slf4j
@RequiredArgsConstructor
@Component
public class HouseItemProcessor implements ItemProcessor<House, HouseEntity> {

    private final ModelMapper modelMapper;

    @Override
    public HouseEntity process(House item) throws Exception {
        if (item.getStrucNum() != null && item.getStrucNum().length() > 10) {
            log.warn("House item {} contains field strucNum that violates field length constraint", item);
            return null;
        }
        if (item.getBuildNum() != null && item.getBuildNum().length() > 10) {
            log.warn("House item {} contains field buildNum that violates field length constraint", item);
            return null;
        }
        if (item.getHouseNum() != null && item.getHouseNum().length() > 20) {
            log.warn("House item {} contains field houseNum that violates field length constraint", item);
            return null;
        }
        if (item.getCadNum() != null && item.getCadNum().length() > 100) {
            log.warn("House item {} contains field cadNum that violates field length constraint", item);
            return null;
        }
        log.info("house entity {}", item);
        return modelMapper.map(item, HouseEntity.class);
    }

}
