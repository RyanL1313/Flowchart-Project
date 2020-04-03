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


    /**
     * Uses the findCourse method in FullCourseList to check if a course ID entered by the user is valid or not, then removes it from
     * the full course list if it finds it (since the user has already taken this class, they don't want to see it again
     * in the electives dropdown box).
     * @param courseID The ID of the course (i.e. CS 321). Must have the space; the user should be told the format to type the course ID in (on the GUI).
     * @return True if the course ID was found in the FullCourseList hash map, false otherwise.
     */
    private boolean isEnteredCourseValid(String courseID) {
        int indexOfCourse; // Index of the course in question in the linked list associated with the key of the courseID (i.e. the "MA" linked list)
        FullCourseList courseList = new FullCourseList();

        indexOfCourse = courseList.findCourse(courseID); // Returns -1 if it's not found, otherwise it returns the index of the course's location

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
    public Course removeCourse(String courseID) {
        boolean validCourse = isEnteredCourseValid(courseID); // Calls an internal method that checks if the course entered is valid

        if (!validCourse) // We don't want to remove an invalid course
            return null;

        FullCourseList courseList = new FullCourseList();

        return courseList.removeCourse(courseID);
        // still needs to remove from degree's list
    }
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
     * @return An array of the full list of courses
     */
    public String[] getElectives() {
        FullCourseList courseList = new FullCourseList();
        HashMap<String, LinkedList<Course>> courseMap = courseList.getFullCourseList(); // Full list/mapping of UAH's courses
        ArrayList<String> tempElectiveList = new ArrayList<String>(); // The array list of elective courses to be returned

        // Iterating through each linked list in courseMap and adding each course ID to electiveList
        for (HashMap.Entry<String, LinkedList<Course>> entry: courseMap.entrySet()) {
            LinkedList<Course> courseListByDepartment =  entry.getValue();

            Iterator<Course> courseListIterator = courseListByDepartment.iterator();

            // Go through a single linked list, pulling out the course ID of each Course object
            while (courseListIterator.hasNext()) {
                tempElectiveList.add(courseListIterator.next().getCourseID());
            }
        }

        Iterator<String> courseIDIterator = tempElectiveList.iterator();
        String[] ElectiveList = new String[tempElectiveList.size()]; // Primitive array of the course IDs to be returned

        // Populate the array
        for (int i = 0; i < tempElectiveList.size(); i++) {
            ElectiveList[i] = courseIDIterator.next();
        }

        return ElectiveList;
    }

}
