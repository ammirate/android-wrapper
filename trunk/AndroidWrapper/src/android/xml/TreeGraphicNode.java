package android.xml;

import java.util.ArrayList;
import java.util.List;

/**
 * The TreeGraphicNode object is a recursive structure that allows to create a 
 * hierarchical structure like a tree. 
 * 
 * Each node has an id, a graphicType, some attributes, a parent and a list 
 * of children
 * 
 * This class maps a parsed XML node into a Swing component that 
 * includes all the informations about it's position in a RelativeLayout and
 * others.
 * @author Antonio Cesarano
 *
 */
public class TreeGraphicNode 
{
    /**
     * grapgicElement represents which graphic object the node represent
     * e.g. Button, TextView, RadioButton, etc
     */
    private String graphicElement;
    
    /**
     * Unique string used to identify an element
     */
    private String androidId;

    /**
     * Structure details
     */
    private List<TreeGraphicNode> children;
    private TreeGraphicNode parent;
    /**
     * Layout details
     */
    private String text;
    private int textSize;
    
    private int marginTop = 0;
    private int marginBottom = 0;
    private int marginLeft = 0;
    private int marginRight = 0;
    
    private String alignBottomId;
    private String alignTopId;
    private String alignLeftId;
    private String alignRightId;
    
    private String aboveId;
    private String belowId;
    private boolean centerHorizontal = false;
    private boolean wrapContent = false;
    private boolean fillParent = false;
    private boolean checked = false;
    
    private boolean alignParentLeft = false;
    private boolean alignParentRight = false;
    private boolean alignParentTop = false;
    private boolean alignParentBottom = false;
    
    private String listenerName;
    private String context;

    private int max;
    private int progress;
    /**
     * Defines the type of the graphic component and sets its ID
     * @param graphicEl type of graphic component (e.g. Button, TextView)
     * @param id unique name that identifies the component in the XML file
     */
    public TreeGraphicNode(String graphicEl, String id)
    {
        this.graphicElement = graphicEl;
        this.setAndroidId(id);
        children = new ArrayList<TreeGraphicNode>();
        parent = null;
    }


    /**
     * Takes the margin attribute written in Android xml and convert it into
     * an Integer value, removing all unused substring
     * @param margin string with margin attribute
     * @return a real margin value
     */
    private int getMargin(String margin) {
        int realMargin = 0;
        if( margin != null && !(margin.equals("")) )
        {
            margin = margin.replace("dp", "");
            margin = margin.replace("pt", "");  
            margin = margin.replace("px", "");  
            
            realMargin = Integer.parseInt(margin);
        }
        return realMargin;
    }
    
    
    public void setMarginTop(String marginTop) 
    {
        this.marginTop = getMargin(marginTop);
    }

    
    public int getMarginBottom() 
    {
        return marginBottom;
    }


    public void setMarginBottom(String marginBottom) 
    {
        this.marginBottom = getMargin(marginBottom);
    }


    public int getMarginLeft() 
    {
        return marginLeft;
    }


    public void setMarginLeft(String marginLeft) 
    {
        this.marginLeft = getMargin(marginLeft);
    }


    public int getMarginRight() {
        return marginRight;
    }


    public void setMarginRight(String marginRight) 
    {
        this.marginRight = getMargin(marginRight);
    }


    public String getAlignBottomId() 
    {
        return alignBottomId;
    }


    public void setAlignBottomId(String alignBottomId) 
    {
        this.alignBottomId = removeUnusedChars(alignBottomId);
    }


    public String getAlignTopId() 
    {
        return alignTopId;
    }


    public void setAlignTopId(String alignTopId) 
    {
        this.alignTopId = removeUnusedChars(alignTopId);
    }


    public String getAlignLeftId() 
    {
        return alignLeftId;
    }


    public void setAlignLeftId(String alignLeftId) 
    {
        this.alignLeftId = removeUnusedChars(alignLeftId);
    }


    public String getAlignRightId() 
    {
        return alignRightId;
    }


    public void setAlignRightId(String alignRightId) 
    {
        this.alignRightId = removeUnusedChars(alignRightId);
    }


    public String getAboveId() 
    {
        return aboveId;
    }


    public void setAboveId(String aboveId) 
    {
        this.aboveId = removeUnusedChars(aboveId);
    }


    public String getBelowId() 
    {
        return belowId;
    }


    public void setBelowId(String belowId) 
    {
        this.belowId = removeUnusedChars(belowId);
    }


    public boolean isCenterHorizontal() 
    {
        return centerHorizontal;
    }


    public void setCenterHorizontal(boolean centerHorizontal) 
    {
        this.centerHorizontal = centerHorizontal;
    }


    public void setChildren(List<TreeGraphicNode> children) {
        this.children = children;
    }


    public List<TreeGraphicNode> getChildren() 
    {
        return children;
    }


    public TreeGraphicNode getParent() 
    {
        return parent;
    }

    public void setParent(TreeGraphicNode parent) 
    {
        this.parent = parent;
    }

    public String getGraphicElement() 
    {
        return graphicElement;
    }

    public void setGraphicElement(String graphicElement) 
    {
        this.graphicElement = graphicElement;
    }

    public String getAndroidId() 
    {
        return androidId;
    }

    
    /**
     * Sets the id for this node
     * @param androidId
     */
    public void setAndroidId(String androidId) 
    {
        this.androidId = removeUnusedChars(androidId);
    }

    
    public String getText() 
    {
        return text;
    }
    
    
    /**
     * Sets the Text attribute of this node
     * @param text the text of the component
     */
    public void setText(String text) 
    {
      //the original xml text is like '@string/hello_world'
        //here we take only the final substring
        if(text != null)
        {       
            text = removeUnusedChars(text);
            text.replaceAll("_", " ");     
            this.text = text;
        }
    }
    
    
    /**
     * Returns a string without all unused characters
     * e.g. '@+id/button1' will become 'button1'
     * @param string
     * @return
     */
    private String removeUnusedChars(String string)
    {
        int i = string.indexOf("/");
        if(i>0)
        {
            string = string.substring(i+1);
        }
        return string;
    }
    
    public int getTextSize() 
    {
        return textSize;
    }
    
    public void setTextSize(String textSize) 
    {
        if(!textSize.equals(""))
            this.textSize = Integer.parseInt(textSize);
    }

    public void addChild(TreeGraphicNode child)
    {
        children.add(child);
    }
 

    public boolean isWrapContent() {
        return wrapContent;
    }


    public void setWrapContent(boolean wrapContent) {
        this.wrapContent = wrapContent;
        this.fillParent = !wrapContent;
    }


    public boolean isFillParent() {
        return fillParent;
    }


    public void setFillParent(boolean fillParent) {
        this.fillParent = fillParent;
        this.wrapContent = !fillParent;
    }


    public void setMarginTop(int marginTop) {
        this.marginTop = marginTop;
    }


    public void setMarginBottom(int marginBottom) {
        this.marginBottom = marginBottom;
    }


    public void setMarginLeft(int marginLeft) {
        this.marginLeft = marginLeft;
    }


    public void setMarginRight(int marginRight) {
        this.marginRight = marginRight;
    }

    
    
    public boolean isChecked() {
        return checked;
    }


    public void setChecked(boolean checked) {
        this.checked = checked;
    }
    
    
    public boolean isAlignParentLeft() {
        return alignParentLeft;
    }


    public void setAlignParentLeft(boolean alignParentLeft) {
        this.alignParentLeft = alignParentLeft;
    }


    public boolean isAlignParentRight() {
        return alignParentRight;
    }


    public void setAlignParentRight(boolean alignParentRight) {
        this.alignParentRight = alignParentRight;
    }


    public boolean isAlignParentTop() {
        return alignParentTop;
    }


    public void setAlignParentTop(boolean alignParentTop) {
        this.alignParentTop = alignParentTop;
    }


    public boolean isAlignParentBottom() {
        return alignParentBottom;
    }


    public void setAlignParentBottom(boolean alignParentBottom) {
        this.alignParentBottom = alignParentBottom;
    }
    public String toString()
    {
        return "[GraphicType: " + getGraphicElement() + ", AndroidID: " + getAndroidId() + "]";
    }
    
    

    public String getContext() {
        return context;
    }


    public void setContext(String context) {
        this.context = context.replace(".", "");
    }
    
    

    public String getListenerName() {
        return listenerName;
    }


    public void setListenerName(String listenerName) {
        this.listenerName = listenerName;
    }
    
    public boolean isCenterVertical() 
    {
        return centerVertical;
    }


    public void setCenterVertical(boolean centerVertical) 
    {
        this.centerVertical = centerVertical;
    }

    private boolean centerVertical;


    public int getMarginTop() 
    {
        return marginTop;
    }


    public int getMax() {
        return max;
    }


    public void setMax(String max) {
        if(max.equals(""))
            this.max=100;
        else
            this.max = Integer.parseInt(max);
    }


    public int getProgress() {
        return progress;
    }


    public void setProgress(String progress) {
        if(progress.equals(""))
            this.progress=10;
        else
            this.progress = Integer.parseInt(progress);
    }
    
    
    
    
}









