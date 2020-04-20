/**
 * A course has a course ID, name, prerequisites and corequisites list, and number of credit hours.
 * A course is the most concrete object type used in this program
 * The setter methods will be called by the FullCourseList class
 * The nested Array Lists for prereqs and coreqs are necessary to distinguish equivalent prereqs and coreqs from separate preregs and coregs.
 * Equivalents are in the same row (or same ArrayList in the ArrayList of ArrayLists), separates are on different rows (different ArrayList inside the ArrayList of ArrayLists)
 */
public class BroadCourse implements Course{
    private String fullCourseName;
    private String courseID;

    /**
     * Course constructor
     * If the attributes end up staying with these values, then that shows us an error in file reading occurred.
     */
    BroadCourse(String broadTitle) {
        fullCourseName = broadTitle;
        courseID = broadTitle;
    }

    /**
     * Getter for the course ID
     * @return The course ID
     */
    public String getCourseID() {
        return courseID;
    }

    @Override
    public int getHours()
    {
        return 0;
    }

    /**
     * Getter for the full course name
     * @return The full course name
     */
    public String getFullCourseName() {
        return fullCourseName;
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
     * Tells if a course is equal to another course or not
     * Useful when removing a course from FullCourseList by providing the course ID
     * @param courseID The ID of the course (i.e. CS 321)
     * @return Whether or not the two courses are equal
     */
    public boolean equals(String courseID) {
        return this.courseID.equals(courseID);
    }
}

