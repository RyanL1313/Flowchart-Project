import java.awt.*;

public interface PlanDisplay
{
    public void removeSemester(int yLow, int yHigh);
    public void removeNode(int semester, int column);
    public void paint(Graphics g);
    public static void updateDisplay() {}
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