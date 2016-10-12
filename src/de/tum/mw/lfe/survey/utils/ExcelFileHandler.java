package de.tum.mw.lfe.survey.utils;

import java.io.File; 
import java.io.IOException;
import java.lang.Character.Subset;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date; 
import jxl.*; 
import jxl.read.biff.BiffException;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.Number;
import jxl.write.WriteException;


//http://jexcelapi.sourceforge.net/ is the source of the used lib

public class ExcelFileHandler {
	
	private Workbook workbook;
	private String[] DataNeeded = {"Time","# charachters", "# of erros"};
	
	public boolean openFile(File file) throws BiffException, IOException
	{
		if (file!=null){
			workbook = Workbook.getWorkbook(file);
	
			/** test to ensure that file has been correctly read - only for debugging**/
/*			Sheet sheet = workbook.getSheet(0);
	
			Cell a1 = sheet.getCell(0,0); 
			Cell b2 = sheet.getCell(1,1); 
			Cell c2 = sheet.getCell(2,1); 
	
			String stringa1 = a1.getContents(); 
			String stringb2 = b2.getContents(); 
			String stringc2 = c2.getContents(); 
			
			System.out.println("sheet.getColumns(): "+sheet.getColumns()+" sheet.getRows() "+sheet.getRows());
			System.out.print(stringa1 +", " + stringb2 +", "+ stringc2);*/
			return true;
		}
		return false;
	}

	public String readCell(int i, int j)
	{
		if(workbook!=null)
		{
			Sheet sheet = workbook.getSheet(0);
			return sheet.getCell(i,j).getContents();
		}
		else return null;

	}

	public int getNumberOfUsers(File file) {
		// TODO Auto-generated method stub
		if(workbook!=null)
		{
			Sheet sheet = workbook.getSheet(0);
			return sheet.getRows()-1;
		}
		else return -1;
	}

	public void FillSubTasksData(File file, ArrayList<SubTask> subTasksList, int currentUser) {
		// TODO Auto-generated method stub
		if(workbook!=null)
		{
			for(int i=0;i<subTasksList.size();i++)
			{
				SubTask currentSubTask = subTasksList.get(i);
				try {
					FillSubTaskData(currentSubTask, currentUser);
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}		
	}

	public void saveNewFile(File newFile,ArrayList<SurveyTask> tasksList) throws Exception {
		// TODO Auto-generated method stub
		
		WritableWorkbook writableWorkbook = Workbook.createWorkbook(newFile);
		WritableSheet sheet = writableWorkbook.createSheet("First Sheet", 0);
		
		
		//write header
		int counter =0;
		Label label = new Label(counter, 0, "id");
		counter++;
		sheet.addCell(label);
		
		Label firstLanguage_label = new Label(counter, 0, "first language");
		counter++;
		sheet.addCell(firstLanguage_label); 
		
		Label secondLanguage_label = new Label(counter, 0, "second language");
		counter++;
		sheet.addCell(secondLanguage_label);
		
		//write user data
		int numOfRows = workbook.getSheet(0).getRows();		
		for(int userIndex = 1;userIndex<numOfRows;userIndex++)
		{
			//
			Label userId = new Label(0,userIndex,workbook.getSheet(0).getCell(0,userIndex).getContents());
			sheet.addCell(userId);
		
			Label firstLanguage = new Label(1,userIndex,workbook.getSheet(0).getCell(Utilities.getLanguageIndices().firstLanguage,userIndex).getContents());
			sheet.addCell(firstLanguage);
			Label secondLanguage = new Label(2,userIndex,workbook.getSheet(0).getCell(Utilities.getLanguageIndices().SecondLanguage,userIndex).getContents());
			sheet.addCell(secondLanguage);
		
		}
	
		
		for(int i =0;i<tasksList.size();i++)
		{
			
			SurveyTask task = tasksList.get(i);
			for (int j=0;j<task.getSubTasks().size();j++)
			{
				SubTask subtask = task.getSubTasks().get(j);
				for(int k =0;k<DataNeeded.length;k++)
				{	
					Label label2 = new Label( counter, 0 , subtask.getName()+ " "+DataNeeded [k]);
					sheet.addCell(label2);
					
					
					//Write user data
					//Write user data
					for(int userIndex = 1;userIndex<numOfRows;userIndex++)
					{
						//fill the data for current user
						FillSubTaskData(subtask, userIndex);
						//
						Label userId = new Label(0,userIndex,workbook.getSheet(0).getCell(0,userIndex).getContents());
						sheet.addCell(userId);
						Label userData;
						if(k==0)
							userData = new Label( counter, userIndex , new Double(subtask.getTime()).toString());
						else if (k==1)
							userData = new Label(counter,userIndex,new Integer(subtask.getSubTaskAnswer().length()).toString());
						else
							userData = new Label(counter,userIndex,new Integer(Utilities.StringMismatchCounter(subtask.getOriginalSubTaskText(), subtask.getSubTaskAnswer())).toString());
						sheet.addCell(userData);
					}
					counter++;
				}
			}
		}
		
			
		writableWorkbook.write(); 
		writableWorkbook.close();
		
	}

	private void FillSubTaskData(SubTask currentSubTask,
			int currentUser) throws ParseException {
		// TODO Auto-generated method stub
		Sheet sheet = workbook.getSheet(0);
		String timeIndex = currentSubTask.getTimeIndex();
		String subTaskName ="";
		if(timeIndex!=null&&!timeIndex.equals(""))
		{
			subTaskName = sheet.getCell(new Integer(timeIndex).intValue(),0).getContents();
			subTaskName = subTaskName.substring(subTaskName.indexOf(':')+2);
		}
		currentSubTask.setName(subTaskName);
		//System.out.println("Task Name: " + subTaskName);
		
		//retrieve all different languages (Indices) and check which one is not null
		String TypedText = "";
		int indexOfTypedText = -1;
		for (int j =0;j<currentSubTask.getTextIndices().length;j++)
		{	
			String textIndex = currentSubTask.getTextIndices()[j];
			if (textIndex!=null&&!textIndex.equals(""))
				TypedText = sheet.getCell(new Integer(textIndex).intValue(),currentUser).getContents();
			if (TypedText!=null&&!TypedText.equalsIgnoreCase(""))
			{
				indexOfTypedText = j;
			}
		//	System.out.println("TypedText: "+ TypedText);
		//	System.out.println("indexOfTypedText: "+ indexOfTypedText);
		}
		
		String OriginalText = "";
		
	//	int NumberOfCharachters = OriginalText.length();
		if(indexOfTypedText>=0)
		{
			OriginalText = sheet.getCell(new Integer( currentSubTask.getTextIndices()[indexOfTypedText]).intValue(),0).getContents();
			TypedText = sheet.getCell(new Integer(currentSubTask.getTextIndices()[indexOfTypedText]).intValue(),currentUser).getContents();
		}
		currentSubTask.setOriginalSubTaskText(OriginalText);
		if(TypedText!= null)
			currentSubTask.setSubTaskAnswer(TypedText);
		String time = null;
		if(timeIndex!=null&&!timeIndex.equals(""))
		{
			time = sheet.getCell(new Integer(currentSubTask.getTimeIndex()).intValue(),currentUser).getContents();
		}
		if (time==null||time.equalsIgnoreCase(""))
			time = "-1";
		NumberFormat nf = NumberFormat.getInstance();
		currentSubTask.setTime(nf.parse(time).doubleValue());
		
//		System.out.println("TypedText: "+ TypedText);
//		System.out.println("Original Text:" + OriginalText);
//		System.out.println("Time: " + (sheet.getCell(new Integer(currentSubTask.getTimeIndex()).intValue(),currentUser).getContents()));
//

		
	}
	
	
	
	
	/*public static void Op(String[] args) throws Exception {
		
		
	
		Workbook workbook = Workbook.getWorkbook(new File("C:/Users/ahmed.elsherbiny/Downloads/results-survey745583.xls"));
	    
		Sheet sheet = workbook.getSheet(0);

		Cell a1 = sheet.getCell(0,0); 
		Cell b2 = sheet.getCell(1,1); 
		Cell c2 = sheet.getCell(2,1); 

		String stringa1 = a1.getContents(); 
		String stringb2 = b2.getContents(); 
		String stringc2 = c2.getContents(); 
		
	System.out.print(stringa1 +", " + stringb2 +", "+ stringc2);
	
	}*/
	
}
