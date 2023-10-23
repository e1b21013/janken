package oit.is.z2086.kaizi.janken.controller;

import java.security.Principal;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import oit.is.z2086.kaizi.janken.model.Janken;
import oit.is.z2086.kaizi.janken.model.Entry;
import oit.is.z2086.kaizi.janken.model.UserMapper;
import oit.is.z2086.kaizi.janken.model.User;
import oit.is.z2086.kaizi.janken.model.Match;
import oit.is.z2086.kaizi.janken.model.MatchMapper;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @GetMapping("/janken")
  public String janken_get(ModelMap model,Principal prin) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);
    // model.addAttribute("entry", this.entry);
    return "janken.html";
  }

  /*
   * @PostMapping("/janken")
   * public String janken_post(@RequestParam String name1, ModelMap model) {
   * model.addAttribute("jankenpost", name1);
   * return "janken.html";
   * }
   */
  @GetMapping("/match")
  public String match_post
  (@RequestParam Integer id,Principal prin, ModelMap model)
  {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectById(id);
    model.addAttribute("user", user);
    return "match.html";
  }
  /**
   *
   */
  @GetMapping("/fight")
  @Transactional
  public String fight_set(@RequestParam String hand, @RequestParam Integer id,ModelMap model,Principal prin) {
    Janken jn = new Janken(hand);
    String cpu_hand = "Gu";
    String loginUser = prin.getName();
    User user2 = userMapper.selectByName(loginUser);
    model.addAttribute("login_user", loginUser);
    User user = userMapper.selectById(id);
    model.addAttribute("user", user);
    Match match = new Match();
    match.setUser1(user2.getId());
    match.setUser2(id);
    match.setUser1Hand(hand);
    match.setUser2Hand(cpu_hand);
    matchMapper.insertMatch(match);
    model.addAttribute("match", match);
    model.addAttribute("jankenResult", jn.getResult());
    return "match.html";
  }

}
