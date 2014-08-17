package android.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.graphics.Canvas;
import android.os.Bundle;
import android.view.SurfaceView;
import android.view.View;
import android.widget.AnalogClock;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.WidgetProperties;
import android.xml.GraphicNodeParser;
import android.xml.TreeGraphicNode;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.Direction;
import edu.cmu.relativelayout.Edge;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;

/**
 * This class is responsable to get all the resources of an Android application
 * and generate the graphics components that they describe.
 * It implements the Singleton Pattern, so you have to invoke getInstance() to instantiate it.
 * 
 * The class uses object from external JAR files which are RelativeLayout and 
 * Jama. The files are downloadable from these two links:
 * 
 * RelativeLayout: http://code.google.com/p/relativelayout/downloads/detail?name=Jama-1.0.2.jar&can=2&q=
 * Jama: http://code.google.com/p/relativelayout/downloads/detail?name=Jama-1.0.2.jar&can=2&q=
 * 
 * @author Antonio Cesarano
 *
 */
public class GraphicDrawer{


    /**
     * Logger to test the class
     */
    private Logger logger = Logger.getLogger("global");

    /**
     * path of the xml file from which read the graphic components informations
     */
    private String xmlPath;

    /**
     * xml parser
     */
    private GraphicNodeParser parser;

    /**
     * bundle used to take the class path informations
     */
    private Bundle bundle;

    /**
     * xml attribute that indicates which activity is in execution
     */
    private String context;

    /**
     * Activity for which the graficDrawer is working
     */
    private Activity owner;

    /**
     * 
     */
    private int contentView;

    /**
     * counter for elements without id
     */
    private int idCount = 1;

    /**
     * JPanel in which the activity is shown
     */
    private ActivityPanel activityPanel;

    /**
     * JFrame reachable pressing the Back button in the main frame
     */
    private JFrame previusFrame;

    /**
     * button to reach the previous frame
     */
    private JButton backButton;

    /**
     * button to exit from the application
     */
    private JButton homeButton;

    /**
     * flag to check if is in use a relativeLayout
     */
    private boolean usingRelativeLayout = false;

    /**
     * A TreeGraphicNode object that represents the xml file as a tree 
     * structure. It is used to draw correctly the graphic components.
     */
    private  TreeGraphicNode tree;

    /**
     * The main frame
     */
    private JFrame frame;

    /**
     * hashMap contains a list of entry made by the value of any graphic 
     * component variable value written in the R.java class and its name.
     * So it's easy have the name of a component searching by its number
     * 
     * example of entry: <0x7f080001,button1> ion R.java
     */
    private HashMap<Integer, String> R_hashMap;

    /**
     * HashMap where keys are a string that represents the graphic element type
     * and the values are the graphic components
     * 
     * example of entry: < EditText, [editText0, columns=20;textSize=10;etc] >
     */
    private HashMap<Integer, Object> componentsMap;



    /**
     * HashMap containing a set of JPanel objects used to wrap objects into panels
     * like RadioButton or CheckBox. 
     * For each RadioGroup object in the graphic layout
     * will be created a panel in which add the RadioGroup children
     */
    private HashMap<Integer, Component> panels;
    
    private Canvas canvas;
    
    private Component lastComponentDrowed = null;
    
    
    /**
     * instance of this class used to implement Singleton Pattern
     */
    private static  GraphicDrawer myInstance = null;


    //_______________________________________________________________________________
    
    
    public static GraphicDrawer getInstance(Bundle bundle){
    	if(myInstance == null){
    		myInstance = new GraphicDrawer(bundle);
    	}
    	return myInstance;
    }
    
    
    
    public static GraphicDrawer getInstance(){
    	if (myInstance == null)
    		throw new RuntimeException("GraphicDrawer not instantiated yet");
    	return myInstance;
    }
    

    /**
     * The constructor takes in input the xml source file for the Android graphic
     * and parse it. Then fill the hashMap with all references of R class 
     * and the relative AndroidId string
     * @param filename xml file path
     * @throws ParserConfigurationException
     */
    public GraphicDrawer(Bundle bundle)
    {   
        this.bundle = bundle;
        xmlPath = bundle.getXmlPath();
        tree = null;
        R_hashMap = new HashMap<Integer, String>();
        componentsMap = new HashMap<Integer,Object>();
        panels = new HashMap<Integer, Component>();
        

        //Creating a Document object that will be parsed by the parser
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;

        try 
        {
            builder = builderFactory.newDocumentBuilder();
            
         //   xmlPath = xmlPath.replace('\\', '/');
            InputStream in = getClass().getResourceAsStream(xmlPath);
            
            
           // document = builder.parse(new FileInputStream(xmlPath));
            document = builder.parse(in);
            document.getDocumentElement().normalize();           
            /*
             * All catch clauses. The program will exit if an exception is thrown
             */
        } catch (FileNotFoundException e) {
            logger.severe("XML file not found. Program will exit");
            System.exit(-1);
        } catch (SAXException e) {
            logger.severe("XML parse error. Program will exit");
            System.exit(-1);
        } catch (IOException e) {
            logger.severe("IO exception was louched. Program will exit");
            System.exit(-1);
        } catch (ParserConfigurationException e) {
            logger.severe("XML parse error: wrong configuration. Program will exit");
            System.exit(-1);
        }
        /*
         * call the GraphicNodeParser constructor to receive a 
         * TreeGraficNode object from the DOM parsed above here
         */
        parser = new GraphicNodeParser(document);
        tree = parser.getGraphicTree();

        try 
        {
            fillR_HashMap();
        } 
        catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } catch (IllegalAccessException e) 
        {
            e.printStackTrace();
        }
        
        myInstance = this;
    }


    //____________________________________________________________________________________________________


    /**
     * Creates a map between graphic elements id and its value from the R class
     * 
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void fillR_HashMap() throws IllegalArgumentException, IllegalAccessException{
        //takes all subclasses in R class
        Class[] classes = bundle.getR().getClasses();
        Field[] fields = null;

        //for each class takes all the fields
        for(int i=0; i<classes.length; i++)
        {
            fields = classes[i].getFields();  
            //for each field put it into the hashMap
            for(int c=0; c<fields.length; c++)
            {
                fields[c].setAccessible(true);
              //  System.out.println("fillR_HashMap() --> " + fields[c].get(null) +" name: "+ fields[c].getName());
            
                //start_edit: IS2 project - MAEESTRO 2013/2014
                if(fields[c].get(null) instanceof Integer){
                	R_hashMap.put((Integer)fields[c].get(null), fields[c].getName());
                }
                else  if(fields[c].get(null) instanceof Integer[]){
                }
                else{
                	throw new IllegalArgumentException("R.java file contains object not allowed");
                }
                //end_edit: IS2 project - MAEESTRO 2013/2014

            } 
        }
    }

    //____________________________________________________________________________________________________

    /**
     * Return the key (id of graphic component written in R.java) 
     * searched by its value. The searching operation is unique beacuse the
     * search criteria is the variable name in the code, that is unique
     * 
     * @param androidId is the variable name
     * @return the key value if exists (e.g. 0xf714539) otherwise -1
     */
    private int getHashKey(String androidId)
    { 
        if( R_hashMap.containsValue(androidId))
        {
            Set<Integer> keys = R_hashMap.keySet();
            for( Integer i : keys)
            {
                if(R_hashMap.get(i).equalsIgnoreCase(androidId)){
                    return i;
                }
            }
        }
        return -1;
    }


    //____________________________________________________________________________________________________

    /**
     * Creates the main frame in which the activity will be executed and 
     * generates the Swing graphic components from the TreeGraphicNode tree
     * parsed in the constructor. 
     */
    public void generateGraphic()
    {
        generateGUI();              


        //create the tree structure of the components
        draw(tree);
        //add the components to the activity panel
        addAll(tree, activityPanel);

        //final settings for the main frame
        showGraphic();
       
        activityPanel.setCanvas(new Canvas(activityPanel.getGraphics()));
    }



	private void generateGUI() {
		//Main frame in wich put the commands panel and the activity panel
        frame = createMainFrame();
        
        //Panel for the system buttons located in the bottom of the screen
        JPanel comandPanel = createCommandsPanel();          

        //The panel in which the Android app will be execute
        activityPanel = createActivityPanel();
        
        //Create a scrollable panel that contains the activity panel
        JScrollPane activityPanelScrollable = createScrollablePanel();
        
        /* 
         * put the panel in which execute the activity at north and
         * the panel with the system buttons (Back, Home, RecentApps) at south
         */
        frame.getContentPane().add(comandPanel,BorderLayout.SOUTH);
        frame.getContentPane().add(activityPanelScrollable,BorderLayout.NORTH);
	}


    //_________________________________________________________________________

    /**
     * 
     */
    private JFrame createMainFrame() 
    {
        int width = WidgetProperties.getFRAME_WIDTH();
        int height = WidgetProperties.getFRAME_HEIGHT();
       
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("MAEESTRO"); 
        frame.setPreferredSize(new Dimension(width,height));
        frame.setSize(width,height);
        frame.setLocation(WidgetProperties.getX_LOCATION(), WidgetProperties.getY_LOCATION());
        frame.getContentPane().setLayout(new BorderLayout());
       // frame.setResizable(false);
        lastComponentDrowed = frame.getContentPane();
                
        if(previusFrame != null)
        {
            previusFrame.setVisible(false);
            frame.setLocation(previusFrame.getLocation());
        }
        return frame;
    }

    //_________________________________________________________________________

    /**
     * 
     * @return
     */
    private JScrollPane createScrollablePanel() 
    {
        int w = WidgetProperties.getACTIVITY_WIDTH();
        int h = WidgetProperties.getACTIVITY_HEIGHT() - 20;
        
        JScrollPane activityPanelScrollable = new JScrollPane(activityPanel);
        activityPanelScrollable.setPreferredSize(new Dimension(w, h));
        activityPanelScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        activityPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        return activityPanelScrollable;
    }

    //_________________________________________________________________________

    /**
     * 
     * @return
     */
    private JPanel createCommandsPanel() 
    {
        GridLayout layout = new GridLayout(1,4);
        layout.setHgap(WidgetProperties.getFRAME_WIDTH()/ 4 );

        JPanel comandPanel = new JPanel(layout);
        int com_width = WidgetProperties.getFRAME_WIDTH();
        int com_height = WidgetProperties.getCOMMAND_HEIGHT();
        comandPanel.setSize(com_width, com_height);
        comandPanel.setBackground(Color.black);


        JLabel backButton = createBackButton();
        JLabel homeButton = createHomeButton();
        JLabel recentAppsButton = createRecentAppButton();

       
        //adding the buttons to the bottom of the screen
        comandPanel.add(backButton);
        comandPanel.add(homeButton);   
        comandPanel.add(recentAppsButton);
        return comandPanel;
    }

    //_________________________________________________________________________
    
    /**
     * 
     * @return
     */
    private ActivityPanel createActivityPanel() 
    {
        ActivityPanel activityPanel = new ActivityPanel(new FlowLayout(FlowLayout.LEFT,5,5));
        int act_width = WidgetProperties.getACTIVITY_WIDTH();
        int act_height = WidgetProperties.getACTIVITY_HEIGHT() - 40;
        
        /* if the orientation is in portrait the activityPanel size and
         * the activityPanelScrollable size are the same
         * 
         * else the activityPanel will be in portrait but the 
         * activityPanelScrollable will be in landscape.
         * 
         * In this way the screen is rendered as landscape but the contents of 
         * the activity will be rendered for portrait so the user can scroll
         * the entire activity when is in landscape mode
         */
        if(act_width<act_height)
        {
            activityPanel.setPreferredSize(new Dimension(act_width,act_height));
        }
        else
        {
            act_height = act_width;
            act_width = (int) (act_height / 1.6667);
        }
        activityPanel.setPreferredSize(new Dimension(act_width,act_height));
        activityPanel.setSize(new Dimension(act_width,act_height));
        activityPanel.setOpaque(false);
        
        return activityPanel;
    }



    //____________________________________________________________________________________________________


    /**
     * Sets the last attributes of the frame after that all 
     * components are drawn.
     */
    public void showGraphic()
    {
        if(previusFrame != null)
        {
            previusFrame.setVisible(false);
        }
        frame.getContentPane().repaint();
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
        frame.paintComponents(frame.getGraphics());
    }

    //____________________________________________________________________________________________________

    /**
     * Takes a TreeGraphicNode and creates a correspondent Swing object.
     *   
     * List of Android Widget supported:
     *     -RelativeLayout
     *     -LineaLayout
     *     -TextView
     *     -EditText
     *     -Button
     *     -RadioGroup
     *     -RadioButton
     *     -CheckBox
     *     -SeekBar
     *     -ListView
     *     -ImageView
     *     -SurfaceView
     *     -Switch
     *     -Spinner
     * 
     * ##################### IMPORTANT! ######################################
     * 
     * If you want to be able to draw a new Android Widget EDIT THIS METHOD.
     * 
     * #######################################################################
     *  
     * @param node the TreeGraphicNode to convert in Swing object
     * @return a Swing object represented by @node
     */
    private Object drawComponent(TreeGraphicNode node)
    {
        int id = getHashKey(node.getAndroidId());
        //May be an element doesn't have an id 
        if(id<=0)
        {
            id = idCount;
            idCount++;
            //create an entry for that node
            R_hashMap.put(id, node.getAndroidId());
        }
        // ###### Layout #######
        if(node.getGraphicElement().endsWith("Layout"))
        {
            this.context = node.getContext();
            if(node.getGraphicElement().equals("RelativeLayout"))
            {
                RelativeLayout layout = new RelativeLayout();
                usingRelativeLayout = true;
                return layout;
            }
            if(node.getGraphicElement().equals("LinearLayout"))
            {
                FlowLayout layout = new FlowLayout(FlowLayout.CENTER,5,5);
                usingRelativeLayout = false;
                return layout;
            }    
        }  
        // ###### TextView #######
        else if(node.getGraphicElement().equals("TextView"))
        {
            TextView label = new TextView(id);
            label.setName(node.getAndroidId());
            label.setText(node.getText());
            label.setTextSize(node.getTextSize());
            label.setFont(WidgetProperties.getFONT());

            return label;
        }
        // ###### Button #######
        else if(node.getGraphicElement().equals("Button"))
        {
            Button button = new Button(id);
            button.setName(node.getAndroidId());
            button.setText(node.getText());
            button.setFont(WidgetProperties.getFONT());
            return button;
        }
        // ###### EditText #######
        else if(node.getGraphicElement().equals("EditText"))
        {
            EditText text = new EditText(id);
            text.setName(node.getAndroidId());
            text.setEditable(true);
            text.setFont(WidgetProperties.getFONT());
            return text;
        }   
        // ###### RadioButton #######
        else if(node.getGraphicElement().equals("RadioButton"))
        {
            RadioButton rb = new RadioButton(id);
            rb.setName(node.getAndroidId());
            rb.setText(node.getText());
            rb.setSelected(node.isChecked());
            rb.setFont(WidgetProperties.getFONT());
            return rb;
        }
        // ###### RadioGroup #######
        else if(node.getGraphicElement().equals("RadioGroup"))
        {
            RadioGroup rg = new RadioGroup(id); 
            rg.setName(node.getAndroidId());
            return rg;
        }
        //###### CheckBox #######
        else if(node.getGraphicElement().equals("CheckBox"))
        {
            CheckBox cb = new CheckBox(id);
            cb.setName(node.getAndroidId());
            cb.setText(node.getText());
            cb.setSelected(node.isChecked());
            cb.setFont(WidgetProperties.getFONT());
            return cb;
        }
        //###### SeekBar #######
        else if(node.getGraphicElement().equals("SeekBar"))
        {
            SeekBar sb = new SeekBar(id);
            sb.setName(node.getAndroidId());
            sb.setMinimum(0);
            sb.setMaximum(node.getMax());
            sb.setValue(node.getProgress());
            sb.setPaintTrack(true);
            sb.setPaintTicks(true);
            return sb;
        }
        //######## ListView ############
        else if(node.getGraphicElement().equals("ListView"))
        {
            ListView lv = new ListView(id);
            lv.setName(node.getAndroidId());
            return lv;
        }
        //######## ImageView ###########
        else if(node.getGraphicElement().equals("ImageView"))
        {
            ImageView iv = new ImageView(id);
            iv.setName(node.getAndroidId());
            return iv;
        }
        //######## SurfaceView ###########
        else if(node.getGraphicElement().equals("SurfaceView"))
        {
            SurfaceView sv = new SurfaceView(id);
            sv.setName(node.getAndroidId());
            return sv;
        }
        
        
        //start_edit: IS2 project - MAEESTRO 2013/2014
        
        //########## Switch ############
        else if(node.getGraphicElement().equals("Switch"))
        {
            Switch switch_ = new Switch();
            switch_.setName(node.getAndroidId());
            switch_.setText(node.getText());
            return switch_;
        }
        
        else if(node.getGraphicElement().equals("Spinner"))
        {
            Spinner spinner = new Spinner(null);
            spinner.setName(node.getAndroidId());
            return spinner;
        }
        
        else if(node.getGraphicElement().equals("AnalogClock"))
        {
        	AnalogClock clock = new AnalogClock(null);
        	clock.setName(node.getAndroidId());
            return clock;
        }
        
      //end_edit: IS2 project - MAEESTRO 2013/2014
        
        return null;
    }

    //__________________________________________________________________________

    /**
     * Converts the TreeGraphicNode element given in input to the relative
     * graphic component and put it into the hashMap componentsList.
     * 
     * @param root the root element from which start the tree graphic structure
     * @param parent the main container (e.g. JPanel object)
     */
    private void draw(TreeGraphicNode root)
    {
        Object currentComponent = drawComponent(root);//if the root is a Layout element will be returned a JPanel
        List<TreeGraphicNode> currentChildren = root.getChildren();
        int id = getHashKey(root.getAndroidId());
        this.componentsMap.put(id, currentComponent);
        //recursive call of this method on all children
        for(int i=0; i<currentChildren.size(); i++)
        {	
            draw(currentChildren.get(i));
        }
    }


    //____________________________________________________________________________________________________

    /**
     * Once created all graphic components, this method can add them into the 
     * main JFrame correctly
     * 
     * @param node the node to draw in the main frame
     * @param parent the graphic component that contain the object to draw
     */
    private void addAll(TreeGraphicNode node, Object parent)
    {        
    	
        //get the current graphic component to add to the JFrame
        int key = getHashKey(node.getAndroidId());
        Object currentComponent = componentsMap.get(key);

        /*Android Layout components don't have a key and are located in 
          the layouts hashmap   */
        if(node.getGraphicElement().endsWith("Layout"))
        {
            LayoutManager l = (LayoutManager) currentComponent;    
            currentComponent = parent;
            ((Container)parent).setLayout(l);    
        }       

        //get all TreeGraphicNode children
        List<TreeGraphicNode> currentChildren = node.getChildren();

        //add the currenComponent to it's parent
        add(parent, currentComponent, node);

        // recursive call of this method to draw the node's children
        for(int i=0; i<currentChildren.size(); i++)
        {
            addAll(currentChildren.get(i), currentComponent);
        }
    }

    //_____________________________________________________________________________________________________

    /**
     * Adds a swing element to the above container (the parent)
     * 
     * @param parent the container (Swing instance) in which add the component
     * @param child the object (Swing instance) to add into @parent
     * @param node the TreeGraphicNode from which take the layout information
     */
    private void add(Object parent, Object child, TreeGraphicNode node)
    {         
        /* while setting a layout, the two objects are the same element
         * so it's not possible add an object to itself
         */
        //start_edit: IS2 project - MAEESTRO 2013/2014
    	if (parent == null)
    	{
    		throw new IllegalArgumentException("Parent component missed. Seems like it is null");
    	}
        //end_edit: IS2 project - MAEESTRO 2013/2014

        if(parent == child)
        {
            return;
        }

        if(parent instanceof RadioGroup || child instanceof RadioGroup)
        {
            if(parent instanceof RadioGroup)
            {                
                //add the child to the RadioGroup object so it's checkable 
                //correctly together the other radioButtons
                ((RadioGroup)parent).add(child);
                //add the graphic RadioButton instance to the parent panel 
                int groupId = getHashKey(node.getParent().getAndroidId());
                addToGroup(groupId, child, node); 
            }
            else if(child instanceof RadioGroup)
            {
                int key = getHashKey(node.getAndroidId());
                //creating the panel that represent the radioGroup in the frame
                GridLayout l = new GridLayout(4,1);

                JPanel group = new JPanel(l);
                /* in this way this group will be found by its androidId by 
                 * their children
                 */
                group.setName(node.getAndroidId());
                panels.put(key, group);

                //add the group to the panel
                addToParentWithLayout(parent, group, node);
            }           
        } 
        /* if child and parent are JComponent instances it's possible to add one
         * to the other
         */
        else if(parent instanceof JComponent)
        {
            //System.out.println("DEBUG " + parent + "||" + child + "\n"+node+"\n\n\n");
            addToParentWithLayout(parent, child, node);
        }
        
        lastComponentDrowed = getComponentForLayout(node.getAndroidId());
    }

    //____________________________________________________________________________________________________

    private void addToParentWithLayout(Object parent, Object child, TreeGraphicNode node) 
    {
        if(usingRelativeLayout)
        {
            RelativeConstraints rc = getRelativeConstraints(node, (Component)parent);
            
//          if(node.getGraphicElement().equals("Spinner")){
//        	Vector<String> v = new Vector<String>();
//        	v.add("1");
//        	JComboBox<String> j = new JComboBox<String>(v);
//        	System.out.println("j = " + j);
//        	((Container)parent).add(j,rc);
//         }
//        	else
//    	 System.out.println(node.getGraphicElement());
            ((Container)parent).add((Component) child, rc);
        }
        else
        {
        	((Container)parent).add((Component) child);
        }
    }

    //____________________________________________________________________________________________________

   
    /**
     * Gets the androidId of the key taken in input and returns the relative 
     * graphic element
     * @param key the value passed from the Activity (e.g. R.id.button1)
     * @return the graphic element drawn
     */
    public Object findElementById(int key)
    {
        Object comp = componentsMap.get(key);
        return comp;   
    }


    //__________________________________________________________________________________


    /**
     * Adds the child object (a RadioButton instance) to the JPanel in the 
     * 'groups' hashmap with id=groupId
     * 
     * @param groupId is the key of the hashmap to get the panel. It must be a 
     * value from the R.class. You can get it with 
     * getHashKey(TreeGraphicNode.getAndroidId())
     * @param child the object to add into the JPanel
     */
    private void addToGroup(int groupId, Object child, TreeGraphicNode childNode)
    {
        JPanel panel = (JPanel) panels.get(groupId);
        RelativeConstraints rc = getRelativeConstraints(childNode, panel);
        panel.add((Component) child,rc);
    }

    //__________________________________________________________________________________


    /**
     * Searches the graphic component correspondent to the androidId
     * 
     * @param androidId the unique string for component
     * @return a graphic component like JComponent or RadioGroup
     */
    private Component getComponentForLayout(String androidId)
    {
        //search the component in the hashmap componentsMap
        int key = getHashKey(androidId);
        Object comp = findElementById(key);

        //but if the component is a RadioGroup, it takes the correspondent panel
        //from the hashmap groups
        if(comp instanceof RadioGroup)
        {
            comp = panels.get(key); 
        }

        return (Component) comp;
    }

    //__________________________________________________________________________________

    /**
     * Adds a component into the activity panel
     * 
     * @param view the component to add to the activityPanel
     */
    public void setContentView(View view) {

        if(usingRelativeLayout)
        {
            activityPanel.add((Component) view, new RelativeConstraints());
        }
        else
        {
            activityPanel.add((Component) view);
        }
        
        activityPanel.addViewToComponentsList(view);
       // view.onDraw(canvas);
    }

    //__________________________________________________________________________________



    public HashMap getComponentsMap()
    {
        return this.componentsMap;
    }

    public Bundle getBundle() 
    {
        return bundle;
    }

    public void setBundle(Bundle bundle) 
    {
        this.bundle = bundle;
    }

    public Activity getOwnerActivity() 
    {
        return owner;
    }

    public void setOwnerActivity(Activity owner) 
    {
        this.owner = owner;
    }

    public int getContentView() 
    {
        return contentView;
    }

    public void setContentView(int contentView) 
    {
        this.contentView = contentView;
        String xmlNameFile = R_hashMap.get(contentView);
    }

    public JFrame getPreviusFrame() 
    {
        return previusFrame;
    }


    public void setPreviusFrame(JFrame previusFrame) 
    {
        this.previusFrame = previusFrame;
    }

    public JFrame getCurrentFrame() 
    {
        return frame;
    }
    
    public TreeGraphicNode getTree() {
		return tree;
	}

    
    
    //_________________________________________________________________________    
      
    



	/**
     * Creates a RelativeConstraint object from the TreeGraphicNode properties
     * to add correctly the component into a Container with a RelativeLayout
     * 
     * @param node TreeGraphicNode from which get the layout properties
     * @return a RelativeConstraints the RelativeConstraint to add together 
     * the component
     */
    private RelativeConstraints getRelativeConstraints(TreeGraphicNode node,Component parent)
    {
        // System.out.println("\n___________Creating constraint____________\n");

        BindingFactory bf = new BindingFactory();

        int mTop = node.getMarginTop();
        int mBottom = node.getMarginBottom();
        int mLeft = node.getMarginLeft();
        int mRight = node.getMarginRight();

        String alignBottomId = node.getAlignBottomId();
        String alignTopId = node.getAlignTopId();
        String alignLeftId = node.getAlignLeftId();
        String alignRightId = node.getAlignRightId();

        String aboveId = node.getAboveId();
        String belowId = node.getBelowId();

        boolean centerHorizontal = node.isCenterHorizontal();
        boolean centerVertical = node.isCenterVertical();
        
        boolean alignParentLeft = node.isAlignParentLeft();
        boolean alignParentRight = node.isAlignParentRight();
        boolean alignParentTop = node.isAlignParentTop();
        boolean alignParentBottom = node.isAlignParentBottom();

 
        RelativeConstraints constraints = new RelativeConstraints();
        Component component;
        //>>>>>>> alignBottomId >>>>>>>>>
        if(!alignBottomId.equals(""))
        {
            component = getComponentForLayout(alignBottomId);
            constraints.addBinding(bf.bottomAlignedWith(component));
        }

        //<<<<<<< alignBottomId <<<<<<<<<
        if(!alignTopId.equals(""))
        {
            component = getComponentForLayout(alignTopId);
            constraints.addBinding(bf.topAlign(component));
        }

        //#### belowId && mTop ####
        if(!belowId.equals("") && mTop>0)
        {
            component = getComponentForLayout(belowId);
            constraints.addBinding(new Binding(Edge.TOP, mTop, Direction.BELOW, Edge.BOTTOM, component));          
        }
        else if(belowId.equals("") && mTop>0 && !alignParentTop)
        {
            constraints.addBinding(new Binding(Edge.TOP, mTop, Direction.BELOW, Edge.BOTTOM, lastComponentDrowed));          
        }
        else if(!belowId.equals("") && mTop<=0)
        {
            component = getComponentForLayout(belowId);
            constraints.addBinding(bf.directlyBelow(component));
        }

        //@@@@@@ aboveId && mBottom @@@@@@@@
        if(!aboveId.equals("") && mBottom>0)
        {
            component = getComponentForLayout(aboveId);
            constraints.addBinding(new Binding(Edge.TOP, mBottom, Direction.BELOW, Edge.BOTTOM, component));          
        }
        else if(aboveId.equals("") && mBottom>0 && (!alignParentBottom))
        {
            constraints.addBinding(new Binding(Edge.TOP, mBottom, Direction.BELOW, Edge.BOTTOM, lastComponentDrowed));          
        }
        else if(!aboveId.equals("") && mBottom<=0)
        {
            component = getComponentForLayout(aboveId);
            constraints.addBinding(bf.directlyAbove(component));
        }


        //******* alignLeftId && mLeft ********
        if(!alignLeftId.equals("") && mLeft>0)
        {
            component = getComponentForLayout(alignLeftId);
            constraints.addBinding(new Binding(Edge.LEFT, mLeft, Direction.RIGHT, Edge.LEFT, component));
        }
        else if(alignLeftId.equals("") && mLeft>0 && !alignParentLeft)
        {
            constraints.addBinding(new Binding(Edge.LEFT, mLeft, Direction.RIGHT, Edge.LEFT, lastComponentDrowed));
        }
        else if(!alignLeftId.equals("") && mLeft<=0)
        {
            component = getComponentForLayout(alignLeftId);
            constraints.addBinding(bf.leftAlignedWith(component));
        }

        //========== alignrightId && mRight ============
        if(!alignRightId.equals("") && mRight>0)
        {
            component = getComponentForLayout(alignRightId);
            constraints.addBinding(new Binding(Edge.RIGHT, mRight, Direction.LEFT, Edge.RIGHT, component));
        }
        else if(alignRightId.equals("") && mRight>0 && !alignParentRight)
        {
            constraints.addBinding(new Binding(Edge.RIGHT, mRight, Direction.LEFT, Edge.RIGHT, lastComponentDrowed));
        }
        else if(!alignRightId.equals("") && mRight<=0)
        {
            component = getComponentForLayout(alignRightId);
            constraints.addBinding(bf.rightAlignedWith(component));
        }


        //______________ alignParentTop _______________
        if(alignParentTop)
        {
            if(mTop>0 && alignTopId.equals(""))
            {
                constraints.addBinding(new Binding(Edge.TOP, mTop, Direction.BELOW, Edge.TOP, parent));
            }else
            {
                constraints.addBinding(bf.topAlign(lastComponentDrowed));
            }
        }
        //______________ alignParentBottom _______________
        if(alignParentBottom)
        {
            if(mBottom>0 && alignBottomId.equals(""))
            {
                constraints.addBinding(new Binding(Edge.BOTTOM, mBottom, Direction.ABOVE, Edge.BOTTOM, parent));
            }else
            {
                constraints.addBinding(bf.bottomAlignedWith(parent));
            }
        }
        //______________ alignParentLeft _______________
        if(alignParentLeft)
        {
            if(mLeft>0  && alignLeftId.equals(""))
            {
                constraints.addBinding(new Binding(Edge.LEFT, mLeft, Direction.RIGHT, Edge.LEFT, parent));
            }else
            {
                constraints.addBinding(bf.leftAlignedWith(parent));
            }
        }
        //______________ alignParentRight _______________
        if(alignParentRight)
        {
            if(mRight>0 && alignRightId.equals(""))
            { 
                constraints.addBinding(bf.rightAlignedWith(parent));
            }
            else
            {
                constraints.addBinding(bf.rightAlignedWith(parent));
            }
        }

        if(centerHorizontal)
        {
            constraints.addBinding(bf.horizontallyCenterAlignedWith(parent));
        }

        if(centerVertical)
        {
            constraints.addBinding(bf.verticallyCenterAlignedWith(parent));
        }
        //    System.out.println("Using " + node);
        //   System.out.println(constraints);
        return constraints;  
    }

    //__________________________________________________________________________________


    /**
     * 
     */
    public HashMap getRHashMap()
    {
        return R_hashMap;
    }
    
    //__________________________________________________________________________________

    /**
     * Create the firts button of the command panel
     * 
     * @return a JLabel object with the icon setted and scaled
     */
    public JLabel createBackButton()
    {
        int buttonSize = WidgetProperties.getCOMMAND_BUTTON_SIZE();

        final JLabel backButton = new JLabel();
        backButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        backButton.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        backButton.setOpaque(false);
        backButton.setText(null);
        backButton.setPreferredSize(new Dimension(buttonSize,buttonSize));  
        
        
       
        Image backImage = getScaledImageForCommand("/images/backButton.png"); 
        Image whenPressed = getScaledImageForCommand("/images/backButtonPressed.png");
        final ImageIcon backIcon = new ImageIcon(backImage);
        final ImageIcon backIconPressed = new ImageIcon(whenPressed);
        
        
        backButton.setIcon(backIcon);
        backButton.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) 
            {
                backButton.setIcon(backIcon);

            }
            public void mousePressed(MouseEvent e) 
            {
                backButton.setIcon(backIconPressed);
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) 
            {
                /*
                 * if the app is in the main activity, it will ask for exit
                 * otherwise it will come back to the previous activity
                 */
                if(previusFrame != null)
                {
                    previusFrame.setVisible(true);
                    frame.setVisible(false);
                    previusFrame.setLocation(frame.getLocation());
                }
                else
                {
                    if(JOptionPane.showConfirmDialog(null, "Do you really want to exit?") == JOptionPane.YES_OPTION)
                    {
                        System.exit(0);
                    }
                }
            }
        });
        return backButton;
    }
    
    //__________________________________________________________________________________

    /**
     * Create the second button of the command panel
     * 
     * @return a JLabel object with the icon setted and scaled
     */
    private JLabel createHomeButton()
    {
        int buttonSize = WidgetProperties.getCOMMAND_BUTTON_SIZE();

        final JLabel homeButton = new JLabel();
        homeButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        homeButton.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        homeButton.setPreferredSize(new Dimension(buttonSize,buttonSize));
        homeButton.setOpaque(false);
        homeButton.setText(null);
      
        Image homeImage = getScaledImageForCommand("/images/homeButton.png"); 
        Image whenPressed = getScaledImageForCommand("/images/homeButtonPressed.png");
        final ImageIcon homeIcon = new ImageIcon(homeImage);
        final ImageIcon homeIconPressed = new ImageIcon(whenPressed);
        
        homeButton.setIcon(homeIcon);
        homeButton.addMouseListener(new MouseListener() 
        {
            public void mouseReleased(MouseEvent e) 
            {
                homeButton.setIcon(homeIcon);

            }
            public void mousePressed(MouseEvent e) 
            {
                homeButton.setIcon(homeIconPressed);
            }
            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) 
            {
                //Exit from the app
                frame.dispose();
                frame.removeAll();
                activityPanel.removeAll();
                activityPanel.repaint();
                frame.repaint();
                System.gc();
                //System.exit(0);
                AndroidWrapperLauncher launcher = new AndroidWrapperLauncher();
                launcher.setVisible(true);
                
                
            }
        });
        return homeButton;
    }
    
    //__________________________________________________________________________________

    
    /**
     * Create the third button of the command panel
     * 
     * @return a JLabel object with the icon setted and scaled
     */
    private JLabel createRecentAppButton()
    {
        int buttonSize = WidgetProperties.getCOMMAND_BUTTON_SIZE();

        final JLabel recentAppsButton = new JLabel();
        recentAppsButton.setAlignmentX(JLabel.CENTER_ALIGNMENT);
        recentAppsButton.setAlignmentY(JLabel.BOTTOM_ALIGNMENT);
        recentAppsButton.setOpaque(false);
        recentAppsButton.setText(null);
        recentAppsButton.setPreferredSize(new Dimension(buttonSize,buttonSize)); 
        
        
        Image recentAppsImage = getScaledImageForCommand("/images/thirdButton.png");
        Image whenPressed = getScaledImageForCommand("/images/thirdButtonPressed.png");
        final ImageIcon recentIcon = new ImageIcon(recentAppsImage);
        final ImageIcon recentIconPressed = new ImageIcon(whenPressed);
          
        recentAppsButton.setIcon(new ImageIcon(recentAppsImage));
        recentAppsButton.addMouseListener(new MouseListener() {
            public void mouseReleased(MouseEvent e) 
            {
                recentAppsButton.setIcon(recentIcon);

            }
            public void mousePressed(MouseEvent e) 
            {
                recentAppsButton.setIcon(recentIconPressed);
            }

            public void mouseExited(MouseEvent e) {}
            public void mouseEntered(MouseEvent e) {}
            public void mouseClicked(MouseEvent e) {}
        });
        return recentAppsButton;
    }
    
    
    
    
    //_____________________________________________________________________________________________
    
    /**
     * Load the image from the path and return it with the correct dimension
     * in base of the size of the phone's screen
     * 
     * @param path the path of the image to load
     * @return the image with the correct dimension for the screen
     */
    private Image getScaledImageForCommand(String path) 
    {

       ImageIcon ic = new ImageIcon(getClass().getResource(path));
       
       int h = WidgetProperties.getCOMMAND_BUTTON_SIZE();
       
       Image toreturn = ic.getImage().getScaledInstance(h, h, Image.SCALE_FAST);
       return toreturn;
    }
    
    
    
    public void printTree(){
    	parser.printGraphicNode(tree, 0);
    }
    
}















