package backup_old_classes;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.xml.GraphicNodeParser;
import android.xml.TreeGraphicNode;
import edu.cmu.relativelayout.Binding;
import edu.cmu.relativelayout.BindingFactory;
import edu.cmu.relativelayout.Direction;
import edu.cmu.relativelayout.Edge;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;


public class GraphicGenerator2 
{
    private Logger logger = Logger.getLogger("global");
    private String path;
    private GraphicNodeParser parser;
    private Bundle bundle;
    private boolean usingRelativeLayout = false;
    private BindingFactory bindingFactory = new BindingFactory();


    /**
     * A tree of all graphic component in the xml file
     */
    private  TreeGraphicNode tree;


    /**
     * The main frame
     */
    private JFrame frame;
    private Container container;


    /**
     * hashMap contains a list of entry made by the value of any graphic 
     * component variable value written in the R.java class and its name.
     * So it's easy have the name of a component searching by its number
     * 
     * example of entry: <0x7f080001,button1> ion R.java
     */
    private HashMap<Integer, String> hashMap;

    /**
     * HashMap where keys are a string that represents the graphic element type
     * and the values are the graphic components
     */
    private HashMap<Integer, Object> componentsList;




    /**
     * The constructor takes in input the xml source file for the Android graphic
     * and parse it
     * @param filename xml file path
     * @throws ParserConfigurationException
     */
    public GraphicGenerator2(Bundle bundle)
    {   
        this.bundle = bundle;
        path = bundle.getXmlPath();
        tree = null;
        hashMap = new HashMap<Integer, String>();
        componentsList = new HashMap<Integer,Object>();
        frame = new JFrame();
        container = frame.getContentPane();
        container.setLayout(new RelativeLayout());

        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try 
        {
            builder = builderFactory.newDocumentBuilder();
            document = builder.parse(new FileInputStream(path));
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
         * call the GraphicNodeParser constructor to can receive a 
         * TreeGraficNode object from the DOM parsed above here
         */
        parser = new GraphicNodeParser(document);
        tree = parser.getGraphicTree();

        try 
        {
            fillHashMap();
        } 
        catch (IllegalArgumentException e) 
        {
            e.printStackTrace();
        } catch (IllegalAccessException e) 
        {
            e.printStackTrace();
        }
    }

  //____________________________________________________________________________________________________

    /**
     * Generate the Swing graphic from the list of SimpleGraphicNode object
     * parsed in the constructor. It creates a new JFrame and for each 
     * SimpleGraphicNode in the list call @method printElement.
     */
    public void generateGraphic()
    {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setTitle("Aicas-AndroidWrapper"); 
                frame.setPreferredSize(new Dimension(300, 400));
                frame.getContentPane().setLayout(new FlowLayout(FlowLayout.LEFT,5,5));
                draw(tree,frame.getContentPane());
            }
        }); 
    }
    
  //____________________________________________________________________________________________________


    /**
     * Prints the last graphic elements not printed
     * (usually TextView object, don't called in the source code).
     * Call this method at last to don't lose the reference to the code variables
     */
    public void showGraphic()
    {
        frame.getContentPane().repaint();
        frame.repaint();
        frame.pack();
        frame.setVisible(true);
    }

  //____________________________________________________________________________________________________


    /**
     * Creates a map between graphic elements id and their value from the R class
     * @throws IllegalArgumentException
     * @throws IllegalAccessException
     */
    private void fillHashMap() throws IllegalArgumentException, IllegalAccessException{
        //takes all subclasses in R class
        Class[] classes = bundle.getR().getClasses();
        Field[] fields = null;

        //for each class takes all the fields
        for(int i=0; i<classes.length; i++)
        {
            fields = classes[i].getFields();           
            for(int c=0; c<fields.length; c++)
            {
                fields[c].setAccessible(true);
                hashMap.put((Integer)fields[c].get(null), fields[c].getName());
            } 
        }
    }

//____________________________________________________________________________________________________

    /**
     * This method return the key (graphic component id written in R.java) 
     * searching by its value. The searching operation is univoque beacuse the
     * search criteria is the variable name in the code, that is unique
     * @param androidId is the variable name
     * @return the key value if exists (e.g. 0xf714539) otherwise -1
     */
    private int getHashKey(String androidId)
    {
        if( hashMap.containsValue(androidId))
        {
            Set<Integer> keys = hashMap.keySet();
            for( Integer i : keys)
            {
                if(hashMap.get(i).equalsIgnoreCase(androidId)){
                    return i;
                }
            }
        }
        return -1;
    }



    //        DA IMPLEMENTARE SUBITO
    /**
     * Gets the androidId of the key taken in input and generates the relative 
     * graphic element
     * @param key the value passed from the Activity (e.g. R.id.button1)
     * @return the graphic element drawed
     */
    public JComponent findElementById(int key)
    {
        return null;
    }

  //____________________________________________________________________________________________________


    /**
     * Takes a TreeGraphicNode and creates a correspondent Swing object 
     * @param node is the TreeGraphicNode to convert in Swing object
     * @return a Swing object represented by @node
     */
    private Object drawComponent(TreeGraphicNode node)
    {
        int id = getHashKey(node.getAndroidId());

        // ###### RelativeLayout #######
        if(node.getGraphicElement().equals("RelativeLayout"))
        {
            RelativeLayout layout = new RelativeLayout();
            JPanel panel = new JPanel();
            panel.setLayout(layout);
            usingRelativeLayout = true;
            return panel;
        }
        // ###### LinearLayout #######
        if(node.getGraphicElement().equals("LinearLayout"))
        {
            FlowLayout layout = new FlowLayout(FlowLayout.CENTER,5,5);
            //     layout.setHgap(50);//each component will be in a new row
            JPanel panel = new JPanel();
            panel.setLayout(layout);
            return panel;
        }       
        // ###### TextView #######
        else if(node.getGraphicElement().equals("TextView"))
        {
            TextView label = new TextView(id);
            label.setText(node.getText());
            usingRelativeLayout = true;
            return label;
        }
        // ###### Button #######
        else if(node.getGraphicElement().equals("Button"))
        {
            Button button = new Button(id);
            button.setText(node.getText());
            return button;
        }
        // ###### EditText #######
        else if(node.getGraphicElement().equals("EditText"))
        {
            EditText text = new EditText(id);
            text.setColumns(20);
            text.setEditable(true);
            return text;
        }   
        // ###### RadioButton #######
        else if(node.getGraphicElement().equals("RadioButton"))
        {
            RadioButton rb = new RadioButton(id);
            rb.setText(node.getText());
            return rb;
        }
        // ###### RadioGroup #######
        else if(node.getGraphicElement().equals("RadioGroup"))
        {
            RadioGroup rg = new RadioGroup(id);
            return rg;
        }

        return null;
    }

  //____________________________________________________________________________________________________


    /**
     * Converts the TreeGraphicNode element given in input to the relative
     * complete graphic layout.
     * @param root the root element from which start the tree graphic structure
     * @param parent the main container (e.g. JPanel object)
     */
    private void draw(TreeGraphicNode root, Object parent)
    {
        Object currentParent = parent;
        Object currentComponent = drawComponent(root);//if the root is a Layout element will be returned a JPanel
        List<TreeGraphicNode> currentChildren = root.getChildren();

        add(currentParent,currentComponent,root);

        //recursive call of this method on all children
        for(int i=0; i<currentChildren.size(); i++)
        {
            draw(currentChildren.get(i), currentComponent);
        }
    }
//____________________________________________________________________________________________________
    
    /**
     * Adds a swing element to the above container (the parent)
     * @param parent the container (Swing instance) in which add the component
     * @param child the object (Swing instance) to add into @parent
     * @param node the TreeGraphicNode from which take the layout information
     */
    private void add(Object parent, Object child,TreeGraphicNode node)
    { 
        if(parent instanceof RadioGroup || child instanceof RadioGroup)
        {
            if(parent instanceof RadioGroup)
            {                
                ((RadioGroup)parent).add(child);
                if(usingRelativeLayout)
                {
                    RelativeConstraints rc = getRelativeConstraints(node, (View)child);
                    frame.getContentPane().add((View)child,rc);
                }else
                {
                    frame.getContentPane().add((View)child);
                }
            }
            else
            {
                /* if the child is a RadioGroup element, you can't add it in any
                 * swing object, it is useful only as parent */
            }
        } 
        else if(parent instanceof JComponent)
        {
            if(usingRelativeLayout)
            {
                RelativeConstraints rc = getRelativeConstraints(node, (View)child);
                ((Container)parent).add((View) child, rc);

            }
            else
            {
                ((Container)parent).add((View) child);
            }
        }
    }

  //____________________________________________________________________________________________________

    /**
     * Creates a RelativeLayout object from the TreeGraphicNode properties
     * @param node TreeGraphicNode from which get the layout properties
     * @return a RelativeConstraints object
     */
    private RelativeConstraints getRelativeConstraints(TreeGraphicNode node,View component)
    {
        BindingFactory bf = new BindingFactory();
        List<Binding> bindings = new ArrayList<Binding>();

        int mTop = node.getMarginTop();
        int mBottom = node.getMarginBottom();
        int mLeft = node.getMarginLeft();
        int mRight = node.getMarginRight();

        Binding top = new Binding(Edge.TOP, mTop, Direction.BELOW, Edge.TOP, component);
        Binding bottom = new Binding(Edge.TOP, mBottom, Direction.BELOW, Edge.TOP, component);
        Binding left = new Binding(Edge.LEFT, mLeft, Direction.RIGHT, Edge.LEFT, component);
        Binding right = new Binding(Edge.LEFT, mRight, Direction.RIGHT, Edge.LEFT, component);

        return new RelativeConstraints(top,bottom,left,right);
    }


  /*____________________________________________________________________________________________________

    public static void main(String[] args) throws Exception {

        Class[] classes = R.class.getClasses();
        System.out.println("Numbero of classes: " + classes.length);

        for(int i=0; i< classes.length; i++)
        {
            Field[] fields = classes[i].getFields();
            System.out.println("Numbero of fields: " + fields.length);            

            for(int c=0; c< fields.length; c++)
            {
                fields[c].setAccessible(true);
                System.out.println(fields[c] + " = " + fields[c].get(null));
            } 
        }
    }
    __________________________________________________________________________________________________*/


}








