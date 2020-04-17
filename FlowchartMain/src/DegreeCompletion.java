import java.io.IOException;
import java.lang.reflect.Array;
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
    private LinkedList<String> coursesNeededToGraduate;
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

    public void setCoursesRequiredByMajorAndOrMinorAndNonSpecificCoursesRequiredByMajorAndOrMinor(){
        coursesRequiredByMajorAndOrMinor = new LinkedList<>();
        nonSpecificCoursesRequiredByMajorOrMinor = new LinkedList<>();

        for(String course: coursesNeededToGraduate){
            if(checkIfTextIsCourse(course) == true){
               this.coursesRequiredByMajorAndOrMinor.add(course);
            }
            else{
                this.nonSpecificCoursesRequiredByMajorOrMinor.add(course);
            }
        }
    }

    public LinkedList<String> getCoursesNeededToGraduate(){
        return coursesNeededToGraduate;
    }

    //debugging purposes
    public LinkedList<String> getCoursesRequiredByMajorAndOrMinor(){return coursesRequiredByMajorAndOrMinor;}

    //debugging purposes
    public LinkedList<String> getNonSpecificCoursesRequiredByMajorOrMinor(){return nonSpecificCoursesRequiredByMajorOrMinor;}

    public static String[] coursesListAcronyms = {"ACC", "AMS","ARH", "ARS","AST", "ATS", "BYS","BLS","CHE","CH","CE","CM","CPE","CS",
            "ECH","ESS","ECN","ED","EDC","EE","ENG", "EH","EHL","FIN", "GY","GS","HPE","HY","ISE","IS","KIN","MGT",
            "MSC","MS","MKT","MA","MAE","ME","MIL","MU","MUA","MUE","MUJ","NUR","OPE","OPT","PHL","PH","PSC","PRO",
            "PY","SOC","ST","TH","WGS","WLC"};

    public boolean isMajorComplete(Degree degree) throws IOException {

        boolean majorComplete = true;

       ArrayList<Semester> degreeSemesters =  degree.getSemesterList();

       for(Semester singleSemester: degreeSemesters){
           for(Course singleCourse: singleSemester.getCourseList()){
               String singleCourseID = singleCourse.getCourseID();
                    boolean courseRemoved = false;
                    //check to see if its in coursesRequiredByMajorAndOrMinor list
                   for(String singleCourseFromMajorOrMinorList: coursesRequiredByMajorAndOrMinor){
                       if(singleCourseID.equals(singleCourseFromMajorOrMinorList)){
                           coursesNeededToGraduate.removeFirstOccurrence(singleCourseID);
                           courseRemoved = true;
                       }
                   }
                   if(courseRemoved == false){
                        //check if it's a lab science
                       //break

                       //check if it's an elective

                       //check if it's FYE 101

                       //check if it's MA 200+ level course


                       //check if it's
                   }

           }
       }

       if(coursesNeededToGraduate.isEmpty()){
           majorComplete = true;
       }
       else{
           majorComplete = false;
       }

       return majorComplete;
    }

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

    public boolean checkIfElectiveIsValid(String elective){
        //Electives can be taken from any department and do not have to be taken in your major or minor.
        //No more than 4 credit hours of 100 level HPE courses can count toward degree requirements.
        boolean validCourse = true;

        return validCourse;
    }
    public boolean checkIf200PlusCourseIsValid(String course){
        boolean validCourse = true;

        for(String singleNonSpecificCourse: nonSpecificCoursesRequiredByMajorOrMinor){
            String[] arrOfCourse = course.split(" ");
            String[] arrOfSingleNonSpecificCourse = singleNonSpecificCourse.split(" ");

            //remove the first occurrance of that 300+ course with the matching acronym
            if(arrOfCourse[0].equals(arrOfSingleNonSpecificCourse[0])){
                coursesNeededToGraduate.removeFirstOccurrence(arrOfSingleNonSpecificCourse + " 200+");
                validCourse = true;
                break;
            }
            else{
                validCourse = false;
            }

        }
        return validCourse;
    }

    public boolean checkIf300PlusCourseIsValid(String course){
    //Choose CS courses at the 300 level not listed in required courses.
        boolean validCourse = true;

        for(String singleNonSpecificCourse: nonSpecificCoursesRequiredByMajorOrMinor){
            String[] arrOfCourse = course.split(" ");
            String[] arrOfSingleNonSpecificCourse = singleNonSpecificCourse.split(" ");

            //remove the first occurrance of that 300+ course with the matching acronym
            if(arrOfCourse[0].equals(arrOfSingleNonSpecificCourse[0])){
                coursesNeededToGraduate.removeFirstOccurrence(arrOfSingleNonSpecificCourse + " 300+");
                validCourse = true;
                break;
            }
            else{
                validCourse = false;
            }

        }
        return validCourse;
    }

    public boolean checkIf400PlusCourseIsValid(String course){
        boolean validCourse = true;

        for(String singleNonSpecificCourse: nonSpecificCoursesRequiredByMajorOrMinor){
            String[] arrOfCourse = course.split(" ");
            String[] arrOfSingleNonSpecificCourse = singleNonSpecificCourse.split(" ");

            //remove the first occurrance of that 300+ course with the matching acronym
            if(arrOfCourse[0].equals(arrOfSingleNonSpecificCourse[0])){
                coursesNeededToGraduate.removeFirstOccurrence(arrOfSingleNonSpecificCourse + " 400+");
                validCourse = true;
                break;
            }
            else{
                validCourse = false;
            }

        }
        return validCourse;
    }

    public boolean checkIfHumFineArtOrLiteratureIsValid(String course){

        boolean validCourse = true;



        return validCourse;
    }
    public boolean checkIfTechnicalElectiveIsValid(String course){
        //Choose any 300+ level or higher course in the College of Science, IS 400+ course, CPE 412, CPE 436, or PHL 320
        boolean validCourse = true;

        return validCourse;
    }

    public boolean checkIfTextIsCourse(String text){

        boolean trueOrFalse = false;
        String[] arr = text.split(" ");
        for (int i = 0; i < coursesListAcronyms.length; i++) {
            if (arr[0].equals(coursesListAcronyms[i])) {
                if (text.contains("+")) {
                    trueOrFalse = false;
                    break;
                }
                trueOrFalse = true;
                break;

            }
        }
        return trueOrFalse;
    }





}


