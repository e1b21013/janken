package oit.is.z2086.kaizi.janken.model;

import java.util.Random;

public class Janken {
  String CpuHand;
  String Result;

  public void CpuJanken(String hand) {
    int num;
    Random random = new Random();
    num = random.nextInt(3) + 1;
    switch (num) {
      case 1:
        CpuHand = "Gu";
        break;
      case 2:
        CpuHand = "Tyo";
        break;
      case 3:
        CpuHand = "Pa";
        break;
      default:
        break;
    }
    if (hand.equals(CpuHand)) {
      Result = "Draw";
    } else if ((hand.equals("Pa") && CpuHand.equals("Gu")) || (hand.equals("Gu") && CpuHand.equals("Tyo"))
        || (hand.equals("Tyo") && CpuHand.equals("Pa"))) {
      Result = "You Win!";
    } else {
      Result = "You lose";
    }
  }

  public void JankenResult(String user1Hand, String user2Hand) {
    if (user1Hand.equals(user2Hand)) {
      Result = "Draw";
    } else if ((user1Hand.equals("Pa") && user2Hand.equals("Gu")) || (user1Hand.equals("Gu") && user2Hand.equals("Tyo"))
        || (user1Hand.equals("Tyo") && user2Hand.equals("Pa"))) {
      Result = "user1 Win!";
    } else {
      Result = "user2 Win!";
    }
  }

  public String getCpuHand() {
    return CpuHand;
  }

  public void setCpuHand(String cpuHand) {
    CpuHand = cpuHand;
  }

  public void setResult(String result) {
    Result = result;
  }

  public String getResult() {
    return Result;
  }

  public void setAve(String Result) {
    this.Result = Result;
  }
}
