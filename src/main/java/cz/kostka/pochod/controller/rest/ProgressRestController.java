package cz.kostka.pochod.controller.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by dkostka on 2/6/2022.
 */
@RestController
@RequestMapping(ProgressRestController.ENDPOINT)
public class ProgressRestController {

    public static final String ENDPOINT = "api/progress";
}
