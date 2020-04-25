import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * The PlanDisplay class is used to display the flowchart based on the major/minor combination the student
 * entered and the previous courses they have already taken.
 *
 * This class uses the Decorator pattern since it implements a scroll bar to prevent the course nodes from being
 * squashed. The scroll bar does not alter the underlying functionality of the flowchart display. It simply
 * enhances the visual aspect of the flowchart.
 */
public class PlanDisplay extends JFrame
{
    ArrayList<ArrayList<String>> Degree = Planner.getDegree();
    static ArrayList<JLabel> semesters = new ArrayList<JLabel>();

    ActionListener check = new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            LinkedList<String> CRG = Planner.getRequiredCourses();
            JFrame popup = new JFrame();
            popup.setBounds(100,100,500,500);
            JLabel requirements = new JLabel();
            String message;
            if(CRG.isEmpty())
            {
                message = "<html>Congratulations! All Degree requirements filled. Close this window and use the snipping tool to save your plan!";
            }
            else
            {
                message = "<html>These courses/course types are still required: ";
                for (int i = 0; i < CRG.size(); i++)
                {
                    if(i != CRG.size() - 1)
                        message = message.concat(CRG.get(i) + ", ");
                    else
                        message = message.concat(CRG.get(i) + ".");
                }
            }
            requirements.setText(message);
            popup.add(requirements);
            popup.setVisible(true);
        }
    };

    public PlanDisplay()
    {
        JPanel completion = new JPanel();
        completion.setSize(1200,60);
        JButton checkForCompletion = new JButton("Check Schedule for Completion");
        checkForCompletion.addActionListener(check);
        completion.add(checkForCompletion);
        add(completion,BorderLayout.NORTH);
        completion.setVisible(true);

        JPanel chart = new JPanel(new GridLayout(0,1));
        chart.setPreferredSize(new Dimension(1200,1500));

        for(int i = 0; i < Degree.size();i++)
        {
            JPanel semester = new JPanel();
            JLabel semNum = new JLabel("Semester " + (i+1));

            semesters.add(semNum);

            semNum.setSize(10,10);
            chart.add(semNum);
            semester.setSize(1200, 60);
            semester.setLayout(new GridLayout(1, 8, 10, 0));
            semester.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            semester.setVisible(true);
            for (int j = 0; j < Degree.get(i).size(); j++)
            {
                FlowNode node;
                if(((Degree.get(i).get(j).contains("+")) || Degree.get(i).get(j).contains("Elective")) || (Degree.get(i).get(j).contains("Humanities")) ||
                        (Degree.get(i).get(j).contains("History")) || (Degree.get(i).get(j).contains("Lab Science")) || (Degree.get(i).get(j).contains("Social")) ||
                        (Degree.get(i).get(j).contains("Literature") || (Degree.get(i).get(j).contains("Fine"))))
                    node = new FlowNode();
                else
                    node = new FlowNode(Degree.get(i).get(j));

                semester.add(node, BorderLayout.CENTER);
                node.setVisible(true);
                chart.add(semester);
            }
        }
        JScrollPane pane = new JScrollPane(chart);
        pane.getVerticalScrollBar().setUnitIncrement(20);
        pane.setPreferredSize(new Dimension(1300,605));
        add(pane, BorderLayout.CENTER);
    }

    public void removeSemester(int yLow, int yHigh)   // erases all nodes within y range.
    {

    }

    public void removeNode(int x, int y)
    {

    }
/*
    public FlowNode search(String courseID)
    {
        JPanel chart = (JPanel)getComponent(0).getComponentAt(0,0);
        for(int i = 1; i < chart.getComponentCount(); i += 2)
        {
            for(int j = 0; j < chart.getComponent(i).get
        }
    }

    public static void addNode(FlowNode node)
    {

    }
*/

    /**
     * Returns what semester the given node is contained in.
     * @param node
     * @return
     */
    public static int getSemesterNum(JComboBox node)
    {
        Point p = node.getLocation();
        p.move(0,40);
        for(int i = 0; i < semesters.size(); i++)
        {
            Rectangle bounds = semesters.get(i).getBounds();
            if((p.getY() > bounds.getMinY()) && (p.getY() < bounds.getMaxY()))
                return i - 7;
        }
        return 0;
    }

    public static void updateDisplay()
    {

        PlanDisplay f = new PlanDisplay();
        f.setBounds(0,0,1350,605);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setVisible(true);
    }

    public static void main(String[] args)
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                updateDisplay();
            }
        });
    }
}
