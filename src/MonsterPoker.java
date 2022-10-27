import java.util.Scanner;

/**
 * MonsterPoker
 */
public class MonsterPoker {
  double hp = 1000; // player, cpuのHP
  Player player = new Player(hp, "Player");
  Cpu cpu = new Cpu(hp, "CPU");

  /**
   * 5枚のモンスターカードをプレイヤー/CPUが順に引く
   *
   * @throws InterruptedException
   */
  public void drawPhase(Scanner scanner) throws InterruptedException {
    // Player DrawPhase
    player.firstDrowMove();
    player.changeCardMove(scanner);

    // CPU DrawPhase
    cpu.firstDrowMove();
    cpu.changeCardMove();
    cpu.changeCardMove();
  }

  public void battlePhase() throws InterruptedException {
    // Playerの役の判定
    player.checkYaku();
    cpu.checkYaku();

    // バトル
    System.out.println("バトル！！");
    // Playerの攻撃
    fight(player, cpu);
    // CPUの攻撃
    fight(cpu, player);
  }

  public void fight(Player attacker, Player defender) throws InterruptedException {
    attacker.printName();
    System.out.print("のDrawした");

    attacker.printYaku();
    System.out.println("の攻撃！");
    Thread.sleep(1000);
    defender.printName();
    System.out.println("のモンスターによるガード！");

    double damage = defender.guard(attacker.attack());

    defender.printName();
    if (damage > 0) {
      System.out.printf("は%.0fのダメージを受けた！\n", damage);
    } else {
      System.out.println("はノーダメージ！");
    }
  }

  public boolean isEnd() {
    player.printHp();
    cpu.printHp();
    if (player.getHp() <= 0 && cpu.getHp() <= 0) {
      System.out.println("引き分け！");
    } else if (player.getHp() <= 0) {
      System.out.println("CPU Win!");
    } else if (cpu.getHp() <= 0) {
      System.out.println("Player Win!");
    } else {
      return false;
    }
    return true;
  }
}
