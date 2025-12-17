package facilities.buildings;

import facilities.Facility;

public class Theatre extends Facility implements Building {

  int level;
  int maxLevel;

  public Theatre(String _name) {
    super(_name);
    this.level = 1;
    this.maxLevel = 6;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String getType() {
    return "Theatre";
  }

  public int getLevel() {
    return level;
  }

  public boolean increaseLevel() {
    if (this.getLevel() == maxLevel) {
      return false;
    } else {
      level++;
      return true;
    }
  }

  public int getUpgradeCost() {
    int currentLevel = this.getLevel();
    return 200 * (currentLevel + 1);
  }

  public int getCapacity() {
    int baseCapacity = 10;
    int currentLevel = this.getLevel();
    return (int) (baseCapacity * (Math.pow(2, (currentLevel - 1))));
  }

  public String getName() {
    return super.getName();
  }
}
