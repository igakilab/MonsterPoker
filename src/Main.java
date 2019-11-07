/**
 * Main
 */
public class Main {

  /**
   * モンスターポーカー 1. モンスターカードをユーザとCPUがランダムに5枚引く モンスターカードは5種類存在し，役によって攻撃力が変わる
   * 2回まで指定したカードを交換できる [役] ペア（AP，DPを合計する），スリー（AP，DPを合計して1.5倍），フォー（2倍），ファイブ（5倍）
   * フルハウス（AP3倍，DP2倍），ストレート（すべて違うモンスター．10倍）
   *
   * 2. AP，DPを役に応じて計算し，バトル．DPを超えたAPの場合，差分はプレーヤーへのダメージとなる 3. プレーヤーのHPが0になったら終わり．
   *
   * @param args
   * @throws InterruptedException
   */
  public static void main(String[] args) throws InterruptedException {
    MonsterPoker mp = new MonsterPoker();
    // while (true) {
    mp.drawPhase();
    mp.battlePhase();
    if (mp.getPlayerHp() <= 0 && mp.getCpuHp() <= 0) {
      System.out.println("引き分け！");
    } else if (mp.getPlayerHp() <= 0) {
      System.out.println("CPU Win!");
    } else if (mp.getCpuHp() <= 0) {
      System.out.println("Player Win!");
    } else {
      Thread.sleep(2000);
      // continue;
    }
    // break;
    // }
  }
}
