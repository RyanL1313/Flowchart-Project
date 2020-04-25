package degree;

import degree.Course;

import java.util.*;

/**
 * A course has a course ID, name, prerequisites and corequisites list, and number of credit hours.
 * A course is the most concrete object type used in this program
 * The setter methods will be called by the FullCourseList class
 * The nested Array Lists for prereqs and coreqs are necessary to distinguish equivalent prereqs and coreqs from separate preregs and coregs.
 * Equivalents are in the same row (or same ArrayList in the ArrayList of ArrayLists), separates are on different rows (different ArrayList inside the ArrayList of ArrayLists)
 */
public class SpecificCourse implements Course {
    private ArrayList<ArrayList<String>> prereqs = new ArrayList<ArrayList<String>>(); // ArrayList of ArrayList of prerequisite courses
    private ArrayList<ArrayList<String>> coreqs = new ArrayList<ArrayList<String>>(); // ArrayList of ArrayList of corequisite courses
    private int courseHours; // Hours the course satisfies
    private String fullCourseName; // Full name of the course
    private String courseID; // Course ID with 2-3 letters then 3 numbers (ex. CS 321, ARH 101)

    /**
     * Course constructor
     * If the attributes end up staying with these values, then that shows us an error in file reading occurred.
     */
    public SpecificCourse() {
        courseHours = 0;
        fullCourseName = "Unknown";
        courseID = "Unknown";
    }

    /**
     * Getter for the course ID
     * @return The course ID
     */
    public String getCourseID() {
        return courseID;
    }

    /**
     * Getter for the full course name
     * @return The full course name
     */
    public String getFullCourseName() {
        return fullCourseName;
    }

    /**
     * Getter for the course's hours
     * @return The course's hours
     */
    public int getHours() {
        return courseHours;
    }

    /**
     * Getter for the prerequisites list
     * @return the prerequisites list
     */
    public ArrayList<ArrayList<String>> getPrereqs() {
        return prereqs;
    }

    /**
     * Getter for the corequisites list
     * @return the corequisites list
     */
    public ArrayList<ArrayList<String>> getCoreqs() {
        return coreqs;
    }

    /**
     * Setter for the course ID
     * @param courseID The course ID
     */
    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    /**
     * Setter for the full course name
     * @param fullCourseName The full name of the course
     */
    public void setFullCourseName(String fullCourseName) {
        this.fullCourseName = fullCourseName;
    }

    /**
     * Setter for the credit hours associated with the course
     * @param courseHours The number of hours
     */
    public void setHours(int courseHours) {
        this.courseHours = courseHours;
    }

    /**
     * Setter for the list of prerequisite courses
     * This list comes from the FullCourseList (FullCourseList will use this setPrereqs method)
     * @param prereqs The list of prerequisites (by courseID)
     */
    public void setPrereqs(ArrayList<ArrayList<String>> prereqs) {
        this.prereqs = prereqs;
    }

    /**
     * Setter for the list of corequisite courses
     * This list comes from the FullCourseList (FullCourseList will use this setCoreqs method)
     * @param coreqs The list of corequisites (by courseID)
     */
    public void setCoreqs(ArrayList<ArrayList<String>> coreqs) {
        this.coreqs = coreqs;
    }

    /**
     * Used to print the attributes of a course
     * Used for our purposes only to check if FullCourseList is created properly
     */
    public void printCourseValues() {
        System.out.println("Course ID: " + courseID + "\nCourse Name: " + fullCourseName + "\nCredits: " + courseHours);

        System.out.println("Prerequisites: ");
        for (ArrayList<String> separates: prereqs) {
            for (String prereq: separates) {
                System.out.printf(prereq + " ");
            }

            System.out.println();
        }

        System.out.println("Corequisites: ");
        for (ArrayList<String> separates: coreqs) {
            for (String coreq: separates) {
                System.out.printf(coreq + " ");
            }

            System.out.println();
        }
    }

    /**
     * Tells if a course is equal to another course or not
     * Useful when removing a course from FullCourseList by providing the course ID
     * @param courseID The ID of the course (i.e. CS 321)
     * @return Whether or not the two courses are equal
     */
    public boolean equals(String courseID) {
        return this.courseID.equals(courseID);
    }
}
