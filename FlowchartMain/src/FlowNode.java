import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlowNode extends JComponent
{

    String[] availableCourses = {"Placeholder", "Courses", "Go", "Here"};
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
        rect.setBackground(Color.DARK_GRAY);
        rect.setBounds(0, 0, 180, 40);
        add(rect);

        String classType = (String) dropDown.getSelectedItem();

        switch (classType)
        {
            case "Placeholder":
                rect.setBackground(Color.BLUE);
                break;
            case "Courses":
                rect.setBackground(Color.RED);
                break;
            case "Go":
                rect.setBackground(Color.GREEN);
                break;
            case "Here":
                rect.setBackground(Color.YELLOW);
                break;
            default:
                rect.setBackground(Color.DARK_GRAY);
        }
        setVisible(true);

        //rect.setVisible(true);
    }

    // Constructor for a uneditable, filled node.
    public FlowNode(int x, int y, String data)
    {

    }

}
