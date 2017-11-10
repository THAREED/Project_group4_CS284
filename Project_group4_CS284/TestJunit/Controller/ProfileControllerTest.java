package Controller;

import static org.junit.Assert.*;

import java.io.IOException;

import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import model.CourseList;
import model.Member;
import model.MemberList;

public class ProfileControllerTest {
	ProfileController pro;
	MemberList list;
	Member member;
	
	@Before
	public void setUp() throws Exception {
		list = new MemberList();
		JOptionPane.showMessageDialog(null, "Strat test Profile!");
	}

	@Test
	public void test() throws IOException {
		
		pro = new ProfileController();
		member = list.getMemberList(0);
		
		
		String str = list.getMemberList(0).getEmail();
		char pass[] = list.getMemberList(0).getPassword().toCharArray();
		String test = ","+ list.getMemberList(0).getPassword();
		
		pro.updateProfile(member, str, pass);
		System.out.println(pass+ "\n "+ str);
		
		assertEquals(pro.isUpdate(), true);
	}

}
