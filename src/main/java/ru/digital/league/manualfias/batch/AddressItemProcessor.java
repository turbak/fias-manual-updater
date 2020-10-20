package ru.digital.league.manualfias.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;
import ru.digital.league.manualfias.db.entity.AddressEntity;
import ru.digital.league.manualfias.xml.pojo.AddressObject;

@Slf4j
@RequiredArgsConstructor
@Component
public class AddressItemProcessor implements ItemProcessor<AddressObject, AddressEntity> {

    private final ModelMapper modelMapper;

    @Override
    public AddressEntity process(AddressObject item) throws Exception {
        if (item.getOffName() != null && item.getOffName().length() > 120) {
            log.warn("Item name {} violates field length constraint (120)", item.getOffName());
            return null;
        }
        if (item.getFormalName() != null && item.getFormalName().length() > 120) {
            log.warn("Item name {} violates field length constraint (120)", item.getFormalName());
            return null;
        }
        if (item.getNormDoc() != null && item.getNormDoc().length() > 36) {
            log.warn("Item name {} violates field length constraint (36)", item.getNormDoc());
            return null;
        }
        if (item.getShortName() != null && item.getShortName().length() > 10) {
            log.warn("Item name {} violates field length constraint (10)", item.getShortName());
            return null;
        }
        log.info("address entity {}", item);
        return modelMapper.map(item, AddressEntity.class);
    }

}
