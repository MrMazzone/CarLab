import javax.swing.JFrame;

import info.gridworld.gui.GUIController;

import javax.swing.BorderFactory;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.BasicStroke;
import java.awt.RenderingHints;
import java.awt.Color; 

class NetView extends JFrame
{
    Color startColorInput1 = new Color(255,0,0);
    Color startColorInput2 = new Color(255,0,0);
    Color startColorInput3 = new Color(255,0,0);
    Color startColorOutput1 = new Color(255,0,0);
    

    ColorAnimation in1 = new ColorAnimation(startColorInput1);
    ColorAnimation in2 = new ColorAnimation(startColorInput2);
    ColorAnimation in3 = new ColorAnimation(startColorInput3);
    ColorAnimation out1 = new ColorAnimation(startColorOutput1);
    
    public NetView(String title, int x, int y)
    { 
        Color backgroundColor = new Color(242,242,242);
        Color borderColor = new Color(155,155,155);
        this.setTitle(title);
        this.setSize(250, 200);
        this.setVisible(true);
        this.getContentPane().setBackground(backgroundColor );
        this.setDefaultCloseOperation(1);
        this.getRootPane().setBorder(BorderFactory.createMatteBorder(4, 4, 4, 4, borderColor));
        this.setLocation(x, y);
        setDefaultLookAndFeelDecorated(false);
    }

    public void paint(Graphics g1)
    {
        super.paint(g1);
        Graphics2D g = (Graphics2D) g1;
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        //drawLine(int x1, int y1, int x2, int y2) 
        //x1 and y1 are starting point coordinates 
        //x2 and y2 are ending point coordinates 
        g.setStroke(new BasicStroke(9));

        g.setColor(startColorInput1);
        g.drawLine(50,60,120,110);

        g.setColor(startColorInput2);
        g.drawLine(50,110,120,110);

        g.setColor(startColorInput3);
        g.drawLine(50,160,120,110);

        g.setColor(startColorOutput1);
        g.drawLine(120,110,180,110);

        Color ovalColor = new Color(28, 160, 136);//easy to see line contrast
        g.setColor(ovalColor);

       // g.drawString("Hello", 200, 200);
        g.fillOval(35,45,25,25);
        g.fillOval(30,98,25,25);
        g.fillOval(35,150,25,25);

        g.fillOval(100,98,25,25);
        g.fillOval(175,98,25,25);
    }

    /**
     * This is the Method that constantly gets called to update the colors of the Neural Net
     * @param w1  Weight to feedforward
     * @param w2  Weight to feedforward
     * @param w3  Weight to feedforward
     * @param choice accesses boolean from Trainer whether or not move was made
     * @param terminate comes from call in NetworkCar accessing static GUIController.disNet
     */
    public void updateColors(double w1, double w2, double w3, boolean choice, boolean terminate)
    {
        startColorInput1 = in1.changeColors(w1);
        startColorInput2 = in2.changeColors(w2);
        startColorInput3 = in3.changeColors(w3);

        startColorOutput1 = out1.outputChanger(choice);
        
        if(terminate)
        {
        	dispose();
        }
        
        repaint();
    }
}

