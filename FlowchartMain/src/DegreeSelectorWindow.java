import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.util.*;
import java.awt.event.ActionListener;
/**
 * The DegreeSelectorWindow class is a GUI class that will be a window containing
 * three dropdown windows; one for the major, one for the minor, and one for a desired concentration.
 * Both the minor and concentration dropdowns will be optional, with their default value being N/A.
 * The major dropdown menu requires input for the program to continue. Upon hitting the continue button,
 * the user will be prompted to enter any college credits they have already obtained. If no credits are
 * entered, the program will progress to the FourYearPlanDisplay class. If credits are entered, the
 * program will progress to the CustomPlanDisplay class.
 */
public class DegreeSelectorWindow extends JFrame //implements Runnable
{
    String MAJORS[] = {"Please select a Major", "Computer Science", "Mathematical Sciences"};
    String MINORS[] = {"N/A", "Mathematical Sciences"};

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
            String min = minor.getSelectedItem() + " Minor";
            Planner.setMajor(maj);
            try
            {
                Planner.setMinor(min);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            dispose();
            Planner.drawFourYearPlanDisplay();
        }
    };

    ActionListener yesPress = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            // - This is where a new JFrame will be made for the user to enter their existing
            // - college credits. It will implement FullCourseList and populate the JFrame with every
            // - possible course to take with a check box next to it. It is the user's responsibility to
            // - know what classes they have credit for already.
            String maj = major.getSelectedItem() + " Major";
            String min = minor.getSelectedItem() + " Minor";
            Planner.setMajor(maj);
            try
            {
                Planner.setMinor(min);
            } catch (IOException ex)
            {
                ex.printStackTrace();
            }
            dispose();
            Planner.drawCreditAdder();
        }
    };

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
