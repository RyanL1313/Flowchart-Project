import java.util.*;

/**
 * A semester is composed of course objects. It needs to be its own class so it can have its own hours.
 */
public class Semester {
    private ArrayList<Course> courseList = new ArrayList<>(); // the number of hours
    private int semesterHours = 0;

    /**
     * Updates the semester's hours as courses get added to it
     */
    public void setSemesterHours()
    {
        semesterHours = 0;
        //iterates through courseList
        for (Course course : courseList) semesterHours += course.getHours(); // tallies the hours of each class
    }

    /**
     * Adds a course to the semester
     * @param a Course to add
     */
    public void addCourse(Course a)
    {
        courseList.add(a);
    }

    /**
     * Removes a course from the courseList array list
     * @param cID The course ID
     * @return The Course object associated with the course ID
     */
    public Course removeCourse(String cID)
    {
        for (Course course : courseList) { //iterates through courseList
            if (cID.equals(course.getCourseID())) {
                courseList.remove(course);
                return course;
            }
            else if(course.getCourseID().contains(cID))
            {
                courseList.remove(course);
                return course;
            }
        }
        return null;
    }

    /**
     * Returns the course object in this semester based on a given course ID string
     * @param courseID The course ID
     * @return The course Object
     */
    public Course getCourse(String courseID){

        Course returnedCourse;
        if(courseID.contains("+"))
            returnedCourse = new BroadCourse(courseID);
        else
            returnedCourse = new SpecificCourse();

        for(Course course: courseList){
            if(courseID.equals(course.getCourseID())){
                returnedCourse = course;
                break;
            }
        }
        return returnedCourse;
    }

    /**
     * Getter for the hours in the semester
     * @return
     */
    public int getSemesterHours(){
        setSemesterHours();
        return semesterHours;
    }

    /**
     * Gets the array list of courses in this semester
     * @return Course list for this semester
     */
    public ArrayList<Course> getCourseList(){
        return courseList;
    }

    /**
     * Gets how many courses are in this Semester object
     * @return Number of courses in a semester
     */
    public int getTotalNumberOfCourses(){
        int counter = 0;
        for(Course course: courseList){
            counter++;
        }
        return counter;
    }

}