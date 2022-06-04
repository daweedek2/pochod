package cz.kostka.pochod.service;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    public Player createPlayer(final RegistrationRequestDTO dto, final User user) {
        if (user == null) {
            return null;
        }

        return playerRepository.save(new Player(dto.getNickName(), dto.getEmail(), dto.getPin(), user));
    }

    public Optional<Player> getPlayerByNickname(final String nickname) {
        return playerRepository.getPlayerByNickname(nickname);
    }
}
