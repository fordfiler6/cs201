package chat;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ListenServer extends Thread
{
	int portNumber = 7890;
	ServerSocket sSock = null;
	ArrayList<ServerClientThread> connectedClients = new ArrayList<>();
	public ListenServer()
	{
		try
		{
			sSock = new ServerSocket(portNumber);
		}
		catch(Exception e)
		{
			
		}
	}
	public void run()
	{
		System.out.println("Listener Started");
		while(true)
		{
			try
			{
				Socket cSock = sSock.accept();
				connectedClients.add(new ServerClientThread(cSock, connectedClients));
				connectedClients.get(connectedClients.size()-1).start();	
			}
			catch(Exception e)
			{
			
			}
		}
	}
}
