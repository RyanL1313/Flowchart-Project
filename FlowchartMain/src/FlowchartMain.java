import java.io.IOException;
import java.util.*;

public class FlowchartMain {
    public static void main(String[] args) throws IOException {
        System.out.println("This is the main class for our whole project. Just testing things out.");

        ArrayList<Semester> degreeSemesters = new ArrayList<>();
        Degree degreeObj = new Degree();

        degreeObj.setMajor("Computer Science Major");
        degreeObj.setMinor(null);
        degreeObj.setConcentration(null);

        degreeObj.setSemesters();

       String output =  degreeObj.addCourseToASemester("MA 201", 3);
       System.out.println(output);
//        FullCourseList fullCourseList = new FullCourseList();
//        Course course = new Course();
//        course = fullCourseList.getCourseByID("CS 214");

//        course.printCourseValues();
       // FullCourseList courseList = new FullCourseList(); // CourseList contains a hashmap of all of UAH's courses which is used throughout this program
    }
}
