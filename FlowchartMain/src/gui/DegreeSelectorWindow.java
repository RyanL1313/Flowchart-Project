package gui;

import controller.Planner;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 * The DegreeSelectorWindow class is a GUI class that will be a window containing
 * two dropdown windows; one for the major, one for the minor
 *
 * The minor dropdown is optional, its default value is N/A
 *
 * The major dropdown menu requires input for the program to continue. Upon hitting the Yes button,
 * the user will be prompted to enter any college credits they have already obtained.
 * If the No button is pressed, the program will progress to the PlanDisplay class. If credits are entered, the
 * program will progress to the CreditAdder class.
 */
public class DegreeSelectorWindow extends JFrame //implements Runnable
{
    String MAJORS[] = {"Please select a Major", "Computer Science", "Mathematical Sciences"};
    String MINORS[] = {"N/A", "Computer Science","Mathematical Sciences"};

    JComboBox major = new JComboBox(MAJORS);
    JComboBox minor = new JComboBox(MINORS);

    JButton no = new JButton("No");
    JButton yes = new JButton("Yes");

    ActionListener majorListener = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // Updating minor
            minor.removeAllItems();
            for (int i = 0; i < MINORS.length; i++)
                minor.addItem(MINORS[i]);

            for (int i = 0; i < MINORS.length; i++)
            {
                if (major.getItemAt(major.getSelectedIndex()).equals(minor.getItemAt(i)))
                {
                    minor.removeItemAt(i);
                }
            }

            // Enable buttons
            if(major.getItemAt(major.getSelectedIndex()).equals("Please select a Major"))
            {
                no.setEnabled(false);
                yes.setEnabled(false);
            }
            else
            {
                no.setEnabled(true);
                yes.setEnabled(true);
            }
        }
    };

    ActionListener noPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String maj = major.getSelectedItem() + " Major";
            String min = (String) minor.getSelectedItem();
            if (min != "N/A") // Don't want to add " Minor" if it's N/A
                 min = min.concat(" Minor");

            Planner.setMajor(maj);
            Planner.setMinor(min);


            dispose();
            Planner.callDegreeParser(maj, min);
            Planner.removeCoreCoursesFromFullCourseList(); // Added by Ryan - So core courses already in flowchart don't show up in dropdowns
            Planner.drawPlanDisplay();
        }
    };

    ActionListener yesPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            String maj = major.getSelectedItem() + " Major";
            String min = (String) minor.getSelectedItem();
            if (min != "N/A") // Don't want to add " Minor" if it's N/A
                min = min.concat(" Minor");
            Planner.setMajor(maj);
            Planner.setMinor(min);

            dispose();
            Planner.callDegreeParser(maj, min);
            Planner.drawCreditAdder();
        }
    };

    /**
     * The DegreeSelectorWindow is the initial starting point of the program. It prompts the user to select their major and, if desired,
     * their intended minor.
     *
     * The user is prompted with the ability to enter college credits previously obtained. If they wish to do so, they will be
     * taken to the CreditAdder window.
     * If not, the program will progress to PlanDisplay immediately.
     */
    public DegreeSelectorWindow()
    {
        initMajors();
        initMinors();

        no.setBounds(50, 400, 150, 30);
        no.setEnabled(false);
        no.addActionListener(noPress);
        add(no);
        no.setVisible(true);

        yes.setBounds(250, 400, 150, 30);
        yes.setEnabled(false);
        yes.addActionListener(yesPress);
        add(yes);
        yes.setVisible(true);

        JLabel question = new JLabel("Do you have college credits already?");
        question.setBounds(50, 360, 300, 30);
        add(question);
        question.setVisible(true);
    }

    public void initMajors()
    {
        major.setBounds(100, 100, 300, 50);
        major.addActionListener(majorListener);
        add(major);
        JLabel majTitle = new JLabel("Major");
        majTitle.setBounds(100, 70, 300, 20);
        add(majTitle);
    }

    public void initMinors()
    {
        minor.setBounds(100, 200, 300, 50);
        add(minor);
        JLabel minTitle = new JLabel("Minor");
        minTitle.setBounds(100, 170, 300, 20);
        add(minTitle);
    }

    private static void updateDropdowns()
    {
        DegreeSelectorWindow frame = new DegreeSelectorWindow();
        frame.setLayout(null);
        frame.setSize(500, 500);
        frame.setVisible(true);
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                updateDropdowns();
            }
        });
    }
}
