package oit.is.z2086.kaizi.janken.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/janken")
public class JankenController {
  @GetMapping("/janken")
  public String Janken() {
    return "janken.html";
  }

  /**
   *
   */
  @PostMapping("/janken")
  public String janken(@RequestParam Integer name1, ModelMap model) {
    model.addAttribute("name1", name1);
    return "janken.html";
  }


}
