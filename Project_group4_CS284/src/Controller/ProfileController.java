package Controller;

import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import model.Member;
import model.MemberList;
import model.ProfileException;

public class ProfileController 
{
	private MemberList list;
	private boolean update = true;
	public static String exceptionStr = "";
	public ProfileController() throws IOException
	{
		list = new MemberList();
	}
	public void updateProfile(Member member, String email, char pass[]) throws IOException
	{
		try
		{
			String emailForm[] = email.split("@");
			String emailForm2 = email.substring(email.length()-4);
			String emailForm3 = email.substring(email.length()-6);
			for(int i=0 ; i<email.length() ; i++)
			{
				String ch = email.charAt(i)+"";
				if(ch.equals(","))
				{
					throw new ProfileException("Email must not character ' , '");
				}
			}
			for(int i=0 ; i<pass.length ; i++)
			{
				String ch = pass[i]+"";
				if(ch.equals(","))
				{
					throw new ProfileException("Password must not character ' , '");
				}
			}
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
			else if(emailForm3.equals(".co.th")|| emailForm3.equals(".ac.th"))
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
	

}
