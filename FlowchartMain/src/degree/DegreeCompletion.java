package degree;
import parsing.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * A DegreeCompletion class is responsible for checking whether a certain degree is complete or not.
 * Because of time constraints, the methods that determine whether a degree is complete in this class
 * will be "hard-coded" since degree requirements are unique to a degree
 */

public class DegreeCompletion {

    static DegreeParser degreeParser = new DegreeParser();

    /**
     * Removes a broad course from CoursesReqToGraduate in the DegreeParser class
     * Goes through a hierarchy of substring checks to determine what to remove (General elective at the bottom of hierarchy).
     * @param selectedCourseType Type of course the user selects in the flowchart dropdown
     * @return Whatever was removed from coursesReqToGraduate. It may need to be added back to coursesReqToGraduate
     * if the student wants to erase a node in the drop-down, so that's why we need to return what was removed.
     */
    public static String removeCourseFromCoursesNeededToGraduate(String selectedCourseType){
        LinkedList<String> CRG = DegreeParser.getCoursesRequiredToGraduate(); // Courses required to graduate
        Iterator courseIterator = DegreeParser.getCoursesRequiredToGraduate().iterator();
        String courseInIteration; // The string currently being read from CRG
        String courseRemoved = ""; // Course removed from CRG. Gets returned.

        // Remove from CRG based on the option the student selected from the drop-down
        switch (selectedCourseType) {
            case "MA 200+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("MA 200+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration; // We will return this course
                        break;
                    }
                }
                break;
            case "MA 300+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("MA 300+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                    if (courseInIteration.contains("ST 300+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "MA 400+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("MA 400+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                    if (courseInIteration.contains("ST 300+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "CS 200+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("CS 200+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "CS 300+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("CS 300+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "CS 400+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("CS 400+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "ST 400+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("ST 300+")) { // ST 300+ or 400+ (no ST 300 courses)
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Literature":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Literature")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Soc. & Behav. Science":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Social and Behavioral Science")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Fine Art":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Fine Art")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "History":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("History")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Lab Science":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Lab Science")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Technical Elective":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Technical Elective")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "Humanities":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Humanities")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "General Elective 300+":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.contains("Elective 300+")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
                break;
            case "General Elective":
                while (courseIterator.hasNext()) {
                    courseInIteration = (String) courseIterator.next();
                    if (courseInIteration.trim().equals("Elective")) {
                        CRG.removeFirstOccurrence(courseInIteration);
                        courseRemoved = courseInIteration;
                        break;
                    }
                }
            default: // Not really necessary. All cases are covered
        }
        return courseRemoved; // Could be the empty string if the user's selected course type is not in CRG. Therefore, nothing was removed in this case
    }
    /**
     * In case that the user changes their mind about the course they selected, the type of course
     * previously selected is added back to coursesNeededToGraduate
     * @param typeOfCourse type of course the user selects
     */
    public static void addBackInCourse(String typeOfCourse) {
        LinkedList<String> CRG = DegreeParser.getCoursesRequiredToGraduate();
        CRG.add(typeOfCourse);

    }



}


