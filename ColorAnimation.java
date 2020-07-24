import java.awt.Color;

class ColorAnimation
{
    Color startColor;
    //Color targetColor = new Color(0,0,255);

    ColorAnimation(Color sc)
    {
        startColor = sc;
    }

    public Color changeColors(double w)
    {
        if (w <= 0) 
        {
            w = 0;
        } 
        else if (w >= 1)
        {
            w = 1;
        }
       
        int r = startColor.getRed(); //255
        int b = startColor.getBlue();  //0

        r *= w;
        b *= 1-w;
       
        double norm1 = Math.sqrt(r);
        double norm2 = Math.sqrt(b);

        r += norm1;
        b += norm2;
       
        if(r >= 255)
        {
            r = 255;
        }

        if(b >= 255)
        {
            b = 255;
        }
        
        return new Color( r, 0, b);
    }

    public Color outputChanger(boolean c)
    {
        if(c)
        {
        	return new Color(242,242,242); //disappears into background of JFrame
        }
        else
        {
        	return new Color(0,0,0);
        }
    }
}
