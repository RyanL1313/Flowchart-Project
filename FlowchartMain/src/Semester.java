import java.util.*;


public class Semester {
    private ArrayList<Course> courseList = new ArrayList<>(); // the number of hours
    private int semesterHours = 0;


    public void updateSemesterHours()
    {
        //itterates through courseList
        for (Course course : courseList) semesterHours += course.getHours(); // tallies the hours of each class
    }
    
    public void addCourse(Course a)
    {
        courseList.add(a);
    }

    public Course removeCourse(String cID)
    {
        for (Course course : courseList) { //itterates through courseList
            if (cID.equals(course.getCourseID())) {
                courseList.remove(course);
                return course;
            }
        }
        return null;
    }
}