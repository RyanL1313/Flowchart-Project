import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

public class FlowNode extends JComponent
{
    String[] menu = {"Menu","MA 200+","MA 300+","MA 400+","CS 200+","CS 300+","CS 400+", "ST 400+",
            "Literature","Soc. & Behav. Science","Fine Art","History",
            "Lab Science","Technical Elective","Humanities", "General Elective 300+", "General Elective"};
    static String[] ma200plus = Planner.getMA200PlusCourseIDs();
    static String[] ma300plus = Planner.getMA300PlusCourseIDs();
    static String[] ma400plus = Planner.getMA400PlusCourseIDs();
    static String[] cs200plus = Planner.getCS200PlusCourseIDs();
    static String[] cs300plus = Planner.getCS300PlusCourseIDs();
    static String[] cs400plus = Planner.getCS400PlusCourseIDs();
    static String[] st400plus = Planner.getST400PlusCourseIDs();
    static String[] technicalElective = Planner.getTechnicalElectiveCourseIDs();
    static String[] fineArts = Planner.getFineArtsCourseIDs();
    static String[] history = Planner.getHistoryCourseIDs();
    static String[] labSciences = Planner.getLabScienceCourseIDs();
    static String[] humanities = Planner.getHumanitiesCourseIDs();
    static String[] literatures = Planner.getLiteratureCourseIDs();
    static String[] socBeh = Planner.getSocialAndBehavioralSciencesCourseIDs();
    static String[] electiveCourses300plus = Planner.getElective300PlusCourseIDs();
    static String[] electiveCourses = Planner.getElectives();
    JComboBox dropDown = new JComboBox(menu);
    JLabel courseID = new JLabel();
    JButton clearButton = new JButton("CLEAR");
    JPanel rect = new JPanel();

    ActionListener clearButtonPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dropDown.setSelectedIndex(0);
            dropDown.setVisible(true);
            courseID.setVisible(false);
            clearButton.setVisible(false);

        }
    };

    ActionListener dropDownListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if((dropDown.getSelectedItem().equals("BACK")) || (dropDown.getSelectedItem().equals("Menu")))
            {
                dropDown.removeAllItems();
                for(int i = 0; i < menu.length; i++)
                    dropDown.addItem(menu[i]);
            }
            else
            {
                String choice = (String) dropDown.getSelectedItem();
                switch (choice)
                {
                    case "MA 200+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < ma200plus.length; i++)
                            dropDown.addItem(ma200plus[i]);
                        break;
                    case "MA 300+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < ma300plus.length; i++)
                            dropDown.addItem(ma300plus[i]);
                        break;
                    case "MA 400+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < ma400plus.length; i++)
                            dropDown.addItem(ma400plus[i]);
                        break;
                    case "CS 200+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < cs200plus.length; i++)
                            dropDown.addItem(cs200plus[i]);
                        break;
                    case "CS 300+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < cs300plus.length; i++)
                            dropDown.addItem(cs300plus[i]);
                        break;
                    case "CS 400+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < cs400plus.length; i++)
                            dropDown.addItem(cs400plus[i]);
                        break;
                    case "ST 400+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < st400plus.length; i++)
                            dropDown.addItem(st400plus[i]);
                        break;
                    case "Literature":
                        dropDown.removeAllItems();
                        for (int i = 0; i < literatures.length; i++)
                            dropDown.addItem(literatures[i]);
                        break;
                    case "Soc. & Behav. Science":
                        dropDown.removeAllItems();
                        for (int i = 0; i < socBeh.length; i++)
                            dropDown.addItem(socBeh[i]);
                        break;
                    case "Fine Art":
                        dropDown.removeAllItems();
                        for (int i = 0; i < fineArts.length; i++)
                            dropDown.addItem(fineArts[i]);
                        break;
                    case "History":
                        dropDown.removeAllItems();
                        for (int i = 0; i < history.length; i++)
                            dropDown.addItem(history[i]);
                        break;
                    case "Lab Science":
                        dropDown.removeAllItems();
                        for (int i = 0; i < labSciences.length; i++)
                            dropDown.addItem(labSciences[i]);
                        break;
                    case "Technical Elective":
                        dropDown.removeAllItems();
                        for (int i = 0; i < technicalElective.length; i++)
                            dropDown.addItem(technicalElective[i]);
                        break;
                    case "Humanities":
                        dropDown.removeAllItems();
                        for (int i = 0; i < humanities.length; i++)
                            dropDown.addItem(humanities[i]);
                        break;
                    case "General Elective 300+":
                        dropDown.removeAllItems();
                        for (int i = 0; i < electiveCourses300plus.length; i++)
                            dropDown.addItem(electiveCourses300plus[i]);
                        break;
                    case "General Elective":
                        dropDown.removeAllItems();
                        for (int i = 0; i < electiveCourses.length; i++)
                            dropDown.addItem(electiveCourses[i]);
                        break;
                    default:
                        dropDown.setVisible(false);
                        courseID.setText((String) dropDown.getSelectedItem());
                        courseID.setVisible(true);
                        clearButton.setVisible(true);

                        String menuSlot = (String)dropDown.getItemAt(1);    // this gets the intended category the class falls under
                        System.out.println("Menu slot: " + menuSlot);
                        String returnedCourse = Planner.removeFromCoursesReqToGraduate(menuSlot); // Ex: remove Technical Elective from CRG

                        // Check if removal was invalid
                        if (!Planner.checkIfValidCourseReqToGraduateRemoval(returnedCourse)) { // Something was unsuccessfully removed from CRG
                            // We need something in here to tell the student that they already have every credit for the course type selected, and they need to select something else
                            // Also set the drop-down back to its initial state
                        }

                        // Next part for debugging purposes:

                        /* System.out.println("After removal: ");
                        LinkedList<String> courses = DegreeParser.getCoursesRequiredToGraduate();
                        Iterator coursesIterator = courses.iterator();
                        while (coursesIterator.hasNext()) {
                            System.out.println(coursesIterator.next());
                        } */


/*

                        ArrayList<ArrayList<String>> coreqs = Planner.getCoReq((String)dropDown.getSelectedItem());
                        if(!coreqs.isEmpty())
                             Planner.FYPD.addNode(new FlowNode(coreqs));
*/

                }
            }
        }
    };

    // Constructor for creating an empty node.
    public FlowNode()
    {
        dropDown.setBounds(20, 20, 160, 20);
        dropDown.addActionListener(dropDownListener);
        add(dropDown);

        courseID.setBounds(20,20,160,20);
        courseID.setForeground(Color.WHITE);
        add(courseID);
        courseID.setVisible(false);

        clearButton.setBounds(140,10,60,20);
        clearButton.addActionListener(clearButtonPress);
        clearButton.setFont(clearButton.getFont().deriveFont(7.0f));
        add(clearButton);
        clearButton.setVisible(false);

        // Drawing a rectangle
        rect.setBackground(Color.BLUE);
        rect.setBounds(10,10, 190, 40);
        add(rect);

        setVisible(true);
    }

    // Constructor for a uneditable, filled node.
    public FlowNode(String id)
    {
        courseID.setText(id);
        courseID.setBounds(75,20,160,20);
        courseID.setForeground(Color.WHITE);
        add(courseID);
        courseID.setVisible(true);

        // Drawing a rectangle
        rect.setBackground(Color.BLUE);
        rect.setBounds(10, 10, 190, 40);
        add(rect);

        setVisible(true);
    }

    public FlowNode(ArrayList<ArrayList<String>> coreqs)
    {

    }
}
