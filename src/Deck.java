import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Deck {
  int maxDeck = 5;
  Point ap = new Point();
  Point dp = new Point();
  Random random = new Random();
  Monster cards[] = new Monster[maxDeck];
  int monsters[] = new int[maxDeck];
  int yaku[] = new int[maxDeck];

  public Deck() {
    Arrays.setAll(cards, i -> new Monster());
  }

  public void check() throws InterruptedException {
    Thread.sleep(1000);
    // 初期化
    ap.reset();
    dp.reset();
    Arrays.fill(monsters, 0);
    Arrays.fill(yaku, 0);

    // モンスターカードが何が何枚あるかをyaku配列に登録
    Arrays.stream(cards).forEach(card -> monsters[card.id]++);
    Arrays.stream(monsters).forEach(monster -> {
      if (monster == 1) {
        yaku[0] += 1;
      } else if (monster == 2) {
        yaku[1] += 1;
      } else if (monster == 3) {
        yaku[2] = 1;
      } else if (monster == 4) {
        yaku[3] = 1;
      } else if (monster == 5) {
        yaku[4] = 1;
      }
    });
    if (yaku[0] == 5) {
      specialFive();
    } else if (yaku[4] == 1) {
      five();
    } else if (yaku[3] == 1) {
      four();
    } else if (yaku[2] == 1 && yaku[1] == 1) {
      fullhouse();
    } else if (yaku[2] == 1) {
      three();
    } else if (yaku[1] == 2) {
      two();
    } else if (yaku[1] == 1) {
      one();
    }

    // APとDPの計算
    IntStream.range(0, maxDeck).forEach(i -> {
      if (monsters[i] >= 1) {
        ap.addValue(cards[i].getAp() * monsters[i]);
        dp.addValue(cards[i].getAp() * monsters[i]);
      }
    });
  }

  public void specialFive() {
    System.out.println("スペシャルファイブ！AP/DPは両方10倍！");
    ap.setAmp(10);
    dp.setAmp(10);
  }

  public void five() {
    System.out.println("ファイブ！AP/DPは両方5倍！");
    ap.setAmp(5);
    dp.setAmp(5);
  }

  public void four() {
    System.out.println("フォー！AP/DPは両方4倍！");
    ap.setAmp(3);
    dp.setAmp(3);
  }

  public void fullhouse() {
    System.out.println("フルハウス！AP/DPは両方3倍");
    ap.setAmp(3);
    dp.setAmp(3);
  }

  public void three() {
    System.out.println("スリーカード！AP/DPはそれぞれ3倍と2倍");
    ap.setAmp(3);
    dp.setAmp(2);
  }

  public void two() {
    System.out.println("ツーペア！AP/DPは両方2倍");
    ap.setAmp(3);
    dp.setAmp(2);
  }

  public void one() {
    System.out.println("ワンペア！AP/DPは両方1/2倍");
    ap.setAmp(0.5);
    dp.setAmp(0.5);
  }

  public double getAp() {
    return ap.getAmpValue();
  }

  public double getDp() {
    return dp.getAmpValue();
  }

  public void firstDrow() {
    IntStream.range(0, maxDeck).forEach(index -> drowCard(index));
  }

  public void drowCard(int card_id) {
    cards[card_id].setMonster(random.nextInt(5));
  }

  public void printCards() {
    Arrays.stream(cards).forEach(card -> System.out.printf("%s ", card.getName()));
  }
}
