package Controller;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import Controller.CourseController;
import model.Course;
import model.CourseList;

public class CourseControllerTest {
	
	CourseController course;
	CourseList list;
	Course c;

	@Before
	public void setUp() throws Exception {
		list = new  CourseList();
		JOptionPane.showMessageDialog(null, "Strat test CourseSelect.!");
	}

	@Test
	public void testGetCourseSelect() throws IOException {
		
		course = new CourseController(list.getCourse(0).getCourseID().toUpperCase()+" "+list.getCourse(0).getCourseName());
		assertNotNull(course.getCourseSelect());
		
	}


}

