package cz.kostka.pochod.controller.admin;

import cz.kostka.pochod.service.StatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 7/2/2022.
 */
@Controller
@RequestMapping("/admin/statistics")
public class StatisticsController {
    private final StatisticsService statisticsService;

    public StatisticsController(final StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @GetMapping
    public String get(final Model model) {
        model.addAttribute("stageList", statisticsService.collect());
        return "admin/statistics";
    }
}
