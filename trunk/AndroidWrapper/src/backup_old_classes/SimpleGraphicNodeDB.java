package backup_old_classes;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class SimpleGraphicNodeDB 
{
    private NodeList nodes;
    List<SimpleGraphicNode> list;

    public SimpleGraphicNodeDB(Document doc)
    {
        String root = doc.getDocumentElement().getNodeName();
       // System.out.println("Root: " + root);
        nodes = doc.getElementsByTagName(root);
       // System.out.println("The nodeList has: "+ nodes.getLength()+" elements");
    }

    private NodeList getNodes()
    {
        return nodes;
    }


    public void setNodes(NodeList nodes) {
        this.nodes = nodes;


    }
    
    
    public List<SimpleGraphicNode> getAllNodes()
    {
        list = new ArrayList<SimpleGraphicNode>();
        //usually each element in nodes is a Layout element and inside it
        //there can be Buttons, TextVienw, EditText or other Layout elements
        for(int i=0; i<nodes.getLength(); i++)
        {
            putAllChildsFromParentInList(nodes.item(i), list);
           
        }
        return list;
    }


    /**
     * Takes an element and add to the list all its children 
     * ##### TO ADD MORE GRAPHIC OBJECT EDIT THIS METHOD ####
     * 
     * @return the node element not yet requested, if there is. Otherwise null.
     */
    public void putAllChildsFromParentInList(Node node, List<SimpleGraphicNode> list)
    {      
            Element el = (Element)node;
            SimpleGraphicNode sgn = getSimpleNodeFrom(el);
            addIfNotPresent(sgn,list);

            //adding the textView object in the SimplegraphicNode list
            NodeList tempList = el.getElementsByTagName("TextView");
            putAllInList(list, tempList);
            
            //adding the Button object in the SimplegraphicNode list
            tempList = el.getElementsByTagName("Button");
            putAllInList(list, tempList);
            
            //adding the EditText object in the SimplegraphicNode list
            tempList = el.getElementsByTagName("EditText");
            putAllInList(list, tempList);   
            
            tempList = el.getElementsByTagName("RadioButton");
            putAllInList(list, tempList);   
            
            
           // System.out.println("------------------executing getAllNodes(): \n" + sgn);
            addIfNotPresent(sgn,list);
            
         
    }

    /**
     * Ausiliare method that add to the list the target element
     * @param list a the list of SimpleGraphicNode object
     * @param tempList
     */
    private void putAllInList(List<SimpleGraphicNode> list, NodeList tempList) {
        for(int c=0; c<tempList.getLength(); c++)
        {
            Element el = (Element)tempList.item(c);
            SimpleGraphicNode node = getSimpleNodeFrom(el);
            addIfNotPresent(node, list);
           // System.out.println("------------------executing putAllInList: \n" + node);
        }
    }

    /**
     * Creates a SimpleGraphicNode by a DOM Element
     * @param el DOM element
     * @return SimpleGraphicNode object
     */
    private SimpleGraphicNode getSimpleNodeFrom(Element el) {
        SimpleGraphicNode node = new SimpleGraphicNode();
        node.setGraphicElement(el.getTagName());
        node.setAndroidId(el.getAttribute("android:id"));
        node.setText(el.getAttribute("android:text"));
        node.setTextSize(el.getAttribute("android:textSize"));
        return node;
    }


    
    /**
     * Adds an element in the list only if it is not present 
     * @param node
     * @param list
     */
    private void addIfNotPresent(SimpleGraphicNode node, List<SimpleGraphicNode> list)
    {
        for(int i=0; i<list.size(); i++)
        {
            if(list.get(i).equals(node))
            {
                return;
            }
        }
        list.add(node);
    }

    
    
    /**
     * To test
     * @param args
     * @throws ParserConfigurationException
     * @throws FileNotFoundException
     * @throws SAXException
     * @throws IOException
     *

    public static void main(String args[]) throws ParserConfigurationException, FileNotFoundException, SAXException, IOException
    {


        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = null;

        builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream("/home/cesarano/workspace/AndroidWrapper/src/com/aicas/android/androidCode/HelloWorld/activity_main.xml"));

        //    System.out.println("root of xml file" + document.getDocumentElement().getNodeName());

        SimpleGraphicNodeDB db = new SimpleGraphicNodeDB(document);

        List<SimpleGraphicNode> graphicNodes = db.getAllNodes();

        //  System.out.println(graphicNodes.size());
    }
        */
}


















