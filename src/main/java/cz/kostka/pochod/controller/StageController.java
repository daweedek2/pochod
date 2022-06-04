package cz.kostka.pochod.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/4/2022.
 */
@Controller
@RequestMapping("/pop/stages")
public class StageController {
    @GetMapping
    public String getAllStages() {
        return "pop/list";
    }

    @GetMapping("/1")
    public String getStageDetail() {
        return "pop/stage";
    }

    @GetMapping("/1/stamp")
    public String getStamp() {
        return "pop/stamp";
    }
}
