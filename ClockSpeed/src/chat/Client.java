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

import javax.swing.JOptionPane;

import core.GameBoard;

public class Client extends Thread
{
	Socket cSock = null;
	int clientId;
	Vector<String> messages;
	ChatGUI gui;
	public Semaphore messageLock = new Semaphore(1);
	boolean startGame;
	int numPlayers;
	GameBoard board;
	public Client()
	{
		messages = new Vector<String>();
		clientId = -1;
		try
		{
			String input = JOptionPane.showInputDialog("Enter IP Address:");
			cSock = new Socket(input,7890);
		}
		catch(Exception e)
		{
			System.out.println("Failed to create Client sockett");
		}
		gui = new ChatGUI(this);
	}
	public void startGame(int players)
	{
		numPlayers = players;
		startGame = true;
		
	}
	public void setGameBoard(GameBoard in)
	{
		board = in;
	}
	public int getClientId()
	{
		return clientId;
	}
	public int getNumPlayers()
	{
		return numPlayers;
	}
	public boolean getStartGame()
	{
		return startGame;
	}
	public void sendRoll(int spaces)
	{
		messageLock.acquireUninterruptibly();
		messages.add("roll:"+spaces);
		messageLock.release();
	}
	public void sendStartMessage()
	{
		messageLock.acquireUninterruptibly();
		messages.add("start");
		messageLock.release();
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
	public void sendPieceSelection(int id)
	{
		messageLock.acquireUninterruptibly();
		messages.add("icon:"+id);
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
						boolean startGame = false;
						if(clientId != 1 && clientId <4)
						{
							int input = JOptionPane.showConfirmDialog (null, "There are "+clientId+" players. Start game?","Start Game",JOptionPane.YES_NO_OPTION);
							if(input == JOptionPane.YES_OPTION)
							{
								startGame(clientId);
								sendStartMessage();
							}
						}
						if(clientId >=4)
						{
							startGame(clientId);
							sendStartMessage();
						}
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
					else if(instruction.equalsIgnoreCase("start"))
					{
						String numPlayers = tok.nextToken();
						startGame(Integer.parseInt(numPlayers));
					}
					else if(instruction.equalsIgnoreCase("icon"))
					{
						int from = Integer.parseInt(tok.nextToken());
						int id = Integer.parseInt(tok.nextToken());
						while(board== null)
						{
							try
							{
								Thread.sleep(100);
							}
							catch (Exception e)
							{
								
							}
						}
						board.initPlayer(from, id);
					}
					else if(instruction.equalsIgnoreCase("move"))
					{
						int spaces = Integer.parseInt(tok.nextToken());
						board.makeMove(spaces);
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
