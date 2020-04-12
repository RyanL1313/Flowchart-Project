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
     * @return arrayList
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


    public boolean addCourseToASemester(String course, int semesterNumber){

        boolean returnBool = true;
        if(checkPreReq(course, semesterNumber) == false || checkCoReq(course, semesterNumber) == false){
            returnBool = false;
        }
        else{
            returnBool = true;
            Course newCourse = new Course();
            newCourse = makeStringToCourseObject(course);
            semesterList.get(semesterNumber - 1).addCourse(newCourse);
        }
        return returnBool;
    }
    public boolean checkPreReq(String course, int semesterNumber){
        ArrayList<Course> allPreviousCourses = new ArrayList<>();
        for(int i = 0; i < semesterNumber; i++ ){
            for(int j = 0; j < semesterList.get(i).getCourseList().size(); j++){
                allPreviousCourses.add(semesterList.get(i).getCourseList().get(j));
            }
        }
        Course courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> preReqCourses = courseUserWantsToAdd.getPrereqs();

        boolean sendBackBool = true;

        int row;
        int column;

        if(preReqCourses.size() != 0) {
            for (row = 0; row < preReqCourses.size(); row++) {
                boolean PreReqs = false;
                for (column = 0; column < preReqCourses.get(row).size(); column++){

                    for(int k = 0; k < allPreviousCourses.size(); k++) {
                        if (preReqCourses.get(row).get(column).equals(allPreviousCourses.get(k).getCourseID())) {
                            PreReqs = true;
                        }
                    }

                }
                if(PreReqs == false){
                    sendBackBool = false;
                    break;
                }
            }

        }


        return sendBackBool;

    }

    public boolean checkCoReq(String course, int semesterNumber){
        ArrayList<Course> currentSemesterCourses = new ArrayList<>();
        for(int i = 0; i < semesterList.get(semesterNumber - 1).getCourseList().size(); i++){
            currentSemesterCourses.add(semesterList.get(semesterNumber - 1).getCourseList().get(i));
        }

        Course courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> coReqCourses = courseUserWantsToAdd.getCoreqs();

        boolean sendBackBool = true;

        int row;
        int column;

        if(coReqCourses.size() != 0) {
            for (row = 0; row < coReqCourses.size(); row++) {
                boolean coReqs = false;
                for (column = 0; column < coReqCourses.get(row).size(); column++){

                    for(int k = 0; k < currentSemesterCourses.size(); k++) {
                        if (coReqCourses.get(row).get(column).equals(currentSemesterCourses.get(k).getCourseID())) {
                            coReqs = true;
                        }
                    }

                }
                if(coReqs == false){
                    sendBackBool = false;
                    break;
                }
            }

        }


        return sendBackBool;

    }
    public static Course makeStringToCourseObject(String courseID){

        FullCourseList fullCourseList = new FullCourseList();
        Course newCourse = fullCourseList.getCourseByID(courseID);
        return newCourse;
    }

}
