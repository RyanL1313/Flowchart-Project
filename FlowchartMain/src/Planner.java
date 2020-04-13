import java.util.*;

/**
 * A planner object is used to facilitate interactions between the GUI and the model. It is important for upholding
 * the Model-View-Control principle. It will process user interactions with the GUI, and it will send data to the GUI
 * from the model (such as the full hash map of courses to the drop-down box of electives the user can choose from).
 */
public class Planner {
    public static boolean studentHasPreviousClasses; // Is set to true if the user selects the option that they do have previous credits to enter
    private boolean studentFinishedEnteringCourses; // Is set to true when the user selects "Done" when they're done entering previous credits into the textbox (should be in a loop)
    public static String MAJOR;
    public static String MINOR;
    public static String CONCENTRATION;
    public static ArrayList<String> coursesAlreadyTaken = new ArrayList<String>();

   // public static FullCourseList fcl = new FullCourseList();    // BRYCE - made one static instance across
                                                                // entirety of Planner


    /**
     * Uses the findCourse method in FullCourseList to check if a course ID entered by the user is valid or not, then removes it from
     * the full course list if it finds it (since the user has already taken this class, they don't want to see it again
     * in the electives dropdown box).
     * @param courseID The ID of the course (i.e. CS 321). Must have the space; the user should be told the format to type the course ID in (on the GUI).
     * @return True if the course ID was found in the FullCourseList hash map, false otherwise.
     */
    private static boolean isEnteredCourseValid(String courseID) {
        int indexOfCourse; // Index of the course in question in the linked list associated with the key of the courseID (i.e. the "MA" linked list)
        //FullCourseList courseList = new FullCourseList();

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
    public static Course removeCourse(String courseID) {
        boolean validCourse = isEnteredCourseValid(courseID); // Calls an internal method that checks if the course entered is valid

        if (!validCourse) // We don't want to remove an invalid course
            return null;
        else
        {
            FullCourseList courseList = new FullCourseList();
            return FullCourseList.removeCourse(courseID);
            // still needs to remove from degree's list
        }
    }

    //--------------------- ADDED BY BRYCE -----------------------------------
    public static Course addCourse(String courseID)
    {
        boolean validCourse = isEnteredCourseValid(courseID);
        if(!validCourse)
            return null;
        else{
            //FullCourseList courseList = new FullCourseList();

            //return fcl.addCourse(courseID);
            return new Course();    // placeholder return
        }
    }
    //------------------------------------------------------------------------
    void drawSelectorWindow()
    {
        DegreeSelectorWindow selection= new DegreeSelectorWindow();
    }
    void drawCreditAdder()
    {
        CreditAdder adder= new CreditAdder();
    }
    void drawFourYearPlanDisplay()
    {
        FourYearPlanDisplay FYPD = new FourYearPlanDisplay();
    }
    void updateCoursesAlreadyTaken() //checks classes in courses alrady taken to see if valid, and then removes them from necessary
    {

    }

    /**
     * Used to get every remaining class in the full list of courses
     * Used in the GUI to display all possible electives in a drop-down box
     * @return A basic array of Strings that correspond to the full list of courses
     */
    public static String[] getElectives() {
        HashMap<String, LinkedList<Course>> courseMap = FullCourseList.getFullCourseList(); // Full list/mapping of UAH's courses
        ArrayList<String> electiveList = new ArrayList<String>(); // The list of elective courses to be returned

        // Iterating through each linked list in courseMap and adding each course ID to electiveList
        for (HashMap.Entry<String, LinkedList<Course>> entry: courseMap.entrySet()) {
            LinkedList<Course> courseListByDepartment =  entry.getValue();

            Iterator<Course> courseListIterator = courseListByDepartment.iterator();

            // Go through a single linked list, pulling out the course ID of each Course object
            while (courseListIterator.hasNext()) {
                electiveList.add(courseListIterator.next().getCourseID());
            }
        }

        //------------ ADDED BY BRYCE ------------------------------
        String[] electivesArray = new String[electiveList.size() + 1];
        electivesArray[0] = "EMPTY";
        for (int i = 1; i < electiveList.size() + 1; i++)
        {
            electivesArray[i] = electiveList.get(i - 1); // The array and array list are offset by 1 because of the EMPTY slot in the array
        }
        return electivesArray;
    }

    /**
     *
     * @return A String telling the user what prereqs they must take before the class they just tried
     */
    public String electivePrereqAddError(String courseID, int semesterNumber) {
        return null;
    }
}
