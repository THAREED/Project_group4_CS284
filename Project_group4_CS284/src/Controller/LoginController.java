package Controller;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import View.CourseFrame;
import model.MemberList;

public class LoginController 
{
	private String user;
	private MemberList list;
	private boolean check;
	public LoginController(String username, char pass[]) throws IOException 
	{
		list = new MemberList();
		this.user = username;
		this.check = false;
		try
		{	
			int index = checkUsername(user);
			if(index == -1)
			{
				throw new LoginException("Username or password is incorrect, please fill in again.");
			}
			checkPassword(index, pass);
		}
		catch (LoginException ex) 
		{
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
		}
	}
	
	public int checkUsername(String user) 
	{
		int index = -1;
		for (int i = 0; i < list.getSize(); i++) 
		{
			if(user.equalsIgnoreCase(list.getMemberList(i).getUsername()))
			{
				index = i;
			}
		}
		return index;
	}
	
	public void checkPassword(int index, char pass[]) throws IOException
	{
		String passStr = "";
		for (int i = 0; i < pass.length; i++) 
		{
			passStr = passStr + pass[i];			
		}
		if(list.getMemberList(index).getPassword().equals(passStr))
		{			
			new CourseFrame(list.getMemberList(index));
			check = true;
		}
		else
		{
			JOptionPane.showMessageDialog(null, new LoginException("Username or password is incorrect, please fill in again.").getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
			check = false;
		}		
	}
	public boolean disposeLoginForm() 
	{
		if(check)
		{
			return true;
		}
		return false;
	}
}
