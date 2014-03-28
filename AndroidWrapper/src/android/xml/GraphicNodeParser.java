package android.xml;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class GraphicNodeParser 
{

    /**
     * counter for node without id
     */
    private int counterId = 1;
    
    /**
     * nodeList for parsed elements
     */
    private NodeList nodes;
    
    /**
     * Initializes the nodeList with all nodes in the xml file
     * @param doc the Document that represent the xml file to parse
     */
    public GraphicNodeParser(Document doc)
    {
        nodes = doc.getElementsByTagName(doc.getDocumentElement().getNodeName());
        /*
        printTree(nodes.item(0),0);
        GraphicNode tree = createTree(nodes.item(0));
        printGraphicNode(tree, 0);
        */           
    }

    /**
     * Create a tree contents all graphic elements of the Android application.
     * The use of a tree is necessary to well represent the nested structure of 
     * the elements
     * 
     * @return a TreeGraphicNode contents all graphic components
     */
    public TreeGraphicNode getGraphicTree()
    {
        return createTree(nodes.item(0));
        
    }


    /**
     * Prints the DOM tree starting from the root node
     * 
     * @param root the xml root element
     * @param spaces start number of columns for graphic indentation
     */
    public void printTree(Node root,int spaces)
    {
        for(int i=0; i<spaces;i++)
            System.out.print("-");

        System.out.println(root.getNodeName());
        NodeList children = root.getChildNodes();

        for(int i=0; i<children.getLength();i++)
        {
            Node node = children.item(i);
            if(node.getNodeType() == 1)
            {
                printTree(node,spaces+1);
            }
        }
    }


    /**
     * Prints on the console the entire structure of the GraphicNode object
     * tooke in input
     * @param root the GraphicNode root element
     * @param spaces start number of columns for graphic indentation
     */
    public void printGraphicNode(TreeGraphicNode root, int spaces)
    {
        for(int i=0; i<spaces;i++)
            System.out.print("-");

        System.out.println(root.getAndroidId());
        List<TreeGraphicNode> children = root.getChildren();

        for(int i=0; i<children.size();i++)
        {
            TreeGraphicNode node = children.get(i);
            printGraphicNode(node,spaces+1);

        }
    }


    /**
     * Creates the tree structure from the xml root element 
     * @param root the xml root element
     * @return a GraphicNode object that contains all the graphic elements
     */
    private TreeGraphicNode createTree(Node root)
    {
        TreeGraphicNode treeRoot = getGraphicNodeFrom((Element)root);       
        NodeList children = root.getChildNodes();

        for(int i=0; i<children.getLength();i++)
        {
            Node node = children.item(i);
            if(node.getNodeType() == 1 && !(node.getNodeName().equals("requestFocus")))
            {
                TreeGraphicNode child = getGraphicNodeFrom((Element)node);
                treeRoot.addChild(child);
                child.setParent(treeRoot);
                TreeGraphicNode subTree = createTree(node);

                for(TreeGraphicNode g : subTree.getChildren())
                {
                    child.addChild(g);
                    g.setParent(child);
                }
            }
        }
        return treeRoot;
    }



    /**
     * Creates a TreeGraphicNode by a DOM Element
     * @param el DOM element
     * @return SimpleGraphicNode object
     */
    private TreeGraphicNode getGraphicNodeFrom(Element el) {
        //this variable counts how many elements without id are found
        
        
        String androidId = el.getAttribute("android:id");
        if(androidId == null || androidId.equals(""))// elements without id
        {
            androidId = "element" + counterId;
            counterId++;
        }
        String graphicType = el.getTagName();
        TreeGraphicNode node = new TreeGraphicNode(graphicType,androidId);

        /*
         * Read margin attributes from the node and set the relative 
         * properties to the node
         */
        node.setText(el.getAttribute("android:text"));
       // node.setTextSize(el.getAttribute("android:textSize"));
   
        node.setMarginLeft(el.getAttribute("android:layout_marginLeft"));
        node.setMarginRight(el.getAttribute("android:layout_marginRight"));
        node.setMarginTop(el.getAttribute("android:layout_marginTop"));
        node.setMarginBottom(el.getAttribute("android:layout_marginBottom"));
         
        node.setAlignLeftId(el.getAttribute("android:layout_alignLeft"));
        node.setAlignRightId(el.getAttribute("android:layout_alignRight"));
        node.setAlignTopId(el.getAttribute("android:layout_alignTop"));
        node.setAlignBottomId(el.getAttribute("android:layout_alignBottom"));

        node.setBelowId(el.getAttribute("android:layout_below"));
        node.setAboveId(el.getAttribute("android:layout_above"));
        
        node.setChecked(Boolean.parseBoolean(el.getAttribute("android:checked")));
        
        node.setAlignParentLeft(Boolean.parseBoolean(el.getAttribute("android:layout_alignParentLeft")));
        node.setAlignParentRight(Boolean.parseBoolean(el.getAttribute("android:layout_alignParentRight")));
        node.setAlignParentTop(Boolean.parseBoolean(el.getAttribute("android:layout_alignParentTop")));
        node.setAlignParentBottom(Boolean.parseBoolean(el.getAttribute("android:layout_alignParentBottom")));
        
        node.setMax(el.getAttribute("android:max"));
        node.setProgress(el.getAttribute("android:progress"));
        
        /*
         * Take the listener method e.g. to launch a new activity
         */
        node.setContext(el.getAttribute("tools:context"));
        node.setListenerName(el.getAttribute("android:onClick"));
        
        
        //width and height have always the same value
        String width = el.getAttribute("android:layout_width");
        if(width.equals("wrap_content"))
        {
            node.setWrapContent(true);
        }
        else
        {
            node.setWrapContent(false);
        }
        
        return node;
    }

/*

    public static void main(String args[]) throws ParserConfigurationException, FileNotFoundException, IOException, SAXException
    {
        DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = builderFactory.newDocumentBuilder();
        Document document = builder.parse(new FileInputStream("/home/cesarano/workspace/AndroidWrapper/src/android/androidCode/calculator/activity_main.xml"));
        document.getDocumentElement().normalize();

        GraphicNodeParser db = new GraphicNodeParser(document);
        TreeGraphicNode tree = db.getGraphicTree();
        db.printGraphicNode(tree, 0);
    }
*/

}
