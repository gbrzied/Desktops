import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class imageGridDemo extends JFrame {
    
     static imageGridDemo frame;
     serveur srv;
     private Map<String, Component> componentsMap = new HashMap<String, Component>();
    
    
    public imageGridDemo(String name) throws Exception{
        super(name);
        setResizable(true);
        srv=new serveur(12345,this);
        srv.start();
        
        
    }
    
    
    public void addComponentsToPane( Container pane) throws Exception {
    
        //JPanel compsToExperiment = new JPanel();
   
       // pane.setLayout(new GridLayout(4,3));
        GridLayout L=new GridLayout(0,2);
        
        pane.setLayout(L);
        L.setHgap(10);
        L.setVgap(10);
              //Add buttons to experiment with Grid Layout
        StretchIcon scimg=new StretchIcon(ImageIO.read(new File("test.png")),false);
     
        JLabel jlab =new JLabel(scimg);
        //jlab.setText("  ");
        //compsToExperiment.add(jlab);
        //compsToExperiment.add(new JLabel(scimg));
   
          
       // compsToExperiment.add(new JLabel(new StretchIcon(scimg)));
       
       
 
       
       pane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent me) {
               
           
                if (me.getButton()==MouseEvent.BUTTON1)
                {
                   
                    //remove from the map
                    JLabel jlab= (JLabel)(pane.getComponentAt(me.getPoint()));
                    
                    String removalKey = null;

                    for (Map.Entry<String, Component> entry : componentsMap.entrySet()) {
                    System.out.println("Components " + entry.getKey());
                    if (jlab.equals((JLabel)entry.getValue())) {
                        removalKey = entry.getKey();
                        System.out.println("Component equality"+ entry.getKey());
                        break;
                    }
                    }
                

                    if (removalKey != null) 
                    {   
                        srv.getClient(removalKey).close();
                        componentsMap.remove(removalKey);
                        System.out.println("Component removed from the Map" + removalKey);
                        pane.remove(pane.getComponentAt(me.getPoint()));
                    }
                    
                }
                else if(me.getButton()==MouseEvent.BUTTON3)
                {
                    pane.add(new JLabel(scimg));
                    pack();
                }else if(me.getButton()==MouseEvent.BUTTON2)
                {
                    String col= JOptionPane.showInputDialog("Columns : ");
                    int icol = Integer.parseInt(col);
                    GridLayout z=new GridLayout(0,icol);
        
                    pane.setLayout(z);
                
                 
                
                }
                repaint();
                
                //compsToExperiment.p
               // throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
            

            
        });
       
        //pane.add(compsToExperiment);
       
    }
    
    /**
     * Create the GUI and show it.  For thread safety,
     * this method is invoked from the
     * event dispatch thread.
     */
    private static void createAndShowGUI() throws Exception{
        //Create and set up the window.
        frame = new imageGridDemo("GridLayoutDemoz");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(50, 50);
        //Set up the content pane.
        frame.addComponentsToPane(frame.getContentPane());
        //Display the window.
        frame.pack();
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        
       // frame.paintAll(frame.getContentPane().getGraphics());
    }
    
    public static void main(String[] args) throws Exception{
        /* Use an appropriate Look and Feel */
        try {
            //UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        /* Turn off metal's use of bold fonts */
        UIManager.put("swing.boldMetal", Boolean.FALSE);
        
        //Schedule a job for the event dispatch thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                try {
                    createAndShowGUI();
                } catch (Exception ex) {
                    Logger.getLogger(imageGridDemo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    void handle(String ID, BufferedImage image) throws Exception {
        
         ShrinkIcon img=new ShrinkIcon(image);
         //StretchIcon img=new StretchIcon(ImageIO.read(new File("test.png")),false);
         if (image==null)
             System.out.println("imageGridDemo.handle() image is null !!");
        JLabel jlab; 
        if (componentsMap.containsKey(ID)) {
                jlab=(JLabel)componentsMap.get(ID);
                jlab.setIcon(img);
        }
        else 
         {
            jlab=new JLabel(img);
            componentsMap.put(ID, jlab);
            frame.getContentPane().add(jlab);
         }
         frame.pack();
              
         
  
    }
}
