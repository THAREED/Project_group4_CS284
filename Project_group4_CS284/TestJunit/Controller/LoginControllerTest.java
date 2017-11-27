package Controller;

import static org.junit.Assert.*;

import java.io.IOException;
import javax.swing.JOptionPane;

import org.junit.Before;
import org.junit.Test;

import Controller.LoginController;
import model.MemberList;

public class LoginControllerTest {

	LoginController login;
	MemberList list;
	

	@Before
	public void setUp() throws Exception {
		login = null;
		list = new MemberList();
		JOptionPane.showMessageDialog(null, "Strat test ligin.!");
	}

	@Test
	public void testCheckUsernamePassword() throws IOException {

		String user = list.getMemberList(3).getUsername();
		char[] pass = list.getMemberList(3).getPassword().toCharArray();

		login = new LoginController(user, pass);

		user = "b.helloboo";
		int inputUser = login.checkUsername(user);
		login.checkPassword(3, pass);

		assertEquals("Username and Password do not match",3,inputUser);
		assertNotNull(login);

	}

	

}
