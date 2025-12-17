import facilities.Facility;
import facilities.buildings.Building;
import university.Estate;
import university.HumanResource;
import university.Staff;
import university.University;

import java.io.*;
import java.util.*;

public class ECSSim {

  // Instance variables/objects

  University university;
  ArrayList<Staff> staffMarket;
  HumanResource HR;
  Estate estate;
  String whatBuild;
  int year;

  // Tracking the number of building types that have been built
  int numOfHalls;
  int numOfLabs;
  int numOfTheatres;

  public ECSSim(String file, int _funding) throws FileNotFoundException {
    university = new University(_funding);
    staffMarket = new ArrayList<>();
    // Initialise these objects through the university class
    HR = university.getHR();
    estate = university.getEstate();
    year = 1;
    numOfHalls = 0;
    numOfLabs = 0;
    numOfTheatres = 0;
    staffMarket = new ArrayList<Staff>();

    // Read in the file for the staff market:
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) { // Go through all lines of the file
        // Split the current line using the open bracket
        // Using \\ for the regex as '(' is a reserved character
        String[] split = line.split("\\("); // This gives an array: {"Name ","Number)"
        String name = split[0];
        // Remove the last character, to get rid of other bracket and whitespace
        name = name.substring(0, name.length() - 1); // "Name " -> "Name"
        String skill = split[1];
        skill = skill.substring(0, skill.length() - 1); // "Number)" -> "Number"
        int _skill =
            Integer.parseInt(
                skill); // Parse String to number so it can be used to construct staff object
        // Create staff object and add to staff market
        staffMarket.add(new Staff(name, _skill, university));
      }
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void simulate() throws Exception {

    // Display initial stats
    System.out.println("Starting year " + year);
    // Created a display stats class for the ECSSim as it is called multiple times
    this.displayStats();
    year = year + 1;

    // ---------------------------------------------------------------------------------------

    // This algorithm determines which type of building to make based on which has the least
    // instances

    // Create 2-3 new Buildings
    System.out.println("Creating new Facilities...");
    Thread.sleep(
        500); // This is used throughout the project in order to make the output easier to read
    int howManyBuilds =
        2 + (int) (Math.random() * ((3 - 2) + 1)); // Decide how many buildings to create

    for (int i = 0; i < howManyBuilds; i++) {

      // Decide what building to make based on which has the least total capacity;
      int hallsCapacity = numOfHalls * 6;
      int labsCapacity = numOfLabs * 5;
      int theatresCapacity = numOfTheatres * 10;

      if (hallsCapacity <= labsCapacity && hallsCapacity <= theatresCapacity) {
        whatBuild = "Hall";
      } else if (labsCapacity <= hallsCapacity && labsCapacity <= theatresCapacity) {
        whatBuild = "Lab";
      } else {
        whatBuild = "Theatre";
      }

      if (whatBuild.equals("Hall")) {
        // Create the name of the building. Uses the type of building and the current number of that
        // type of build
        String buildName =
            whatBuild.concat(
                String.valueOf(
                    numOfHalls
                        + 1)); // If you create the first hall: "Hall" is concatenated with "1"
        // University.build method returns null if the building could not be made, so if that
        // happens, the hall wouldn't be created
        if (university.build(whatBuild, buildName) == null) {
        } else {
          numOfHalls++;
        }
      } else if (whatBuild.equals("Lab")) {
        // Repeated method for the Lab build type
        String buildName = whatBuild.concat(String.valueOf(numOfLabs + 1));
        if (university.build(whatBuild, buildName) == null) {
        } else {
          numOfLabs++;
        }
      } else {
        // Repeated method for the Theatre build type
        String buildName = whatBuild.concat(String.valueOf(numOfTheatres + 1));
        if (university.build(whatBuild, buildName) == null) {
        } else {
          numOfTheatres++;
        }
      }
    }

    // ------------------------------------------------------------------------------------------

    // Display stats
    this.displayStats();

    // ------------------------------------------------------------------------------------------

    // This algorithm will upgrade as many facilities as it can.
    // It keeps a buffer budget, that covers maintenance costs, plus some more for salaries
    // This avoids going into debt when paying maintenance costs, salaries etc.
    // The algorithm will also check that the next upgrade can be afforded, before attempting it.
    // It will then try to find the building with the lowest level, and upgrade it

    System.out.println("Upgrading Facilities...");
    Thread.sleep(500);

    // Get list of facilities from the estate
    ArrayList<Facility> facilities = estate.getFacilityList();

    // Determine bufferBudget
    double bufferBudget = (estate.getMaintenanceCost() * 1.25);

    boolean allBuildsMax = false;
    boolean nextBuildAffordable = true;

    while (university.getBudget() > bufferBudget && !allBuildsMax && nextBuildAffordable) {

      // Find the lowest level facility
      // Variable stores location of lowest level facility - starts off with first one
      int lowestLevelFacilityIndex = 0;
      // Loops through list of facilities
      for (int j = 0; j < facilities.size(); j++) {
        // Gets level of the current lowest level facility
        int currentLowestLevel = facilities.get(lowestLevelFacilityIndex).getLevel();
        // Gets level of next facility
        int nextFacilityLevel = facilities.get(j).getLevel();
        // Compares the two
        if (currentLowestLevel > nextFacilityLevel) {
          // If the next facility is a lower level, that is now the lowest level facility
          lowestLevelFacilityIndex = j;
        }
      }

      // Check that the upgrade can be afforded
      if (university.getBudget() > facilities.get(lowestLevelFacilityIndex).getUpgradeCost()) {
        // Upgrade building at index
        university.upgrade((Building) facilities.get(lowestLevelFacilityIndex));
      } else {
        // If the next upgrade cant be afforded, switch the boolean flag to stop the while loop
        nextBuildAffordable = false;
      }
    }

    // ----------------------------------------------------------------------------------------

    // Increase budget
    System.out.println(
        "Receiving tuition from " + university.getNumberOfStudents() + " Students...");
    Thread.sleep(500);
    // Calculate received tuition
    int tuition = (university.getNumberOfStudents()) * 10;
    // Increase budget using tuition
    university.increaseBudget(tuition);
    // Output results to user
    System.out.println("Received " + tuition + " ECS coins");
    Thread.sleep(500);
    System.out.println("New budget : " + university.getBudget() + "\n");
    Thread.sleep(500);

    // ------------------------------------------------------------------------------------------------

    // Hire a new staff member
    // Don't attempt to hire staff if the market is empty, avoids errors
    if (staffMarket.isEmpty()) {
      System.out.println("Couldn't hire any more staff, market is empty");
      Thread.sleep(500);
    } else {
      System.out.println("Hiring new staff...");
      Thread.sleep(500);
      // Choose random staff from market
      int whatStaff = ((int) (Math.random() * ((staffMarket.size()))));
      // Get chosen staff from market
      Staff newStaff = staffMarket.get(whatStaff);
      HR.addStaff(newStaff); // Add the random staff member from the market
      // Remove hired staff from market to prevent duplicates
      staffMarket.remove(newStaff);
      // Output what happened to user
      System.out.println("Hired a new staff member");
      Thread.sleep(500);
      System.out.println("Name : " + newStaff.getName());
      Thread.sleep(500);
      System.out.println("Skill: " + newStaff.getSkill() + "\n");
    }

    // ------------------------------------------------------------------------------------------------

    int untaughtStudents = 0;

    // Decide how many students each teacher should teach

    // Get total number of students at the university
    int numOfStudents = university.getNumberOfStudents();

    // First check if there are any students, otherwise not possible to instruct
    if (university.getNumberOfStudents() > 0) {

      // Get iterator from HR
      Iterator<Staff> it = HR.getStaff();
      // Get the number of staff members working at the university
      int numOfStaff = estate.getFacilities().length;

      // Divide students into equal groups, round down as you cannot teach decimals of students,
      // and you cannot round up as you cannot teach more students than there are at the university.
      int groupSize = (int) Math.floor((double) numOfStudents / numOfStaff);

      // Track how many students have been taught
      int numOfStudentsTaught = 0;

      // Iterate through the staff members
      while (it.hasNext()) {
        Staff staff = it.next();
        int currentGroup = groupSize;
        // Check that staff member can at least instruct 1 student
        if (staff.canInstruct(1)) {
          // Check if the Staff has enough stamina/skill to instruct the number of students in the
          // group
          if (staff.canInstruct(currentGroup)) {
            // If they can, instruct that many students
            // instruct method returns reputation gain, so increase rep by number returned
            university.increaseRep(staff.instruct(currentGroup));
            // SHow user new reputation
            System.out.println("New Reputation: " + university.getReputation() + "\n");
            Thread.sleep(500);
            // Increment staff teaching years
            staff.increaseYearsOfTeaching();
          }
          // If they cannot instruct the amount of students that has been determined, reduce the
          // group size until they can
          else {
            while (!staff.canInstruct(currentGroup)) {
              currentGroup--;
            }
          }
        } else {
          // Tell user if staff member cannot instruct any students
          System.out.println(staff.getName() + " Cannot instruct any students!\n");
          Thread.sleep(500);
        }
        numOfStudentsTaught += currentGroup; // Records the number fo students that are taught
        untaughtStudents = numOfStudents - numOfStudentsTaught;
      }
    } else {
      untaughtStudents = numOfStudents;
    }

    // ----------------------------------------------------------------------------

    // Pay the Maintenance cost
    System.out.println("Paying maintenance costs...");
    Thread.sleep(500);
    university.payMaintenance();

    // ----------------------------------------------------------------------------

    // Pay the staff salary
    System.out.println("Paying staff salaries...");
    Thread.sleep(500);
    university.payStaff();

    // ----------------------------------------------------------------------------

    // Uninstructed student deductions

    // Find number of uninstructed students
    System.out.println(untaughtStudents + " students were not taught this year!");
    Thread.sleep(500);
    // Decrease reputation based on untaught students
    university.decreaseRep(untaughtStudents);

    // ----------------------------------------------------------------------------

    // Staff leave after 30 years + Chance that staff leaves + Replenish other staff

    // Iterate through staff members
    Iterator<Staff> it2 = HR.getStaff();
    while (it2.hasNext()) {
      Staff staff = it2.next();
      // Get random number from 0 - staff members' stamina
      int staffStaminaRand = ((int) (Math.random() * ((staff.getStamina()) + 1)));
      // Check staff member has taught 30 years
      if (staff.getYearsOfTeaching() == 30) {
        // Remove staff from staff salary hash map
        it2.remove();
        System.out.println(
            staff.getName() + " has been for teaching for 30 years, and has left the university\n");
        Thread.sleep(500);
        // If the random number is 0 - staff leaves. If the staff has 100, 1/100 chance of this
        // happening
      } else if (staffStaminaRand == 0) {
        // Remove staff from staff salary hash map
        it2.remove();
        System.out.println(staff.getName() + " has left the university\n");
        Thread.sleep(500);
      } else {
        // Replenish stamina of staff that haven't left
        staff.replenishStamina();
      }
    }

    this.writeFile();
  }

  // ----------------------------------------------------------------------------

  // Display statistics of university
  public void displayStats() throws InterruptedException {
    Thread.sleep(500);
    System.out.println("Number of Halls built: " + numOfHalls);
    Thread.sleep(500);
    System.out.println("Number of Labs built: " + numOfLabs);
    Thread.sleep(500);
    System.out.println("Number of Theatres built: " + numOfTheatres);
    Thread.sleep(500);
    System.out.print("Budget: ");
    System.out.printf("%.2f", university.getBudget());
    System.out.println();
    Thread.sleep(500);
    System.out.println("Reputation: " + university.getReputation() + "\n");
  }

  // ----------------------------------------------------------------------------

  public void simulate(int _years) throws Exception {

    Scanner kb = new Scanner(System.in);

    for (int i = 0; i < _years; i++) {
      try {
        Thread.sleep(500);
        this.simulate();
        System.out.println("Save and quit? Y/N");
        if (kb.nextLine().equals("Y")) {
          System.exit(0);
        }
      } catch (InterruptedException e) {

      }
    }
  }

  public void writeFile() throws IOException {
    // https://cordova.apache.org/docs/en/1.5.0/phonegap/file/filewriter/filewriter.html#:~:text=By%20default%2C%20the%20FileWriter%20writes,the%20end%20of%20the%20file.
    FileWriter saveFileWiper = new FileWriter("save.txt", false);
    BufferedWriter saveFileWriter = new BufferedWriter(new FileWriter("save.txt", true));
    FileWriter staffFileWiper = new FileWriter("staffSalary.txt", false);
    BufferedWriter staffFileWriter = new BufferedWriter(new FileWriter("staffSalary.txt", true));
    FileWriter facilitiesFileWiper = new FileWriter("Facilities.txt", false);
    BufferedWriter facilitiesFileWriter =
        new BufferedWriter(new FileWriter("Facilities.txt", true));
    FileWriter marketFileWiper = new FileWriter("staffMarket.txt", false);
    BufferedWriter marketFileWriter = new BufferedWriter(new FileWriter("StaffMarket.txt", true));

    saveFileWiper.write("");
    saveFileWiper
        .close(); // https://stackoverflow.com/questions/6994518/how-to-delete-the-content-of-text-file-without-deleting-itself

    staffFileWiper.write("");
    staffFileWiper.close();

    facilitiesFileWiper.write("");
    facilitiesFileWiper.close();

    marketFileWiper.write("");
    marketFileWiper.close();

    // Budget
    saveFileWriter.write(String.valueOf(university.getBudget()));
    saveFileWriter.newLine();
    // Reputation
    saveFileWriter.write(String.valueOf(university.getReputation()));
    saveFileWriter.newLine();
    // numOfHalls
    saveFileWriter.write(String.valueOf(numOfHalls));
    saveFileWriter.newLine();
    // numOfLabs
    saveFileWriter.write(String.valueOf(numOfLabs));
    saveFileWriter.newLine();
    // numOfTheatres
    saveFileWriter.write(String.valueOf(numOfTheatres));
    saveFileWriter.newLine();
    // year
    saveFileWriter.write(String.valueOf(year));
    saveFileWriter.newLine();

    saveFileWriter.close();

    // staffSalary
    for (Map.Entry<Staff, Float> entry : HR.getStaffSalary().entrySet()) {
      // Staff name
      staffFileWriter.write(String.valueOf(entry.getKey().getName()));
      staffFileWriter.newLine();
      // Staff skill
      staffFileWriter.write(String.valueOf(entry.getKey().getSkill()));
      staffFileWriter.newLine();
      // Staff years of teaching
      staffFileWriter.write(String.valueOf(entry.getKey().getYearsOfTeaching()));
      staffFileWriter.newLine();
      // Staff salary
      staffFileWriter.write(String.valueOf(entry.getValue()));
      staffFileWriter.newLine();
    }

    staffFileWriter.close();

    for (int i = 0; i < estate.getFacilityList().size(); i++) {
      // Facility Type
      facilitiesFileWriter.write(estate.getFacilityList().get(i).getType());
      facilitiesFileWriter.newLine();
      // Facility name
      facilitiesFileWriter.write(estate.getFacilityList().get(i).getName());
      facilitiesFileWriter.newLine();
      // Facility level
      facilitiesFileWriter.write(String.valueOf(estate.getFacilityList().get(i).getLevel()));
      facilitiesFileWriter.newLine();
    }

    facilitiesFileWriter.close();

    for (int i = 0; i < staffMarket.size(); i++) {
      // Staff name
      marketFileWriter.write(staffMarket.get(i).getName());
      marketFileWriter.newLine();
      // Staff skill
      marketFileWriter.write(String.valueOf(staffMarket.get(i).getSkill()));
      marketFileWriter.newLine();
    }

    marketFileWriter.close();
  }

  public static void main(String[] args) throws Exception {
    Scanner kb = new Scanner(System.in);
    System.out.println("Start a new save or continue a save file? 1 or 2");
    if (kb.nextLine().equals("1")) {
      ECSSim sim = new ECSSim("Staff.txt", 2000);
      sim.simulate(50);
    } else {
      ContinueECSSim sim = new ContinueECSSim();
      sim.simulate(50);
    }
  }
}
