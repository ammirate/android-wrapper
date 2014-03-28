package backup_old_classes;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class ImageIcontest extends JPanel {

    private JPanel contentPane;
    private JTextField textField;
    ImageIcon ii;

    /**
     * Launch the application.
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {

        JFrame frame = new JFrame();
        frame.setSize(new Dimension(500,500));
        frame.add(new ImageIcontest());
        frame.setVisible(true);

    }

    /**
     * Create the frame.
     * @throws IOException 
     */
    public ImageIcontest() throws IOException {
        setSize(new Dimension(556, 485));
        setBounds(100, 100, 300, 477);
        contentPane = new JPanel();
        contentPane.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        contentPane.setAlignmentX(Component.LEFT_ALIGNMENT);
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        BufferedImage bi = ImageIO.read(new File("src/res/football.png"));
        ii = new ImageIcon(bi);

        
    }
    
   @Override
   public void paintComponent(Graphics g)
   {
      super.paintComponent(g);
      Image image = ii.getImage();
      
      g.drawImage(image,50,50,20,20,null,null);
      repaint();
   }

}
