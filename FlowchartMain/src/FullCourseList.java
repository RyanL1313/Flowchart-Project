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

                        courseAttributes = (Element) courseInfo.item(1);
                        courseCredits = courseAttributes.getTextContent();

                        courseAttributes = (Element) courseInfo.item(2);
                        courseDescription = courseAttributes.getTextContent();

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


    /**
     * Three strings were pulled using the DOM parser for each courseblock. This method goes through those strings and
     * pulls out the necessary information about the courses
     * @param s1 Contains the course ID and course name
     * @param s2 Contains the credit hours
     * @param s3 Contains the prereqs and coreqs
     */
    private void extractDataFromStrings(String s1, String s2, String s3) {
        String courseDepartment; // ex. CS (used to go to hash map key)
        String courseID;
        String courseName;
        int courseHours = 0;
        ArrayList<ArrayList<Course>> prereqs = new ArrayList<ArrayList<Course>>();
        ArrayList<ArrayList<Course>> coreqs = new ArrayList<ArrayList<Course>>();
        String temp; // To throw out string tokens we don't need
        String prereqAndCoreqDataToParse;

        Course CourseToAdd = new Course(); // The course gets added to FullCourseList after its data has been identified
        boolean hasPrereqs = false; // Marker that signifies if we need to search for prereq courses
        boolean hasCoreqs = false; // Marker that signifies if we need to search for coreq courses

        Scanner courseNameScanner = new Scanner(s1);
        Scanner courseHoursScanner = new Scanner(s2);
        Scanner prereqsAndCoreqsScanner = new Scanner(s3);

        courseDepartment = courseNameScanner.next();
        courseID = courseDepartment + " " + courseNameScanner.next();

        // Setting the course's courseID
        CourseToAdd.setCourseID(courseID);

        temp = courseNameScanner.next(); // Throw out the em dash


        courseName = courseNameScanner.nextLine(); // The rest of this string is the course name

        // Setting the course's fullCourseName
        CourseToAdd.setFullCourseName(courseName);

        temp = courseHoursScanner.nextLine();
        temp = temp.replaceAll("\\D", ""); // So we only read in the hours
        courseHours = Integer.parseInt(temp);
        courseHours = fixCourseHours(courseHours);

        // Setting the courseHours
        CourseToAdd.setHours(courseHours);

        while (prereqsAndCoreqsScanner.hasNext()) {
            temp = prereqsAndCoreqsScanner.next();

            // This denotes if and when the file starts listing out prerequisite courses
            if (temp.contains("Prerequisite") || temp.contains("Co-requisite")) {
                prereqAndCoreqDataToParse = prereqsAndCoreqsScanner.nextLine(); // Right after "Prerequisite" marker, so it should have the prereq courses (We could run into the "Corequisite" marker, though)
                sortOutAndSetPrereqs(prereqAndCoreqDataToParse, CourseToAdd); // Gets the prereqs and sets them to the Course object
            }
        }

        prereqsAndCoreqsScanner = new Scanner(s3); // To go back to the beginning of the string to parse coreqs

        while (prereqsAndCoreqsScanner.hasNext()) {
            temp = prereqsAndCoreqsScanner.next();

            // Same as above but for corequisites
            if (temp.contains("Corequisite") || temp.contains("Co-requisite"))  {
                prereqAndCoreqDataToParse = prereqsAndCoreqsScanner.nextLine(); // Right after "Corequisite" marker, so it should have the prereq courses (We could run into the "Prerequisite" marker, though)
                sortOutAndSetCoreqs(prereqAndCoreqDataToParse, CourseToAdd); // Gets the coreqs and sets them to the Course object
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
     * Prerequisites grouped together in the inner array list are equivalents for the course in question (i.e. on the same row)
     * The outer array lists are separate prerequisites (not equivalent to one another)
     * @param prereqData The string containing the prerequisites that need to be parsed out
     */
    private void sortOutAndSetPrereqs(String prereqData, Course courseToAdd) {
        ArrayList<ArrayList<String>> allPrereqsForThisCourse = new ArrayList<ArrayList<String>>();
        ArrayList<String> rowOfPrereqs = new ArrayList<String>(); // Equivalent courses (i.e. you can take one course or this course and you have acquired the necessary prerequisite)
        String equivMarker = "or"; // When this is encountered, the prereq has equivalents that will be read in next (same row)
        String separateMarker = "and"; // When this is encountered, the next prereq is not equivalent to the previous and must be in a new ArrayList (new row)
        Scanner prereqScanner = new Scanner(prereqData);
        String courseID; // Stores the various courseIDs of the prereqs as you loop through the string
        boolean moreCourses; // Used to check if there are more prereq courses that need to be read in
        String nextToken; // Used after a courseID is read in to check for "or" or "and"
        boolean getOutFlag = false; // Used to get out of the while loop if need be

        while (prereqScanner.hasNext()) {
            courseID = prereqScanner.next() + " " + prereqScanner.next(); // Read in course ID
            rowOfPrereqs.add(courseID);

            if (prereqScanner.hasNext()) { // Then more courses are available, otherwise, we just read in the last course
                nextToken = prereqScanner.next(); // "or" or "and"
                if (nextToken.contains("Corequisite") || nextToken.contains("Co-requisite")) { // Don't want to read the coreqs in as prereqs also
                    getOutFlag = true;
                    break; // We have to get out of the while loop; don't read in any more data
                }
                if (nextToken.toLowerCase().equals(equivMarker)) { // The course we just read in has equivalents, add them to the same ArrayList
                    continue; // Goes to beginning of the loop where a course gets added to the row

                } else if (nextToken.toLowerCase().equals(separateMarker)) { // The course we just read in is not equivalent to the last one; new row
                    allPrereqsForThisCourse.add(rowOfPrereqs); // All done with this row, add it
                    rowOfPrereqs = new ArrayList<String>(); // Reset rowOfPrereqs to read in courses of different value
                }
            }
        }

        if (!rowOfPrereqs.isEmpty()) // Another row of courses has yet to be added
            allPrereqsForThisCourse.add(rowOfPrereqs);

        courseToAdd.setPrereqs(allPrereqsForThisCourse); // Add the prereqs to the course in question
    }

    /**
     * Corequisites grouped together in the inner array list are equivalents for the course in question (i.e. on the same row)
     * The outer array lists are separate corequisites (not equivalent to one another)
     * @param coreqData The string containing the corequisites that need to be parsed out
     */
    private void sortOutAndSetCoreqs(String coreqData, Course CourseToAdd) {
        ArrayList<ArrayList<String>> allCoreqsForThisCourse = new ArrayList<ArrayList<String>>();
        ArrayList<String> rowOfCoreqs = new ArrayList<String>(); // Equivalent courses (i.e. you can take one course or this course and you have acquired the necessary corequisite)
        String equivMarker = "or"; // When this is encountered, the coreq has equivalents that will be read in next (same row)
        String separateMarker = "and"; // When this is encountered, the next coreq is not equivalent to the previous and must be in a new ArrayList (new row)
        Scanner coreqScanner = new Scanner(coreqData);
        String courseID; // Stores the various courseIDs of the coreqs as you loop through the string
        String nextToken; // Used after a courseID is read in to check for "or" or "and"
        boolean getOutFlag = false; // Used to get out of the while loop if need be

        while (coreqScanner.hasNext() && getOutFlag == false) {
            courseID = coreqScanner.next() + " " + coreqScanner.next();
            rowOfCoreqs.add(courseID);

            if (coreqScanner.hasNext()) { // Then more courses are available, otherwise, we just read in the last course
                nextToken = coreqScanner.next(); // "or" or "and". Could also be prerequisite marker; in that case, no more corequisites to be read in
                if (nextToken.contains("Prerequisite") || nextToken.contains("Pre-requisite")) { // Don't want to read the prereqs in as coreqs also
                    getOutFlag = true;
                    break; // We have to get out of the while loop; don't read in any more data
                }
                if (nextToken.toLowerCase().equals(equivMarker)) { // The course we just read in has equivalents, add them to the same ArrayList
                    continue; // Goes to beginning of the loop where a course gets added to the row

                } else if (nextToken.toLowerCase().equals(separateMarker)) { // The course we just read in is not equivalent to the last one; new row
                    allCoreqsForThisCourse.add(rowOfCoreqs); // All done with this row, add it
                    rowOfCoreqs = new ArrayList<String>(); // Reset rowOfPrereqs to read in courses of different value
                }
            }
        }

        if (!rowOfCoreqs.isEmpty()) // Another row of courses has yet to be added
            allCoreqsForThisCourse.add(rowOfCoreqs);

        CourseToAdd.setCoreqs(allCoreqsForThisCourse); // Add the coreqs to the course in question
    }

    /**
     * This internal method is used to fix when, after using regex to isolate the credit hours, two different credit
     * hours totals combine into one. (ex. if a course says 3-6 semester hours, after regex it will say 36, so remove
     * the 6. Can't really account for the varying hours so we'll just take the lower value)
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
        int indexToRemove = findCourse(courseID); // Index in the linked list with the associated key
        String courseDepartment;  // Stores the key value of the FullCourseList hash map

        Scanner courseIDScanner = new Scanner(courseID);
        courseDepartment = courseIDScanner.next();

        LinkedList<Course> ListToShorten = FullCourseList.get(courseDepartment); // Return the linked list for the necessary department

        return ListToShorten.remove(indexToRemove); // Remove the course from FullCourseList and return the course object
    }

    /**
     * Checks if a specified course is in FullCourseList by checking if the ID of the course is in FullCourseList
     * Returns -1 if the course is not found in FullCourseList
     * @param courseID The courseID (i.e. CS 321) (Space required)
     * @return The index of the course in the corresponding linked list for that course's department (i.e. The CS linked list)
     */
    public int findCourse(String courseID) {
        String courseDepartment; // Stores the key value of the FullCourseList hash map

        Scanner courseIDScanner = new Scanner(courseID);
        courseDepartment = courseIDScanner.next();

        LinkedList<Course> ListToShorten = FullCourseList.get(courseDepartment); // Return the linked list for the necessary department

        Iterator<Course> courseIterator = ListToShorten.iterator();
        for (int i = 0; i < ListToShorten.size(); i++) {
            if (courseIterator.hasNext()) {
                if (courseIterator.next().getCourseID().equals(courseID))
                    return i; // Index of the course in the associated linked list
            }
        }
        return -1; // The course was not found in FullCourseList
    }

    /**
     * Used to print every course in FullCourseList
     * Used for debugging purposes
     */
    public void printFullCourseList() {
        for (HashMap.Entry<String, LinkedList<Course>> entry: FullCourseList.entrySet()) {
            String key = entry.getKey();
            System.out.printf(key + " courses:\n");

            LinkedList<Course> courseList = new LinkedList<>();
            courseList = entry.getValue();

            Iterator<Course> courseListIterator = courseList.iterator();

            while (courseListIterator.hasNext()) {
                courseListIterator.next().printCourseValues();
            }
        }

    }
}