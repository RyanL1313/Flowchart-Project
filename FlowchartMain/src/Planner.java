import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * A planner object is used to facilitate interactions between the GUI and the model. It is important for upholding
 * the Model-View-Control principle. It will process user interactions with the GUI, and it will send data to the GUI
 * from the model (such as the full hash map of courses to the drop-down box of electives the user can choose from).
 *
 * edu.cs321.controller.Planner follows the Observer pattern as it notifies the various GUI windows when they should be displayed by using
 * the draw methods for each window (ex: drawSelectorWindow()). Also, it notifies the model when data needs to be changed
 * (ex: remove a course from FullCourseList, or remove a course from coursesReqToGraduate in DegreeParser).
 */

public class Planner {
    public static Degree deg = new Degree();
    private static String courseGroupsTextFileLocation = "./List of Courses/Various Courses"; // Used for reading in Humanities course IDs, for example

    /**
     * Uses the findCourse method in FullCourseList to check if a course ID entered by the user is valid or not, then removes it from
     * the full course list if it finds it (since the user has already taken this class, they don't want to see it again
     * in the electives dropdown box).
     * @param courseID The ID of the course (i.e. CS 321). Must have the space; the user should be told the format to type the course ID in (on the GUI).
     * @return True if the course ID was found in the FullCourseList hash map, false otherwise.
     */
    private static boolean isEnteredCourseValid(String courseID) {
        int indexOfCourse; // Index of the course in question in the linked list associated with the key of the courseID (i.e. the "MA" linked list)

        indexOfCourse = FullCourseList.findCourse(courseID); // Returns -1 if it's not found, otherwise it returns the index of the course's location

        if (indexOfCourse == -1)
            return false; // The course was not found

        return true; // The course was found
    }

    /**
     * Removes courses from the hash map of UAH's courses as the user enters past credits.
     * We can extend this method to remove from the Degree object's list of courses, too (remove this comment once done)
     * @param courseID The ID of the course (i.e. CS 321). Space is necessary.
     * @return The course object. We probably don't need it after this, but just in case.
     */
    public static String removeCourse(String courseID) {
        boolean validCourse = isEnteredCourseValid(courseID); // Calls an internal method that checks if the course entered is valid

        if (!validCourse) // We don't want to remove an invalid course
            return null;
        else
        {
            Course removed = deg.removeObtainedCredit(courseID);
            return "Success";
        }
    }

    /**
     * Creates a new instance of DegreeSelectorWindow and runs its main method.
     */
    public void drawSelectorWindow()
    {
        DegreeSelectorWindow selection = new DegreeSelectorWindow();
        selection.main(null);
    }

    /**
     * Creates a new instances of CreditAdder
     */
    static void drawCreditAdder()
    {
        CreditAdder adder = new CreditAdder();
    }

    /**
     * Creates a new instance of PlanDisplay and runs its main method.
     */
    static void drawPlanDisplay()
    {
        PlanDisplay display = new PlanDisplay();
        display.main(null);
    }

    /**
     * Used to get every remaining class in the full list of courses
     * Used in the GUI to display all possible electives in a drop-down box
     * @return A basic array of Strings that correspond to the full list of courses
     */
    public static String[] getElectives(int flag) {
        HashMap<String, LinkedList<SpecificCourse>> courseMap = FullCourseList.getFullCourseList(); // Full list/mapping of UAH's courses
        ArrayList<String> electiveList = new ArrayList<String>(); // The list of elective courses to be returned

        // Iterating through each linked list in courseMap and adding each course ID to electiveList
        for (HashMap.Entry<String, LinkedList<SpecificCourse>> entry: courseMap.entrySet()) {
            LinkedList<SpecificCourse> courseListByDepartment =  entry.getValue();

            Iterator<SpecificCourse> courseListIterator = courseListByDepartment.iterator();

            // Go through a single linked list, pulling out the course ID of each Course object
            while (courseListIterator.hasNext()) {
                electiveList.add(courseListIterator.next().getCourseID());
            }
        }

        ArrayList<String> coreCourses = new ArrayList<String>();
        ArrayList<Semester> sem = deg.getSemesterList();
        for(int i = 0; i < sem.size(); i++)     // moves all courses from the Degree into a single ArrayList containing the course IDs
        {
            for(int j = 0; j < sem.get(i).getCourseList().size(); j++)
            {
                coreCourses.add(sem.get(i).getCourseList().get(j).getCourseID());
            }
        }

        String[] electivesArray = new String[electiveList.size() + 2];
        electivesArray[0] = "BACK";
        electivesArray[1] = "General Elective";     // added title of list to list

        if(flag == 1)
        {
            for (int i = 0; i < electiveList.size(); i++)
                for (int j = 0; j < coreCourses.size(); j++)
                    if (coreCourses.get(j).equals(electiveList.get(i)))
                    {
                        electiveList.remove(i);
                        i--;
                    }
        }

        for (int i = 2; i < electiveList.size() + 2; i++)
        {
            electivesArray[i] = electiveList.get(i - 2); // The array and array list are offset by 2 because of the BACK slot in the array and the title slot
        }

        return electivesArray;
    }

    /**
     * Tells the user what prerequisite courses they are missing in their degree plan.
     * Uses checkPreReq method from Degree to obtain the missing prerequisites that need to be put into the String to be returned
     * @return A String telling the user what prereqs they must take before the class they just tried
     */
    public static String electivePrereqAddError(String courseID, int semesterNumber) {
        String errorMessage = ""; // Error message to be returned
        ArrayList<ArrayList<String>> missingPrereqs = deg.checkPreReq(courseID, semesterNumber);
        if (missingPrereqs.size() == 0)
            return errorMessage; // The user had all the prerequisites, the course can be added to the flowchart (returns empty string)
        // Otherwise there was an error adding electives. Send a message to the user.

        errorMessage = "Missing prereqs for " + courseID + ": ";
        String errorMessageAddition = ""; // Adds on to errorMessage throughout the loop

        for (ArrayList<String> orRelationshipRow : missingPrereqs) {
            errorMessage = errorMessage.concat("Must take ");
            Iterator<String> colIterator = orRelationshipRow.iterator(); // Goes through each course ID in the row
            while (colIterator.hasNext()) {
                int count = 0; // Number of courses read in on this row

                errorMessageAddition = errorMessageAddition.concat(colIterator.next()); // A course
                if (colIterator.hasNext()) { // Need to add an "or". Otherwise, we are at the end of this row. No more "ors".
                    errorMessageAddition = errorMessageAddition.concat(" or ");
                }
                count++;
            }

            errorMessage = errorMessage.concat(errorMessageAddition + ". ");
            errorMessageAddition = ""; // Make this empty again
        }

        return errorMessage;
    }

    /**
     * Tells the user what corequisite courses they are missing for a single course
     * Uses checkCoReq method from Degree to obtain the missing corequisites
     * @return A String telling the user what coreqs they must take for that course
     */
    public static String electiveCoreqAddError(String courseID, int semesterNumber) {
        String errorMessage = ""; // Error message to be returned

        ArrayList<ArrayList<String>> missingCoreqs = deg.checkCoReq(courseID, semesterNumber); //missing coReq's of a course

        if(!missingCoreqs.isEmpty()) {
            return errorMessage; //user has all the coReq's for that course
        }
        else { //user is missing some coReq's for that course

            errorMessage = "Missing coreqs: ";
            String errorMessageAddition = ""; // Adds on to errorMessage throughout the loop
            for (ArrayList<String> orRelationshipRow : missingCoreqs) {
                errorMessage = errorMessage.concat("Must take ");
                Iterator<String> colIterator = orRelationshipRow.iterator(); // Goes through each course ID in the row
                while (colIterator.hasNext()) {
                    int count = 0; // Number of courses read in on this row

                    errorMessageAddition = errorMessageAddition.concat(colIterator.next()); // A course
                    if (colIterator.hasNext()) { // Need to add an "or". Otherwise, we are at the end of this row. No more "ors".
                        errorMessageAddition = errorMessageAddition.concat(" or ");
                    }
                    count++;
                }

                errorMessage = errorMessage.concat(errorMessageAddition + ". ");
                errorMessageAddition = ""; // Make this empty again



            }
        }

        return errorMessage;
    }

    /**
     * Retrieves all MA 200+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for MA 200+ courses
     */
    public static String[] getMA200PlusCourseIDs() {
        LinkedList<SpecificCourse> MACourses = FullCourseList.getFullCourseList().get("MA"); // All MA courses
        Iterator<SpecificCourse> courseIterator = MACourses.iterator();
        ArrayList<String> MA200CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        MA200CourseList.add("MA 200+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("MA 2")) { // Start looping at this point to get 200+ courses
                MA200CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            MA200CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(MA200CourseList); // Return primitive array with necessary course IDs

    }

    /**
     * Retrieves all CS 200+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for CS 200+ courses
     */
    public static String[] getCS200PlusCourseIDs() {
        LinkedList<SpecificCourse> CSCourses = FullCourseList.getFullCourseList().get("CS"); // All CS courses
        Iterator<SpecificCourse> courseIterator = CSCourses.iterator();
        ArrayList<String> CS200CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        CS200CourseList.add("CS 200+");     //added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("CS 2")) { // Start looping at this point to get 200+ courses
                CS200CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            CS200CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(CS200CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Retrieves all MA 300+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for MA 300+ courses
     */
    public static String[] getMA300PlusCourseIDs() {
        LinkedList<SpecificCourse> MACourses = FullCourseList.getFullCourseList().get("MA"); // All MA courses
        Iterator<SpecificCourse> courseIterator = MACourses.iterator();
        ArrayList<String> MA300CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        MA300CourseList.add("MA 300+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("MA 3")) { // Start looping at this point to get 300+ courses
                MA300CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            MA300CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(MA300CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Retrieves all CS 300+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for CS 300+ courses
     */
    public static String[] getCS300PlusCourseIDs() {
        LinkedList<SpecificCourse> CSCourses = FullCourseList.getFullCourseList().get("CS"); // All CS courses
        Iterator<SpecificCourse> courseIterator = CSCourses.iterator();
        ArrayList<String> CS300CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        CS300CourseList.add("CS 300+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("CS 3")) { // Start looping at this point to get 300+ courses
                CS300CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            CS300CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(CS300CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Retrieves all MA 400+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for MA 400+ courses
     */
    public static String[] getMA400PlusCourseIDs() {
        LinkedList<SpecificCourse> MACourses = FullCourseList.getFullCourseList().get("MA"); // All MA courses
        Iterator<SpecificCourse> courseIterator = MACourses.iterator();
        ArrayList<String> MA400CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        MA400CourseList.add("MA 400+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("MA 4")) { // Start looping at this point to get 400+ courses
                MA400CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            MA400CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(MA400CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Retrieves all CS 400+ courses and puts their course IDs into an array.
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of course IDS for CS 400+ courses
     */
    public static String[] getCS400PlusCourseIDs() {
        LinkedList<SpecificCourse> CSCourses = FullCourseList.getFullCourseList().get("CS"); // All CS courses
        Iterator<SpecificCourse> courseIterator = CSCourses.iterator();
        ArrayList<String> CS400CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        CS400CourseList.add("CS 400+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("CS 4")) { // Start looping at this point to get 400+ courses
                CS400CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            CS400CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(CS400CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Getter for ST 400+ course IDs
     * There are no ST 300+ courses so we shouldn't worry about that
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of ST 400+ course IDs
     */
    public static String[] getST400PlusCourseIDs() {
        LinkedList<SpecificCourse> STCourses = FullCourseList.getFullCourseList().get("ST"); // All ST courses
        Iterator<SpecificCourse> courseIterator = STCourses.iterator();
        ArrayList<String> ST400CourseList = new ArrayList<>(); // Temporarily holds the courses to be returned
        ST400CourseList.add("ST 400+");     // added title of list to list

        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next(); // Get the next course in the linked list
            if (NextCourse.getCourseID().contains("ST 4")) { // Start looping at this point to get 400+ courses
                ST400CourseList.add(NextCourse.getCourseID()); // Store this course now; it gets wiped out later
                break; // Break out of the while loop because now we need to store course IDs
            }
        }

        // New loop to start storing course IDs into the array list
        while (courseIterator.hasNext()) {
            SpecificCourse NextCourse = courseIterator.next();
            ST400CourseList.add(NextCourse.getCourseID());
        }

        return convertArrayListToArray(ST400CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Getter for literature course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * Just three courses count for a literature credit at UAH.
     * @return List of literature course IDs
     */
    public static String[] getLiteratureCourseIDs() {
        final int litCourses = 5; // 3 literature courses offered at UAH
        String[] literatureCourseList = new String[litCourses];

        literatureCourseList[0] = "BACK"; literatureCourseList[1] = "Literature";     // added title of list to list
        literatureCourseList[2] = "EH 207"; literatureCourseList[3] = "EH 208"; literatureCourseList[4] = "EH 242";

        return literatureCourseList;

    }

    /**
     * Getter for History course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of History course IDs
     */
    public static String[] getHistoryCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("History");   // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("History")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext() && !tempLine.equals("Fine Arts") && !tempLine.equals("Lab Science") && !tempLine.equals("Technical Elective") && !tempLine.equals("Humanities") && !tempLine.equals("Social and Behavioral Science")) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for fine arts course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of fine arts course IDs
     */
    public static String[] getFineArtsCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("Fine Art");     // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("Fine Arts")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext() && !tempLine.equals("Lab Science") && !tempLine.equals("Technical Elective") && !tempLine.equals("Humanities") && !tempLine.equals("Social and Behavioral Science")) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for Lab Science course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of lab science course IDs
     */
    public static String[] getLabScienceCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("Lab Science");      // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("Lab Science")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext() && !tempLine.equals("Technical Elective") && !tempLine.equals("Humanities") && !tempLine.equals("Social and Behavioral Science")) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for technical elective course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of technical elective course IDs for CS courses
     */
    public static String[] getTechnicalElectiveCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("Technical Elective");       // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("Technical Elective")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext() && !tempLine.equals("Humanities") && !tempLine.equals("Social and Behavioral Science")) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for humanities course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of humanities course IDs
     */
    public static String[] getHumanitiesCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("Humanities");        // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("Humanities")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext() && !tempLine.equals("Social and Behavioral Science")) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for social and behavioral science course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of social and behavioral science course IDs
     */
    public static String[] getSocialAndBehavioralSciencesCourseIDs() {
        File courseFile = new File(courseGroupsTextFileLocation); // File for reading
        Scanner scanCourseFile;
        String tempLine;
        ArrayList<String> courseIDs = new ArrayList(); // Course IDs that get returned
        courseIDs.add("Soc. & Behav. Science");     // added title of list to list

        try {
            scanCourseFile = new Scanner(courseFile); // Scanner to read the file
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        while (scanCourseFile.hasNext()) {
            tempLine = scanCourseFile.nextLine();
            if (tempLine.equals("Social and Behavioral Science")) // Start reading from here
                break; // Go to next loop
        }

        tempLine = scanCourseFile.nextLine();
        while (scanCourseFile.hasNext()) {
            courseIDs.add(tempLine);

            tempLine = scanCourseFile.nextLine();
        }

        courseIDs.add(tempLine); // To read in last course of file

        return convertArrayListToArray(courseIDs);
    }

    /**
     * Getter for Elective 300+ course IDs
     * Used by FourYearPlanDisplay's dropdowns.
     * @return List of Elective 300+ course IDs
     */
    public static String[] getElective300PlusCourseIDs() {
        ArrayList<String> Elective300CourseList = new ArrayList(); // List of 300+ elective courses to be returned
        HashMap<String, LinkedList<SpecificCourse>> courseList = FullCourseList.getFullCourseList();

        Elective300CourseList.add("General Elective 300+");     // added title of list to list

        for (HashMap.Entry<String, LinkedList<SpecificCourse>> entry : courseList.entrySet()) {
            LinkedList<SpecificCourse> coursesInDepartment;
            coursesInDepartment = entry.getValue();
            String courseNumberString = "";

            Iterator<SpecificCourse> courseListIterator = coursesInDepartment.iterator(); // To iterate over a linked list of courses

            while (courseListIterator.hasNext()) {
                SpecificCourse nextCourse = courseListIterator.next();
                Scanner courseIDScanner = new Scanner(nextCourse.getCourseID());
                courseIDScanner.next(); // Throw out the course
                courseNumberString = courseIDScanner.next(); // The course number
                if (courseNumberString.startsWith("3") || courseNumberString.startsWith("4")) // 300+ course
                    Elective300CourseList.add(nextCourse.getCourseID()); // Add the course to the array list if it is 300+
            }
        }

        return convertArrayListToArray(Elective300CourseList); // Return primitive array with necessary course IDs
    }

    /**
     * Getter for the core courses in the student's degree plan
     * @return The list of core courses
     */
    public static String[] getCoreCourseIDs() {
        ArrayList<String> coreCourseList = new ArrayList<>(); // List of core courses in the student's degree to be returned

        coreCourseList.add("Core Course"); // For the drop-down menu

        ArrayList<Semester> semesters = Degree.getSemesterList(); // List of semesters with SpecificCourse objects and BroadCourse objects

        for (Semester sem : semesters) {
            for (int i = 0; i < sem.getCourseList().size(); i++) {
                Course courseInThisSemester = sem.getCourseList().get(i);
                if (courseInThisSemester.getClass().toString().contains(("SpecificCourse"))) {// Core course
                    coreCourseList.add(courseInThisSemester.getCourseID());
                }
            }
        }

        return convertArrayListToArray(coreCourseList);
    }

    /**
     * Converts the Degree object into an ArrayList<ArrayList<String>> for the PlanDisplays
     * @return ArrayList<ArrayList<String>> which has the courseID's separated by semester.
     */
    public static ArrayList<ArrayList<String>> getDegree()
    {
        ArrayList<ArrayList<String>> degree = new ArrayList<ArrayList<String>>();
        for(int i = 0; i < deg.getSemesterList().size(); i++)
        {
            ArrayList<String> semester = new ArrayList<String>();
            for(int j = 0; j < deg.getSemesterList().get(i).getCourseList().size(); j++)
            {
                semester.add(deg.getSemesterList().get(i).getCourseList().get(j).getCourseID());
            }
            degree.add(semester);
        }
        return degree;
    }

    /**
     * Sets the static instance of the Degree class deg.
     * @param degree
     */
    public static void setDegree(Degree degree)
    {
        deg = degree;
    }

    /**
     * Sets the major field of the Degree class
     * @param maj
     */
    public static void setMajor(String maj) { deg.setMajor(maj);}

    /**
     * Sets the minor field of the Degree class
     */
    public static void setMinor(String min)
    {
        deg.setMinor(min);
    }

    /**
     * Calls the DegreeParser object so the program can get information about the user's selected major/minor
     * @param major Student's selected major (from DegreeSelectorWindow)
     * @param minor Student's selected minor (from DegreeSelectorWindow)
     */
    public static void callDegreeParser(String major, String minor) {
        DegreeParser parser = new DegreeParser(); // Should be the only time this gets called in the program
        if (minor != "N/A") {
            try {
                parser.degreeParser(major, minor);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try {
                parser.degreeParser(major);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Converts an ArrayList to an array
     * Useful in the getters for MA 200+, CS 200+ courses etc.
     * @param tempList The ArrayList to be converted to an array
     * @return A primitive array with the given information
     */
    private static String[] convertArrayListToArray(ArrayList<String> tempList) {
        Iterator<String> tempListIterator = tempList.iterator();
        String[] newPrimArray = new String[tempList.size() + 1]; // Array with the exact amount of space necessary for copying
        int index = 1;
        newPrimArray[0] = "BACK";
        while (tempListIterator.hasNext())
            newPrimArray[index++] = tempListIterator.next();

        return newPrimArray;
    }

    public static ArrayList<ArrayList<String>> getCoReq (String course)
    {
        SpecificCourse inNeedofCoReq = FullCourseList.getCourseByID(course);
        ArrayList<ArrayList<String>> coreqs = inNeedofCoReq.getCoreqs();
        return coreqs;
    }

    /**
     * Removes a broad course from CoursesReqToGraduate in the DegreeParser class
     * Goes through a hierarchy of substring checks to determine what to remove (General elective at the bottom of hierarchy).
     * Uses method in DegreeCompletion to complete this task.
     *
     * @param selectedCourseType The course type selected from the dropdown in the flowchart
     * (ex: Technical Elective, CS 300+).
     */
    public static String removeFromCoursesReqToGraduate(String selectedCourseType) {
        return DegreeCompletion.removeCourseFromCoursesNeededToGraduate(selectedCourseType);
    }

    public static void addToCoursesReqToGraduate(String selectedCourseType) {
        DegreeCompletion.addBackInCourse(selectedCourseType);
    }

    /**
     * The removeFromCoursesReqToGraduate method could fail at removing anything from coursesReqToGraduate, and
     * if this happens, it returns the empty string. Using this method in FlowNode ensures that the user will
     * empty the coursesReqToGraduate linked list.
     *
     * @param returnedSelectedCourseType String returned from removeFromCoursesReqToGraduate method.
     * @return Whether or not returnedSelectedCourseType is empty.
     */
    public static boolean checkIfValidCourseReqToGraduateRemoval(String returnedSelectedCourseType) {
        return !returnedSelectedCourseType.equals(""); // If the string is not empty, return true. It was a valid entry.
    }

    /**
     * Removes SpecificCourse objects in semesterList from FullCourseList so they don't
     * appear again in the dropdowns in the flowchart.
     */
    public static void removeCoreCoursesFromFullCourseList() {
        ArrayList<Semester> semesters = Degree.getSemesterList();

        for (Semester sem : semesters) {
            for (int i = 0; i < sem.getCourseList().size(); i++) {
                Course courseInThisSemester = sem.getCourseList().get(i);

                if (courseInThisSemester.getClass().toString().contains(("SpecificCourse"))) {// It's a core course
                    FullCourseList.removeCourse(courseInThisSemester.getCourseID());
                    FullCourseList.removeCourse(courseInThisSemester.getCourseID().concat("L")); // Also remove the lab associated with the course if necessary
                    FullCourseList.removeCourse(courseInThisSemester.getCourseID().concat("R")); // Remove recitation for this course too
                }
            }
        }
    }

    /**
     * Retrieves the LinkedList of courseID's that are required for the Degree to be completed.
     * @return
     */
    public static LinkedList getRequiredCourses()
    {
        return DegreeParser.getCoursesRequiredToGraduate();
    }
}
