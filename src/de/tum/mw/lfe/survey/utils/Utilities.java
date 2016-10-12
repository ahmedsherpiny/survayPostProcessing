package de.tum.mw.lfe.survey.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Utilities {
	static private LanguageIndicies langIndicis = new LanguageIndicies(); 
	public static int StringMismatchCounter (String ip1,String ip2)
	{
		int count = 0; // difference in string
	    String ipx2 = ip2;
	    String ipx1 = ip1;
	    
	    if(ipx1.equals(ipx2))
	    	count=0;
	    else
	    {
	    	for(int i=0;i<ip2.length();i++)
	    	{
	    		if(ipx2.length()<=0){
	    		//	count+=ipx1.length();
	    			break;
	    		}
	    		if(ipx1.length()<=0)
	    		{
	    			//count+=ipx2.length();
	    			break;
	    		}
	    		int value = ipx1.indexOf(ipx2); //check first (occurrence of the first) character of second string
	    		if(value >=0) //matched a non starting char of first string
	    		{
	    			count += value;
	    			ipx1 = ipx1.substring(value+1);
	    			ipx2 = ip2.substring(i+1);
	    		}
	    		else
	    		{
	    			if(ipx1.indexOf(ipx2.substring(0,1))>=0)//check only first character
	    			{
	    				ipx1 = ipx1.substring(1);
	    				ipx2 = ipx2.substring(1);
	    			}
	    			else
	    			{
		    			ipx2 = ipx2.substring(1);
		    			count +=1;
	    			}
	    		}
	    	}
	    }
	    
	    if (ipx2.length()<=0)
	    {
	    	count += ipx1.length();
	    }
	    if (ipx1.length()<=0)
	    {
	    	count += ipx2.length();
	    }
	    
//	    for (int j = 0; j < ip2.length(); j++) {
//	        int value = ipx1.indexOf(ipx2);
//	        if (value != -1) {
//	          if (("").equals(ipx2)) { // if the second string is blank after continous reducing
//	                count += ipx1.length();
//	                break;
//	            }
//	            if (("").equals(ipx1)) { // if the first string is blank after continous reducing
//	                count += ipx2.length();
//	                break;
//	            }else {
//	                //count = ip1.length()  - ipx2.length();
//	                count += value;
//	                ipx1=ipx1.substring(value+1);
//	            }
//	         //   break;
//	        } 
//	        else {
//	            count +=1; // if there is no match at all
//	        }
//	        ipx2 = ip2.substring(j);
//	    }
	    return count;
	}
	public static boolean readXML(String xmlFileName,ArrayList<SurveyTask> tasks) {
		   try {
			   
				File fXmlFile = new File(xmlFileName);
				DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
				DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(fXmlFile);
			 
				//optional, but recommended
				//read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
				doc.getDocumentElement().normalize();
			 
				System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
			 
				//Read labguage Indecies
				Node langList = doc.getElementsByTagName("languages").item(0);
				 
				System.out.println("----------------------------");
				System.out.println("\nCurrent Element :" + langList.getNodeName());
			 
				if (langList.getNodeType() == Node.ELEMENT_NODE) {
		 
					Element eElement = (Element) langList;
					langIndicis.firstLanguage = new Integer(eElement.getElementsByTagName("first").item(0).getTextContent()).intValue();
					langIndicis.SecondLanguage = new Integer(eElement.getElementsByTagName("second").item(0).getTextContent()).intValue();					
				}
				

				
				
				
				NodeList nList = doc.getElementsByTagName("task");
			 
				System.out.println("----------------------------");
			 
				SurveyTask task;
				SubTask subTask;
				for (int temp = 0; temp < nList.getLength(); temp++) {
					task = new SurveyTask();
					Node nNode = nList.item(temp);
			 
					System.out.println("\nCurrent Element :" + nNode.getNodeName());
			 
					if (nNode.getNodeType() == Node.ELEMENT_NODE) {
			 
						Element eElement = (Element) nNode;
						task.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
					//	System.out.println("Staff id : " + eElement.getAttribute("id"));
						NodeList nSubList = eElement.getElementsByTagName("subtask");
						ArrayList<SubTask> subTasks = new ArrayList<SubTask>();
						for (int subTaskIndex = 0; subTaskIndex < nSubList.getLength(); subTaskIndex++) {
							subTask = new SubTask();
							NodeList nIndexList = ((Element)nSubList.item(subTaskIndex)).getElementsByTagName("index");
							String[] indices = new String[nIndexList.getLength()];
							for (int i = 0; i < nIndexList.getLength(); i++) {
								indices[i] = ((Element)nIndexList.item(i)).getTextContent();
							}
							subTask.setTextIndices(indices);

							subTask.setTimeIndex(((Element)nSubList.item(subTaskIndex)).getElementsByTagName("time").item(0).getTextContent());
							
							subTasks.add(subTask);
						}
						task.setSubTasks(subTasks);
						tasks.add(task);
					}
				}
	           return true;

        } catch (ParserConfigurationException pce) {
            System.out.println(pce.getMessage());
        } catch (SAXException se) {
            System.out.println(se.getMessage());
        } catch (IOException ioe) {
            System.err.println(ioe.getMessage());
        }

        return false;
    }	
	
	static public LanguageIndicies getLanguageIndices()
	{
		return langIndicis;
	}
}
