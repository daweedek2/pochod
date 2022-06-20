package cz.kostka.pochod.mapper;

import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.StageAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by dkostka on 6/15/2022.
 */
@Mapper
public interface StageMapper {

    StageMapper INSTANCE = Mappers.getMapper(StageMapper.class);

    StageAdminDTO stageToDTO(final Stage stage);
}
