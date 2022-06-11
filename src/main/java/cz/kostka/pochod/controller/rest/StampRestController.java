package cz.kostka.pochod.controller.rest;

import cz.kostka.pochod.api.StampApi;
import cz.kostka.pochod.dto.StampRequestDTO;
import cz.kostka.pochod.dto.StampResultDTO;
import cz.kostka.pochod.enums.StampSubmitStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dkostka on 2/6/2022.
 */
@RestController
public class StampRestController {

    private final StampApi stampApi;

    public StampRestController(final StampApi stampApi) {
        this.stampApi = stampApi;
    }

    @PostMapping("api/submitStamp")
    public ResponseEntity<StampResultDTO> submitStamp(@RequestBody final StampRequestDTO stampRequestDTO) {
        final var result = stampApi.submitStamp(stampRequestDTO);

        if (result.getStampSubmitStatus() == StampSubmitStatus.REJECTED) {
            return ResponseEntity.badRequest().body(result);
        }

        return ResponseEntity.ok(result);
    }
}
