package TestJUnit;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import View.GradingGroupFrame;
import model.Course;
import model.CourseList;
import model.Member;
import model.MemberList;
import model.StudentList;

public class GradingGroupTest {
	GradingGroupFrame grouup;
	Member member;
	MemberList mList;
	Course course;
	CourseList courseList;
	StudentList studentList;
	File file;

	@Before
	public void setUp() throws Exception {
		mList = new MemberList();
		courseList = new CourseList();
		JOptionPane.showMessageDialog(null, "Strat test GradingGroup.!");
	}

	@Test
	public void testGroupGrading() throws IOException {
		
		member = mList.getMemberList(1);
		course = new Course(courseList.getCourse(0).getCourseID(), courseList.getCourse(0).getCourseName(),
				courseList.getCourse(0).getAssFull(), courseList.getCourse(0).getAssAcc(),
				courseList.getCourse(0).getMidFull(), courseList.getCourse(0).getMidAcc(),
				courseList.getCourse(0).getFinalFull(), courseList.getCourse(0).getFinalAcc());
		
		file = new File("b.helloboo_Computer Programming Fund.List.txt");
		grouup = new GradingGroupFrame(member, course, file);
		studentList = new StudentList(member, course);
		studentList.setStudentList();
		studentList.setGradeStudent();
		studentList.getGradeStudentFromFile();
	
		grouup.sumGrading();
		grouup.GroupGrading();

		assertEquals(grouup.saveGrade(), true);
	}


}
