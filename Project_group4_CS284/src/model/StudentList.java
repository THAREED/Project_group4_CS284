package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class StudentList 
{
	private ArrayList<Student> studentList;
	private ArrayList<String> studentStr;
	private Member member;
	private Course course;

	public StudentList(Member member, Course course) 
	{
		studentStr = new ArrayList<String>();
		studentList = new ArrayList<Student>();	
		this.member = member;
		this.course = course;
	}
	public boolean loadList()
	{
		JFileChooser c = new JFileChooser(".");
		int option = c.showOpenDialog(null);
		if(option == JFileChooser.APPROVE_OPTION)
		{
			try
			{
				File file = c.getSelectedFile();
				if(file.getName().substring(file.getName().length()-4).equals(".csv"))
				{	
					FileReader fileReader = new FileReader(file);
					BufferedReader reader = new BufferedReader(fileReader);
					String str = reader.readLine();
					while(str != null)
					{
						studentStr.add(str);
						str = reader.readLine();
					}			
					reader.close();
					fileReader.close();
					return true;
				}
				else
				{
					JOptionPane.showMessageDialog(null, "Please select only file.csv \n(e.g. studentList.csv)","Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("exceptionIcon.png"));
					loadList();
				}
			}
			catch (FileNotFoundException e) 
			{
				System.out.println(e.getMessage());
				return false;
			}
			catch (IOException e) 
			{
				System.out.println(e.getMessage());
				return false;
			}
		}
		return false;
	}
	public void setStudentList() 
	{
		for (int i = 7; i < studentStr.size(); i++) 
		{						
			String info[] = studentStr.get(i).toString().split(",");
			if(info.length == 5)
			{
				int number = Integer.valueOf(info[0]);
				String id = info[1];
				String name = info[2];
				String category = info[3];
				String status = info[4];
				studentList.add(new Student(number, id, name, category, status));
			}		
			if(info.length == 11)
			{
				int number = Integer.valueOf(info[0]);
				String id = info[1];
				String name = info[2];
				String category = info[3];
				String status = info[4];
				double assFull = Double.valueOf(info[5]);
				double midFull = Double.valueOf(info[6]);
				double finalFull = Double.valueOf(info[7]);
				double assAcc = Double.valueOf(info[8]);
				double midAcc = Double.valueOf(info[9]);
				double finalAcc = Double.valueOf(info[10]);
				studentList.add(new Student(number, id, name, category, status, assFull, midFull, finalFull, assAcc, midAcc, finalAcc));
			}	
		}		
		saveList();
	}
	public void setStudentShowInFill(File file) 
	{
		try
		{
			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			String str = reader.readLine();
			while(str != null)
			{
				String info[] = str.toString().split(",");
				if(info.length == 11)
				{
					int number = Integer.valueOf(info[0]);
					String id = info[1];
					String name = info[2];
					String category = info[3];
					String status = info[4];
					double assFull = Double.valueOf(info[5]);
					double midFull = Double.valueOf(info[6]);
					double finalFull = Double.valueOf(info[7]);
					double assAcc = Double.valueOf(info[8]);
					double midAcc = Double.valueOf(info[9]);
					double finalAcc = Double.valueOf(info[10]);
					studentList.add(new Student(number, id, name, category, status, assFull, midFull, finalFull, assAcc, midAcc, finalAcc));
				}	
				str = reader.readLine();
			}
			reader.close();
			fileReader.close();	
		}
		catch (FileNotFoundException e) 
		{
			JOptionPane.showMessageDialog(null, "Please import student file again.","Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("exceptionIcon.png"));
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("exceptionIcon.png"));
		}
	}
	public boolean saveList() 
	{
		try
		{
			FileWriter fileWriter = new FileWriter(new File(member.getUsername()+"_"+course.getCourseName()+"List.txt"),false);
			PrintWriter writer = new PrintWriter(fileWriter);
			for (int i = 0; i < studentList.size(); i++) 
			{
				writer.println(studentList.get(i).toString());	
			}		
			writer.close();
			fileWriter.close();
			member.setImportClassList(true);
			MemberList mList = new MemberList();
			mList.updateFile(member);
			return true;
		}
		catch (IOException e) 
		{
			JOptionPane.showMessageDialog(null, e.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon("exceptionIcon.png"));
			return false;
		}
	}	
	public Student getIndex(int index) 
	{
		return studentList.get(index);		
	}
	public int getSize()
	{
		return studentList.size();
	}
}
