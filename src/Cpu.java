import java.util.Random;

public class Cpu extends Player {
  Random random = new Random();

  public Cpu(double hp, String name) {
    super(hp, name);
  }

  public void changeCardMove() throws InterruptedException {
    printName();
    System.out.println("が交換するカードを考えています・・・・・・");
    Thread.sleep(2000);
    this.exchangeCards();
  }

  public void exchangeCards() {
    int maxDeck = deck.maxDeck;
    int[] cpuExchangeCards = new int[maxDeck];
    // 交換するカードの決定

    // cpuDeckを走査して，重複するカード以外のカードをランダムに交換する
    // 0,1,0,2,3 といったcpuDeckの場合，2枚目，4枚目，5枚目のカードをそれぞれ交換するかどうか決定し，例えば24といった形で決定する
    // 何番目のカードを交換するかを0,1で持つ配列の初期化
    // 例えばcpuExchangeCards[]が{0,1,1,0,0}の場合は2,3枚目を交換の候補にする
    for (int i = 0; i < maxDeck; i++) {
      cpuExchangeCards[i] = -1;
    }
    for (int i = 0; i < maxDeck; i++) {
      if (cpuExchangeCards[i] == -1) {
        for (int j = i + 1; j < maxDeck; j++) {
          if (deck.cards[i].id == deck.cards[j].id) {
            cpuExchangeCards[i] = 0;
            cpuExchangeCards[j] = 0;
          }
        }
        if (cpuExchangeCards[i] != 0) {
          cpuExchangeCards[i] = random.nextInt(2);// 交換するかどうかをランダムに最終決定する
        }
      }
    }

    // 交換するカード番号の表示
    String cpuChangeCards = "";
    for (int i = 0; i < cpuExchangeCards.length; i++) {
      if (cpuExchangeCards[i] == 1) {
        cpuChangeCards = cpuChangeCards + (i + 1);
      }
    }
    if (cpuChangeCards.length() == 0) {
      cpuChangeCards = "0";
    }
    System.out.println(cpuChangeCards);

    // カードの交換
    exchangeCards(cpuChangeCards);
  }
}
