public class Monster {
  String monsters[] = { "スライム", "サハギン", "ドラゴン", "デュラハン", "シーサーペント" };// それぞれが0~4のIDのモンスターに相当する
  int monsterAp[] = { 10, 20, 30, 25, 30 }; // 各モンスターのAP
  int monsterDp[] = { 40, 20, 25, 15, 20 }; // 各モンスターのDP
  int id;
  String name;
  int ap;
  int dp;

  public void setMonster(int id) {
    this.id = id;
    this.name = monsters[id];
    this.ap = monsterAp[id];
    this.dp = monsterDp[id];
  }

  public int getAp() {
    return this.ap;
  }

  public int getDp() {
    return this.dp;
  }

  public String getName() {
    return this.name;
  }
}
