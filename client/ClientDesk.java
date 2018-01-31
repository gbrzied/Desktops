import java.awt.AWTException;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.Socket;

import javax.imageio.ImageIO;

public class ClientDesk
{
    Image newimg;
    static BufferedImage bimg;
    byte[] bytes;

    public static void main(String [] args) throws Exception
    {
        String serverName = "localhost";
        int port = 12345;
        Socket client = new Socket(serverName, port);
        boolean running=true;
        while (running)
        try
        {
            
            Robot bot;
            bot = new Robot();
            bimg = bot.createScreenCapture(new Rectangle(Toolkit.getDefaultToolkit()
                .getScreenSize()));
            ///client.getOutputStream().write(0);
            ImageIO.write(bimg,"PNG",client.getOutputStream());
            System.out.println("image sent");
            //client.close();
            Thread.sleep(5000);
            
        } catch(Exception e) {
            //e.printStackTrace();
            running=false;
            System.out.println("The Server closed the Stream, exiting");
            break;
        }
    }
}
