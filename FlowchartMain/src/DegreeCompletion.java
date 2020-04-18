import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * A DegreeCompletion class is responsible for checking whether a certain degree is complete or not.
 * Because of time constraints, the methods that determine whether a degree is complete in this class
 * will be "hard-coded" since degree requirements are unique to a degree
 */

public class DegreeCompletion {
    private static String major;
    private static String minor;
    private static LinkedList<String> coursesNeededToGraduate;
    private LinkedList<String> coursesRequiredByMajorAndOrMinor;
    private LinkedList<String> nonSpecificCoursesRequiredByMajorOrMinor;
    static DegreeParser degreeParser = new DegreeParser();

    public void setMajor(String major) {
        this.major = major;

    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public void setCoursesNeededToGraduate() throws IOException {
        if(minor.equals("N/A Minor")){
            degreeParser.degreeParser(major);
        }
        else{
            degreeParser.degreeParser(major, minor);
        }
        coursesNeededToGraduate = degreeParser.getCoursesRequiredToGraduate();
    }

//    public void setCoursesRequiredByMajorAndOrMinorAndNonSpecificCoursesRequiredByMajorAndOrMinor(){
//        coursesRequiredByMajorAndOrMinor = new LinkedList<>();
//        nonSpecificCoursesRequiredByMajorOrMinor = new LinkedList<>();
//
//        for(String course: coursesNeededToGraduate){
//            if(checkIfTextIsCourse(course) == true){
//               this.coursesRequiredByMajorAndOrMinor.add(course);
//            }
//            else{
//                this.nonSpecificCoursesRequiredByMajorOrMinor.add(course);
//            }
//        }
//    }

    public LinkedList<String> getCoursesNeededToGraduate(){
        return coursesNeededToGraduate;
    }

    //debugging purposes
    public LinkedList<String> getCoursesRequiredByMajorAndOrMinor(){return coursesRequiredByMajorAndOrMinor;}

    //debugging purposes
    public LinkedList<String> getNonSpecificCoursesRequiredByMajorOrMinor(){return nonSpecificCoursesRequiredByMajorOrMinor;}

    /**
     * Checks if the degree is complete by making sure all of the classes inside the semesters
     * are valid and then removes the courses from coursesNeededToGraduate.
     * @return LinkedList<Strings> of courses that are still needed to graduate OR it can be empty meaning
     * the degree is completed
     * @param degree the degree the user created
     */
    public LinkedList<String> isDegreeComplete(Degree degree) throws IOException {

        boolean majorComplete = true;

       ArrayList<Semester> degreeSemesters =  degree.getSemesterList();

       for(Semester singleSemester: degreeSemesters){
           for(Course singleCourse: singleSemester.getCourseList()){
               String singleCourseID = singleCourse.getCourseID();
                    //check to see if its in coursesRequiredByMajorAndOrMinor list
                   for(String singleCourseFromMajorOrMinorList: coursesRequiredByMajorAndOrMinor){
                       if(singleCourseID.equals(singleCourseFromMajorOrMinorList)){
                           coursesNeededToGraduate.removeFirstOccurrence(singleCourseID);
                           break;
                       }
                   }

           }
       }

       return coursesNeededToGraduate;
    }

    /**
     * Checks if the course is an already required course.
     * @return true or false of whether the course is an already required course
     */
    public boolean checkIfItsAnAlreadyRequiredCourse(String course){
        boolean alreadyRequiredCourse =  false;
        for(String singleReqCourse: coursesRequiredByMajorAndOrMinor){
            if(course.equals(singleReqCourse)){
                alreadyRequiredCourse = true;
                break;
            }
        }
        return alreadyRequiredCourse;
    }

    /**
     * Whenever the user selects a course from the drop-down options, that course will be sent back to
     * here and removed from coursesNeedToGraduate
     * @param typeOfCourse type of course the user selects
     */
    public void removeCourseFromCoursesNeededToGraduate(String typeOfCourse){
        for(int i = 0; i < coursesNeededToGraduate.size(); i++){
            if(typeOfCourse.equals(coursesNeededToGraduate.get(i))){
                coursesNeededToGraduate.removeFirstOccurrence(coursesNeededToGraduate.get(i));
            }
        }
    }
    /**
     * In case that the user changes their mind about the course they selected, the type of course
     * previously selected is added back to coursesNeededToGraduate
     * @param typeOfCourse type of course the user selects
     */
    public void addBackInCourse(String typeOfCourse){
        coursesNeededToGraduate.add(typeOfCourse);
    }



}


