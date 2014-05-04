package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class ServerClientThread extends Thread
{
	Socket sock;
	int clientId;
	ArrayList<ServerClientThread> connectedClients;
	Vector<String> messages;
	public Semaphore messageLock = new Semaphore(1);
	public ServerClientThread(Socket _sock,ArrayList<ServerClientThread> clientList)
	{
		this.clientId = clientList.size()+1;
		connectedClients = clientList;
		sock = _sock;
		messages = new Vector<String>();
	}
	public void run()
	{
		BufferedReader in = null;
		PrintWriter out = null;
		  try
		  {
		   in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		   out = new PrintWriter(sock.getOutputStream(), true);
		   sendRaw("id:"+clientId);
		  } 
		  catch (IOException e) 
		  {
		    System.out.println("Read failed");
		    System.exit(-1);
		  }
		

	    while(sock.isConnected())
	    {
	      try
	      { 
	    	 if(in.ready())
	        {
		        String line = in.readLine();
		        StringTokenizer tok = new StringTokenizer(line,":");
		        if(tok.nextToken().equalsIgnoreCase("msg"))
		        {
		        	String to = tok.nextToken();
		        	String msg = tok.nextToken();
		        	if(to.equalsIgnoreCase("all"))
		        	{
			    		for(ServerClientThread recip : connectedClients)
			    		{
			    			if(recip != this)
			    				recip.sendMessageFrom(msg, clientId, true);
			    		}
		        	}
		        	else
		        	{
		        		for(ServerClientThread recip : connectedClients)
			    		{
		        			if(recip.clientId == Integer.parseInt(to))
		        			{
		        				recip.sendMessageFrom(msg, clientId, false);
		        			}
			    		
			    		}
		        	}
		        }
	        }
	        if(messageLock.tryAcquire())
			{
				if(messages.size() > 0)
				{
					out.println(messages.get(0));
					messages.remove(0);
					out.flush();
				}
				messageLock.release();
			}
	      } 
	      catch (IOException e) 
	      {
	    	e.printStackTrace();
	        System.out.println("Read failed");
	        System.exit(-1);
	      }
	    }
	}
	public void sendRaw(String msg)
	{
		messageLock.acquireUninterruptibly();
		messages.add(msg);
		messageLock.release();
	}
	public void sendMessage(String msg)
	{
		messageLock.acquireUninterruptibly();
		messages.add("msgp:"+clientId+":"+msg);
		messageLock.release();
	}
	public void sendMessageFrom(String msg, int from, boolean all)
	{
		messageLock.acquireUninterruptibly();
		if(!all)
			messages.add("msgp:"+from+":"+clientId+":"+msg);
		else
			messages.add("msga:"+from+":"+msg);
		messageLock.release();
	}
}
