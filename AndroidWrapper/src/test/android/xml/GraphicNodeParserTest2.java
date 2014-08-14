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
import org.w3c.dom.Node;

import android.xml.GraphicNodeParser;
import android.xml.TreeGraphicNode;

/**
 * This class is a test case suite for tesing the 
 * getGetGraphicNodeFrom(Element el) method in the GraphicNodeParser class.
 * That is a private method, so it's necessary to use reflaction.
 * @author Antonio
 *
 */
public class GraphicNodeParserTest2 extends TestCase{

	GraphicNodeParser myparser;
	Document document;
	DocumentBuilder builder;
	Method methodCreateTree;

	public GraphicNodeParserTest2(String string) {
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
		builder = builderFactory.newDocumentBuilder();
		FileInputStream fis = new FileInputStream("src/test/android/xml/test_DOM.xml");
		assertNotNull(fis);
		document = builder.parse(fis);
		document.getDocumentElement().normalize();

		myparser = new GraphicNodeParser(document);

		methodCreateTree = GraphicNodeParser.class.getDeclaredMethod("createTree", Node.class);
		methodCreateTree.setAccessible(true);

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

		suite.addTest(new GraphicNodeParserTest2("TC2_1"));
		suite.addTest(new GraphicNodeParserTest2("TC2_2"));
		suite.addTest(new GraphicNodeParserTest2("TC2_3"));	
		suite.addTest(new GraphicNodeParserTest2("TC2_4"));	

		return suite;
	}




	/**
	 * This test represents the Test Case 1.1. 
	 * @author Antonio Cesarano
	 */
	public void TC2_1(){
		System.out.print("\n\nRunning test 2.1... ");

		Element el = null; // <- argument passed is null!
		Node root = (Node) el;
		try {
			methodCreateTree.invoke(myparser, root);
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
	public void TC2_2(){
		System.out.print("\n\nRunning test 2.2... ");

		Element el = document.getDocumentElement();
		Node root = (Node)el;
		TreeGraphicNode tgn = null;
		try
		{
			tgn = (TreeGraphicNode) methodCreateTree.invoke(myparser, root);
			assertNotNull(tgn);
			assertTrue(tgn instanceof TreeGraphicNode);

//			myparser.printGraphicNode(tgn,0);
		}
		catch(IllegalArgumentException success){/* exception found, result is correct */ } 
		catch (IllegalAccessException e) {} 
		catch (InvocationTargetException e) {}

		System.out.println("ok");
	}




	/**
	 * This test represents the Test Case 1.3 
	 * @author Antonio Cesarano	
	 */
	public void TC2_3(){
		System.out.print("\n\nRunning test 2.3... ");

		TreeGraphicNode tgn = null;

		Element root = document.getDocumentElement();
		Node button = root.getElementsByTagName("Button").item(0);
		assertNotNull(button);
		Node textView =  root.getElementsByTagName("TextView").item(0);
		assertNotNull(textView);
		textView.appendChild(button);

		try
		{
			tgn = (TreeGraphicNode) methodCreateTree.invoke(myparser, (Node)root);
//			System.out.println();
//			myparser.printGraphicNode(tgn, 0);

			fail("Should raise an exception");

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
	public void TC2_4(){
		System.out.print("\n\nRunning test 2.4... ");

		TreeGraphicNode tgn = null;
		Element root = document.getDocumentElement();
		/* create a new node unknown to MAEESTRO and add it to the XML */
		Element stranger = document.createElement("StrangerWidget");
		stranger.setTextContent("content");
		stranger.setAttribute("android:id", "stranger1");
		root.appendChild((Node) stranger);
		
		try
		{
			tgn = (TreeGraphicNode) methodCreateTree.invoke(myparser, (Node)root);
//			System.out.println();
//			myparser.printGraphicNode(tgn, 0);

		}
		catch(IllegalArgumentException success){/* exception found, result is correct */ } 
		catch (IllegalAccessException e) {} 
		catch (InvocationTargetException e) {}

		System.out.println("ok");
	}







}
