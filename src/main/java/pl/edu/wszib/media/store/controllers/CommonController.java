package pl.edu.wszib.media.store.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import pl.edu.wszib.media.store.services.ITripService;

@Controller
public class CommonController {

    private final ITripService tripService;

    public CommonController(ITripService tripService) {
        this.tripService = tripService;
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Model model, @RequestParam(required = false) String pattern) {
        if (pattern == null) {
            model.addAttribute("trips", this.tripService.getAll());
            model.addAttribute("pattern", "");
        } else {
            model.addAttribute("trips", this.tripService.getByPattern(pattern));
            model.addAttribute("pattern", pattern);
        }
        return "index";
    }

    @RequestMapping(path = "/contact", method = RequestMethod.GET)
    public String contact() {
        return "contact";
    }
}
