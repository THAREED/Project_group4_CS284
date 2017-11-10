package model;

public class ProfileException extends Exception
{
	public ProfileException() 
	{
		super("Please, Update your profile again.");
	}
	public ProfileException(String str)
	{
		super(str);
	}

}
