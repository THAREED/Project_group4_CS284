package model;
import java.util.ArrayList;

public class Member 
{
	private String firstname , lastName;
	private String email;
	private String username;
	private String password;
	private boolean importClasslist;
	private ArrayList<String> subjectList;
	
	public Member(String firstName , String lastName, String email, String username, String password, boolean importClassList ,ArrayList<String> subject) 
	{
		this.firstname = firstName;
		this.lastName = lastName;
		this.email = email;
		this.username = username;
		this.password = password;
		this.subjectList = new ArrayList<String>();
		for (int i = 0; i < subject.size(); i++) 
		{
			this.subjectList.add(subject.get(i));
		}
		this.importClasslist = importClassList;
	}
	public String getEmail() 
	{
		return email;
	}
	public void setEmail(String email) 
	{
		this.email = email;
	}
	public String getUsername() 
	{
		return username;
	}
	public void setUsername(String username) 
	{
		this.username = username;
	}
	public String getPassword() 
	{
		return password;
	}
	public void setPassword(String password) 
	{
		this.password = password;
	}
	public String getName() 
	{
		return firstname+"   "+lastName;
	}	
	public String getLastname() 
	{
		return lastName;
	}
	public String getFirstname() 
	{
		return firstname;
	}
	public String getSubjectInIndex(int index) {
		return subjectList.get(index);
	}
	public void addSubject(String subject)
	{
		this.subjectList.add(subject);
	}
	public String allSubject() 
	{
		String sub = "";
		for (int i = 0; i < subjectList.size(); i++) 
		{
			if(i == subjectList.size()-1)
			{
				sub = sub + subjectList.get(i);
			}
			else
			{
				sub = sub + subjectList.get(i) + ",";
			}
		}
		return sub;		
	}
	public int getSizeSubject()
	{
		return subjectList.size();
	}
	public String toString() 
	{
		return firstname+" "+lastName+","+email+","+username+","+password+","+isImportClassList()+","+allSubject()+"\n";		
	}
	public boolean isImportClassList() 
	{
		return 	this.importClasslist;
	}
	public void setImportClassList(boolean importClassList) 
	{
		this.importClasslist = importClassList;
	}
	
}
