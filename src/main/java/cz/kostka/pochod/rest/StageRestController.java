package cz.kostka.pochod.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dkostka on 2/6/2022.
 */
@RestController
@RequestMapping(StageRestController.ENDPOINT)
public class StageRestController {

    public static final String ENDPOINT = "/stage";
}
