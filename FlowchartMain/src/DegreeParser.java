import org.json.JSONArray;
import org.json.JSONObject;
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

    public static void main(String[] args) throws IOException {


        String fileName = "C:/Users/Jossy/Documents/School docs/Spring 2020/CS321/4-yr plan html.html";
        Document doc = Jsoup.parse(new File(fileName), "utf-8");

        /*
        Removes all of the unncessary parts of the html document
         */
        doc.select("div#tabs").remove();
        doc.select("div#textcontainer").remove();
        doc.select("div#requirementstextcontainer").remove();

        //Selects the first table of the html
        Element table = doc.selectFirst("table");

        //Creates a new degree JSONObject
        JSONObject Degree = new JSONObject();

        //When the HTML is parsed, all of its text is put into this list
        List<String> arr = new LinkedList<>();

        //HTML is parsed by tag "tr" and put into the LinkedList
        for (Element tr : table.getElementsByTag("tr")) {
            arr.add(tr.text());
        }

        //Removes unncessary sentences from linkedlist
        for(int j = 0; j< arr.size(); j++) {
            if (arr.get(j).contains("Electives") || arr.get(j).contains("No more than") || arr.get(j).contains("Term Semester")
                    || arr.get(j).contains("If interested") || arr.get(j).contains("Choose") || arr.get(j).contains("For a")
                    || arr.get(j).contains("Ex:") || arr.get(j).contains("Total") || arr.get(j).contains("See Requirements")) {
                arr.remove(j);
                j--;
            }
        }

        //This loop goes through the linkedlist and organizes it into an JSONArray of courses that
        //are then put into a JSONObject called semester and then put into a JSONObject called year
        int i = 0;
        String text1 = arr.get(i);
        while(text1.contains("Year") && i < arr.size()) {
            String yearName = text1;
            JSONObject year = new JSONObject();
            i++;
            text1 = arr.get(i);
                if (text1.contains("Fall")) {
                    JSONArray semester = new JSONArray();
                    i++;
                    do {
                        text1 = arr.get(i);
                        semester.put(text1);
                        System.out.println(text1);
                        i++;
                        if(i == arr.size()){
                            i--;
                            break;
                        }
                    } while (!arr.get(i).contains("Fall") && !arr.get(i).equals("Spring") && !arr.get(i).contains("Year"));
                    year.put("Fall", semester);
                }
            text1 = arr.get(i);
                if (text1.equals("Spring")) {
                    JSONArray semester = new JSONArray();
                    i++;
                    do {

                        text1 = arr.get(i);
                        semester.put(text1);
                        System.out.println(text1);
                        i++;
                        if(i == arr.size()){
                            i--;
                            break;
                        }

                    } while (!arr.get(i).contains("Fall") && !arr.get(i).equals("Spring") && !arr.get(i).contains("Year"));
                    text1 = arr.get(i);
                    year.put("Spring", semester);
                }

            Degree.put(yearName, year);
        }

        //outputs linkedlist to a JSON file that is organized
        try (FileWriter file = new FileWriter("Degree.json")) {

            file.write(Degree.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}



