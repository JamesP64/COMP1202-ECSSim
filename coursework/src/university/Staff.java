package university;

public class Staff {

  String name;
  int skill;
  int yearsOfTeaching;
  int stamina;
  University uni;

  public Staff(String _name, int _skill, University _u) {
    this.name = _name;
    this.skill = _skill;
    this.uni = _u;
    yearsOfTeaching = 0;
    stamina = 100;
  }

  public int instruct(int numOfStudents) throws InterruptedException {
    int reputation = (100 * skill) / (100 + numOfStudents);

    if (skill < 100) {
      skill++;
    }

    float num1 = (float) numOfStudents / (20 + skill);
    int num2 = (int) Math.ceil(num1);
    stamina = stamina - (num2 * 20);

    System.out.println(name + " is instructing " + numOfStudents + " students.");
    Thread.sleep(500);
    System.out.println("Reputation increased by " + reputation);
    Thread.sleep(500);

    return reputation;
  }

  public void replenishStamina() {
    stamina = stamina + 20;

    if (stamina > 100) {
      stamina = 100;
    }
  }

  public void setYearsOfTeaching(int yearsOfTeaching) {
    this.yearsOfTeaching = yearsOfTeaching;
  }

  public void increaseYearsOfTeaching() {
    yearsOfTeaching++;
  }

  public int getSkill() {
    return skill;
  }

  public int getStamina() {
    return stamina;
  }

  public boolean canInstruct(int _studentGroup) {
    float num1 = (float) _studentGroup / (20 + skill);
    int num2 = (int) Math.ceil(num1);
    if (stamina < (num2 * 20)) {
      return false;
    } else {
      return true;
    }
  }

  public int getYearsOfTeaching() {
    return yearsOfTeaching;
  }

  public String getName() {
    return name;
  }
}
