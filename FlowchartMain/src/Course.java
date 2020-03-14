import java.util.*;

/**
 * A course has a course ID, name, prerequisites and corequisites list, and number of credit hours.
 * A course is the most concrete object type used in this program
 * The setter methods will be called by the FullCourseList class
 */
public class Course {
    private ArrayList<Course> prereqs = new ArrayList<>(); // ArrayList of prerequisite courses
    private ArrayList<Course> coreqs = new ArrayList<>(); // ArrayList of corequisite courses
    private int courseHours; // Hours the course satisfies
    private String fullCourseName; // Full name of the course
    private String courseID; // Course ID with 2-3 letters then 3 numbers (ex. CS 321, ARH 101)

    /**
     * Course constructor
     * If the attributes end up staying with these values, then that shows us an error in file reading occurred.
     */
    public Course() {
        courseHours = -1;
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
    public ArrayList<Course> getPrereqs() {
        return prereqs;
    }

    /**
     * Getter for the corequisites list
     * @return the corequisites list
     */
    public ArrayList<Course> getCoreqs() {
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
     * @param prereqs The list of prerequisites
     */
    public void setPrereqs(ArrayList<Course> prereqs) {
        this.prereqs = prereqs;
    }

    /**
     * Setter for the list of corequisite courses
     * This list comes from the FullCourseList (FullCourseList will use this setCoreqs method)
     * @param coreqs The list of corequisites
     */
    public void setCoreqs(ArrayList<Course> coreqs) {
        this.coreqs = coreqs;
    }
}
