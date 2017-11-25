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
	Member member;
	Course course;
	StudentList studentList;
	public CastToExcelFile(Member member, Course course) 
	{
		this.member = member;
		this.course = course;
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
			String fileName = course.getCourseID().toUpperCase() + "_" + course.getCourseName() + "_Grade_By_"+member.getFirstname()+"_"+member.getLastname()+".xls";
			WritableWorkbook workbook = Workbook.createWorkbook(new File(fileName));
			
			WritableSheet ws1 = workbook.createSheet("mySheet1", 0);
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
