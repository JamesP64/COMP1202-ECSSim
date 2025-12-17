package facilities;

public class Facility {

  String name;

  public Facility(String _name) {
    this.name = _name;
  }

  public String getName() {
    return name;
  }

  // Placeholders methods that are overridden by child classes

  public String getType() {
    return"";
  }

  public int getUpgradeCost() {
    return 0;
  }

  public boolean increaseLevel() {
    return false;
  }

  public int getLevel() {
    return 1;
  }

  public int getCapacity() {
    return 0;
  }

  public void setLevel(int _level) {}
}
