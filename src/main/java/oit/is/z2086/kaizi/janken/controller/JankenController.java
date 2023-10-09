package oit.is.z2086.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2086.kaizi.janken.model.Janken;

@Controller
public class JankenController {
  @GetMapping("/janken")
  public String janken_get() {
    return "janken.html";
  }

  @PostMapping("/janken")
  public String janken_post(@RequestParam String name1, ModelMap model) {
    model.addAttribute("jankenpost", name1);
    return "janken.html";
  }

  /**
   *
   */
  @GetMapping("/jankengame")
  public String jankenGame(@RequestParam String hand, ModelMap model1,ModelMap model2) {
    Janken jn = new Janken(hand);
    model1.addAttribute("hand", hand);
    model2.addAttribute("jankenResult", jn.getResult());
    return "janken.html";
  }

}
