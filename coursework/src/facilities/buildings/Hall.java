package facilities.buildings;

import facilities.Facility;

public class Hall extends Facility implements Building {

  int level;
  int maxLevel;

  public Hall(String _name) {
    super(_name);
    this.level = 1;
    this.maxLevel = 4;
  }

  public void setLevel(int level) {
    this.level = level;
  }

  @Override
  public String getType() {
    return "Hall";
  }

  public int getLevel() {
    return level;
  }

  public boolean increaseLevel() {
    // Check building isnt already max level
    if (this.getLevel() == maxLevel) {
      return false;
    } else {
      level++;
      return true;
    }
  }

  public int getUpgradeCost() {
    int currentLevel = this.getLevel();
    return 100 * (currentLevel + 1);
  }

  public int getCapacity() {
    int baseCapacity = 6;
    int currentLevel = this.getLevel();
    return (int) (baseCapacity * (Math.pow(2, (currentLevel - 1))));
  }

  public String getName() {
    return super.getName();
  }
}
