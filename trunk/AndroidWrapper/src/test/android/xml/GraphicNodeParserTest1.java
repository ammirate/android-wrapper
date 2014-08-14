package test.android.xml;

import java.io.FileInputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;


import android.xml.GraphicNodeParser;
import android.xml.TreeGraphicNode;

/**
 * This class is a test case suite for tesing the 
 * getGetGraphicNodeFrom(Element el) method in the GraphicNodeParser class.
 * That is a private method, so it's necessary to use reflaction.
 * @author Antonio
 *
 */
public class GraphicNodeParserTest1 extends TestCase{

	GraphicNodeParser myparser;
	Document document;
	Method methodGetGraphicNode;

	public GraphicNodeParserTest1(String string) {
		super(string);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {

		DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = builderFactory.newDocumentBuilder();
		FileInputStream fis = new FileInputStream("src/test/android/xml/test_DOM.xml");
		assertNotNull(fis);
		document = builder.parse(fis);
		document.getDocumentElement().normalize();

		myparser = new GraphicNodeParser(document);

		methodGetGraphicNode = GraphicNodeParser.class.getDeclaredMethod("getGraphicNodeFrom", Element.class);
		methodGetGraphicNode.setAccessible(true);

	}

	@After
	public void tearDown() throws Exception {
		myparser = null;
	}

	@Test
	public void test() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		suite();
	}


	public static TestSuite suite() {
		TestSuite suite= new TestSuite();

		suite.addTest(new GraphicNodeParserTest1("TC1_1"));
		suite.addTest(new GraphicNodeParserTest1("TC1_2"));
		suite.addTest(new GraphicNodeParserTest1("TC1_3"));	
		suite.addTest(new GraphicNodeParserTest1("TC1_4"));	
		return suite;
	}




	/**
	 * This test represents the Test Case 1.1. 
	 * @author Antonio Cesarano
	 */
	public void TC1_1(){
		System.out.print("\nRunning test 1.1...");

		Element el = null; // <- argument passed is null!

		try {
			methodGetGraphicNode.invoke(myparser, el);
			fail("Should raise a NullPointerException");
		} 
		catch (IllegalAccessException e) {}
		catch (IllegalArgumentException e){}
		catch (InvocationTargetException e)
		{/* exception caused by NullPointerException, result is correct */}	
		
		System.out.println("ok");
	}


	/**
	 * This test represents the Test Case 1.2 
	 * @author Antonio Cesarano	
	 */
	public void TC1_2(){
		System.out.print("\nRunning test 1.2...");

		Element el = document.getDocumentElement();
		el.removeAttribute("android:id");
		el.setAttribute("NoSenseAttribute1", "noSenseValue1");
		el.setAttribute("NoSenseAttribute2", "noSenseValue2");
		assertNotNull(el);
		TreeGraphicNode result = null;
		try
		{
			result = (TreeGraphicNode)methodGetGraphicNode.invoke(myparser, el);
		}
		catch(RuntimeException success){/* exception found, result is correct */}
		catch(IllegalAccessException e){}
		catch(InvocationTargetException e1){}
		
		assertNotNull(result);
		
		System.out.println("ok");
	}


	/**
	 * This test represents the Test Case 1.3 
	 * @author Antonio Cesarano	
	 */
	public void TC1_3(){
		System.out.print("\nRunning test 1.3...");
		
		String el = "Test for invalid type";
		try
		{
			methodGetGraphicNode.invoke(myparser, el);
			fail("Should raise an IllegalArgumentException");
		}
		catch(IllegalArgumentException success){/* exception found, result is correct */ } 
		catch (IllegalAccessException e) {} 
		catch (InvocationTargetException e) {}
		
		System.out.println("ok");
	}
	
	
	
	/**
	 * This test represents the Test Case 1.4
	 * @author Antonio Cesarano	
	 */
	public void TC1_4(){
		System.out.print("\nRunning test 1.4...");

		Element el = document.getDocumentElement();
		TreeGraphicNode tgn = null;
		try
		{
			tgn = (TreeGraphicNode) methodGetGraphicNode.invoke(myparser, el);
			assertNotNull(tgn);
			assertTrue(tgn instanceof TreeGraphicNode);
		}
		catch(IllegalArgumentException success){/* exception found, result is correct */ } 
		catch (IllegalAccessException e) {} 
		catch (InvocationTargetException e) {}
		
		System.out.println("ok");
	}







}
