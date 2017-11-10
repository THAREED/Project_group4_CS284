package Controller;

import java.io.IOException;

import model.Course;
import model.CourseList;

public class CourseController 
{
	private CourseList courseList;
	private String subject;
	public CourseController(String subject) throws IOException 
	{
		courseList = new CourseList();
		this.subject = subject;
	}
	public Course getCourseSelect()
	{
		Course course = null;
		for (int i = 0; i < courseList.getSize(); i++) 
		{
			if(subject.equalsIgnoreCase(courseList.getCourse(i).getCourseID()+" "+courseList.getCourse(i).getCourseName()))	
			{
				course = courseList.getCourse(i);
			}
		}	
		return course;		
	}

}
