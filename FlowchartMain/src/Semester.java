import java.util.*;


public class Semester {
    private ArrayList<Course> courseList = new ArrayList<>(); // the number of hours
    private int semesterHours = 0;


    public void updateSemesterHours()
    {
        //iterates through courseList
        for (Course course : courseList) semesterHours += course.getHours(); // tallies the hours of each class
    }

    //May not need this method here
    public void addCourse(Course a)
    {
        courseList.add(a);
    }

    public Course removeCourse(String cID)
    {
        for (Course course : courseList) { //iterates through courseList
            if (cID.equals(course.getCourseID())) {
                courseList.remove(course);
                return course;
            }
        }
        return null;
    }

    public Course getCourse(String courseID){

        Course returnedCourse = new Course();

        for(Course course: courseList){
            if(courseID.equals(course.getCourseID())){
                returnedCourse = course;
                break;
            }
        }
        return returnedCourse;
    }
    public int getSemesterHours(){

        return semesterHours;
    }

    public ArrayList<Course> getCourseList(){
        return courseList;
    }

    public int getTotalNumberOfCourses(){
        int counter = 0;
        for(Course couse: courseList){
            counter++;
        }
        return counter;
    }


}