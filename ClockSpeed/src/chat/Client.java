package chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.Queue;
import java.util.StringTokenizer;
import java.util.Vector;
import java.util.concurrent.Semaphore;

public class Client extends Thread
{
	Socket cSock = null;
	int clientId;
	Vector<String> messages;
	ChatGUI gui;
	public Semaphore messageLock = new Semaphore(1);
	public Client()
	{
		messages = new Vector<String>();
		clientId = -1;
		try
		{
			cSock = new Socket("localhost",7890);
		}
		catch(Exception e)
		{
			System.out.println("Failed to create Client sockett");
		}
		gui = new ChatGUI(this);
	}
	public void sendMessage(String msg)
	{
		messageLock.acquireUninterruptibly();
		messages.add("msg:"+"all"+":"+msg);
		messageLock.release();
	}
	public void sendMessageTo(String msg, int to)
	{
		messageLock.acquireUninterruptibly();
		messages.add("msg:"+to+":"+msg);
		messageLock.release();
	}
	public void run()
	{
		BufferedReader in = null;
		PrintWriter out = null;
		try
		{
			while(!cSock.isConnected())
			{
				System.out.println("I'm not connected");
			}
			out = new PrintWriter(cSock.getOutputStream());
			in = new BufferedReader(new InputStreamReader(cSock.getInputStream()));
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		while(true)
		{
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
			try 
			{
				if(in.ready())
				{
					String line = in.readLine();
					StringTokenizer tok = new StringTokenizer(line, ":");
					System.out.println(clientId+": "+line);
					String instruction = tok.nextToken();
					if(instruction.equalsIgnoreCase("id"))
					{
						clientId = Integer.parseInt(tok.nextToken());
					}
					else if(instruction.equalsIgnoreCase("msgp"))
					{
						String from = tok.nextToken();
						String to = tok.nextToken();
						String msg = tok.nextToken();
						gui.addMsgPrivate(msg, from, to);
					}
					else if(instruction.equalsIgnoreCase("msga"))
					{
						String from = tok.nextToken();
						String msg = tok.nextToken();
						gui.addMsg(msg, from);
					}
					
				}
			} 
			catch (IOException e) 
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
		
}
