package cz.kostka.pochod.mapper;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.PlayerAdminDTO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * Created by dkostka on 7/2/2022.
 */
@Mapper
public interface PlayerMapper {

    PlayerMapper INSTANCE = Mappers.getMapper(PlayerMapper.class);

    PlayerAdminDTO playerToDTO(final Player player);
}
