package chat;

import java.net.ServerSocket;
import java.net.Socket;

public class ListenServer extends Thread
{
	int portNumber = 7890;
	ServerSocket sSock = null;
	try
	{
		sSock = new ServerSocket(portNumber);
	}
	catch(Exception e)
	{
		
	}
	while(true)
	{
		Socket cSock = sSock.accept();
		
	}
}
