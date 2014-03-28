package backup_old_classes;

public class SimpleGraphicNode 
{


    private String graphicElement;
    private String androidId;
    private String text;
    private String textSize;
    
    public SimpleGraphicNode()
    {
        graphicElement = "";
        androidId = "";
        text = "";
        textSize = "";
    }
    
    public SimpleGraphicNode(String graphicEl, String id)
    {
        this.graphicElement = graphicEl;
        this.androidId = id;
        text = "";
        textSize = "";
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

    public void setAndroidId(String androidId) 
    {
        if(androidId != null)
        {       
            int i = androidId.indexOf("/");
            if(i>0)
            {
                androidId = androidId.substring(i+1);
            }
            this.androidId = androidId;
        }
    }

    public String getText() 
    {
        return text;
    }

    public void setText(String text) 
    {   //the original xml text is like '@string/hello_world'
        //here we take only the final substring
        if(text != null)
        {       
            int i = text.indexOf("/");
            if(i>0)
            {
                text = text.substring(i+1);
            }
            
            text.replaceAll("_", " ");
            
            this.text = text;
        }
    }

    public String getTextSize() 
    {
        return textSize;
    }

    public void setTextSize(String textSize) 
    {
        if(textSize != null)
        {
            this.textSize = textSize;
        }
    }

    @Override
    public String toString() {
        return "SimpleNode: " + 
                "\n  Graphic element: " + graphicElement +
                "\n  ID: " + androidId +
                "\n  Text: "+ text;
    }
    
    public boolean equals(SimpleGraphicNode other) {
        if(androidId.equals(other.getAndroidId()))
            if(graphicElement.equals(other.getGraphicElement()))
                if(text.equals(other.getText()))
                    if(textSize.equals(other.getTextSize()))
                        return true;
        return false;
    }    
    
    
    
}
