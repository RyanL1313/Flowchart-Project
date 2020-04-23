import java.io.IOException;
import java.util.*;

/**
 * A Degree class has a major, minor (if applicable), concentration (if applicable), and semesters.
 */
public class Degree {

    private static ArrayList <Semester> semesterList;
    private static String major;
    private static String minor;
    private int MAX_SEMESTERS = 8;

    static DegreeParser degreeParser = new DegreeParser();
    /**
     * Getter for the degree major
     *
     * @return major + concentration
     * This is because majors and concentrations are related
     */
    public String getMajor() {
        return major;

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
     * @param Major The Degree's major
     */
    public void setMajor(String Major) {
        this.major = major;

    }

    /**
     * Setter for the minor
     *
     * @param Minor The Degree's minor
     */
    public void setMinor(String Minor) {
        this.minor = minor;
    }

    /**
     * Sets the semesters for the degree by calling DegreeParser, sending the major and minor
     *
     */
    public static void setSemesters(ArrayList<Semester> semesters) throws IOException {
        semesterList = semesters;

       /* if(minor.equals("N/A Minor")) {
            semesterList = degreeParser.degreeParser(major);
        }
        else {
            semesterList = degreeParser.degreeParser(major, minor);

        }*/

    }
    /**
     * Getter for the array list of semesters
     *
     * @return semesterList
     */
    public static ArrayList<Semester> getSemesterList() {
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
        java.util.ArrayList<Course> allPreviousCourses = new ArrayList<>();
        for(int i = 0; i < semesterNumber - 1; i++ ){
            for(int j = 0; j < semesterList.get(i).getCourseList().size(); j++){
                allPreviousCourses.add(semesterList.get(i).getCourseList().get(j));
            }
        }
        SpecificCourse courseUserWantsToAdd = makeStringToCourseObject(course);
        ArrayList<ArrayList<String>> preReqCourses = courseUserWantsToAdd.getPrereqs();
        Boolean PreReqs;
        ArrayList<ArrayList<String>> newPreReqCourses = new ArrayList<ArrayList<String>>(); // This 2D array list has rows removed if the user has at least one prereq on that row

        if(preReqCourses.size() != 0) {
            for (int row = 0; row < preReqCourses.size(); row++) {
                PreReqs = false;
                for (int column = 0; column < preReqCourses.get(row).size(); column++){
                    for (Course allPreviousCours : allPreviousCourses) {
                        if (preReqCourses.get(row).get(column).equals(allPreviousCours.getCourseID())) {
                            PreReqs = true;
                        }
                    }
                }
                if (PreReqs == false){
                    newPreReqCourses.add(preReqCourses.get(row));
                }
            }

        }


        return newPreReqCourses;
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

        SpecificCourse courseUserWantsToAdd = makeStringToCourseObject(course);
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

    public static SpecificCourse makeStringToCourseObject(String courseID){

        FullCourseList fullCourseList = new FullCourseList();
        return fullCourseList.getCourseByID(courseID);
    }




//    public boolean checkIfDegreeIsComplete() throws IOException {
//        DegreeParser degreeParser = new DegreeParser();
//        degreeParser.degreeParser(major);
//        LinkedList<String> coursesRequiredToGraduate = degreeParser.getCoursesRequiredToGraduate();
//
//
//    }

    public static void printSemesters() {
        for (Semester sem : semesterList) {
            for (Course c : sem.getCourseList()) {
                System.out.println("The course is " + c.getCourseID());
            }
        }
    }

    public Course removeObtainedCredit(String courseID)
    {
        Course result = null;

        ArrayList<String[]> list = new ArrayList<String[]>();           // Order of priority
        list.add(Planner.getCS400PlusCourseIDs());                      // CS 400+
        list.add(Planner.getMA400PlusCourseIDs());                      // MA 400+
        list.add(Planner.getCS300PlusCourseIDs());                      // CS 300+
        list.add(Planner.getMA300PlusCourseIDs());                      // MA 300+
        list.add(Planner.getCS200PlusCourseIDs());                      // CS 200+
        list.add(Planner.getMA200PlusCourseIDs());                      // MA 200+
        list.add(Planner.getHistoryCourseIDs());                        // History
        list.add(Planner.getSocialAndBehavioralSciencesCourseIDs());    // Soc. & Behav. Sciences
        list.add(Planner.getHumanitiesCourseIDs());                     // Humanities
        list.add(Planner.getLiteratureCourseIDs());                     // Literature
        list.add(Planner.getLabScienceCourseIDs());                     // Lab Sciences
        list.add(Planner.getTechnicalElectiveCourseIDs());              // Technical Electives
        list.add(Planner.getFineArtsCourseIDs());                       // Fine Arts
        list.add(Planner.getST400PlusCourseIDs());                      // ST 400+
        list.add(Planner.getElective300PlusCourseIDs());                // Elective 300+
        list.add(Planner.getElectives(1));                         // Electives

        for(int i = 0; i < semesterList.size(); i++)
        {
            result = semesterList.get(i).removeCourse(courseID);
            if(result != null)      // if result is an instance of SpecificCourse
                return result;
            else                    // else result is an instance of BroadCourse
            {
                int x = 0;
                while(x < 16)   // check if the Course ID is in each list, it will always be in Electives at the end.
                {
                    for(int j = 0; j < list.get(x).length; j++)     // checks each list for the course ID.
                    {
                        if(courseID.equals(list.get(x)[j]))     // if found, the semester removes that BroadCourse
                        {
                            semesterList.get(i).removeCourse(list.get(x)[1]);   // BroadCourse type
                            x = 16;
                            break;
                        }
                    }
                    x++;
                }
            }
        }
        return result;
    }

}



