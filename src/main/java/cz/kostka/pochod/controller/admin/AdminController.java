package cz.kostka.pochod.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by dkostka on 6/7/2022.
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping
    public String getAdminView() {
        return "admin/welcome";
    }
}
