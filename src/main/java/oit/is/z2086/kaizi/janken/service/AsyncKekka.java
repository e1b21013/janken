package oit.is.z2086.kaizi.janken.service;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import oit.is.z2086.kaizi.janken.model.MatchInfoMapper;
import oit.is.z2086.kaizi.janken.model.Match;
import oit.is.z2086.kaizi.janken.model.MatchMapper;
import oit.is.z2086.kaizi.janken.model.Janken;

@Service
public class AsyncKekka {
  boolean result_flag = false;
  int cnt=0;
  private final Logger logger = LoggerFactory.getLogger(AsyncKekka.class);

  @Autowired
  MatchInfoMapper matchInfoMapper;

  @Autowired
  MatchMapper matchMapper;

  public Match syncShowMatch() {
    return matchMapper.selectByActive();
  }
  @Transactional
  public Match setMatches(int user1,int user2,String user1Hand,String user2Hand) {
    Match match = new Match();
    match.setUser1(user1);
    match.setUser2(user2);
    match.setUser1Hand(user1Hand);
    match.setUser2Hand(user2Hand);
    match.setActive(true);
    this.result_flag = true;
    matchMapper.insertMatches(match);
    match = matchMapper.selectByActive();
    return match;
  }

  @Async
  public void asyncKekka(SseEmitter emitter) {
    try {
      while (true) {
        if (result_flag == false) {
          TimeUnit.MILLISECONDS.sleep(500);
          continue;
        }
        Match match = this.syncShowMatch();
        emitter.send(match);
        cnt++;
        if (cnt == 2) {
          result_flag = false;
          matchMapper.updateMatchByusers(match.getUser1(), match.getUser2());
          cnt = 0;
        }
        TimeUnit.MILLISECONDS.sleep(10000);
      }
    } catch (Exception e) {
      logger.warn("Exception:" + e.getClass().getName() + ":" + e.getMessage());
    } finally {
      emitter.complete();
    }
    System.out.println("asyncKekka complete");
  }

}
