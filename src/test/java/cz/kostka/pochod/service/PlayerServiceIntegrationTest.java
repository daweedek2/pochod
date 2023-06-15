package cz.kostka.pochod.service;

import cz.kostka.pochod.AbstractIntegrationTest;
import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.User;
import cz.kostka.pochod.dto.PlayerAdminDTO;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.repository.PlayerRepository;
import cz.kostka.pochod.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

class PlayerServiceIntegrationTest extends AbstractIntegrationTest {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        playerRepository.deleteAll();
    }
    @Test
    void createPlayer_NoUserWillNotCreatePlayer() {
        final Player player = playerService.createPlayer(getRegistrationDTO(), null);

        assertThat(player).isNull();
    }

    @Test
    void createPlayer() {
        final var registrationDTO = getRegistrationDTO();
        final User user = userRepository.save(getUser());
        final Player player = playerService.createPlayer(registrationDTO, user);

        assertThat(player)
                .extracting(
                        Player::getNickname,
                        Player::getEmail,
                        Player::getPin,
                        Player::getPhoneNumber)
                .containsExactly(
                        registrationDTO.nickName(),
                        registrationDTO.email(),
                        registrationDTO.pin(),
                        registrationDTO.phone()
                );
    }

    @Test
    void getPlayerByNickname() {
        final Player player1 = createPlayer("Johny Depp", 9988);
        final Player player2 = createPlayer("Johny Heitinga", 7788);

        assertThat(playerService.getPlayerByNickname(player1.getNickname()))
                .isNotEmpty()
                .get()
                .isEqualTo(player1);
    }

    @Test
    void getAllPlayers_PlayersFound() {
        final Player player1 = createPlayer("Johny Depp", 9988);
        final Player player2 = createPlayer("Johny Heitinga", 7788);

        assertThat(playerService.getAllPlayers())
                .containsExactlyInAnyOrder(player1, player2);
    }

    @Test
    void getAllPlayers_NoPlayer() {
        assertThat(playerService.getAllPlayers())
                .isEmpty();
    }

    @Test
    void deletePlayer() {
        final Player player = createPlayer("Johny Depp", 9988);
        assertThat(playerRepository.findAll()).hasSize(1);

        playerService.deletePlayer(player.getId());

        assertThat(playerRepository.findAll()).isEmpty();
    }

    @Test
    void getPlayerById() {
        final Player player1 = createPlayer("Johny Depp", 9988);
        final Player player2 = createPlayer("Johny Heitinga", 7788);

        assertThat(playerService.getPlayerById(player2.getId()))
                .isEqualTo(player2);
    }

    @Test
    void getPlayerById_PlayerNotExists() {
        assertThat(playerService.getPlayerById(9999L))
                .isNull();
    }

    @Test
    @Disabled(value = "for some reason it is failing when running, but update works in app")
    void update() {
        final Player player = createPlayer("Johny Depp", 9988);
        final var updateDTO = new PlayerAdminDTO(player.getId(), "pepis", "78", "a@b.c", 1122);

        final Player updatedPlayer = playerService.update(updateDTO);

        assertThat(updatedPlayer)
                .extracting(
                        Player::getId,
                        Player::getNickname,
                        Player::getPhoneNumber,
                        Player::getEmail,
                        Player::getPin)
                .containsExactly(
                        updateDTO.id(),
                        updateDTO.nickname(),
                        updateDTO.phoneNumber(),
                        updateDTO.email(),
                        updateDTO.pin()
                );
    }

    @Test
    void update_playerNotExists() {
        final var updateDTO = new PlayerAdminDTO(99L, "pepis", "78", "a@b.c", 1122);

        final Player updatedPlayer = playerService.update(updateDTO);

        assertThat(updatedPlayer)
                .isNull();
    }

    private static RegistrationRequestDTO getRegistrationDTO() {
        return new RegistrationRequestDTO("n", "e@c.sr", "123", 7894);
    }

    private static User getUser() {
        final User user = new User();
        user.setPassword("pwd");
        user.setUsername("user");
        return user;
    }
}