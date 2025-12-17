package university;

import com.sun.jdi.event.ExceptionEvent;
import facilities.*;
import facilities.buildings.Building;
import facilities.buildings.Hall;

import java.util.ArrayList;

public class University {

  float budget;
  Estate estate;
  int reputation;
  HumanResource humanResource;

  // ----------------------------------------------------------------------

  // Constructor

  public University(int _funding) {
    this.budget = _funding;
    estate = new Estate();
    this.reputation = 0;
    humanResource = new HumanResource();
  }

  // Methods

  // Build method
  public Facility build(String _type, String _name) throws InterruptedException {
    // Get type of building
    if (_type.equals("Hall")) {
      System.out.println("Creating a new Hall...");
      // Check budget is sufficient to build
      if (budget < 100) {
        // If not, tell user
        System.out.println("Couldn't build a new Hall");
        Thread.sleep(500);
        System.out.println("Not enough money!\n");
        Thread.sleep(500);
        return null;
      } else {
        // Create the facility with provided information
        Facility f = estate.addFacility(_type, _name);
        Thread.sleep(1000);
        // Alter budget for the build
        budget = budget - 100;
        System.out.println("This costs 100 ECS coins");
        Thread.sleep(500);
        // Output updated budget
        System.out.println("New budget: " + budget);
        Thread.sleep(500);
        // Alter reputation for the build
        reputation = reputation + 100;
        System.out.println("You gained 100 reputation!");
        Thread.sleep(500);
        // Output updated reputation
        System.out.println("New reputation: " + reputation + "\n");
        Thread.sleep(500);
        return f;
      }
    } else if (_type.equals("Lab")) {
      // Similar method for other build types
      System.out.println("Creating a new Lab...");
      if (budget < 300) {
        System.out.println("Couldn't build a new Lab");
        Thread.sleep(500);
        System.out.println("Not enough money!\n");
        Thread.sleep(500);
        return null;
      } else {
        Facility f = estate.addFacility(_type, _name);
        Thread.sleep(1000);
        budget = budget - 300;
        System.out.println("This costs 300 ECS coins");
        Thread.sleep(500);
        System.out.println("New budget: " + budget);
        Thread.sleep(500);
        reputation = reputation + 100;
        System.out.println("You gained 100 reputation!");
        Thread.sleep(500);
        System.out.println("New reputation: " + reputation + "\n");
        Thread.sleep(500);
        return f;
      }
    } else if (_type.equals("Theatre")) {
      System.out.println("Creating a new Theatre...");
      if (budget < 200) {
        System.out.println("Couldn't build a new Theatre");
        Thread.sleep(500);
        System.out.println("Not enough money!\n");
        Thread.sleep(500);
        return null;
      } else {
        Facility f = estate.addFacility(_type, _name);
        Thread.sleep(1000);
        budget = budget - 200;
        System.out.println("This costs 200 ECS coins");
        Thread.sleep(500);
        System.out.println("New budget: " + budget);
        Thread.sleep(500);
        reputation = reputation + 100;
        System.out.println("You gained 100 reputation!");
        Thread.sleep(500);
        System.out.println("New reputation: " + reputation + "\n");
        Thread.sleep(500);
        return f;
      }
    } else {
      // If the type of build is not Hall,Lab or Theatre, it is not built
      System.out.println("Invalid Building!");
      return null;
    }
  }

  // -----------------------------------------------------------------------------------

  // Building upgrade method
  public void upgrade(Building _building) throws Exception {
    // Get upgrade cost
    int cost = _building.getUpgradeCost();
    // Get facility list
    ArrayList<Facility> facilities = estate.getFacilityList();
    // Check if list contains the specified building
    if (!facilities.contains(_building)) {
      throw new Exception("Building is not a part of the university!\n");
      // Check if building is max level already
      // increaseLevel outputs false if max level
    } else if (!_building.increaseLevel()) {
      throw new Exception("Already max level!\n");
      // Check there is sufficient budget
    } else if (budget < cost) {
      System.out.println("Attempting to upgrade " + _building.getName() + "...");
      Thread.sleep(500);
      System.out.println("Not enough money!\n");
      Thread.sleep(500);
      // Once gone through checks, building can be upgraded
    } else {
      System.out.println("Upgrading " + _building.getName() + "...");
      Thread.sleep(500);
      System.out.println("This costs " + cost + " ECS coins");
      Thread.sleep(500);
      // Adjust budget
      budget = budget - cost;
      System.out.println("New budget: " + budget);
      Thread.sleep(500);
      // Adjust reputation
      reputation = reputation + 50;
      System.out.println("New reputation: " + reputation + "\n");
      Thread.sleep(500);
    }
  }

  public float getBudget() {
    return budget;
  }

  public int getReputation() {
    return reputation;
  }

  public int getNumberOfStudents() {
    return estate.getNumberOfStudents();
  }

  public void increaseBudget(int _increase) {
    budget += _increase;
  }

  public HumanResource getHR() {
    return humanResource;
  }

  public void payMaintenance() throws InterruptedException {
    // Adjust budget
    budget -= estate.getMaintenanceCost();
    System.out.println("Paying " + estate.getMaintenanceCost() + " ECS coins");
    Thread.sleep(500);
    System.out.println("New budget: " + budget + "\n");
    Thread.sleep(500);
  }

  public void payStaff() throws InterruptedException {
    // Adjust budget
    budget -= humanResource.getTotalSalary();
    System.out.println("Paying " + humanResource.getTotalSalary() + " ECS coins");
    Thread.sleep(500);
    System.out.println("New budget: " + budget + "\n");
    Thread.sleep(500);
  }

  public void decreaseRep(int _untaughtStudents) throws InterruptedException {
    // Adjust reputation for number of untaught students
    reputation -= _untaughtStudents;
    if (_untaughtStudents > 0) {
      System.out.println("Lost " + _untaughtStudents + " reputation");
      Thread.sleep(500);
      System.out.println("New reputation " + reputation + "\n");
      Thread.sleep(500);
    }
  }

  public void increaseRep(int _rep) {
    reputation += _rep;
  }

  public Estate getEstate() {
    return estate;
  }

  public void setReputation(int reputation) {
    this.reputation = reputation;
  }
}
