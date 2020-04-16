import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.*;

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
                if(Degree.get(i).get(j).equals("Unknown"))
                    node = new FlowNode();
                else
                    node = new FlowNode(Degree.get(i).get(j));
                semester.add(node,BorderLayout.CENTER);
                node.setVisible(true);
                chart.add(semester);
            }
        }
        JScrollPane pane = new JScrollPane(chart);
        pane.getVerticalScrollBar().setUnitIncrement(20);
        pane.setPreferredSize(new Dimension(1300,605));
        add(pane,BorderLayout.CENTER);
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
/*
    public static void main(String[] args)
    {
        Thread object = new Thread(new FourYearPlanDisplay());
        object.start();
    }

    @Override
    public void run()
    {
        updateDisplay();
    }
    */
}
