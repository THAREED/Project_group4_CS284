package Controller;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Course;
import model.CourseList;
import model.DetailException;
import model.Member;

public class DetailController 
{
	private Course course;
	private CourseList courseList;
	private ImageIcon icon1;
	private boolean checkScoreFull = true;
	private boolean check = false;
	private boolean checkScoreAcc = true;

	public DetailController(Course course) throws IOException 
	{	
		  this.course = course;
		  icon1 = new ImageIcon(this.getClass().getResource("/exceptionIcon.png"));
		  courseList = new CourseList();
	}
	
	public void setFullScore(String assfTxt, String midfTxt, String finalfTxt) throws IOException 
	{
		double a = course.getAssFull();
		double m = course.getMidFull();
		double f = course.getFinalFull();

		try 
		{
			a = Double.valueOf(assfTxt);
			m = Double.valueOf(midfTxt);
			f = Double.valueOf(finalfTxt);
			if (a < 0 || m < 0 || f < 0) 
			{
				throw new DetailException("The score must not be negative.");
			}
		}
		catch (NumberFormatException | DetailException ex)
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE, icon1);
			a = course.getAssFull();
			m = course.getMidFull();
			f = course.getFinalFull();
			setCheckScoreFull(false);
		}
		course.setAssFull(a);
		course.setMidFull(m);
		course.setFinalFull(f);
		courseList.updateFile(course);

	}
	public void setAccumulatedScore(String assaTxt, String midaTxt, String finalaTxt) throws IOException 
	{
		double a = course.getAssAcc();
		double m = course.getMidAcc();
		double f = course.getFinalAcc();
		try 
		{
			a = Double.valueOf(assaTxt);
			m = Double.valueOf(midaTxt);
			f = Double.valueOf(finalaTxt);
			if (a < 0 || m < 0 || f < 0) 
			{
				throw new DetailException("The ratio would not be negative");
			}
		} 
		catch (NumberFormatException | DetailException ex) 
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(), "Message", JOptionPane.INFORMATION_MESSAGE, icon1);

			setCheckScoreAcc(false);
		}
		if (a + m + f != 100)
		{
			JOptionPane.showMessageDialog(null, "Accumulated score exceeds 100 points.", "Message",JOptionPane.INFORMATION_MESSAGE, icon1);
			setCheckScoreAcc(false);
		} 
		else 
		{
			course.setAssAcc(a);
			course.setMidAcc(m);
			course.setFinalAcc(f);
			courseList.updateFile(course);
			updatePieChart(true);
		}
	}	
	public void updatePieChart(boolean check) 
	{
		this.check = check;
	}
	public boolean isUpdatePieChart() 
	{
		return check;
	}	
	public boolean isCheckScoreFull() 
	{
		return checkScoreFull;
	}
	public void setCheckScoreFull(boolean checkScoreFull) 
	{
		this.checkScoreFull = checkScoreFull;
	}
	public boolean isCheckScoreAcc() 
	{
		return checkScoreAcc;
	}
	public void setCheckScoreAcc(boolean checkScoreAcc) 
	{
		this.checkScoreAcc = checkScoreAcc;
	}	
	
	
	
	
	
	
	
}