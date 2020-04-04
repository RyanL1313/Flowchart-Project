
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



/**
 * A DegreeParser class scans a file that has information of a degree and sends that information to Degree class.
 */
    public class DegreeParser {

    private static LinkedList<String> coursesReqToGraduate = new LinkedList<>();

    public static String[] coursesListAcronyms = {"ACC", "AMS","ARH", "ARS","AST", "ATS", "BYS","BLS","CHE","CH","CE","CM","CPE","CS",
                       "ECH","ESS","ECN","ED","EDC","EE","ENG", "EH","EHL","FIN", "GY","GS","HPE","HY","ISE","IS","KIN","MGT",
                        "MSC","MS","MKT","MA","MAE","ME","MIL","MU","MUA","MUE","MUJ","NUR","OPE","OPT","PHL","PH","PSC","PRO",
                        "PY","SOC","ST","TH","WGS","WLC"};


public ArrayList<Semester> degreeParser(String minor, String major) throws IOException {
//public static void main(String[] args) throws IOException {

        String majorName = major + ".html";
        //String majorName = "Degree Plans/Mathematical_Sciences_Major.html";

        String minorName = minor + ".html";
        //String minorName = "Degree Plans/Mathematical Sciences Minor.html";

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
                    || arr1.get(j).contains("Ex:") || arr1.get(j).contains("Total") || arr1.get(j).contains("See Requirements")
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

                        Course newCourse1 = new Course();
                        Course newCourse3 = new Course();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                        //System.out.println(RegCourse);
                        //System.out.println(labCourse);
                    }
                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2 = new Course();
                        if(checkIfItsACourse == true) {
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else{
                            coursesReqToGraduate.add(newText);
                        }

                       // System.out.println(newText);

                    }
                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));
                semester.updateSemesterHours();
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

                        Course newCourse1 = new Course();
                        Course newCourse3 = new Course();
                        newCourse1 = makeStringToCourseObject(RegCourse);
                        newCourse3 = makeStringToCourseObject(labCourse);
                        semester.addCourse(newCourse1);
                        semester.addCourse(newCourse3);
                       //System.out.println(RegCourse);
                       //System.out.println(labCourse);
                    }
                    //course doesn't have lab
                    else{
                        boolean checkIfItsACourse = checkIfTextIsCourse(newText);
                        Course newCourse2 = new Course();
                        if(checkIfItsACourse == true){
                            String courseWithOutDesc = takeAwayCourseDescr(newText);
                            newCourse2 = makeStringToCourseObject(courseWithOutDesc);
                            coursesReqToGraduate.add(newText);
                            semester.addCourse(newCourse2);
                        }
                        else{
                            coursesReqToGraduate.add(newText);
                        }



                    }

                    i++;
                    if (i == arr1.size()) {
                        i--;
                        break;
                    }
                } while (!arr1.get(i).contains("Fall") && !arr1.get(i).equals("Spring") && !arr1.get(i).contains("Year"));
                semester.updateSemesterHours();
                semesterArrayList.add(semester);
            }
            text1 = arr1.get(i);
        }

        //minor HTML is parsed by tag "tr" and put into the LinkedList
        for (Element tr : table2.getElementsByTag("tr")) {
            arr2.add(tr.text());
            //System.out.println(tr.text());
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
                coursesReqToGraduate.add(newText);
            }

        }

//       for(int k = 0; k < semesterArrayList.size(); k++){
//           System.out.println(semesterArrayList.get(k));
//       }
        return semesterArrayList;
//        for(String singleCourse: coursesReqToGraduate){
//            System.out.println(singleCourse);
//        }
    }


    public static String removeCreditHrs(String text){

        if(text.equals("Elective 3") || text.equals("Elective 1")) {
            return text;
        }
        //String[] arrOfText = text.split(" ");
        StringBuilder sb = new StringBuilder(text);
        sb.deleteCharAt(text.length() - 1);
        String newText = sb.toString();
        return newText;
    }

    public static Course makeStringToCourseObject(String courseID){

        FullCourseList fullCourseList = new FullCourseList();
        Course newCourse = fullCourseList.getCourseByID(courseID);
        return newCourse;
    }

    public static boolean checkIfTextIsCourse(String text){

        boolean trueOrFalse = false;
        String[] arr = text.split(" ");
        for(int i = 0; i < coursesListAcronyms.length;i++){
            if(arr[0].equals(coursesListAcronyms[i])) {
                if(text.contains("+")){
                    trueOrFalse = false;
                    break;
                }
                trueOrFalse = true;
                break;

            }
        }

        return trueOrFalse;

    }


    public static String[] splitCourseAndLab(String text){

        String[] arr = text.split(" ");
        String course = arr[0] + " " + arr[1];
        String lab = arr[3] + " " + arr[4];

        String[] courseAndLab = {course, lab};

        return courseAndLab;
    }

    public static String takeAwayCourseDescr(String text){

        String[] arr = text.split(" ");
        String courseAcronym = arr[0];
        String courseID = arr[1];

        String courseIDplusAcr = courseAcronym + " " + courseID;

        return courseIDplusAcr;
    }

    public LinkedList getCoursesRequiredToGraduate(){
        return coursesReqToGraduate;
    }

}










