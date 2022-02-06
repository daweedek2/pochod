package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.dto.RegistrationResponseDTO;
import cz.kostka.pochod.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by dkostka on 2/6/2022.
 */
@Service
public class PlayerService {

    private final PlayerRepository playerRepository;

    @Autowired
    public PlayerService(final PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    public Player createPlayer(final String nickname, final Integer phoneNumber) {
        if (playerRepository.getPlayerByNickname(nickname).isPresent()) {
            return null;
        }

        return playerRepository.save(new Player(nickname, phoneNumber));
    }
}
