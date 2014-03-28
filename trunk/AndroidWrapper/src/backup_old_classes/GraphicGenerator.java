package backup_old_classes;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;


public class GraphicGenerator 
{
    private Logger logger = Logger.getLogger("global");
    private String path;
    private SimpleGraphicNodeDB db;
    private Bundle bundle;
    
    
    /**
     * A list of all graphic component in the xml file
     */
    private  List<SimpleGraphicNode> graphicNodes;
    
    
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
    private HashMap<Integer, String> hashMap;
    
    
    
    /**
     * The constructor takes in input the xml source file for the Android graphic
     * and parse it
     * @param filename xml file path
     * @throws ParserConfigurationException
     */
    public GraphicGenerator(Bundle bundle)
    {   
        this.bundle = bundle;
        path = bundle.getXmlPath();
        graphicNodes = null;
        hashMap = new HashMap<Integer, String>();
        frame = new JFrame();


        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document document = null;
        try 
        {
            builder = builderFactory.newDocumentBuilder();
            document = builder.parse(new FileInputStream(path));
            document.getDocumentElement().normalize();

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
        //call the SimpleGraphicNodeDB constructor to can receive a list of 
        //SimpleGraficNode object from the DOM just parsed
        db = new SimpleGraphicNodeDB(document);
        graphicNodes = db.getAllNodes();
        
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
                frame.setTitle("Aicas/AndroidApp");
                
                FlowLayout flowLayout = new FlowLayout(FlowLayout.CENTER,5,5);
                // in this way each component added to the layout will
                // be added in a new line
                flowLayout.setHgap(1000);
                frame.getContentPane().setLayout(flowLayout);
                //end frame settings
            }
        }); 
    }
    
    
    
    
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
    
    
    /*
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
    */
    
    
    
    /**
     * Gets the androidId of the key taken in input and generates the relative 
     * graphic element
     * @param key the value passed from the Activity (e.g. R.id.button1)
     * @return the graphic element drawed
     */
    public JComponent drawElementById(int key)
    {
        JComponent component = null;  
        String androidIdString = hashMap.get(key);
        SimpleGraphicNode sgn = getSimpleGraphicNodeById(androidIdString);
        
        //System.out.println("drawElementById: key=" + key + " androidID=" + androidIdString);
        //System.out.println(sgn);
        if(sgn != null)
        {
            component = printElement(sgn);
        }
        graphicNodes.remove(sgn);
        return component;
    }
    
    
    /**
     * Takes a SimpleGraphicNode and creates a correspondent Swing object 
     * and prints it in the main frame
     * @param frame  the JFrame in which print the Swing object
     * @param graphicElement the SimpleGrapchicNode element to convert in Swing object
     */
    public JComponent printElement(SimpleGraphicNode graphicElement) 
    {           
        int id = getHashKey(graphicElement.getAndroidId());
        // ###### TextView #######
        if(graphicElement.getGraphicElement().equals("TextView"))
        {
            TextView label = new TextView(id);
            label.setText(graphicElement.getText());
            frame.getContentPane().add(label);
            return label;
        }
        // ###### Button #######
        else if(graphicElement.getGraphicElement().equals("Button"))
        {
            Button button = new Button(id);
            button.setText(graphicElement.getText());
            frame.getContentPane().add(button);
            return button;
        }
        // ###### EditText #######
        else if(graphicElement.getGraphicElement().equals("EditText"))
        {
            EditText text = new EditText(id);
            text.setColumns(20);
            text.setEditable(true);
            frame.getContentPane().add(text);
            return text;
        }   
        // ###### RadioButton #######
        else if(graphicElement.getGraphicElement().equals("RadioButton"))
        {
            RadioButton rb= new RadioButton(id);
            rb.setText(graphicElement.getText());
            frame.getContentPane().add(rb);
            return rb;
        }
         return null;
    }
    
    
    /**
     * Searches a simpleGraphicNode in the list by its id
     * @param id is the id of the Graphic element
     * @return a SimpleGraphicNode object if present, otherwise null
     */
    private SimpleGraphicNode getSimpleGraphicNodeById(String id)
    {
        for(int i=0; i<graphicNodes.size(); i++)
        {
            if(graphicNodes.get(i).getAndroidId().equals(id)){
                return graphicNodes.get(i);
            }
        }
        return null;
    }
    
    
    
    
    /**
     * Prints the last graphic elements not printed
     * (usually TextView object, don't called in the source code).
     * Call this method at last to don't lose the reference to the code variables
     */
    public void showGraphic()
    {
        for(int i=0; i<graphicNodes.size(); i++)
        {
            printElement(graphicNodes.get(i));
        }
        frame.getContentPane().repaint();
        frame.repaint();
        frame.setPreferredSize(new Dimension(300, 400));
        frame.pack();
        frame.setVisible(true);
    }
    
    /**
     * This method return the key (graphic component id written in R.java) 
     * seraching by its value. The searching operation is univoque beacuse the
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
    
   
}








