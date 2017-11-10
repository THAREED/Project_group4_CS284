package Controller;

import java.io.IOException;

import javax.security.auth.login.LoginException;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import View.CourseFrame;
import model.Member;
import model.MemberList;
import model.ProfileException;

public class LoginController 
{
	private String user;
	private MemberList list;
	private boolean check;
//	private boolean update = true;
//	public static String exceptionStr = "";
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
/*	public void updateProfile(Member member, String email, char pass[]) throws IOException
	{
		try
		{
			String emailForm[] = email.split("@");
			String emailForm2 = email.substring(email.length()-4);
			String emailForm3 = email.substring(email.length()-6);
			int cnt = 0;
			if(pass.length < 8)
			{
				throw new ProfileException("Passwords must be at least 8 characters.");
			}
			else if(emailForm.length != 2)
			{
				throw new ProfileException("Email address is invalid.");
			}
			else if(emailForm2.equals(".com"))
			{
				cnt++;
			}
			else if(emailForm3.equals(".co.th"))
			{
				cnt++;
			}
			else if(cnt == 0)
			{
				throw new ProfileException("Email address is invalid.");
			}
			member.setEmail(email);
			String passStr = "";
			for(int i=0 ; i<pass.length ; i++)
			{
				passStr = passStr + pass[i];				
			}
			member.setPassword(passStr);
			list.updateFile(member);
		}
		catch (ProfileException ex) 
		{
			setUpdate(false);
			JOptionPane.showMessageDialog(null, ex.getMessage(),"Message",JOptionPane.INFORMATION_MESSAGE,new ImageIcon(this.getClass().getResource("/exceptionIcon.png")));
			exceptionStr = ex.getMessage();
		}				
	}	
	public boolean isUpdate() 
	{
		return update;
	}
	public void setUpdate(boolean update) 
	{
		this.update = update;
	}
	*/
	
	
	
	
	
	

}
