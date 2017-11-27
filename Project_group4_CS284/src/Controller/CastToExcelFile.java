package Controller;

import java.io.File;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import model.Course;
import model.Member;
import model.StudentList;

public class CastToExcelFile 
{
	private Member member;
	private Course course;
	private StudentList studentList;
	private String fileName;
	
	public CastToExcelFile(Member member, Course course, String filename) 
	{
		this.member = member;
		this.course = course;
		this.fileName = filename;
		studentList = new StudentList(member, course);
		File file = new File(member.getUsername() + "_" + course.getCourseName() + "List.txt");
		studentList.setStudentShowInFill(file);
		studentList.getGradeStudentFromFile();
		castToExcel();
	}
	public void castToExcel() 
	{
		try
		{
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName+".xls"));
			WritableSheet ws1 = workbook.createSheet(course.getCourseID(), 0);
			for (int i = 0; i < studentList.getSize(); i++) 
			{
				ws1.addCell(new Label(0, i, studentList.getIndex(i).getId()));
				ws1.addCell(new Label(1, i, studentList.getIndex(i).getGrad()));
			}
			workbook.write();
			workbook.close();
			JOptionPane.showMessageDialog(null, "Create a file .xls successfully","Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/finishIcon.png")));
		}
		catch (Exception ex) 
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
		}
		
	}

}
