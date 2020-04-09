import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlowNode extends JComponent
{

    //String[] availableCourses = getElectivesArray();
    static String[] availableCourses = Planner.getElectives();
    JComboBox dropDown = new JComboBox(availableCourses);
    JLabel courseID = new JLabel();
    JButton clearButton = new JButton("CLEAR");
    JPanel rect = new JPanel();

    ActionListener clearButtonPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dropDown.setVisible(true);
            String courseToAddBack = courseID.getText();
            if(!(courseToAddBack.equals("EMPTY")))
                Planner.addCourse(courseToAddBack);
            courseID.setVisible(false);
            clearButton.setVisible(false);
        }
    };

    ActionListener dropDownListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            dropDown.setVisible(false);
            courseID.setText((String)dropDown.getSelectedItem());
            /*String selected = (String)dropDown.getSelectedItem();
            if(!(selected.equals("EMPTY")))
            {
                Planner.removeCourse(selected);
                for(int i =0; i < availableCourses.length; i++)
                {
                    //if(availableCourses[i].equals(selected))

                }
            }*/
            courseID.setVisible(true);
            clearButton.setVisible(true);
        }
    };

    // Constructor for creating an empty node.
    public FlowNode()
    {
        dropDown.setBounds(10, 10, 160, 20);
        dropDown.addActionListener(dropDownListener);
        add(dropDown);

        courseID.setBounds(10,10,160,20);
        courseID.setForeground(Color.WHITE);
        add(courseID);
        courseID.setVisible(false);

        clearButton.setBounds(120,0,60,20);
        clearButton.addActionListener(clearButtonPress);
        clearButton.setFont(clearButton.getFont().deriveFont(7.0f));
        add(clearButton);
        clearButton.setVisible(false);

        // Drawing a rectangle
        rect.setBackground(Color.BLUE);
        rect.setBounds(0, 0, 180, 40);
        add(rect);

        setVisible(true);
    }

    // Constructor for a uneditable, filled node.
    public FlowNode(String courseID)
    {

    }

    public static void update()
    {
        FlowNode node = new FlowNode();
        node.setVisible(true);
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                update();
            }
        });
    }
}
