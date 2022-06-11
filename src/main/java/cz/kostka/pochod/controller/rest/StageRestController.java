package cz.kostka.pochod.controller.rest;

import cz.kostka.pochod.api.StageApi;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dkostka on 2/6/2022.
 */
@RestController
@RequestMapping(StageRestController.ENDPOINT)
public class StageRestController {
    public static final String ENDPOINT = "api/stage";

    private final StageApi stageApi;

    public StageRestController(final StageApi stageApi) {
        this.stageApi = stageApi;
    }
}
