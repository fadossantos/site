package br.com.moradas291.site.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class ReservasController {

    @RequestMapping(value = "/reservas", method = RequestMethod.GET)
    public ModelAndView home() {
        return new ModelAndView("emConstrucao");
    }

}
