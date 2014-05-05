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
		        if(line.equals("start"))
		        {
		        	for(ServerClientThread recip : connectedClients)
		    		{
		    				recip.sendRaw("start:"+connectedClients.size());
		    		}
		        }
		        StringTokenizer tok = new StringTokenizer(line,":");
		        String instruction = tok.nextToken();
		        if(instruction.equalsIgnoreCase("msg"))
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
		        else if(instruction.equalsIgnoreCase("icon"))
		        {
		        	String iconId = tok.nextToken();
		        	for(ServerClientThread recip : connectedClients)
		    		{
		    			if(recip != this)
		    				recip.sendIcon(Integer.parseInt(iconId), clientId);
		    		}
		        }
		        else if(instruction.equalsIgnoreCase("roll"))
		        {
		        	int spaces = Integer.parseInt(tok.nextToken());
		        	for(ServerClientThread recip : connectedClients)
		    		{
		        		
		    			if(recip != this)
		    				recip.sendMove(spaces);
		    		}
		        }
		        else if(instruction.equalsIgnoreCase("upgrade"))
		        {
		        	int id = Integer.parseInt(tok.nextToken());
		        	for(ServerClientThread recip : connectedClients)
		    		{
		        		
		    			if(recip != this)
		    				recip.sendUpgrade(id);
		    		}
		        }
		        else if(instruction.equalsIgnoreCase("resign"))
		        {
		        	for(ServerClientThread recip : connectedClients)
		    		{
		        		
		    			if(recip != this)
		    				recip.sendResign();
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
	private void sendResign() 
	{
		messageLock.acquireUninterruptibly();
		messages.add("resign:");
		messageLock.release();
		
	}
	public void sendUpgrade(int id) 
	{
		messageLock.acquireUninterruptibly();
		messages.add("upgrade:"+id);
		messageLock.release();
		
	}
	public void sendRaw(String msg)
	{
		messageLock.acquireUninterruptibly();
		messages.add(msg);
		messageLock.release();
	}
	public void sendMove(int spaces)
	{
		messageLock.acquireUninterruptibly();
		messages.add("move:"+spaces);
		messageLock.release();
	}
	public void sendMessage(String msg)
	{
		messageLock.acquireUninterruptibly();
		messages.add("msgp:"+clientId+":"+msg);
		messageLock.release();
	}
	public void sendIcon(int iconId, int from)
	{
		messageLock.acquireUninterruptibly();
		messages.add("icon:"+from+":"+iconId);
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
