import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The FourYearPlanDisplay class is used to display the flowchart based on the major/minor combination the student
 * entered and the previous courses they have already taken.
 *
 * This class uses the Decorator pattern since it implements a scroll bar to prevent the course nodes from being
 * squashed. The scroll bar does not alter the underlying functionality of the flowchart display. It simply
 * enhances the visual aspect of the flowchart.
 */
public class FourYearPlanDisplay extends JFrame implements PlanDisplay//, Runnable
{
    ArrayList<ArrayList<String>> Degree = Planner.getDegree();

    public FourYearPlanDisplay()
    {
        JPanel chart = new JPanel(new GridLayout(0,1));
        chart.setPreferredSize(new Dimension(1200,1500));

        for(int i = 0; i < Degree.size();i++)
        {
            JPanel semester = new JPanel();
            JLabel semNum = new JLabel("Semester " + (i+1));
            semNum.setSize(10,10);
            chart.add(semNum);
            semester.setSize(1200, 60);
            semester.setLayout(new GridLayout(1, 8, 10, 0));
            semester.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            semester.setVisible(true);
            for (int j = 0; j < Degree.get(i).size(); j++)
            {
                FlowNode node;
 //               System.out.println ("This is what Degree.get(" + i + ").get(" + j + ") contains: " + Degree.get(i).get(j));
                if(((Degree.get(i).get(j).contains("+")) || Degree.get(i).get(j).contains("Elective")) || (Degree.get(i).get(j).contains("Humanities")) ||
                        (Degree.get(i).get(j).contains("History")) || (Degree.get(i).get(j).contains("Lab Science")) || (Degree.get(i).get(j).contains("Social")) ||
                        (Degree.get(i).get(j).contains("Literature") || (Degree.get(i).get(j).contains("Fine"))))
                    node = new FlowNode();
/*                else if(Degree.get(i).get(j).contains("Lab Science"))
                {
                    node = new FlowNode();
                    System.out.println("Semester " + i + " Course " + j + " is a lab science adding another node now");
                    semester.add(node,BorderLayout.CENTER);
                }   */
                else
                    node = new FlowNode(Degree.get(i).get(j));

                semester.add(node, BorderLayout.CENTER);
//                System.out.println ("Adding a node at " + i + " " + j);
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

    /*public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        int x;
        int y = 165;
        for(int j = 0; j < 3; j++)
        {
            x = 0;
            for (int i = 0; i < 15; i++)
            {
                g2.draw(new Line2D.Float(x, y, x + 50, y));
                x += 100;
            }
            y += 140;
        }
    }*/
/*
    public FlowNode search(String courseID)
    {
        JPanel chart = (JPanel)getComponent(0).getComponentAt(0,0);
        for(int i = 1; i < chart.getComponentCount(); i += 2)
        {
            for(int j = 0; j < chart.getComponent(i).)
        }
    }

    public static void addNode(FlowNode node)
    {

    }
*/
    public static void updateDisplay()
    {

        FourYearPlanDisplay f = new FourYearPlanDisplay();
        //f.setLayout(new GridLayout(20,1,0,0));
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
