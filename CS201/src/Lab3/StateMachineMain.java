package Lab3;

import java.util.Scanner;

public class StateMachineMain 
{
	public static void main(String[] args)
	{
		State currentState = State.S;
		Scanner scan = new Scanner(System.in);
		String action;
		while(true)
		{
			System.out.println("You are in state "+ currentState);
			action = scan.nextLine();
			switch(currentState)
			{
				case S:
					if(action.equals("aaa"))
						currentState = State.A;
					if(action.equals("abf"))
						currentState = State.F;
					if(action.equals("bbb"))
						currentState=State.B;
					break;
				case A:
					if(action.equals("abba"))
						currentState = State.B;
					break;
				case B:
					if(action.equals("bff"))
						currentState = State.F;
					break;
				case F:
					System.out.println("You cannot leave");
					break;
					
			}
		}
	}
	
}
