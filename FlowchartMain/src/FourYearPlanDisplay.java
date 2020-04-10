import javax.swing.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.awt.*;

public class FourYearPlanDisplay extends JFrame implements PlanDisplay
{
    ArrayList<ArrayList<String>> Degree = new ArrayList<ArrayList<String>>();

    public FourYearPlanDisplay()// no arguments because Planner class accessed directly within constructor
    {
        // initialize degree for testing
        for(int i = 0; i < 7; i++)  // 7 semesters
        {
            Degree.add(new ArrayList<String>());
            for(int j = 0; j < 6; j++)  // 6 courses per semester
            {
                Degree.get(i).add("S: " + i + " Course " + j);
            }
        }

        for(int i = 0; i < Degree.size(); i++)  // semester loop
        {
            for(int j = 0; j < Degree.get(i).size(); j++)   // course loop
            {
                add(new FlowNode());
            }
        }
        String[] id = {"CS 102","CS 221","MA 177","PH 111","HPE 137","ENG 101"};
        for(int k = 0; k < 6;k++)
            add(new FlowNode(id[k]));
    }

    public void removeSemester(int yLow, int yHigh)   // erases all nodes within y range.
    {

    }

    public void removeNode(int x, int y)
    {

    }

    public void paint(Graphics g) {
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
        //paintLines(g);
    }

    public void paintLines(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        int x;
        int y = 170;
        for(int j = 0; j < 3; j++)
        {
            x = 0;
            for (int i = 0; i < 15; i++)
            {
                double endX = getContentPane().getComponentAt(x,y).getBounds().getWidth();
                double endY = getContentPane().getComponentAt(x,y).getBounds().getHeight();
                g2.draw(new Line2D.Float(x, y, x + (int)endX,y + (int)endY));
                x += 100;
            }
            y += 170;
        }

        int w;
        int z = 0;
        for(int j = 0; j < 4; j++)
        {
            w = 0;
            for(int i = 0; i < 3; i++)
            {
                double endW = getContentPane().getComponentAt(w,z).getBounds().getWidth();
                double endZ = getContentPane().getComponentAt(w,z).getBounds().getHeight();
                g2.draw(new Line2D.Float(w, z, w + (int)endW,z + (int)endZ));
                w += 400;
            }
            w = 200;
            z += 80;
            for(int k = 0; k < 3; k++)
            {
                double endW = getContentPane().getComponentAt(w,z).getBounds().getWidth();
                double endZ = getContentPane().getComponentAt(w,z).getBounds().getHeight();
                g2.draw(new Line2D.Float(w,z,w + (int)endW,z + (int)endZ));
                w += 400;
            }
            z += 60;
        }
    }

    public static void updateDisplay()
    {

        FourYearPlanDisplay f = new FourYearPlanDisplay();
        f.setLayout(new GridLayout(8,6,20,20));
        f.setBounds(0,0,1200,605);
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
                FlowNode.main(null);
            }
        });
    }
}
