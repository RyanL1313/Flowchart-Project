import java.io.IOException;
import java.util.*;

public class FlowchartMain {
    public static void main(String[] args) throws IOException {
        System.out.println("This is the main class for our whole project. Just testing things out.");
        FullCourseList courseList = new FullCourseList(); // CourseList contains a hashmap of all of UAH's courses which is used throughout this program
        ArrayList<Semester> degreeSemesters = new ArrayList<>();
        Degree degreeObj = new Degree();
        Planner plan = new Planner();
        Semester tempSemester = new Semester();
        tempSemester.addCourse(FullCourseList.getCourseByID("EH 101"));
        tempSemester.addCourse(FullCourseList.getCourseByID("CS 105"));
        tempSemester.addCourse(FullCourseList.getCourseByID("MA 171"));
        tempSemester.addCourse(FullCourseList.getCourseByID("CS 102"));
        degreeSemesters.add(tempSemester);
        tempSemester = new Semester();
        tempSemester.addCourse(FullCourseList.getCourseByID("EH 102"));
        tempSemester.addCourse(FullCourseList.getCourseByID("CS 121"));
        tempSemester.addCourse(FullCourseList.getCourseByID("MA 172"));
        degreeSemesters.add(tempSemester);
        tempSemester = new Semester();
        //tempSemester.addCourse(FullCourseList.getCourseByID("CS 221"));
        degreeSemesters.add(tempSemester);

       // courseList.printFullCourseList();
        degreeObj.setSemesterList(degreeSemesters);
        //String errorMessage = plan.electivePrereqAddError("CS 221", 3);


       /* for (ArrayList<String> orRelationships : prereqsNeeded) {
            Iterator<String> courseIterator = orRelationships.iterator();
            while (courseIterator.hasNext()) {
                System.out.printf(courseIterator.next() + " or ");
            }
            System.out.printf("and ");
        }*/
    }
}
