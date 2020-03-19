import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import java.util.LinkedList;
import java.util.ListIterator;
import javax.xml.parsers.*;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

/**
 * The FullCourseList is designed to hold every undergraduate class offered at UAH
 * In the FullCourseList hashmap, the key is the first part of the course ID (i.e. CS), and the
 * value is a linked list of courses associated with that key.
 */
public class FullCourseList {
    private HashMap<String, LinkedList<Course>> FullCourseList = new HashMap<String, LinkedList<Course>>(); // The full list of UAH courses
    private ArrayList<String> HTMLFileList = new ArrayList<String>(); // List of file names for all departments

    /**
     * The FullCourselist constructor goes through each HTML file for each department code and uses DOM parsing to
     * extract out the courseblock sections of each file. These courseblock sections have 3 subsections of information
     * about the course, which get extracted. From there, it's just parsing out the strings to get the course code,
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
                        System.out.println(courseTitle);

                        courseAttributes = (Element) courseInfo.item(1);
                        courseCredits = courseAttributes.getTextContent();
                        System.out.println(courseCredits);

                        courseAttributes = (Element) courseInfo.item(2);
                        courseDescription = courseAttributes.getTextContent();
                        System.out.println(courseDescription);


                        System.out.println("courseInfo length: " + courseInfo.getLength());
                    }
                }
                System.out.println("Size" + " " + courseBlockList.getLength());
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


    private void extractDataFromStrings() {

    }
}