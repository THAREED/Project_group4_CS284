package model;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CourseList
{
	private ArrayList<Course> courseList;
	private ArrayList<String> courseStr;
	private File file = new File("courseList.txt");

	public CourseList() throws IOException 
	{
		courseList = new ArrayList<Course>();
		courseStr = new ArrayList<String>();
		openFile();
		setCourseList();
	}
	public void openFile() throws IOException
	{		
		FileReader fileReader = new FileReader(file);
		BufferedReader reader = new BufferedReader(fileReader);
		String str = reader.readLine();
		while(str != null)
		{
			courseStr.add(str);			
			str = reader.readLine();
		}
		reader.close();
		fileReader.close();
	}
	public void setCourseList() 
	{
		for (int i = 0; i < courseStr.size(); i++) 
		{			
			String info[] = courseStr.get(i).split(",");
			double assFull = Double.valueOf(info[2]);
			double assAcc = Double.valueOf(info[3]);
			double midFull = Double.valueOf(info[4]);
			double midAcc = Double.valueOf(info[5]);
			double finalFull = Double.valueOf(info[6]);
			double finalAcc = Double.valueOf(info[7]);
			courseList.add(new Course(info[0], info[1], assFull, assAcc, midFull, midAcc, finalFull, finalAcc));			
		}
	}
	public void updateFile(Course course) throws IOException 
	{
		FileWriter fileWriter = new FileWriter(file,false);
		PrintWriter writer = new PrintWriter(fileWriter);
		for (int i = 0; i < courseList.size(); i++) 
		{
			if(courseList.get(i).getCourseID().equalsIgnoreCase(course.getCourseID()))
			{
				courseList.get(i).setAssFull(course.getAssFull());
				courseList.get(i).setAssAcc(course.getAssAcc());
				courseList.get(i).setMidFull(course.getMidFull());
				courseList.get(i).setMidAcc(course.getMidAcc());
				courseList.get(i).setFinalFull(course.getFinalFull());
				courseList.get(i).setFinalAcc(course.getFinalAcc());				
			}
			writer.print(courseList.get(i).toString());	
		}		
		writer.close();
		fileWriter.close();
	}	
	
	public Course getCourse(int index) 
	{
		return courseList.get(index);
	}
	public int getSize()
	{
		return courseList.size();
	}
	
	
}
