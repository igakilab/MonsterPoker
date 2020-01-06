import java.util.Random;
import java.util.Scanner;

/**
 * MonsterPoker
 */
public class MonsterPoker {

  Random card = new Random();

  double p11 = 1000; //PlayerのHP
  double c12 = 1000; //cpuのHP
  int playerDeck[] = new int[5]; // 0~4までの数字（モンスターID）が入る
  int cpuDeck[] = new int[5];
  String monsters[] = { "スライム", "サハギン", "ドラゴン", "デュラハン", "シーサーペント" };// それぞれが0~4のIDのモンスターに相当する
  int monsterAp[] = { 10, 20, 30, 25, 30 }; //各モンスターのAP
  int monsterDp[] = { 40, 20, 25, 15, 20 }; //各モンスターのDP
  int cpuExchangeCards[] = new int[5];// それぞれ0,1でどのカードを交換するかを保持する．{0,1,1,0,1}の場合は2,3,5枚目のカードを交換することを表す
  String c13 = new String();// 交換するカードを1~5の数字の組み合わせで保持する．上の例の場合，"235"となる．
  int playerYaku[] = new int[5];// playerのモンスターカードがそれぞれ何枚ずつあるかを保存する配列．{2,3,0,0,0}の場合，ID0が2枚,ID1が3枚あることを示す．
  int cpuYaku[] = new int[5];// playerのモンスターカードがそれぞれ何枚ずつあるかを保存する配列．{2,3,0,0,0}の場合，ID0が2枚,ID1が3枚あることを示す．
  double p15 = 1;// Playerの役によるAP倍率．初期値は1で役が決まると対応した数値になる．1.5倍の場合は1.5となる
  double p16 = 1;// Playerの役によるDP倍率．初期値は1で役が決まると対応した数値になる．1.5倍の場合は1.5となる
  double p17 = 0;// PlayerのAP
  double p18 = 0;// PlayerのDP
  double c15 = 1;// CPUの役によるAP倍率．1.5倍の場合は1.5となる
  double c16 = 1;
  double c17 = 0;
  double c18 = 0;
  // 役判定用フラグ
  // 役判定
  // 5が1つある：ファイブ->five = true
  // 4が1つある：フォー->four = true
  // 3が1つあり，かつ，2が1つある：フルハウス->three = true and pair = 1
  // 2が2つある：ツーペア->pair = 2
  // 3が1つある：スリー->three = true;
  // 2が1つある：ペア->pair = 1
  // 1が5つある：スペシャルファイブ->one=5
  boolean five = false;
  boolean four = false;
  boolean three = false;
  int pair = 0; // pair数を保持する
  int one = 0;// 1枚だけのカードの枚数

  /**
   * 5枚のモンスターカードをプレイヤー/CPUが順に引く
   *
   * @throws InterruptedException
   */
  public void drawPhase(Scanner scanner) throws InterruptedException {
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
    this.c13 = "";
    for (int i = 0; i < cpuExchangeCards.length; i++) {
      if (this.cpuExchangeCards[i] == 1) {
        this.c13 = this.c13 + (i + 1);
      }
    }
    if (this.c13.length() == 0) {
      this.c13 = "0";
    }
    System.out.println(this.c13);

    // カードの交換
    if (c13.charAt(0) != '0') {
      for (int i = 0; i < c13.length(); i++) {
        this.cpuDeck[Character.getNumericValue(c13.charAt(i)) - 1] = card.nextInt(5);
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
    this.c13 = "";
    for (int i = 0; i < cpuExchangeCards.length; i++) {
      if (this.cpuExchangeCards[i] == 1) {
        this.c13 = this.c13 + (i + 1);
      }
    }
    if (this.c13.length() == 0) {
      this.c13 = "0";
    }
    System.out.println(this.c13);

    // カードの交換
    if (c13.charAt(0) != '0') {
      for (int i = 0; i < c13.length(); i++) {
        this.cpuDeck[Character.getNumericValue(c13.charAt(i)) - 1] = card.nextInt(5);
      }
      // カードの表示
      System.out.print("[CPU]");
      for (int i = 0; i < cpuDeck.length; i++) {
        System.out.printf("%s ", this.monsters[cpuDeck[i]]);
      }
      System.out.println();
    }
  }

  public void battlePhase() throws InterruptedException {
    // Playerの役の判定
    // 役判定用配列の初期化
    for (int i = 0; i < playerYaku.length; i++) {
      this.playerYaku[i] = 0;
    }
    // モンスターカードが何が何枚あるかをplayerYaku配列に登録
    for (int i = 0; i < playerDeck.length; i++) {
      this.playerYaku[this.playerDeck[i]]++;
    }
    // 役判定
    // 5が1つある：ファイブ
    // 4が1つある：フォー
    // 3が1つあり，かつ，2が1つある：フルハウス
    // 2が2つある：ツーペア
    // 3が1つある：スリー
    // 2が1つある：ペア
    // 1が5つある：スペシャルファイブ
    // 初期化
    five = false;
    four = false;
    three = false;
    pair = 0; // pair数を保持する
    one = 0;// 1枚だけのカードの枚数
    // 手札ごとのplayerYaku配列の作成
    for (int i = 0; i < playerYaku.length; i++) {
      if (playerYaku[i] == 1) {
        one++;
      } else if (playerYaku[i] == 2) {
        pair++;
      } else if (playerYaku[i] == 3) {
        three = true;
      } else if (playerYaku[i] == 4) {
        four = true;
      } else if (playerYaku[i] == 5) {
        five = true;
      }
    }

    // 役の判定
    System.out.println("Playerの役は・・");
    this.p15 = 1;// 初期化
    this.p16 = 1;
    if (one == 5) {
      System.out.println("スペシャルファイブ！AP/DPは両方10倍！");
      this.p15 = 10;
      this.p16 = 10;
    } else if (five == true) {
      System.out.println("ファイブ！AP/DPは両方5倍！");
      this.p15 = 5;
      this.p16 = 5;
    } else if (four == true) {
      System.out.println("フォー！AP/DPは両方4倍！");
      this.p15 = 3;
      this.p16 = 3;
    } else if (three == true && pair == 1) {
      System.out.println("フルハウス！AP/DPは両方3倍");
      this.p15 = 3;
      this.p16 = 3;
    } else if (three == true) {
      System.out.println("スリーカード！AP/DPはそれぞれ3倍と2倍");
      this.p15 = 3;
      this.p16 = 2;
    } else if (pair == 2) {
      System.out.println("ツーペア！AP/DPは両方2倍");
      this.p15 = 2;
      this.p16 = 2;
    } else if (pair == 1) {
      System.out.println("ワンペア！AP/DPは両方1/2倍");
      this.p15 = 0.5;
      this.p16 = 0.5;
    }
    Thread.sleep(1000);

    // APとDPの計算
    for (int i = 0; i < playerYaku.length; i++) {
      if (playerYaku[i] >= 1) {
        this.p17 = this.p17 + this.monsterAp[i] * playerYaku[i];
        this.p18 = this.p18 + this.monsterDp[i] * playerYaku[i];
      }
    }
    this.p17 = this.p17 * this.p15;
    this.p18 = this.p18 * this.p16;

    // CPUの役の判定
    // 役判定用配列の初期化
    for (int i = 0; i < cpuYaku.length; i++) {
      this.cpuYaku[i] = 0;
    }
    // モンスターカードが何が何枚あるかをcpuYaku配列に登録
    for (int i = 0; i < cpuDeck.length; i++) {
      this.cpuYaku[this.cpuDeck[i]]++;
    }
    // 役判定
    // 5が1つある：ファイブ
    // 4が1つある：フォー
    // 3が1つあり，かつ，2が1つある：フルハウス
    // 2が2つある：ツーペア
    // 3が1つある：スリー
    // 2が1つある：ペア
    // 1が5つある：スペシャルファイブ
    // 初期化
    five = false;
    four = false;
    three = false;
    pair = 0; // pair数を保持する
    one = 0;// 1枚だけのカードの枚数
    // 手札ごとのcpuYaku配列の作成
    for (int i = 0; i < cpuYaku.length; i++) {
      if (cpuYaku[i] == 1) {
        one++;
      } else if (cpuYaku[i] == 2) {
        pair++;
      } else if (cpuYaku[i] == 3) {
        three = true;
      } else if (cpuYaku[i] == 4) {
        four = true;
      } else if (cpuYaku[i] == 5) {
        five = true;
      }
    }

    // 役の判定
    System.out.println("CPUの役は・・");
    this.c15 = 1;// 初期化
    this.c16 = 1;
    if (one == 5) {
      System.out.println("スペシャルファイブ！AP/DPは両方10倍！");
      this.c15 = 10;
      this.c16 = 10;
    } else if (five == true) {
      System.out.println("ファイブ！AP/DPは両方5倍！");
      this.c15 = 5;
      this.c16 = 5;
    } else if (four == true) {
      System.out.println("フォー！AP/DPは両方4倍！");
      this.c15 = 3;
      this.c16 = 3;
    } else if (three == true && pair == 1) {
      System.out.println("フルハウス！AP/DPは両方3倍");
      this.c15 = 3;
      this.c16 = 3;
    } else if (three == true) {
      System.out.println("スリーカード！AP/DPはそれぞれ3倍と2倍");
      this.c15 = 3;
      this.c16 = 2;
    } else if (pair == 2) {
      System.out.println("ツーペア！AP/DPは両方2倍");
      this.c15 = 2;
      this.c16 = 2;
    } else if (pair == 1) {
      System.out.println("ワンペア！AP/DPは両方1/2倍");
      this.c15 = 0.5;
      this.c16 = 0.5;
    }
    Thread.sleep(1000);

    // APとDPの計算
    for (int i = 0; i < cpuYaku.length; i++) {
      if (cpuYaku[i] >= 1) {
        this.c17 = this.c17 + this.monsterAp[i] * cpuYaku[i];
        this.c18 = this.c18 + this.monsterDp[i] * cpuYaku[i];
      }
    }
    this.c17 = this.c17 * this.c15;
    this.c18 = this.c18 * this.c16;

    // バトル
    System.out.println("バトル！！");
    // Playerの攻撃
    System.out.print("PlayerのDrawした");
    for (int i = 0; i < playerYaku.length; i++) {
      if (playerYaku[i] >= 1) {
        System.out.print(this.monsters[i] + " ");
        Thread.sleep(500);
      }
    }
    System.out.print("の攻撃！");
    Thread.sleep(1000);
    System.out.println("CPUのモンスターによるガード！");
    if (this.c18 >= this.p17) {
      System.out.println("CPUはノーダメージ！");
    } else {
      double damage = this.p17 - this.c18;
      System.out.printf("CPUは%.0fのダメージを受けた！\n", damage);
      this.c12 = this.c12 - damage;
    }

    // CPUの攻撃
    System.out.print("CPUのDrawした");
    for (int i = 0; i < cpuYaku.length; i++) {
      if (cpuYaku[i] >= 1) {
        System.out.print(this.monsters[i] + " ");
        Thread.sleep(500);
      }
    }
    System.out.print("の攻撃！");
    Thread.sleep(1000);
    System.out.println("Playerのモンスターによるガード！");
    if (this.p18 >= this.c17) {
      System.out.println("Playerはノーダメージ！");
    } else {
      double damage = this.c17 - this.p18;
      System.out.printf("Playerは%.0fのダメージを受けた！\n", damage);
      this.p11 = this.p11 - damage;
    }

    System.out.println("PlayerのHPは" + this.p11);
    System.out.println("CPUのHPは" + this.c12);

  }

  public double getPlayerHp() {
    return this.p11;
  }

  public double getCpuHp() {
    return this.c12;
  }

  public double getp15() {
    return this.p15;
  }

  public void setp15(double p15) {
    this.p15 = p15;
  }

  public double getp16() {
    return this.p16;
  }

  public void setp16(double p16) {
    this.p16 = p16;
  }

  public double getp17() {
    return this.p17;
  }

  public void setp17(double p17) {
    this.p17 = p17;
  }

  public double getc15() {
    return this.c15;
  }

  public void setc15(double c15) {
    this.c15 = c15;
  }

  public double getc16() {
    return this.c16;
  }

  public void setc16(double c16) {
    this.c16 = c16;
  }

}
