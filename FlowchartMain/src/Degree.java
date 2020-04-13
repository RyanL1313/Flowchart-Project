import java.io.IOException;
import java.util.*;

/**
 * A Degree class has a major, minor (if applicable), concentration (if applicable), and semesters.
 */
public class Degree {

    private static ArrayList <Semester> semesterList; // Ryan - made these static so when Planner creates a Degree object,
    private static String major;
    private static String minor;
    private static String concentration;
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
     * Sets semesterList to a given semester list
     * Made by Ryan to test methods in this class (can delete later)
     * @param semesters A given array list of semesters
     */
    public void setSemesterList(ArrayList<Semester> semesters) {
        semesterList = semesters;
    }

    public ArrayList<ArrayList<String>> addCourseToASemester(String course, int semesterNumber){

        ArrayList<ArrayList<String>> preReqCourses = checkPreReq(course, semesterNumber);

        if (preReqCourses.get(0).isEmpty()) {
            Course newCourse = makeStringToCourseObject(course);
            semesterList.get(semesterNumber - 1).addCourse(newCourse);
        }

          return preReqCourses;

    }
    public ArrayList<ArrayList<String>> checkPreReq(String course, int semesterNumber){
        ArrayList<Course> allPreviousCourses = new ArrayList<>();
        for(int i = 0; i < semesterNumber - 1; i++ ){
            for(int j = 0; j < semesterList.get(i).getCourseList().size(); j++){
                allPreviousCourses.add(semesterList.get(i).getCourseList().get(j));
            }
        }
        Course courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> preReqCourses = courseUserWantsToAdd.getPrereqs();
        Boolean PreReqs;
        ArrayList<ArrayList<String>> newPreReqCourses = new ArrayList<ArrayList<String>>(); // This 2D array list has rows removed if the user has at least one prereq on that row

        if(preReqCourses.size() != 0) {
            for (int row = 0; row < preReqCourses.size(); row++) {
                System.out.println("Row " + row);
                System.out.println(preReqCourses.size());
                PreReqs = false;
                for (int column = 0; column < preReqCourses.get(row).size(); column++){
                    System.out.println("Column " + column);
                    for (Course allPreviousCours : allPreviousCourses) {
                        if (preReqCourses.get(row).get(column).equals(allPreviousCours.getCourseID())) {
                            System.out.println(preReqCourses.get(row).get(column) + " Equals " + allPreviousCours.getCourseID());
                            PreReqs = true;
                        }
                        else
                            System.out.println(preReqCourses.get(row).get(column) + " Does not Equals " + allPreviousCours.getCourseID());
                    }

                }
                if (PreReqs == false){
                    newPreReqCourses.add(preReqCourses.get(row));
                }
                System.out.println("IN HERE: BOOL IS " + PreReqs);
            }

        }


        return newPreReqCourses;

    }

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
