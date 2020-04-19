/**
 * A course has a course ID, name, prerequisites and corequisites list, and number of credit hours.
 * A course is the most concrete object type used in this program
 * The setter methods will be called by the FullCourseList class
 * The nested Array Lists for prereqs and coreqs are necessary to distinguish equivalent prereqs and coreqs from separate preregs and coregs.
 * Equivalents are in the same row (or same ArrayList in the ArrayList of ArrayLists), separates are on different rows (different ArrayList inside the ArrayList of ArrayLists)
 */
public interface Course {

    /**
     * Getter for the course ID
     * @return The course ID
     */
    String getCourseID();

    /**
     * Getter for the full course name
     * @return The full course name
     */
     String getFullCourseName();

    /**
     * Getter for the course's hours
     * @return The course's hours
     */
    int getHours();

    /**
     * Setter for the course ID
     * @param courseID The course ID
     */
    void setCourseID(String courseID);



    /**
     * Tells if a course is equal to another course or not
     * Useful when removing a course from FullCourseList by providing the course ID
     * @param courseID The ID of the course (i.e. CS 321)
     * @return Whether or not the two courses are equal
     */
    boolean equals(String courseID);
}
