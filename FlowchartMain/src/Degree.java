import java.io.IOException;
import java.util.*;

/**
 * A Degree class has a major, minor (if applicable), concentration (if applicable), and semesters.
 */
public class Degree {

    private ArrayList <Semester> semesterList;
    private String major;
    private String minor;
    private String concentration;
    private int MAX_SEMESTERS = 8;

    /**
     * Getter for the degree major
     *
     * @return major + concentration
     * This is because majors and concentrations are related
     */
    public String getMajor() {
        if(concentration == null){
            return major;
        }
        else{
            return major + concentration;
        }

    }
    /**
     * Getter for the degree minor
     *
     * @return minor
     */
    public String getMinor() {
        return minor;
    }

    /**
     * Setter for the major
     *
     * @param major The Degree's major
     */
    public void setMajor(String major) {
        this.major = major;

    }

    /**
     * Setter for the minor
     *
     * @param minor The Degree's minor
     */
    public void setMinor(String minor) {
        this.minor = minor;
    }

    /**
     * Setter for the concentration
     *
     * @param concentration The Degree's concentration
     */
    public void setConcentration(String concentration) {
        this.concentration = concentration;
    }

    /**
     * Sets the semesters for the degree by calling DegreeParser, sending the major, minor, and concentration
     * if applicable
     *
     */
    public void setSemesters() throws IOException {

        DegreeParser degreeParser = new DegreeParser();
        String major = this.major;
        String minor = this.minor;

        if(minor == null){
            this.semesterList = degreeParser.degreeParser(major);
        }
        else {
            this.semesterList = degreeParser.degreeParser(major, minor);
        }

    }
    /**
     * Getter for the array list of semesters
     *
     * @return semesterList
     */
    public ArrayList<Semester> getSemesterList() {
        return semesterList;
    }

    /**
     * Adds a course to a semester
     * @param course
     * @param semesterNumber
     * @return 2D array list with pre-req's needed (may have something or may be empty)
     */
    public ArrayList<ArrayList<String>> addCourseToASemester(String course, int semesterNumber){

        //Call to checkPreReq method
        ArrayList<ArrayList<String>> preReqCourses = checkPreReq(course, semesterNumber);

        //if the 2D array is empty then that means that either there were no pre-reqs for the class
        //or the user had the required pre-reqs for the class they wanted to add
        //and so the course is added successfully to the semester
        if (preReqCourses.get(0).isEmpty()) {
            Course newCourse = makeStringToCourseObject(course);
            semesterList.get(semesterNumber - 1).addCourse(newCourse);
        }


        return preReqCourses;

    }
    /**
     * check the pre-reqs for a course
     * @param course
     * @param semesterNumber
     * @return 2D array list with pre-req's needed (may have something or may be empty)
     */
    public ArrayList<ArrayList<String>> checkPreReq(String course, int semesterNumber){
        ArrayList<Course> allPreviousCourses = new ArrayList<>();
        for(int i = 0; i < semesterNumber - 1; i++ ){
            for(int j = 0; j < semesterList.get(i).getCourseList().size(); j++){
                allPreviousCourses.add(semesterList.get(i).getCourseList().get(j));
            }
        }
        Course courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> preReqCourses = courseUserWantsToAdd.getPrereqs();

        int row;
        int column;

        if(preReqCourses.size() != 0) {
            for (row = 0; row < preReqCourses.size(); row++) {
                boolean PreReqs = false;
                for (column = 0; column < preReqCourses.get(row).size(); column++){

                    for (Course allPreviousCours : allPreviousCourses) {
                        if (preReqCourses.get(row).get(column).equals(allPreviousCours.getCourseID())) {
                            PreReqs = true;
                        }
                    }

                }
                if(PreReqs == true){
                    preReqCourses.get(row).remove(column - 1);
                }
            }

        }


        return preReqCourses;

    }
    /**
     * checks the co-reqs for a course
     * @param course
     * @param semesterNumber
     * @return 2D array list with co-req's needed (may have something or may be empty)
     */
    public ArrayList<ArrayList<String>> checkCoReq(String course, int semesterNumber){
        ArrayList<Course> currentSemesterCourses = new ArrayList<>();
        for(int i = 0; i < semesterList.get(semesterNumber - 1).getCourseList().size(); i++){
            currentSemesterCourses.add(semesterList.get(semesterNumber - 1).getCourseList().get(i));
        }

        Course courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> coReqCourses = courseUserWantsToAdd.getCoreqs();

        int row;
        int column;

        if(coReqCourses.size() != 0) {
            for (row = 0; row < coReqCourses.size(); row++) {
                boolean coReqs = false;
                for (column = 0; column < coReqCourses.get(row).size(); column++){

                    for (Course currentSemesterCours : currentSemesterCourses) {
                        if (coReqCourses.get(row).get(column).equals(currentSemesterCours.getCourseID())) {
                            coReqs = true;
                        }
                    }

                }
                if(coReqs == true){
                    coReqCourses.get(row).remove(column - 1);
                    break;
                }
            }

        }


        return coReqCourses;

    }
    public static Course makeStringToCourseObject(String courseID){

        FullCourseList fullCourseList = new FullCourseList();
        return fullCourseList.getCourseByID(courseID);
    }

}