import javax.swing.*;
import java.awt.event.ActionEvent;
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
public class DegreeSelectorWindow extends JFrame //implements ActionListener
{
    // These three arrays should really be constructed from data taken from the Degree Class. Info just here as a placeholder
    // CONCENTRATIONS will be differenet from MINORS in that it will completely refill with different info based on which
    // MAJOR is selected. CS Concentrations are here as a placeholder.
    String MAJORS[] = {"Please select a Major","Computer Science","Engineering","Nursing","Physics","Math","Biology"};
    String MINORS[] = {"N/A","Computer Science","Engineering","Nursing","Physics","Math","Biology"};
    String CONCENTRATIONS[] = {"N/A","Video Game Design","Cyber Security","Information Systems"};

    JComboBox major = new JComboBox(MAJORS);
    JComboBox minor = new JComboBox(MINORS);
    JComboBox conc = new JComboBox(CONCENTRATIONS);

    ActionListener majorListener = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            // Updating minor
            minor.removeAllItems();
            for(int i = 0; i < MINORS.length; i++)
                minor.addItem(MINORS[i]);

            for(int i = 0; i < MINORS.length; i++)
            {
                if(major.getItemAt(major.getSelectedIndex()).equals(minor.getItemAt(i)))
                {
                    minor.removeItemAt(i);
                }
            }

            // Updating conc ~ not actual code. Need to know how Degree will function before crossing the two.
            conc.removeAllItems();
            for(int i = 0; i < CONCENTRATIONS.length; i++)
                conc.addItem(CONCENTRATIONS[i]);
        }
    };

    public DegreeSelectorWindow()
    {
        initMajors();
        initMinors();
        initConcentrations();
    }

    public void initMajors()
    {
        major.setBounds(100,100,300,50);
        major.addActionListener(majorListener);
        add(major);
        JLabel majTitle = new JLabel("Major");
        majTitle.setBounds(100,70,300,20);
        add(majTitle);
    }

    public void initMinors()
    {
        minor.setBounds(100,200,300,50);
        add(minor);
        JLabel minTitle = new JLabel("Minor");
        minTitle.setBounds(100,170,300,20);
        add(minTitle);
    }

    public void initConcentrations()
    {
        conc.setBounds(100,300,300,50);
        add(conc);
        JLabel concTitle = new JLabel("Concentration");
        concTitle.setBounds(100,270,300,20);
        add(concTitle);
    }

    private static void updateDropdowns()
    {
        DegreeSelectorWindow frame = new DegreeSelectorWindow();
        frame.setLayout(null);
        frame.setSize(500,500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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