package cz.kostka.pochod.mapper;

import cz.kostka.pochod.domain.GameInfo;
import cz.kostka.pochod.dto.GameInfoDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by dkostka on 6/20/2022.
 */
@Mapper
public interface GameInfoMapper {

    GameInfoMapper INSTANCE = Mappers.getMapper(GameInfoMapper.class);

    GameInfoDTO gameInfoToDto(final GameInfo gameInfo);
}
