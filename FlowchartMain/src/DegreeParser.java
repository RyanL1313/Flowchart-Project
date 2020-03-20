import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.File;
import java.io.IOException;

/**
 * A DegreeParser class scans a file that has information of a degree and sends that information to Degree class.
 */
public class DegreeParser {

    public static void main(String[] args) throws IOException {

        String fileName = "FlowchartMain/test.html";
        Document doc = Jsoup.parse(new File(fileName), "utf-8");
        JSONObject jsonParentObject = new JSONObject();

        /*
        for(Element divTag: doc.select("div")){
                for(Element table : doc.select("table")){
                     for (Element row : table.select("tr")) {
                        if (row.select("tr").is("tr")) {

                            System.out.println(row.text());
                        }
                         }
                }

        }*/

        doc.select("div#tabs").remove();
        doc.select("div#textcontainer").remove();
        doc.select("div#requirementstextcontainer").remove();
        for(Element table: doc.select("table")){
            for (Element row : table.select("tr")) {
                if (row.select("tr").is("tr")){

                    System.out.println(row.text());


                }
            }

        }


        //    Element divTag = doc.getElementById("pathwaystextcontainer");
        //  Elements table = divTag.getElementsByTag("th");


        //Element whatev = doc.get
        //   System.out.println(divTag.text());
        //  System.out.println(table.text());





       /*Degree degree = new Degree();
        String filename = degree.getMajor();
        Scanner scanMajorFile = null;

        try {
            scanMajorFile = new Scanner(new FileReader(filename));
        } catch (FileNotFoundException e) {
          */  //Do something here that let's us know this major doesn't exist yet
    }
}






