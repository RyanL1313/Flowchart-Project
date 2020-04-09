import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlowNode extends JComponent
{

    String[] availableCourses = getElectivesArray();
    JComboBox dropDown = new JComboBox(availableCourses);
    JPanel rect = new JPanel();

    ActionListener dropDownListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {

        }
    };

    // Constructor for creating an empty node.
    public FlowNode()
    {
        dropDown.setBounds(10, 10, 160, 20);
        dropDown.addActionListener(dropDownListener);
        add(dropDown);

        // dropDown.setVisible(true);

        // Drawing a rectangle
        rect.setBackground(Color.BLUE);
        rect.setBounds(0, 0, 180, 40);
        add(rect);

        setVisible(true);
    }

    // Constructor for a uneditable, filled node.
    public FlowNode(int x, int y, String data)
    {

    }

    public static String[] getElectivesArray()
    {
        ArrayList<String> temp = Planner.getElectives();
        String[] electivesArray = new String[temp.size()];
        electivesArray[0] = "EMPTY";
        for (int i = 1; i < temp.size(); i++)
        {
            electivesArray[i] = temp.get(i);
        }
        return electivesArray;
    }
}
