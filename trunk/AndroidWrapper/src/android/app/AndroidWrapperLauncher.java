package android.app;

import java.awt.CardLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import android.widget.WidgetProperties;

@Deprecated
public class AndroidWrapperLauncher extends JFrame {


    private JPanel contentPane;
    /** Contains the height of the device */
    private JTextField heightTextField;
    
    /** Contains the width of the device */
    private JTextField widthTextField;
    
    /** Contains the image of the device */
    private JLabel image;
    
    /** List from which choice the Android app to run */
    private JList appList;
    
    /** Reference to this frame */
    private JFrame thisFrame;
    
    private boolean portrait = true;
    
    /** 
     *  path from whic load the app components
     *  e.g. android.androidCode.helloWorld 
     */
    private String packagePath;
    
    /** 
     * Relative path of the application choiced
     * e.g. /android/androidCode/helloWorld
     */
    private String projectPath;
    
    /** the name of the application choiced in the app list */
    private String project;

    
    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AndroidWrapperLauncher frame = new AndroidWrapperLauncher();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    
    /**
     * Create the window from which is possible to set all the option for the
     * device and choice the application to run.
     */
    public AndroidWrapperLauncher() {
        
        thisFrame = this;
        setResizable(false);
        setTitle("MAEESTRO");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(600, 200, 709, 538);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // ######### Dimension Panel #############
        JPanel dimensionPanel = new JPanel();
        dimensionPanel.setBounds(12, 80, 241, 123);
        dimensionPanel.setPreferredSize(new Dimension(10, 50));
        dimensionPanel.setBorder(new TitledBorder(null, "Screen dimensions", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(dimensionPanel);
        dimensionPanel.setLayout(null);

        JLabel lblHeight = new JLabel("Height:  ");
        lblHeight.setBounds(15, 20, 70, 44);
        lblHeight.setHorizontalAlignment(SwingConstants.RIGHT);
        dimensionPanel.add(lblHeight);

        heightTextField = new JTextField();
        heightTextField.setBounds(95, 27, 101, 32);
        heightTextField.setText("600");
        dimensionPanel.add(heightTextField);
        heightTextField.setColumns(10);  
        heightTextField.addKeyListener(new KeyListener() 
        {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) 
            {
                if(!heightTextField.getText().equals(""))
                {
                    Integer height = Integer.parseInt(heightTextField.getText());
                    Integer val = (int) (height / 1.667) + 1;
                    widthTextField.setText(val.toString());
                }
            }
            public void keyPressed(KeyEvent e) {}
        });

        JLabel lblWidth = new JLabel("Width:  ");
        lblWidth.setBounds(15, 67, 70, 44);
        lblWidth.setHorizontalAlignment(SwingConstants.RIGHT);
        dimensionPanel.add(lblWidth);
        
        JLabel label = new JLabel("");
        label.setBounds(5, 74, 70, 44);
        dimensionPanel.add(label);

        widthTextField = new JTextField();
        widthTextField.setBounds(95, 71, 101, 32);
        widthTextField.setText("480");
        dimensionPanel.add(widthTextField);
        widthTextField.setColumns(10);
        
        JLabel label_1 = new JLabel("");
        label_1.setBounds(165, 74, 70, 44);
        dimensionPanel.add(label_1);
        widthTextField.addKeyListener(new KeyListener() 
        {
            public void keyTyped(KeyEvent e) {}
            public void keyReleased(KeyEvent e) 
            {
                if(!widthTextField.getText().equals(""))
                {
                    Integer width = Integer.parseInt(widthTextField.getText());
                    Integer val = (int) (width * 1.667);
                    heightTextField.setText(val.toString());
                }
            }
            public void keyPressed(KeyEvent e) {}
        });


        //################ Orientation Panel##################

        JPanel orientationPanel = new JPanel();
        orientationPanel.setOpaque(false);
        orientationPanel.setBounds(273, 5, 411, 480);
        orientationPanel.setBorder(new TitledBorder(null, "Device orientation", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(orientationPanel);

        ButtonGroup group = new ButtonGroup();
        orientationPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        JPanel panelRadio = new JPanel();
        orientationPanel.add(panelRadio);
        panelRadio.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // RadioButton for portrait
        JRadioButton rdbtnPortrait = new JRadioButton("Portrait");
        rdbtnPortrait.setSelected(true);
        panelRadio.add(rdbtnPortrait);
        rdbtnPortrait.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                portrait = true;
                image.setIcon(new ImageIcon(getClass().getResource("/images/nexus.png")));
            }
        });

        //Radiobutton for landscape
        JRadioButton rdbtnLandscape = new JRadioButton("Landscape");
        panelRadio.add(rdbtnLandscape);
        rdbtnLandscape.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                portrait = false;
                image.setIcon(new ImageIcon(getClass().getResource("/images/nexus2.png")));
            }
        });

        //add RadioButtons to the same ButtonGroup
        group.add(rdbtnPortrait);
        group.add(rdbtnLandscape);


        //Device image
        JPanel panelImage = new JPanel();
        panelImage.setOpaque(false);
        panelImage.setSize(new Dimension(400, 400));
        panelImage.setPreferredSize(new Dimension(400, 400));
        orientationPanel.add(panelImage);
        panelImage.setLayout(new CardLayout(0, 0));

        image = new JLabel("");
        panelImage.add(image, "name_18680731458452");
        //image.setIcon(new ImageIcon("icons/nexus.png"));
        image.setIcon(new ImageIcon(getClass().getResource("/images/nexus.png")));

        //################## Project Panel ############################
        /*   
       String[] list = {
               "helloWorld",
               "sayHello", 
               "radioButton", 
               "seekbar", 
               "graphictest", 
               "calculator", 
               "activities", 
               "listview", 
               "aicas",
               "rectangle" ,
               "animation", 
               "ball",
               "paint"
               };
       */     
       //start_edit: IS2 project - MAEESTRO 2013/2014

       String[] list = {
    		   "helloWorld",
               "toast_Example",
    		   "switch_Example",
               "spinner_Example",
               "analogClock_Example"
               };
       
       //end_edit: IS2 project - MAEESTRO 2013/2014


        JPanel projectPanel = new JPanel();
        projectPanel.setBounds(12, 215, 241, 178);
        projectPanel.setBorder(new TitledBorder(null, "Android apps list", TitledBorder.LEADING, TitledBorder.TOP, null, null));
        contentPane.add(projectPanel);
        projectPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        appList = new JList(list);
        appList.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        appList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        appList.setSelectedIndex(list.length-1);
        appList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        appList.setAutoscrolls(true);
      
        JScrollPane scrollPanel = new JScrollPane(appList);
        scrollPanel.setAutoscrolls(true);
        scrollPanel.setPreferredSize(new Dimension(200, 140));
        scrollPanel.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        projectPanel.add(scrollPanel);
        
        JButton btnRun = new JButton("Run");
        btnRun.setBounds(12, 405, 241, 80);
        contentPane.add(btnRun);
        URL urlAndroidIco = getClass().getResource("/images/androidIco.png");
        btnRun.setIcon(new ImageIcon(urlAndroidIco));
        
        JLabel aicasIco = new JLabel("");
        aicasIco.setHorizontalAlignment(SwingConstants.CENTER);
        aicasIco.setBounds(12, 5, 241, 74);
        contentPane.add(aicasIco);
        URL urlAicas = getClass().getResource("/images/aicas.png");
        aicasIco.setIcon(new ImageIcon(urlAicas));
        
        btnRun.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) 
            {
                runApp();
            }
        });
    }
    
    
    private void runApp()
    {
        //######### set dimension ##############
        int height = Integer.parseInt(heightTextField.getText());
        if(height<400)
        {
            height = 400;
            String message = "To run correctly the current app, the height is set to 400";
            JOptionPane.showMessageDialog(null, message);
        }
        WidgetProperties.setHeight(height);
        
        //####### set orientation ############
        if(portrait)
        {
            WidgetProperties.setPortrait();
        }
        else
        {
            WidgetProperties.setLandscape();
        }
        
        //######### set the project #########
        project = appList.getSelectedValue().toString();
        
        //System.out.println("Height: " + height);
        //System.out.println("Orientation: " + (portrait ? "portrait" : "landscape"));
        //System.out.println("Project: " + project);
        
        packagePath = "android.androidCode." + project;
        
        projectPath = getProjectPath();
        
        AndroidWrapper androidWrapper = new AndroidWrapper(projectPath, packagePath);      
        androidWrapper.startAndroidActivity();
        
        thisFrame.dispose();
    }
    
    
    /**
     * Get the current working path to load correctly all the files
     * to execute the android app. The string returned changes in base of
     * the Operating system in use.
     * 
     * @return the correct project path for the current operative system
     */
   private String getProjectPath()
   {
       String OS = System.getProperty("os.name").toLowerCase();
       String currentPath =  System.getProperty("user.dir");

      // System.out.println("CurrentPath: " + currentPath);
       if(OS.contains("win"))
       {
           return /*currentPath +*/ "\\android\\androidCode\\" + project + "\\";
       }
       else if(OS.contains("nix") || OS.contains("nux"))
       {
          return /*currentPath +*/ "/android/androidCode/" + project + "/";
       }
       else
       {
           throw new RuntimeException("Operating system not supported");
       }
   }

    
}


















