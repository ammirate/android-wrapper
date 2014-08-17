package test.android.app;


import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import android.app.GraphicDrawer;
import android.os.Bundle;

public class GraphicDrawerTest3 extends TestCase{

	private GraphicDrawer gd;
	private Bundle b;
	private Class R_class;
	private Constructor<GraphicDrawer> constructor;
	private Method method1;
	private Method method2;
	private Class R_badClass;


	public GraphicDrawerTest3(String s){
		super(s);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		constructor = GraphicDrawer.class.getConstructor(Bundle.class);
		constructor.setAccessible(true);

		method1 = GraphicDrawer.class.getDeclaredMethod("fillR_HashMap", null);
		method1.setAccessible(true);
		method2 = GraphicDrawer.class.getDeclaredMethod("getHashKey", String.class);
		method2.setAccessible(true);

		this.R_class = R.class;
		this.R_badClass = R_bad.class;
		
		assertNotNull(R_class);
		assertNotNull(R_badClass);
		
		b = new Bundle("/test/android/app/test_DOM.xml", R_class);
		gd = new GraphicDrawer(b);
		assertNotNull(gd);
	}

	@After
	public void tearDown() throws Exception {
		gd = null;
	}

	@Test
	public void test() {
		suite();
	}


	public static TestSuite suite() {
		TestSuite suite= new TestSuite();

		suite.addTest(new GraphicDrawerTest3("TC3_1"));
		suite.addTest(new GraphicDrawerTest3("TC3_2"));
		suite.addTest(new GraphicDrawerTest3("TC3_3"));
		suite.addTest(new GraphicDrawerTest3("TC3_4"));
		suite.addTest(new GraphicDrawerTest3("TC3_5"));
		suite.addTest(new GraphicDrawerTest3("TC3_6"));
		
		return suite;
	}



	/**
	 * This test represents the Test Case 3.1. 
	 * @author Antonio Cesarano
	 */
	public void TC3_1(){
		System.out.print("\nRunning test 3.1...");

		try
		{
			b.setR(null);
			gd = new GraphicDrawer(b);
			method1.invoke(gd, null);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {} 
		catch (NullPointerException success) {}

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 3.2. 
	 * @author Antonio Cesarano
	 */
	public void TC3_2(){
		System.out.print("\nRunning test 3.2...");

		try
		{
			method1.invoke(gd, null);
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}

		HashMap<Integer, String> oracolo = new HashMap<Integer, String>();
		oracolo.put(0x7f080001,"button1" );
		oracolo.put(0x7f080007, "checkBox1" );
		oracolo.put(0x7f080008, "checkBox2" );
		oracolo.put(0x7f080003, "radio0"  );
		oracolo.put(0x7f080004, "radio1" );
		oracolo.put(0x7f080005, "radio2" );
		oracolo.put(0x7f080002, "radioGroup1" );
		oracolo.put(0x7f080006, "seekBar1");
		oracolo.put(0x7f080000, "textView1" );
		oracolo.put(0x7f030000, "activity_main" );

		assertEquals(oracolo, gd.getRHashMap());

		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 3.3 
	 * @author Antonio Cesarano
	 */
	public void TC3_3(){
		System.out.print("\nRunning test 3.3...");
		
		b.setR(R_badClass);
		gd = new GraphicDrawer(b);

		try
		{
			method1.invoke(gd, null);
			fail("Should raise an exception");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException  success) {}
		catch (InvocationTargetException e) {}
		System.out.println("ok");
	}



	/**
	 * This test represents the Test Case 3.4 
	 * @author Antonio Cesarano
	 */
	public void TC3_4(){
		System.out.print("\nRunning test 3.4...");
		
		String param = "button1";
		try
		{
			Integer result = (Integer) method2.invoke(gd, param);
			assertEquals((Integer)0x7f080001, result);   //<--correct
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}
		
		System.out.println("ok");
	}
	
	
	
	
	/**
	 * This test represents the Test Case 3.4 
	 * @author Antonio Cesarano
	 */
	public void TC3_5(){
		System.out.print("\nRunning test 3.5...");
		
		String param = "StringNotValid";
		Integer oracolo = -1;
		try
		{
			Integer result = (Integer) method2.invoke(gd, param);
			assertEquals(oracolo, result);   //<--correct
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}
		
		System.out.println("ok");
	}
	
	
	
	
	/**
	 * This test represents the Test Case 3.4 
	 * @author Antonio Cesarano
	 */
	public void TC3_6(){
		System.out.print("\nRunning test 3.6...");
		
		
		ArrayList<String> param =  new ArrayList<String>();
		//oracolo = Exception
		try
		{
			Integer result = (Integer) method2.invoke(gd, param);
			System.out.println(result);
			fail("Should raise an Exception");
		} 
		catch (IllegalAccessException e) {} 
		catch (IllegalArgumentException e) {} 
		catch (InvocationTargetException e) {}
		
		System.out.println("ok");
	}
	



}



