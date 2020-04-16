import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FlowNode extends JComponent
{
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
            dropDown.setVisible(false);
            courseID.setText((String)dropDown.getSelectedItem());
            courseID.setVisible(true);
            clearButton.setVisible(true);
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
}
