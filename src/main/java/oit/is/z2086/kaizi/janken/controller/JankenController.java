package oit.is.z2086.kaizi.janken.controller;

//import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

//import oit.is.z2086.kaizi.janken.model.Janken;
import oit.is.z2086.kaizi.janken.model.Entry;
import oit.is.z2086.kaizi.janken.model.UserMapper;
import oit.is.z2086.kaizi.janken.model.User;
import oit.is.z2086.kaizi.janken.model.Match;
import oit.is.z2086.kaizi.janken.model.MatchMapper;
import oit.is.z2086.kaizi.janken.model.MatchInfo;
import oit.is.z2086.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2086.kaizi.janken.service.AsyncKekka;

@Controller
public class JankenController {

  @Autowired
  private Entry entry;
  @Autowired
  UserMapper userMapper;

  @Autowired
  MatchMapper matchMapper;

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  AsyncKekka asynckekka;

  private final Logger logger = LoggerFactory.getLogger(JankenController.class);

  @GetMapping("/janken")
  public String janken_get(ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    this.entry.addUser(loginUser);
    model.addAttribute("login_user", loginUser);
    ArrayList<User> users = userMapper.selectAllUsers();
    model.addAttribute("users", users);
    ArrayList<MatchInfo> matchInfos = matchInfoMapper.selectactive();
    model.addAttribute("matchInfos", matchInfos);
    ArrayList<Match> matches = matchMapper.selectAllMatch();
    model.addAttribute("matches", matches);
    return "janken.html";
  }

  @GetMapping("/match")
  public String match_post(@RequestParam Integer id, Principal prin, ModelMap model) {
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
  public String fight_set(@RequestParam String hand, @RequestParam Integer id, ModelMap model, Principal prin) {
    String loginUser = prin.getName();
    User loUser = userMapper.selectByName(loginUser);
    ;
    MatchInfo matchInfo = new MatchInfo();
    matchInfo = matchInfoMapper.SelectByUsers(id, loUser.getId());
    if (matchInfo != null) {
      Match match = new Match();
      match = asynckekka.setMatches(matchInfo.getUser1(), matchInfo.getUser2(), matchInfo.getUser1Hand(), hand);
      matchInfoMapper.updateMatchInfo(match.getUser1(), match.getUser2());
    } else {
      MatchInfo matchinfo = new MatchInfo();
      matchinfo.setUser1(loUser.getId());
      matchinfo.setUser2(id);
      matchinfo.setUser1Hand(hand);
      matchinfo.setActive(true);
      matchInfoMapper.insertinfo(matchinfo);
    }
    return "wait.html";
  }

  @GetMapping("/result")
  public SseEmitter matchresult() {
    final SseEmitter emitter = new SseEmitter();
    this.asynckekka.asyncKekka(emitter);
    return emitter;
    }
}
