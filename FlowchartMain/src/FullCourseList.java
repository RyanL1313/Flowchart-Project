import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;
import javax.xml.parsers.*;
import org.w3c.dom.*;

/**
 * The FullCourseList is designed to hold every undergraduate class offered at UAH
 * In the FullCourseList hashmap, the key is the first part of the course ID (i.e. CS), and the
 * value is an array list of courses associated with that key.
 */
public class FullCourseList {
    private HashMap<String, ArrayList<Course>> FullCourseList; // The full list of UAH courses
    private ArrayList<String> HTMLFileList; // List of file names for all departments

    /**
     * Add the name of each HTML file that will be parsed (The HTML files are saved to the project)
     */
    private void createFileNameList() {
        // Add all html file names to HTMLFileList
        HTMLFileList.add("ACC.html"); HTMLFileList.add("AMS.html"); HTMLFileList.add("ARH.html");
        HTMLFileList.add("ARS.html"); HTMLFileList.add("AST.html"); HTMLFileList.add("ATS.html");
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
        HTMLFileList.add("MAE.html"); HTMLFileList.add("ME.html"); HTMLFileList.add("MIL.html");
        HTMLFileList.add("MU.html"); HTMLFileList.add("MUA.html"); HTMLFileList.add("MUE.html");
        HTMLFileList.add("MUJ.html"); HTMLFileList.add("NUR.html"); HTMLFileList.add("OPE.html");
        HTMLFileList.add("OPT.html"); HTMLFileList.add("PHL.html"); HTMLFileList.add("PH.html");
        HTMLFileList.add("PSC.html"); HTMLFileList.add("PRO.html"); HTMLFileList.add("PY.html");
        HTMLFileList.add("SOC.html"); HTMLFileList.add("ST.html"); HTMLFileList.add("TH.html");
        HTMLFileList.add("WGS.html"); HTMLFileList.add("WLC.html");
    }

    FullCourseList() {
        createFileNameList();
    }
}