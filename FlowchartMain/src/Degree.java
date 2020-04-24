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
                    for (Course previousCourse : allPreviousCourses) {
                        if (preReqCourses.get(row).get(column).equals(previousCourse.getCourseID())) {
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
                            //semesterList.get(i).removeCourse(list.get(x)[1]);   // BroadCourse type
                            result = semesterList.get(i).removeCourse(list.get(x)[1]);
                            if(result == null)
                                break;
                            else
                            {
                                result = DegreeParser.removeFromCRG(result);
                                x = 16;
                                return result;
                            }
                        }
                    }
                    x++;
                }
            }
        }
        return result;
    }

    /**
     * Shifts semesterList after a course is removed from it so that each semester stays between 12-18 credit hours
     */
    public static void shiftSemesters() {
        semesterList.get(0).removeCourse("EH 101");
        semesterList.get(0).removeCourse("CS 102");
        semesterList.get(1).getCourseList().add(0, FullCourseList.getCourseByID("PH 111"));
       // semesterList.get(3).removeCourse("CS 309");
       // semesterList.get(3).removeCourse("CS 321");
       // semesterList.get(1).removeCourse("MA 172");
        //semesterList.get(3).removeCourse("MA 244");
       // semesterList.get(6).removeCourse("EH 301");

        int numberOfShifts = 1;

        while (!checkIfSemestersHaveValidHourRange()) { // Only want to shift semesters if necessary
            for (int i = 0; i < semesterList.size(); i++) {
                System.out.println(numberOfShifts++ + " shift:");
                System.out.println("Semester " + (i + 1) + " hours: " + semesterList.get(i).getSemesterHours());
                if (semesterList.get(i).getSemesterHours() < 12 && i != semesterList.size() - 1) { // Can't do this for the last semester
                    for (int courseIndex = 0; courseIndex < semesterList.get(i + 1).getTotalNumberOfCourses(); courseIndex++) { // Checking if we can move any courses from the next semester down
                        if (checkIfBroadCourse(semesterList.get(i + 1).getCourseList().get(courseIndex))) { // Check if a broad course is in the next semester up. If so, shift that course up.
                            semesterList.get(i).getCourseList().add(semesterList.get(i + 1).getCourseList().get(courseIndex)); // Shifting the broad course up
                            semesterList.get(i + 1).getCourseList().remove(courseIndex); // Also, remove the shifted course from the semester it was previously in
                            break;
                        } else { // It's a specific course, so we need to check if the previous semester contains prereqs for this course, and if this course has coreqs in this semester. If no for both conditions, the course can be shifted.
                            SpecificCourse courseToCheck = (SpecificCourse) semesterList.get(i + 1).getCourseList().get(courseIndex);

                            if (!checkIfThisCourseHasCoreqs(courseToCheck) && checkIfShiftableBasedOnPrereqsForThisCourse(courseToCheck, semesterList.get(i).getCourseList(), i)) {
                                semesterList.get(i).getCourseList().add(semesterList.get(i + 1).getCourseList().get(courseIndex)); // Shifting the specific course up
                                semesterList.get(i + 1).getCourseList().remove(courseIndex); // Also, remove the shifted course from the semester it was previously in
                                break;
                            }
                        }
                    }
                }

            }

        }
    }

    /**
     * Checks if every semester in semesterList has between 12-18 hours.
     * Used in shiftSemesters.
     * @return Whether or not the above statement is true.
     */
    public static boolean checkIfSemestersHaveValidHourRange() {
        for (int i = 0; i < semesterList.size(); i++) {
            if (semesterList.get(i).getSemesterHours() < 12 || semesterList.get(i).getSemesterHours() > 18)
                return false;
        }

        return true; // Otherwise, all the semesters had a valid amount of hours.
    }

    /**
     * Checks if a given course is a BroadCourse object
     * Used in shiftSemesters
     * @param course The course object in question
     * @return Whether or not the course is a BroadCourse object.
     */
    public static boolean checkIfBroadCourse(Course course) {
        return course.getClass().toString().contains("BroadCourse"); // True if it's a broad course, false if specific course
    }

    /**
     * Checks if a course in the next semester has prereqs in the this semester.
     * Used in shiftSemesters to check if a course can be shifted to this semester
     * @param courseInNextSemester The course in question
     * @param coursesInThisSemester List of courses in the current semester which will have their prereqs checked
     * @param currentIndexOfSemesterList Current semester being evaluated; used when calling Planner.electivePrereqAddError
     * @return Whether or not the prereqs allow for this shifting
     */
    public static boolean checkIfShiftableBasedOnPrereqsForThisCourse(SpecificCourse courseInNextSemester, ArrayList<Course> coursesInThisSemester, int currentIndexOfSemesterList) {

        ArrayList<ArrayList<String>> prereqsForCourseInQuestion = courseInNextSemester.getPrereqs();

        for (ArrayList<String> andRelationshipCourses : prereqsForCourseInQuestion) {
            for (String orRelationshipCourse : andRelationshipCourses) { // If none of the strings match the strings of courses in this semester, the course is still shiftable.
                for (Course courseInThisSemester : coursesInThisSemester) {
                    // Firstly, a prereq for the course could be in the current semester. This could be fine if the prereq has an "or" relationship with another course, and that other course is in a previous semester.
                    if (orRelationshipCourse.equals(courseInThisSemester.getCourseID()) && !Planner.electivePrereqAddError(courseInNextSemester.getCourseID(), currentIndexOfSemesterList).equals("")) // Then the course can't be shifted, return false
                        return false;
                }
            }
        }

        return true; // Otherwise, the course is able to be shifted to the current semester. Shift it.
    }

    /**
     * Checks if a course has coreqs.
     * If a course does have coreqs, then it shouldn't be moved unless we're moving a whole semester up and then removing the semester
     * which happens when the last semester dips below 12 credit hours.
     * @param course The course in the next semester that needs to be checked if it can be shifted
     * @return Whether or not the course can be shifted based on its coreqs
     */
    public static boolean checkIfThisCourseHasCoreqs(SpecificCourse course) {
        ArrayList<ArrayList<String>> coreqs = course.getCoreqs();
        if (coreqs.size() != 0) {// The course has coreqs and should be left alone (unless shifting from the last semester up for removing a semester)
            return true;}

        return false; // The course does not have coreqs, can be messed with if its prereqs don't cause issues
    }

}



