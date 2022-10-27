import java.util.Scanner;

public class Player {
  double hp;
  String name;
  Deck deck = new Deck();

  public Player(double hp, String name) {
    this.hp = hp;
    this.name = name;
  }

  public void firstDrowMove() {
    // 初期Draw
    System.out.println(name + "のDraw！");
    deck.firstDrow();
    // カードの表示
    System.out.print("[" + name + "]");
    deck.printCards();
    System.out.println();
  }

  public void changeCardMove(Scanner scanner) {
    System.out.println();
    System.out.println("カードを交換する場合は1から5の数字（左から数えた位置を表す）を続けて入力してください．交換しない場合は0と入力してください");
    boolean againFlag = this.exchangeCards(scanner.nextLine());
    if (againFlag) {
      System.out.println("もう一度カードを交換する場合は1から5の数字（左から数えた位置を表す）を続けて入力してください．交換しない場合は0と入力してください");
      this.exchangeCards(scanner.nextLine());
    }
  }

  public double attack() {
    double damage = deck.getAp();
    return damage;
  }

  public double guard(double attack) {
    double defence = deck.getDp();
    double damage = attack - defence;

    if (damage > 0) {
      this.hp -= damage;
      return damage;
    } else {
      return -1;
    }
  }

  public boolean exchangeCards(String exchange) {
    if (exchange.charAt(0) == '0') {
      return false;
    }
    exchange.chars().forEach(i -> deck.drowCard(Character.getNumericValue(i) - 1));
    // カードの表示
    this.printDeck();
    return true;
  }

  public void checkYaku() throws InterruptedException {
    printName();
    System.out.println("の役は・・");
    deck.check();
  }

  public void printYaku() throws InterruptedException {
    for (int i = 0; i < deck.monsters.length; i++) {
      if (deck.monsters[i] >= 1) {
        System.out.print(deck.cards[i].getName() + " ");
        Thread.sleep(500);
      }
    }
  }

  public void printDeck() {
    System.out.print("[" + name + "]");
    deck.printCards();
    System.out.println();
  }

  public void printName() {
    System.out.print(name);
  }

  public void printHp() {
    printName();
    System.out.print("のHPは");
    System.out.println(hp);
  }

  public double getHp() {
    return hp;
  }
}
