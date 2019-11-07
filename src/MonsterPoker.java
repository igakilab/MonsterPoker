import java.util.Random;
import java.util.Scanner;

/**
 * MonsterPoker
 */
public class MonsterPoker {

  int playerHp = 100;
  int cpuHp = 100;
  int playerDeck[] = new int[5]; // 0~4までの数字（モンスターID）が入る
  int cpuDeck[] = new int[5];
  String monsters[] = { "スライム", "サハギン", "ドラゴン", "デュラハン", "シーサーペント" };// それぞれが0~4のIDのモンスターに相当する
  int monsterAp[] = { 10, 20, 30, 25, 30 };
  int monsterDp[] = { 40, 20, 25, 15, 20 };
  Random card = new Random();
  int cpuExchangeCards[] = new int[5];// それぞれ0,1でどのカードを交換するかを保持する．{0,1,1,0,1}の場合は2,3,5枚目のカードを交換することを表す
  String cpuExchange = new String();// 交換するカードを1~5の数字の組み合わせで保持する．上の例の場合，"235"となる．

  /**
   * 5枚のモンスターカードをプレイヤー/CPUが順に引く
   *
   * @throws InterruptedException
   */
  public void drawPhase() throws InterruptedException {
    // 初期Draw
    System.out.println("PlayerのDraw！");
    for (int i = 0; i < playerDeck.length; i++) {
      this.playerDeck[i] = card.nextInt(5);
    }
    // カードの表示
    System.out.print("[Player]");
    for (int i = 0; i < playerDeck.length; i++) {
      System.out.printf("%s ", this.monsters[playerDeck[i]]);
    }
    System.out.println();

    // カードの交換
    System.out.println("カードを交換する場合は1から5の数字（左から数えた位置を表す）を続けて入力してください．交換しない場合は0と入力してください");
    Scanner scanner = new Scanner(System.in);
    String exchange = scanner.nextLine();
    if (exchange.charAt(0) != '0') {
      for (int i = 0; i < exchange.length(); i++) {
        this.playerDeck[Character.getNumericValue(exchange.charAt(i)) - 1] = card.nextInt(5);
      }
      // カードの表示
      System.out.print("[Player]");
      for (int i = 0; i < playerDeck.length; i++) {
        System.out.printf("%s ", this.monsters[playerDeck[i]]);
      }
      System.out.println();
      System.out.println("もう一度カードを交換する場合は1から5の数字（左から数えた位置を表す）を続けて入力してください．交換しない場合は0と入力してください");
      exchange = scanner.nextLine();
      if (exchange.charAt(0) != '0') {
        for (int i = 0; i < exchange.length(); i++) {
          this.playerDeck[Character.getNumericValue(exchange.charAt(i)) - 1] = card.nextInt(5);
        }
        // カードの表示
        System.out.print("[Player]");
        for (int i = 0; i < playerDeck.length; i++) {
          System.out.printf("%s ", this.monsters[playerDeck[i]]);
        }
        System.out.println();
      }
    }

    System.out.println("CPUのDraw！");
    for (int i = 0; i < cpuDeck.length; i++) {
      this.cpuDeck[i] = card.nextInt(5);
    }
    // カードの表示
    System.out.print("[CPU]");
    for (int i = 0; i < cpuDeck.length; i++) {
      System.out.printf("%s ", this.monsters[cpuDeck[i]]);
    }
    System.out.println();

    // 交換するカードの決定
    System.out.println("CPUが交換するカードを考えています・・・・・・");
    Thread.sleep(2000);
    // cpuDeckを走査して，重複するカード以外のカードをランダムに交換する
    // 0,1,0,2,3 といったcpuDeckの場合，2枚目，4枚目，5枚目のカードをそれぞれ交換するかどうか決定し，例えば24といった形で決定する
    // 何番目のカードを交換するかを0,1で持つ配列の初期化
    // 例えばcpuExchangeCards[]が{0,1,1,0,0}の場合は2,3枚目を交換の候補にする
    for (int i = 0; i < this.cpuExchangeCards.length; i++) {
      this.cpuExchangeCards[i] = -1;
    }
    for (int i = 0; i < this.cpuDeck.length; i++) {
      if (this.cpuExchangeCards[i] == -1) {
        for (int j = i + 1; j < this.cpuDeck.length; j++) {
          if (this.cpuDeck[i] == this.cpuDeck[j]) {
            this.cpuExchangeCards[i] = 0;
            this.cpuExchangeCards[j] = 0;
          }
        }
        if (this.cpuExchangeCards[i] != 0) {
          this.cpuExchangeCards[i] = this.card.nextInt(2);// 交換するかどうかをランダムに最終決定する
          // this.cpuExchangeCards[i] = 1;
        }
      }
    }

    // 交換するカード番号の表示
    this.cpuExchange = "";
    for (int i = 0; i < cpuExchangeCards.length; i++) {
      if (this.cpuExchangeCards[i] == 1) {
        this.cpuExchange = this.cpuExchange + (i + 1);
      }
    }
    if (this.cpuExchange.length() == 0) {
      this.cpuExchange = "0";
    }
    System.out.println(this.cpuExchange);
    Thread.sleep(2000);

    // カードの交換
    if (cpuExchange.charAt(0) != '0') {
      for (int i = 0; i < cpuExchange.length(); i++) {
        this.cpuDeck[Character.getNumericValue(cpuExchange.charAt(i)) - 1] = card.nextInt(5);
      }
      // カードの表示
      System.out.print("[CPU]");
      for (int i = 0; i < cpuDeck.length; i++) {
        System.out.printf("%s ", this.monsters[cpuDeck[i]]);
      }
      System.out.println();
    }

    // 交換するカードの決定
    System.out.println("CPUが交換するカードを考えています・・・・・・");
    Thread.sleep(2000);
    // cpuDeckを走査して，重複するカード以外のカードをランダムに交換する
    // 0,1,0,2,3 といったcpuDeckの場合，2枚目，4枚目，5枚目のカードをそれぞれ交換するかどうか決定し，例えば24といった形で決定する
    // 何番目のカードを交換するかを0,1で持つ配列の初期化
    // 例えばcpuExchangeCards[]が{0,1,1,0,0}の場合は2,3枚目を交換の候補にする
    for (int i = 0; i < this.cpuExchangeCards.length; i++) {
      this.cpuExchangeCards[i] = -1;
    }
    for (int i = 0; i < this.cpuDeck.length; i++) {
      if (this.cpuExchangeCards[i] == -1) {
        for (int j = i + 1; j < this.cpuDeck.length; j++) {
          if (this.cpuDeck[i] == this.cpuDeck[j]) {
            this.cpuExchangeCards[i] = 0;
            this.cpuExchangeCards[j] = 0;
          }
        }
        if (this.cpuExchangeCards[i] != 0) {
          this.cpuExchangeCards[i] = this.card.nextInt(2);// 交換するかどうかをランダムに最終決定する
          // this.cpuExchangeCards[i] = 1;
        }
      }
    }

    // 交換するカード番号の表示
    this.cpuExchange = "";
    for (int i = 0; i < cpuExchangeCards.length; i++) {
      if (this.cpuExchangeCards[i] == 1) {
        this.cpuExchange = this.cpuExchange + (i + 1);
      }
    }
    if (this.cpuExchange.length() == 0) {
      this.cpuExchange = "0";
    }
    System.out.println(this.cpuExchange);
    Thread.sleep(2000);

    // カードの交換
    if (cpuExchange.charAt(0) != '0') {
      for (int i = 0; i < cpuExchange.length(); i++) {
        this.cpuDeck[Character.getNumericValue(cpuExchange.charAt(i)) - 1] = card.nextInt(5);
      }
      // カードの表示
      System.out.print("[CPU]");
      for (int i = 0; i < cpuDeck.length; i++) {
        System.out.printf("%s ", this.monsters[cpuDeck[i]]);
      }
      System.out.println();
    }
  }

  public void battlePhase() {

  }

  public int getPlayerHp() {
    return this.playerHp;
  }

  public int getCpuHp() {
    return this.cpuHp;
  }

}
