import java.util.*;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * The FullCourseList is designed to hold every undergraduate class offered at UAH
 * In the FullCourseList hashmap, the key is the first part of the course ID (i.e. CS), and the
 * value is a linked list of courses associated with that key.
 */
public class FullCourseList {
    private static HashMap<String, LinkedList<Course>> FullCourseList = new HashMap<String, LinkedList<Course>>(); // The full list of UAH courses
    private ArrayList<String> HTMLFileList = new ArrayList<String>(); // List of file names for all departments

    /**
     * The FullCourselist constructor goes through each HTML file for each department code and uses DOM parsing to
     * extract out the courseblock sections of each file. These courseblock sections have 3 subsections of information
     * about the course, which get extracted. From there, it's just parsing out
     * the strings to get the course code,
     * full course name, number of credits, prerequisites, and corequisites, which is done through separate methods
     * but called in the constructor.
     */
    FullCourseList() {
        // These get data extracted from them in the extractDataFromStrings method
        String courseTitle; // Contains the course code, full course name
        String courseCredits; // Contains the credit hours for this course
        String courseDescription; // Contains prerequisites and corequisites

        String fileName; // File name. Changes each iteration

        createFileNameList();
        ListIterator<String> fileIterator= HTMLFileList.listIterator();
        for (String nameOfFile: HTMLFileList) {
            fileName = fileIterator.next();

            File inputFile = new File("./HTML Files (FCL)/" + fileName);

            try {
                // Create the parser
                DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
                DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
                Document doc = dBuilder.parse(inputFile);

                NodeList courseBlockList = doc.getElementsByTagName("div"); // Returns a list of course blocks in the file

                // Now go through each course block
                for (int i = 0; i < courseBlockList.getLength(); i++) {
                    Element courseBlock = (Element) courseBlockList.item(i);

                    if (courseBlock.getNodeType() == Node.ELEMENT_NODE) {
                        NodeList courseInfo = courseBlock.getElementsByTagName("p"); // Returns a list of the information in courseBlock

                        Element courseAttributes = null; // Will hold the three items in courseInfo

                        // Obtaining the 3 strings with course data in the course block
                        courseAttributes = (Element) courseInfo.item(0);
                        courseTitle = courseAttributes.getTextContent();
                        System.out.println("s1: " + courseTitle);

                        courseAttributes = (Element) courseInfo.item(1);
                        courseCredits = courseAttributes.getTextContent();
                        System.out.println("s2: " + courseCredits);

                        courseAttributes = (Element) courseInfo.item(2);
                        courseDescription = courseAttributes.getTextContent();
                        System.out.println("s3: " + courseDescription);

                        // Extract the necessary course attributes out of these strings
                        extractDataFromStrings(courseTitle, courseCredits, courseDescription);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Add the name of each HTML file that will be parsed (The HTML files are saved to the project)
     */
    private void createFileNameList() {
        // Add all html file names to HTMLFileList
        HTMLFileList.add("ACC.html"); HTMLFileList.add("AMS.html"); HTMLFileList.add("ARH.html");
        HTMLFileList.add("ARS.html"); HTMLFileList.add("AST.html");
        HTMLFileList.add("BYS.html"); HTMLFileList.add("BLS.html"); HTMLFileList.add("CHE.html");
        HTMLFileList.add("CH.html"); HTMLFileList.add("CE.html"); HTMLFileList.add("CM.html");
        HTMLFileList.add("CPE.html"); HTMLFileList.add("CS.html"); HTMLFileList.add("ECH.html");
        HTMLFileList.add("ESS.html"); HTMLFileList.add("ECN.html"); HTMLFileList.add("ED.html");
        HTMLFileList.add("EDC.html"); HTMLFileList.add("EE.html"); HTMLFileList.add("ENG.html");
        HTMLFileList.add("EH.html"); HTMLFileList.add("EHL.html"); HTMLFileList.add("FIN.html");
        HTMLFileList.add("GY.html"); HTMLFileList.add("GS.html"); HTMLFileList.add("HPE.html");
        HTMLFileList.add("HY.html"); HTMLFileList.add("ISE.html"); HTMLFileList.add("IS.html");
        HTMLFileList.add("KIN.html"); HTMLFileList.add("MGT.html"); HTMLFileList.add("MSC.html");
        HTMLFileList.add("MS.html"); HTMLFileList.add("MKT.html"); HTMLFileList.add("MA.html");
        HTMLFileList.add("MAE.html"); HTMLFileList.add("MIL.html");
        HTMLFileList.add("MU.html"); HTMLFileList.add("MUA.html"); HTMLFileList.add("MUE.html");
        HTMLFileList.add("MUJ.html"); HTMLFileList.add("NUR.html"); HTMLFileList.add("OPE.html");
        HTMLFileList.add("OPT.html"); HTMLFileList.add("PHL.html"); HTMLFileList.add("PH.html");
        HTMLFileList.add("PSC.html"); HTMLFileList.add("PRO.html"); HTMLFileList.add("PY.html");
        HTMLFileList.add("SOC.html"); HTMLFileList.add("ST.html"); HTMLFileList.add("TH.html");
        HTMLFileList.add("WGS.html"); HTMLFileList.add("WLC.html");
    }



    private void extractDataFromStrings(String s1, String s2, String s3) {
        String courseDepartment; // ex. CS (used to go to hash map key)
        String courseID;
        String courseName;
        int courseHours = 0;
        ArrayList<ArrayList<Course>> prereqs = new ArrayList<ArrayList<Course>>();
        ArrayList<ArrayList<Course>> coreqs = new ArrayList<ArrayList<Course>>();
        String temp; // To throw out string tokens we don't need
        String preReqDataToParse;
        Course CourseToAdd = new Course(); // The course gets added to FullCourseList after its data has been identified

        Scanner s1Scanner = new Scanner(s1);
        Scanner s2Scanner = new Scanner(s2);
        Scanner s3Scanner = new Scanner(s3);

        courseDepartment = s1Scanner.next();
        courseID = courseDepartment + " " + s1Scanner.next();

        // Setting the course's courseID
        CourseToAdd.setCourseID(courseID);
        System.out.println("courseID: " + courseID);

        temp = s1Scanner.next(); // Throw out the em dash


        courseName = s1Scanner.nextLine();

        // Setting the course's fullCourseName
        CourseToAdd.setFullCourseName(courseName);
        System.out.println("courseName: " + " " + courseName);


        temp = s2Scanner.nextLine();
        temp = temp.replaceAll("\\D", "");
        courseHours = Integer.parseInt(temp);
        courseHours = fixCourseHours(courseHours);

        // Setting the courseHours
        CourseToAdd.setHours(courseHours);
        System.out.println("courseHours: " + courseHours);

        while (s3Scanner.hasNext()) {
            temp = s3Scanner.next();

            // This denotes if and when the file starts listing out prerequisite courses
            if (temp.contains("Prerequisite")) {
                System.out.println("Has prereqs");
                sortOutAndSetPrereqs(s3Scanner.nextLine());
            }


        }

        // Adding the course to the hashmap
        if (FullCourseList.containsKey(courseDepartment)) // ex. If this isn't the first CS class being put in the hash map
        {
            LinkedList<Course> updatedList = FullCourseList.get(courseDepartment); // Getting the linked list associated with the department code
            updatedList.add(CourseToAdd); // Adding the completed course to the list
            FullCourseList.put(courseDepartment, updatedList); // Putting the updated list back into FullCourseList
        }
        else { // ex. If this is the first CS class being put in the hashmap => new linked list is added to hashmap
            LinkedList<Course> newList = new LinkedList<Course>();
            newList.add(CourseToAdd);
            FullCourseList.put(courseDepartment, newList);
        }
    }

    /**
     * Getter for the FullCourseList hashmap for other classes to use
     * @return The FullCourseList hashmap
     */
    public HashMap<String, LinkedList<Course>> getFullCourseList() {
        return FullCourseList;
    }

    /**
     * Prerequisites grouped together in the inner array list are equivalents for the course in question
     * The outer array lists are separate prerequisites (not equivalent to one another)
     * @param prereqData The string containing the prerequisites that needs to be parsed out
     */
    private void sortOutAndSetPrereqs(String prereqData) {
        ArrayList<ArrayList<String>> allPrereqsForThisCourse = new ArrayList<ArrayList<String>>();
        System.out.println("In here: " + prereqData);
        String equivPrereqMarker = "or"; // When this is encountered, you can either take this prereq or other equivalent courses


    }

    /**
     * This internal method is used to fix when, after using regex to isolate the credit hours, two different credit
     * hours totals combine into one. (ex. if a course says 3-6 semester hours, after regex it will say 36, so remove
     * the 6.)
     * @param hours The original value of the credit hours that may need to be changed
     * @return The fixed number of credit hours
     */
    private int fixCourseHours(int hours) {
        if (hours >= 10) // Then it read in two different hours totals; we need the first one
            return hours / 10;
        else
            return hours; // Otherwise just keep the original value
    }

    /**
     * Remove a course from the FullCourseList
     * @param courseID
     * @return The course to be removed
     */
    public Course removeCourse(String courseID) {
        int indexToRemove = findCourse(courseID);
        String courseDepartment;
        int courseNumber;

        Scanner courseIDScanner = new Scanner(courseID);

        courseDepartment = courseIDScanner.next();
        courseNumber = Integer.parseInt(courseIDScanner.next());

        LinkedList<Course> ListToShorten = FullCourseList.get(courseDepartment); // Return the linked list for the necessary department

        return ListToShorten.remove(indexToRemove);
    }

    /**
     * Checks if a specified course is in FullCourseList by checking if the ID of the course is in FullCourseList
     * Returns -1 if the course is not found in FullCourseList
     * @param courseID The courseID (i.e. CS 321) (Space required)
     * @return The index of the course in the corresponding linked list for that course's department (i.e. The CS linked list)
     */
    public int findCourse(String courseID) {
        String courseDepartment;
        int courseNumber;

        Scanner courseIDScanner = new Scanner(courseID);

        courseDepartment = courseIDScanner.next();
        courseNumber = Integer.parseInt(courseIDScanner.next());

        LinkedList<Course> ListToShorten = FullCourseList.get(courseDepartment); // Return the linked list for the necessary department

        Iterator<Course> courseIterator = ListToShorten.iterator();
        for (int i = 0; i < ListToShorten.size(); i++) {
            if (courseIterator.hasNext()) {
                if (courseIterator.next().getCourseID().equals(courseID))
                    return i;
            }
        }
        return -1;
    }
}