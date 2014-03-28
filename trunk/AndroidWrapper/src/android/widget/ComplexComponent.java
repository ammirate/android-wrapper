package android.widget;

import java.util.List;

public interface ComplexComponent extends AndroidWidget
{

    public List<AndroidWidget> getChildren();
    
}
