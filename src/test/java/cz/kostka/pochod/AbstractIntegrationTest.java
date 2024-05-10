package cz.kostka.pochod;

import cz.kostka.pochod.domain.Player;
import cz.kostka.pochod.domain.Stage;
import cz.kostka.pochod.dto.RegistrationRequestDTO;
import cz.kostka.pochod.dto.StageCreationDTO;
import cz.kostka.pochod.service.RegistrationService;
import cz.kostka.pochod.service.StageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by dkostka on 7/10/2022.
 */
@SpringBootTest
@Transactional
@Sql(scripts = "classpath:/test-data.sql")
public class AbstractIntegrationTest {
    @Autowired
    private StageService stageService;
    @Autowired
    private RegistrationService registrationService;

    public Player createPlayer(final String nickName, final int pin) {
        return registrationService
                .register(new RegistrationRequestDTO(nickName, "dummy@email.cz", "112233", pin))
                .getRegisteredPlayer();
    }

    public Stage createStage(final String name, final int number, final String pin) {
        return stageService.create(new StageCreationDTO(name, number, null, pin, null, null, 2024, 1.5));
    }
}
