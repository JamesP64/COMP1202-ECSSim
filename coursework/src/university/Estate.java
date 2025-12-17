package university;

import facilities.*;
import facilities.buildings.Hall;
import facilities.buildings.Lab;
import facilities.buildings.Theatre;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;

public class Estate {

  ArrayList<Facility> facilities;

  // ****************************CONSTRUCTOR**************************************

  public Estate() {
    facilities = new ArrayList<Facility>();
  }

  // **************************************METHODS************************************

  public Facility[] getFacilities() {

    int n = facilities.size();
    Facility[] array = new Facility[n];

    for (int i = 0; i < n; i++) {
      array[i] = facilities.get(i);
    }

    return array;
  }

  public ArrayList<Facility> getFacilityList() {
    return facilities;
  }

  public Facility addFacility(String _type, String _name) {

    if (_type.equals("Hall")) {
      Hall h = new Hall(_name);
      facilities.add(h);
      return h;
    } else if (_type.equals("Lab")) {
      Lab l = new Lab(_name);
      facilities.add(l);
      return l;
    } else if (_type.equals("Theatre")) {
      Theatre t = new Theatre(_name);
      facilities.add(t);
      return t;
    } else {
      return null;
    }
  }

  public float getMaintenanceCost() {

    float cost = 0;

    for (Facility i : facilities) {
      cost = cost + ((float) i.getCapacity() / 10);
    }
    return cost;
  }

  public int getNumberOfStudents() {

    int hallsNum = 0;
    int labsNum = 0;
    int theatresNum = 0;

    for (Facility i : facilities) {
      if (i.getClass().toString().equals("class facilities.buildings.Lab")) {
        labsNum = labsNum + i.getCapacity();
      } else if (i.getClass().toString().equals("class facilities.buildings.Hall")) {
        hallsNum = hallsNum + i.getCapacity();
      } else {
        theatresNum = theatresNum + i.getCapacity();
      }
    }

    // Deciding what facility type has the least instances
    if (hallsNum < labsNum && hallsNum < theatresNum) {
      return hallsNum;
    } else if (labsNum < hallsNum && labsNum < theatresNum) {
      return labsNum;
    } else {
      return theatresNum;
    }
  }
}
