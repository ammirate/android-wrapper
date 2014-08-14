package test.android.app;

import java.awt.Component;
import java.awt.Container;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;
import javax.swing.JPanel;

import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import android.app.GraphicDrawer;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.xml.TreeGraphicNode;
import edu.cmu.relativelayout.RelativeConstraints;
import edu.cmu.relativelayout.RelativeLayout;

public class GraphicDrawerTest4 extends TestCase{

	private GraphicDrawer gd;
	private Bundle b;
	private Class R_class;

	private Method methodDrawComponent;
	private Method methodAdd;
	private Method methodDraw;
	private Method methodAddAll;

	private TreeGraphicNode elToDraw;


	public GraphicDrawerTest4(String s) throws Exception{
		super(s);

		methodDrawComponent = GraphicDrawer.class.getDeclaredMethod("drawComponent", TreeGraphicNode.class);
		methodDrawComponent.setAccessible(true);

		methodAdd = GraphicDrawer.class.getDeclaredMethod("add", Object.class, Object.class, TreeGraphicNode.class);
		methodAdd.setAccessible(true);

		methodDraw = GraphicDrawer.class.getDeclaredMethod("draw", TreeGraphicNode.class);
		methodDraw.setAccessible(true);

		methodAddAll = GraphicDrawer.class.getDeclaredMethod("addAll", TreeGraphicNode.class, Object.class);
		methodAddAll.setAccessible(true);

		R_class = R.class;

		b = new Bundle("/test/android/app/test_DOM.xml", R_class);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp()  {
		gd = new GraphicDrawer(b);
		assertNotNull(gd);
	}

	@After
	public void tearDown() throws Exception {
		gd = null;
	}

	@Test
	public void test()  {
		try 
		{
			suite();
		} 
		catch (Exception e){ e.printStackTrace(); }
	}


	public static TestSuite suite() throws Exception {
		TestSuite suite= new TestSuite();

		suite.addTest(new GraphicDrawerTest4("TC4_1_1"));
		suite.addTest(new GraphicDrawerTest4("TC4_1_2"));
		suite.addTest(new GraphicDrawerTest4("TC4_1_3"));

		suite.addTest(new GraphicDrawerTest4("TC4_2_1"));
		suite.addTest(new GraphicDrawerTest4("TC4_2_2"));
		suite.addTest(new GraphicDrawerTest4("TC4_2_3"));
		suite.addTest(new GraphicDrawerTest4("TC4_2_4"));

		suite.addTest(new GraphicDrawerTest4("TC4_3_1"));
		suite.addTest(new GraphicDrawerTest4("TC4_3_2"));

		suite.addTest(new GraphicDrawerTest4("TC4_4_1"));
		suite.addTest(new GraphicDrawerTest4("TC4_4_2"));
		suite.addTest(new GraphicDrawerTest4("TC4_4_3"));

		return suite;
	}


	//###################### METHOD drawComponent #####################################


	/**
	 * This test represents the Test Case 4.1.1
	 * for drawComponent method
	 * @author Antonio Cesarano
	 */
	public void TC4_1_1(){
		System.out.print("\nRunning test 4.1.1...");

		TreeGraphicNode nodeToDraw = gd.getTree().getChildren().get(0); // <- this represent textView1
		TextView oracolo = new TextView(0x7f080000);
		oracolo.setName(nodeToDraw.getAndroidId());
		TextView result = null;

		try 
		{
			result = (TextView) methodDrawComponent.invoke(gd,nodeToDraw);
			//			System.out.println("\n"+result);
			//			System.out.println(oracolo);
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}

		String oracoloValue = oracolo.toString();
		String resultValue = result.toString();

		assertEquals(oracoloValue, resultValue);

		System.out.println("ok");
	}


	/**
	 * This test represents the Test Case 4.1.2. 
	 * for drawComponent method
	 * @author Antonio Cesarano
	 */
	public void TC4_1_2(){
		System.out.print("\nRunning test 4.1.2...");

		TreeGraphicNode nodeToDraw = null;
		TextView result = null;

		try 
		{
			result = (TextView) methodDrawComponent.invoke(gd, nodeToDraw);
			fail("Should raise a NullPointerException");

		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) {/* this exception catch the NullPointerException */ }

		System.out.println("ok");
	}


	/**
	 * This test represents the Test Case 4.1.3. 
	 * for drawComponent method
	 * @author Antonio Cesarano
	 */
	public void TC4_1_3(){
		System.out.print("\nRunning test 4.1.3...");

		TreeGraphicNode nodeToDraw = new TreeGraphicNode("UnknownWidget", "unknown1");
		TextView result = null;

		try 
		{
			result = (TextView) methodDrawComponent.invoke(gd,nodeToDraw);
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}

		assertEquals(null, result);

		System.out.println("ok");
	}


	//###################### METHOD add #####################################



	/**
	 * This test represents the Test Case 4.2.1. 
	 * for add method
	 * @author Antonio Cesarano
	 */
	public void TC4_2_1(){
		System.out.print("\nRunning test 4.2.1...");

		JPanel parent = null;				//param1
		Button child = new Button(1);		//param2
		TreeGraphicNode nodeToDraw = new TreeGraphicNode("Button", "button1");//param3
		TreeGraphicNode result = null;
		try 
		{
			result = (TreeGraphicNode )methodAdd.invoke(gd, parent,child,nodeToDraw);
//			System.out.println(result);

			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) { /*System.out.println("NullPointerException catched");*/ }
		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 4.2.2. 
	 * for add method
	 * @author Antonio Cesarano
	 */
	public void TC4_2_2(){
		System.out.print("\nRunning test 4.2.2...");

		JPanel parent = new JPanel();	//param1
		Button child = new Button(1);	//param2
		TreeGraphicNode nodeToDraw = new TreeGraphicNode("Button", "button1"); //param3

		assertEquals(0, parent.getComponentCount());

		try 
		{
			methodAdd.invoke(gd, parent,child,nodeToDraw);
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}

		assertEquals(1, parent.getComponentCount());
		assertEquals(child, parent.getComponents()[0]);

		System.out.println("ok");
	}




	/**
	 * This test represents the Test Case 4.2.3. 
	 * for add method
	 * @author Antonio Cesarano
	 */
	public void TC4_2_3(){
		System.out.print("\nRunning test 4.2.3...");

		JPanel parent = new JPanel();				//param1
		Button child =null;			//<- NULL		//param2
		TreeGraphicNode nodeToDraw = new TreeGraphicNode("Button", "button1");//param3

		try 
		{
			methodAdd.invoke(gd, parent,child,nodeToDraw);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) { /*System.out.println("NullPointerException catched");*/ }

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 4.2.4. 
	 * for add method
	 * @author Antonio Cesarano
	 */
	public void TC4_2_4(){
		System.out.print("\nRunning test 4.2.4...");

		JPanel parent = new JPanel();			//param1
		Button child = new Button(1);;			//param2
		TreeGraphicNode nodeToDraw = null;//param3

		try 
		{
			methodAdd.invoke(gd, parent,child,nodeToDraw);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) { /*System.out.println("NullPointerException catched");*/ }

		System.out.println("ok");
	}

	//###################### METHOD draw #####################################


	/**
	 * This test represents the Test Case 4.3.1. 
	 * for draw method
	 * @author Antonio Cesarano
	 */
	public void TC4_3_1(){
		System.out.print("\nRunning test 4.3.1...");

		try 
		{
			methodDraw.invoke(gd,null);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}
		catch (NullPointerException success) { /*System.out.println("NullPointerException catched");*/ } 

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 4.3.2. 
	 * for draw method
	 * @author Antonio Cesarano
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void TC4_3_2() {
		System.out.print("\nRunning test 4.3.2...");

		//environment setup
		Method methodGenerateGUI = null;
		try 
		{
			methodGenerateGUI = GraphicDrawer.class.getDeclaredMethod("generateGUI", null);
			methodGenerateGUI.setAccessible(true);
		} 
		catch (NoSuchMethodException e1) {} 
		catch (SecurityException e1) {}

		try 
		{
			methodGenerateGUI.invoke(gd, null);
			methodDraw.invoke(gd, gd.getTree());

		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}
		
		
		assertEquals(10, gd.getComponentsMap().size());

		System.out.println("ok");
	}


	//###################### METHOD addAll #####################################



	/**
	 * This test represents the Test Case 4.4.1. 
	 * for addAll method
	 * @author Antonio Cesarano
	 */
	public void TC4_4_1(){
		System.out.print("\nRunning test 4.4.1...");

		JPanel parent = null;
		TreeGraphicNode tree = gd.getTree();	//param3

		try 
		{
			methodAddAll.invoke(gd, tree,parent);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) { /*System.out.println("NullPointerException catched");*/ }

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 4.4.2. 
	 * for addAll method
	 * @author Antonio Cesarano
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void TC4_4_2() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		System.out.print("\nRunning test 4.4.2...");

		JFrame f = new JFrame();
		Container parent = f.getContentPane();
		TreeGraphicNode tree = gd.getTree();	//param3

		assertEquals(0, parent.getComponentCount());

		//environment setup
		Method methodGenerateGUI = null;
		try 
		{
			methodGenerateGUI = GraphicDrawer.class.getDeclaredMethod("generateGUI", null);
			methodGenerateGUI.setAccessible(true);
		} 
		catch (NoSuchMethodException e1) {} 
		catch (SecurityException e1) {}

//		try 
//		{
			methodGenerateGUI.invoke(gd, null);
			methodDraw.invoke(gd,tree);
			methodAddAll.invoke(gd, tree, parent);
//		} 
//		catch (IllegalAccessException e) {} 
//		catch (IllegalArgumentException e) {} 
//		catch (InvocationTargetException e) {}


		// I would expect 6 elements because to the the parent container will be 
		// set the layout (not add!!) and because the radioButton will be added to rafioGroup, 
		// not to the container. So, in the container there will be button1, textView1, 
		// radioGroup1, seekBar1, checkbox1 and2.
		assertEquals(6, parent.getComponentCount());
		
		Object radiogroup = parent.getComponent(2);
		//System.out.println(radiogroup);
		
		// Here I would expect 3 elements inside this component, which 
		// are radio0, radio1 and radio2
		assertEquals(3, ((Container)radiogroup).getComponentCount());

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 4.4.2. 
	 * for addAll method
	 * @author Antonio Cesarano
	 */
	public void TC4_4_3(){
		System.out.print("\nRunning test 4.4.3...");

		JPanel parent = new JPanel();
		TreeGraphicNode tree = null;	//param3

		try 
		{
			methodAddAll.invoke(gd, tree, parent);
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException success) { /*System.out.println("NullPointerException catched");*/ }

		System.out.println("ok");
	}




}











