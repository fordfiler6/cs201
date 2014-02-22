package Calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.EmptyStackException;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JLabel;

public class FunctionKeyActionListener implements ActionListener
{
	CalculatorButton button;
	JLabel equationLabel;
	JLabel inputLabel;
	
	FunctionKeyActionListener()
	{
		button = null;
		equationLabel = null;
	
	}
	
	FunctionKeyActionListener(CalculatorButton button, JLabel input, JLabel equation)
	{
		this.button = button;
		equationLabel = equation;
		inputLabel = input;
		
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(button == GUI.C)
		{
			inputLabel.setText("0");
			equationLabel.setText("");
		}
		else if(button == GUI.CE)
		{
			inputLabel.setText("0");
		}
		else if (button.is4Function())
		{
			equationLabel.setText(equationLabel.getText()+inputLabel.getText());
			inputLabel.setText(evaluateEquation(equationLabel)+"");
			equationLabel.setText(equationLabel.getText()+" "+button.display+" ");
			NumberKeyActionListener.clear = true;
		}
	}
	// algorithm from http://stackoverflow.com/questions/1946896/conversion-from-infix-to-prefix
	private String convertToPrefix(String infix)
	{
		Stack<String> operators = new Stack<String>();
		Stack<String> operands = new Stack<String>();
		infix = infix.replaceAll("\\(","\\( ");
		infix = infix.replaceAll("\\)"," \\)");
		StringTokenizer tok = new StringTokenizer(infix," ");

		while(tok.hasMoreTokens())
		{
			String token = tok.nextToken();
			if(isNumeric(token))
			{
				System.out.println("pushing operand "+token);
				operands.push(token+" ");
			}
			else if(operators.isEmpty() || precedence(operators.peek(),token))
			{
				System.out.println("pushing operator "+token);
				operators.push(token);
			}
			else if(!precedence(operators.peek(),token))
			{
				while(!operators.empty() && !precedence(operators.peek(),token))
				{
					String operator = safePop(operators);
					String rightOperand = safePop(operands);
					String leftOperand = safePop(operands);
					
					
					System.out.println("pushing operand "+operator+leftOperand+rightOperand);
					operands.push(operator+" "+leftOperand+rightOperand);
				}
				System.out.println("pushing operator "+token);
				operators.push(token);
			}
		}
		while(!operators.empty())
		{
			String operator = safePop(operators);
			String rightOperand = safePop(operands);
			String leftOperand = safePop(operands);
			
			operands.push(operator+" "+leftOperand+rightOperand);
		}
		return operands.pop();
	}
	// function from http://stackoverflow.com/questions/1102891/how-to-check-if-a-string-is-a-numeric-type-in-java
	private boolean isNumeric(String subject) 
	{
	   return subject.matches("-?\\d+(\\.\\d+)?");
	}
	
	private String safePop(Stack<String> stack)
	{
		try
		{
			return stack.pop();
		}
		catch(EmptyStackException e)
		{
			return "";
		}
	}
	
	private boolean precedence(String potLower, String potHigher)
	{
		if(potLower.equals("+") || potLower.equals("-"))
		{
			if(potHigher.equals("*") || potHigher.equals("/"))
			{
				return true;
			}
		}
		return false;
	}
	
	private double evaluateEquation(JLabel equationLabel)
	{
		System.out.println(convertToPrefix(equationLabel.getText()));

		return 0;
	}
	
	

}
