package university;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public class HumanResource {

  HashMap<Staff, Float> staffSalary;

  public HumanResource() {
    staffSalary = new HashMap<Staff, Float>();
  }

  public void addStaff(Staff _staff) {

    float percentage =
        (float)
            (9.5
                + (int)
                    (Math.random()
                        * ((10.5 - 9.5)
                            + 1))); // https://www.geeksforgeeks.org/generating-random-numbers-in-java/
    float salary = _staff.getSkill() * (percentage / 100);

    staffSalary.put(_staff, salary);
  }

  public void addStaff(Staff _staff, float salary) {
    staffSalary.put(_staff, salary);
  }

  public Iterator<Staff> getStaff() {

    return staffSalary.keySet().iterator();
    // https://beginnersbook.com/2014/07/java-hashmap-iterator-example/
  }

  public float getTotalSalary() {
    Iterator<Staff> it = this.getStaff();
    float count = 0;
    float salary;

    while (it.hasNext()) {
      salary = staffSalary.get(it.next());
      count = count + salary;
    }

    return count;
  }

  public void updateMap(HashMap<Staff, Float> _map) {
    staffSalary = _map;
  }

  public HashMap<Staff, Float> getStaffSalary() {
    return staffSalary;
  }
}
