package facilities.buildings;

public interface Building {

  int getLevel();

  boolean increaseLevel();

  int getUpgradeCost();

  int getCapacity();

  String getName();
}
