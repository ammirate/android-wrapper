package android.graphics;

import java.util.ArrayList;
import java.util.List;

public class Path 
{    
    private List<Line> lines;
    
    private Point start,end;
    

    public Path()
    {
       // start = new Point(0, 0);
       // end = new Point(0,0);
        lines = new ArrayList<Path.Line>();
    }


    public void moveTo(float x, float y) 
    {
        start = new Point((int)x, (int)y);
    }

    public void lineTo(float x, float y) 
    {
        end = new Point((int)x, (int)y);
        
        lines.add(new Line(start,end));
        
        start = end;
    }



    public List<Line> getLines() {
        return lines;
    }


    /**
     * Point
     * @author cesarano
     *
     */
    public class Point
    {
        private int x;
        private int y;

        public Point(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
        public int getX(){return x;}
        public int getY(){return y;}
        public String toString(){ return "("+x+","+y+") "; }
    }
    /**
     * Line
     * @author cesarano
     *
     */
    public class Line
    {
        private Point start;
        private Point end;
        
        public Line(Point s, Point e)
        {
            start = s;
            end = e;
        }
        
        public Point getStart(){ return start;}
        public Point getEnd(){return end;}
    }


}
