import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CreditAdder extends JFrame
{

    String[] menu = {"Menu","Core Courses","MA 200+","MA 300+","MA 400+","CS 200+","CS 300+","CS 400+", "ST 400+",
            "Literature","Soc. & Behav. Science","Fine Art","History",
            "Lab Science","Technical Elective","Humanities", "General Elective 300+", "All Courses"};
    //static String[] corecourses = Planner.getCoreCourseIDs();
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
    static String[] electiveCourses = Planner.getElectives(0);

    //JTextField entryBox = new JTextField();
    JComboBox dropDown = new JComboBox(menu);

    ActionListener addPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String addedCourse = (String)dropDown.getSelectedItem();
            //entryBox.setText("");
            removeCourse(addedCourse);
            dropDown.setSelectedIndex(0);
        }
    };

    ActionListener donePress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)

        {
            dispose();
            Planner.removeCoreCoursesFromFullCourseList(); // Added by Ryan so dropdowns in the flowchart can't have the core courses
            Planner.drawFourYearPlanDisplay();
        }
    };

    ActionListener dropDownListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            if ((dropDown.getSelectedItem().equals("BACK")) || (dropDown.getSelectedItem().equals("Menu")))
            {
                dropDown.removeAllItems();
                for (int i = 0; i < menu.length; i++)
                    dropDown.addItem(menu[i]);
            } else
            {
                String choice = (String) dropDown.getSelectedItem();
                switch (choice)
                {
                    case "Core Courses":
                        dropDown.removeAllItems();
                        //for(int i = 0; i < corecourses.length; i++)
                            //dropDown.addItem(corecourses[i]);
                        break;
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
                    case "All Courses":
                        dropDown.removeAllItems();
                        for (int i = 0; i < electiveCourses.length; i++)
                            dropDown.addItem(electiveCourses[i]);
                        break;
                    default:
                        // It's a specific class, do nothing. Wait for user to press ADD button.
                }
            }
        }
    };

    public CreditAdder()
    {
        super("Enter College Credits already obtained");
        setLayout(null);

        JButton addButton = new JButton("ADD");
        addButton.setBounds(300,200,75,50);
        JButton doneButton = new JButton("DONE");
        doneButton.setBounds(250, 400, 150, 30);
        JLabel directions = new JLabel("<html>Select the course ID you have credit for from the list. Then press ADD." +
                " When finished, press the Done button.");
        directions.setBounds(50, 70, 300, 50);

        setSize(500,500);

        addButton.addActionListener(addPress);
        doneButton.addActionListener(donePress);

        dropDown.setBounds(50, 200, 250, 50);
        dropDown.addActionListener(dropDownListener);
        add(dropDown);
        add(addButton);
        add(doneButton);
        add(directions);

        setVisible(true);
    }

    public void removeCourse(String courseID)
    {
        String check;
        check = Planner.removeCourse(courseID);
        if(check == null)
            System.out.println("Invalid course");
    }

}
