package program;


import chat.ListenServer;

public class StartServer 
{
	public static void main(String[] args)
	{
		ListenServer listener = new ListenServer();
		listener.start();
	}
}
