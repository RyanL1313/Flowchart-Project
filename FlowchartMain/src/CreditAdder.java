import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class CreditAdder extends JFrame
{

    JTextField entryBox = new JTextField();

    ActionListener addPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String addedCourse = entryBox.getText();
            entryBox.setText("");


        }
    };

    ActionListener donePress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            System.exit(0);
        }
    };

    public CreditAdder()
    {
        super("Enter College Credits already obtained");

        JButton addButton = new JButton("ADD");
        JButton doneButton = new JButton("DONE");

        //FullCourseList fullList = new FullCourseList();
        //HashMap courseList = new HashMap(fullList.getFullCourseList());

        setSize(500,500);

        addButton.addActionListener(addPress);
        doneButton.addActionListener(donePress);


        add(entryBox,BorderLayout.CENTER);
        add(addButton,BorderLayout.EAST);
        add(doneButton,BorderLayout.SOUTH);
        entryBox.setVisible(true);

        setVisible(true);
    }
}
