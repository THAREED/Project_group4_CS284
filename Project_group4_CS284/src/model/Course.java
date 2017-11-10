package model;
import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTable;
import javax.swing.ListModel;
import javax.swing.border.TitledBorder;

public class Course
{
	private String courseID;
	private String courseName;
	private double assFull , assAcc;
	private double midFull , midAcc;
	private double finalFull , finalAcc;

	public Course(String courseID , String courseName, double assFull , double assAcc, double midFull, double midAcc, double finalFull, double finalAcc) 
	{
		this.courseID = courseID;	
		this.courseName = courseName;
		this.assFull = assFull;
		this.assAcc = assAcc;
		this.midFull = midFull;
		this.midAcc = midAcc;
		this.finalFull = finalFull;
		this.finalAcc = finalAcc;
	}
	
	
	public String getCourseID() {
		return courseID;
	}
	public void setCourseID(String courseID) 
	{
		this.courseID = courseID;
	}
	public String getCourseName() 
	{
		return courseName;
	}
	public void setCourseName(String courseName) 
	{
		this.courseName = courseName;
	}
	public String toString() 
	{
		return getCourseID().toUpperCase()+","+getCourseName()+","+getAssFull()+","+getAssAcc()+","+getMidFull()+","+getMidAcc()+","+getFinalFull()+","+getFinalAcc()+"\n";		
	}	
	public double getAssFull() 
	{
		return assFull;
	}
	public void setAssFull(double assFull) 
	{
		this.assFull = assFull;
	}
	public double getAssAcc() 
	{
		return assAcc;
	}
	public void setAssAcc(double assAcc) 
	{
		this.assAcc = assAcc;
	}
	public double getMidFull() {
		return midFull;
	}
	public void setMidFull(double midFull) 
	{
		this.midFull = midFull;
	}
	public double getMidAcc() 
	{
		return midAcc;
	}
	public void setMidAcc(double midAcc) 
	{
		this.midAcc = midAcc;
	}
	public double getFinalFull() 
	{
		return finalFull;
	}
	public void setFinalFull(double finalFull) 
	{
		this.finalFull = finalFull;
	}
	public double getFinalAcc() 
	{
		return finalAcc;
	}
	public void setFinalAcc(double finalAcc) 
	{
		this.finalAcc = finalAcc;
	}
	
	
	
	

}
