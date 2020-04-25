import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


/**
 * A DegreeParser class scans a file that has information of a degree and sends that information to Degree class.
 */
public class DegreeParser {

    private static LinkedList<String> coursesReqToGraduate = new LinkedList<>();

    public static String[] coursesListAcronyms = {"ACC", "AMS","ARH", "ARS","AST", "ATS", "BYS","BLS","CHE","CH","CE","CM","CPE","CS",
            "ECH","ESS","ECN","ED","EDC","EE","ENG", "EH","EHL","FIN", "FYE", "GY","GS","HPE","HY","ISE","IS","KIN","MGT",
            "MSC","MS","MKT","MA","MAE","ME","MIL","MU","MUA","MUE","MUJ","NUR","OPE","OPT","PHL","PH","PSC","PRO",
            "PY","SOC","ST","TH","WGS","WLC"};

    /**
     * Parses an HTML file for a major and minor
     * @param major The provided major
     * @param minor The provided minor
     * @return The array list of Semester objects as determined in the parsing
     * @throws IOException
     */
    public ArrayList<Semester> degreeParser(String major, String minor) throws IOException
    {
        String majorName = "Degree Plans/" + major + ".html";

        String minorName = "Degree Plans/" + minor + ".html";

        Document doc1 = Jsoup.parse(new File(majorName), "utf-8");

        Document doc2 = Jsoup.parse(new File(minorName), "utf-8");

        /*
        Removes all of the unnecessary parts of the major html document
         */
        doc1.select("div#tabs").remove();
        doc1.select("div#textcontainer").remove();
        doc1.select("div#requirementstextcontainer").remove();

        //Selects the first table of the major html
        Element table1 = doc1.selectFirst("table");

        //ArrayList of Semesters
        ArrayList<Semester> semesterArrayList = new ArrayList<>();

        //When the major HTML is parsed, all of its text is put into this list
        List<String> arr1 = new LinkedList<>();


        //Selects the first table of the minor html
        Element table2 = doc2.selectFirst("table");

        //When the minor HTML is parsed, all of its text is put into this list
        List<String> arr2 = new LinkedList<>();


        //major HTML is parsed by tag "tr" and put into the LinkedList
        for (Element tr : table1.getElementsByTag("tr")) {
            arr1.add(tr.text());
        }

        //Removes unncessary sentences from linkedlist
        for (int j = 0; j < arr1.size(); j++) {
            if (arr1.get(j).contains("Electives") || arr1.get(j).contains("No more than") || arr1.get(j).contains("Term Semester")
                    || arr1.get(j).contains("If interested") || arr1.get(j).contains("Choose") || arr1.get(j).contains("For a")
                    || arr1.get(j).contains("Ex:") || arr1.get(j).contains("Total") || arr1.get(j).startsWith("See Requirements")
                    || arr1.get(j).startsWith("or") || arr1.get(j).startsWith("if considering")) {
                arr1.remove(j);
                j--;
            }
        }

        //This loop goes through the linkedlist and organizes it into an array of courses that
        //are then put into a semester object and then put into ArrayList of Semesters
        int i = 0;
        String text1 = arr1.get(i);
        while (text1.contains("Year") && i < arr1.size()) {
            String yearName = text1;
            i++;
            text1 = arr1.get(i);
            if (text1.contains("Fall")) {
                Semester semester = new Semester();
                i++;
                do {
                    text1 = arr1.get(i);
                    String newText = removeCreditHrs(text1);
                    //if course has a lab
                    if(newText.contains("&")){
                        String[] courseWithLab ={};
                        courseWithLab = splitCourseAndLab(newText);

                        String RegCourse = courseWithLab[0];
                        String labCourse = courseWithLab[1];

                        Course newCourse1 = new SpecificCourse();
                        Course newCourse3 = new SpecificCourse();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                    }

                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2;
                        if(newText.contains("+") || newText.contains("Lab Science") || newText.contains("Elective")
                                || newText.contains("Literature") || newText.contains("Fine Art") || newText.contains("Social")
                                || newText.contains("Humanities") || newText.contains("History")){
                            newCourse2 = new BroadCourse(newText);
                        }
                        else
                        {
                            newCourse2 = new SpecificCourse();
                        }
                        if(checkIfItsACourse == true) {
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else {
                            newText = removeSeeRequirementsTab(newText);
                            newCourse2.setCourseID(newText);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                    }
                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));
                //semester.updateSemesterHours();
                semesterArrayList.add(semester);
            }
            text1 = arr1.get(i);
            if (text1.equals("Spring")) {
                Semester semester = new Semester();
                i++;
                do {
                    text1 = arr1.get(i);
                    String newText = removeCreditHrs(text1);
                    //if course has a lab
                    if(newText.contains("&")){
                        String[] courseWithLab ={};
                        courseWithLab = splitCourseAndLab(newText);

                        String RegCourse = courseWithLab[0];
                        String labCourse = courseWithLab[1];

                        Course newCourse1 = new SpecificCourse();
                        Course newCourse3 = new SpecificCourse();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                    }
                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2;
                        if (newText.contains("+") || (newText.contains("+") || newText.contains("Lab Science") || newText.contains("Elective")
                                || newText.contains("Literature") || newText.contains("Fine Art") || newText.contains("Social")
                                || newText.contains("Humanities") || newText.contains("History"))){
                            newCourse2 = new BroadCourse(newText);
                        }
                        else
                        {
                            newCourse2 = new SpecificCourse();
                        }
                        if(checkIfItsACourse == true){
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else{
                            newText = removeSeeRequirementsTab(newText);
                            newCourse2.setCourseID(newText);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                    }

                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));

               // semester.updateSemesterHours();
                semesterArrayList.add(semester);
            }

            text1 = arr1.get(i);
        }

        // Minor HTML file is parsed by "tr" tags
        for (Element tr : table2.getElementsByTag("tr")) {
            arr2.add(tr.text());
        }

        arr2.remove(0);
        arr2.remove(arr2.size()-1);
        for(int j = 0; j < arr2.size(); j++) {
            String text = arr2.get(j);
            String newText = removeCreditHrs(text);

            boolean checkIfItsACourse = checkIfTextIsCourse(newText);
            if(checkIfItsACourse == true){
                String courseWithOutDesc = takeAwayCourseDescr(newText);
                coursesReqToGraduate.add(newText);
            }
            else{
                if (!newText.contains("recommended")) {
                    coursesReqToGraduate.add(newText);
                }
            }
        }

        removeCoreCoursesFromCNG(coursesReqToGraduate);
        if (coursesReqToGraduate.getLast().contains("ST 300+"))
            separateIntoTwoEntriesMAOrST();

        if (minor.equals("Computer Science Minor")) {
            semesterArrayList.get(1).getCourseList().add(0, new BroadCourse("Elective"));
            semesterArrayList.get(7).getCourseList().add(0, new BroadCourse("Elective"));
        }
        else if (minor.equals("Mathematical Sciences Minor")) {
            semesterArrayList.get(6).getCourseList().add(0, new BroadCourse("Elective"));
            semesterArrayList.get(7).getCourseList().add(0, new BroadCourse("Elective"));
        }
        Degree.setSemesters(semesterArrayList);

        return semesterArrayList;
    }

    /**
     * Parses an HTML file for a major only.
     * @param major The provided major
     * @return The array list of semester objects as determined in the parsing
     * @throws IOException
     */
    public ArrayList<Semester> degreeParser(String major) throws IOException
    {
        String majorName = "Degree Plans/" + major + ".html";

        Document doc1 = Jsoup.parse(new File(majorName), "utf-8");

        /*
        Removes all of the unnecessary parts of the major html document
         */
        doc1.select("div#tabs").remove();
        doc1.select("div#textcontainer").remove();
        doc1.select("div#requirementstextcontainer").remove();

        //Selects the first table of the major html
        Element table1 = doc1.selectFirst("table");

        //ArrayList of Semesters
        ArrayList<Semester> semesterArrayList = new ArrayList<>();

        //When the major HTML is parsed, all of its text is put into this list
        List<String> arr1 = new LinkedList<>();

        //major HTML is parsed by tag "tr" and put into the LinkedList
        for (Element tr : table1.getElementsByTag("tr")) {
            arr1.add(tr.text());
        }

        //Removes unncessary sentences from linkedlist
        for (int j = 0; j < arr1.size(); j++) {
            if (arr1.get(j).contains("Electives") || arr1.get(j).contains("No more than") || arr1.get(j).contains("Term Semester")
                    || arr1.get(j).contains("If interested") || arr1.get(j).contains("Choose") || arr1.get(j).contains("For a")
                    || arr1.get(j).contains("Ex:") || arr1.get(j).contains("Total") || arr1.get(j).startsWith("See Requirements")
                    || arr1.get(j).startsWith("or")) {
                arr1.remove(j);
                j--;
            }
        }

        //This loop goes through the linkedlist and organizes it into an array of courses that
        //are then put into a semester object and then put into ArrayList of Semesters
        int i = 0;
        String text1 = arr1.get(i);
        while (text1.contains("Year") && i < arr1.size()) {
            String yearName = text1;
            i++;
            text1 = arr1.get(i);
            if (text1.contains("Fall")) {
                Semester semester = new Semester();
                i++;
                do {
                    text1 = arr1.get(i);
                    String newText = removeCreditHrs(text1);
                    //if course has a lab
                    if(newText.contains("&")){
                        String[] courseWithLab ={};
                        courseWithLab = splitCourseAndLab(newText);

                        String RegCourse = courseWithLab[0];
                        String labCourse = courseWithLab[1];

                        Course newCourse1 = new SpecificCourse();
                        Course newCourse3 = new SpecificCourse();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                    }
                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2;
                        if(newText.contains("+") || (newText.contains("+") || newText.contains("Lab Science") || newText.contains("Elective")
                                || newText.contains("Literature") || newText.contains("Fine Art") || newText.contains("Social")
                                || newText.contains("Humanities") || newText.contains("History"))) {
                            newCourse2 = new BroadCourse(newText);
                        }
                        else
                        {
                            newCourse2 = new SpecificCourse();
                        }
                        if(checkIfItsACourse == true) {
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else {
                            newText = removeSeeRequirementsTab(newText);
                            newCourse2.setCourseID(newText);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                    }
                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));
                semesterArrayList.add(semester);
            }
            text1 = arr1.get(i);
            if (text1.equals("Spring")) {
                Semester semester = new Semester();
                i++;
                do {
                    text1 = arr1.get(i);
                    String newText = removeCreditHrs(text1);
                    //if course has a lab
                    if(newText.contains("&")){
                        String[] courseWithLab ={};
                        courseWithLab = splitCourseAndLab(newText);

                        String RegCourse = courseWithLab[0];
                        String labCourse = courseWithLab[1];

                        Course newCourse1 = new SpecificCourse();
                        Course newCourse3 = new SpecificCourse();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                    }
                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2;
                        if(newText.contains("+") || (newText.contains("+") || newText.contains("Lab Science") || newText.contains("Elective")
                                || newText.contains("Literature") || newText.contains("Fine Art") || newText.contains("Social")
                                || newText.contains("Humanities") || newText.contains("History"))){
                            newCourse2 = new BroadCourse(newText);
                        }
                        else
                        {
                            newCourse2 = new SpecificCourse();
                        }
                        if(checkIfItsACourse == true){
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else{
                            newText = removeSeeRequirementsTab(newText);
                            newCourse2.setCourseID(newText);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }



                    }

                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));
                semesterArrayList.add(semester);
            }
            text1 = arr1.get(i);
        }

        removeCoreCoursesFromCNG(coursesReqToGraduate);
        Degree.setSemesters(semesterArrayList);

        return semesterArrayList;
    }

    /**
     * Remove the credit hours from course string
     * @param text The original course string
     * @return The updated course string
     */
    public static String removeCreditHrs(String text){

        if(text.equals("Elective 3") || text.equals("Elective 1")) {
            text = text.substring(0, text.length() - 2);
            return text;
        }

        if (text.contains("course 3 or")) {
            text = text.substring(0, text.length() - 6);
            return text;
        }

        StringBuilder sb = new StringBuilder(text);
        sb.deleteCharAt(text.length() - 1);
        String newText = sb.toString();
        return newText;
    }

    /**
     * Takes a course ID and finds that SpecificCourse object in FullCourseList.
     * @param courseID The course ID
     * @return The SpecificCourse object
     */
    public static SpecificCourse makeStringToCourseObject(String courseID){
        SpecificCourse newCourse = FullCourseList.getCourseByID(courseID);
        return newCourse;
    }

    /**
     * Checks if the string contains an actual SpecificCourse type
     * @param text The string to check
     * @return Whether or not it's an actual specific course offered at UAH
     */
    public static boolean checkIfTextIsCourse(String text){

        boolean trueOrFalse = false;
        if (text.contains("recommended"))
            return false;

        String[] arr = text.split(" ");
        for(int i = 0; i < coursesListAcronyms.length;i++){
            if(arr[0].equals(coursesListAcronyms[i])) {
                if(text.contains("+")){
                    return false;
                }
                trueOrFalse = true;
                break;

            }
        }

        return trueOrFalse;

    }

    /**
     * Split the course and lab section into two separate courses.
     * @param text String to split
     * @return The course and lab section strings
     */
    public static String[] splitCourseAndLab(String text){

        String[] arr = text.split(" ");
        String course = arr[0] + " " + arr[1];
        String lab = arr[3] + " " + arr[4];

        String[] courseAndLab = {course, lab};

        return courseAndLab;
    }

    /**
     * Remove the course description from SpecificCourse course IDs
     * @param text Original course
     * @return Trimmed-down course
     */
    public static String takeAwayCourseDescr(String text){

        String[] arr = text.split(" ");
        String courseAcronym = arr[0];
        String courseID = arr[1];

        String courseIDplusAcr = courseAcronym + " " + courseID;

        return courseIDplusAcr;
    }

    /**
     * Remove see requirements tab from courses with this in them.
     * @param text Original course
     * @return Trimmed-down course
     */
    private String removeSeeRequirementsTab(String text) {
        int index = 0; // Index where the parentheses start
        if (text.contains("(See Requirements tab") || text.contains("See requirements tab")) {
            index = text.indexOf("(See");
            text = text.substring(0, index - 1); // Removing that part
        }

        return text;
    }

    /**
     * Removes the core courses from courses needed to graduate
     */
    public void removeCoreCoursesFromCNG(LinkedList<String> originalCNG) {
        Iterator<String> courseIterator = originalCNG.iterator();

        while (courseIterator.hasNext())
        {
            String course = courseIterator.next();
            if (checkIfTextIsCourse(course)) {
                courseIterator.remove();
            }

        }
    }

    /**
     * Used to separate the "Select two MA or ST 300+ or 400+ level courses" entry in coursesReqToGraduate
     * into two separate entries.
     * @return The new altered string
     */
    public String separateIntoTwoEntriesMAOrST() {
        String original = coursesReqToGraduate.getLast();
        original = original.substring(11, original.length() - 3); // Making it neater

        coursesReqToGraduate.remove(coursesReqToGraduate.size() - 1); // Remove the original
        coursesReqToGraduate.add(original); // Add the new ones
        coursesReqToGraduate.add(original); // Add it an extra time

        return original;
    }

    /**
     * Gets the linked list of courses still needed to be satisfied in the student's degree.
     * @return The courses needed to graduate
     */
    public static LinkedList getCoursesRequiredToGraduate(){
        return coursesReqToGraduate;
    }

    public static Course removeFromCRG(Course cID)
    {
        String courseID = cID.getCourseID();
        for(int i = 0; i < coursesReqToGraduate.size(); i++)
        {
            if(coursesReqToGraduate.get(i).contains(courseID))
            {
                coursesReqToGraduate.remove(i);
                return cID;
            }
        }
        return null;
    }

}










