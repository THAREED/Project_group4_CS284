package model;

import java.io.IOException;

public class LoginException extends IOException
{
	public LoginException() 
	{
		super("Please, Enter your username and password.");
	}
	public LoginException(String str)
	{
		super(str);
	}
}
