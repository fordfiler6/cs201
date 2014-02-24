package Calculator;

import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;
import java.util.StringTokenizer;

import javax.swing.JLabel;

public class FunctionKeyActionListener implements ActionListener
{
	CalculatorButton button;
	JLabel equationLabel;
	JLabel inputLabel;
	public static String simpleEquation;
	public static boolean error = false;
	public static CalculatorButton lastPressed = null;
	private static GUI gui;
	
	FunctionKeyActionListener()
	{
		button = null;
		equationLabel = null;
		simpleEquation = "";
	
	}
	
	FunctionKeyActionListener(CalculatorButton button, JLabel input, JLabel equation, GUI gui)
	{
		this.button = button;
		equationLabel = equation;
		inputLabel = input;
		this.gui = gui;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) 
	{
		if(button == GUI.C)
		{
			inputLabel.setText("0");
			equationLabel.setText("");
			error = false;
			simpleEquation = "";
		}
		else if(error)
		{
			
		}
		else if(button == GUI.CE)
		{
			inputLabel.setText("0");
		}
		else if(button == GUI.BKSP)
		{
			String curInput = inputLabel.getText();

			if(curInput.length() > 1)
			{
				inputLabel.setText(curInput.substring(0, curInput.length()-1));
			}
			if(curInput.length() == 1)
			{
				inputLabel.setText("0");
			}
		}
		else if(button == GUI.DEC)
		{
			String curInput = inputLabel.getText();
			if(!curInput.contains("."))
			{
				inputLabel.setText(curInput+".");
			}
			
		}
		else if (button == GUI.SIGN)
		{
			String curInput = inputLabel.getText();
			if(!curInput.contains("-"))
			{
				inputLabel.setText("-"+curInput);
			}
			else
			{
				inputLabel.setText(curInput.substring(1));
			}
		}
		else if(button == GUI.PI)
		{
			inputLabel.setText(Math.PI+"");
			NumberKeyActionListener.clear = true;
		}
		
		else if (button == GUI.EQ)
		{
			if(!NumberKeyActionListener.clear)
			{
				equationLabel.setText(equationLabel.getText()+inputLabel.getText());
				simpleEquation = simpleEquation + inputLabel.getText();
				System.out.println("simple = "+simpleEquation);
			}
			double ans = evaluateEquation(simpleEquation);
			if(!error)
				inputLabel.setText(ans+"");
			NumberKeyActionListener.clear = true;
		}
		else if(button == GUI.MOD)
		{

			equationLabel.setText(equationLabel.getText()+inputLabel.getText()+" Mod ");
			simpleEquation = simpleEquation + inputLabel.getText() +" % ";
			
			NumberKeyActionListener.clear = true;
		}
		else if(button == GUI.EXP)
		{

			equationLabel.setText(equationLabel.getText()+inputLabel.getText()+" ^ ");
			simpleEquation = simpleEquation + inputLabel.getText() +" ^ ";
			
			NumberKeyActionListener.clear = true;
		}
		else if(button == GUI.NROOT)
		{

			equationLabel.setText(equationLabel.getText()+inputLabel.getText()+" yroot ");
			simpleEquation = simpleEquation + inputLabel.getText() +" ^ ";
			
			NumberKeyActionListener.clear = true;
		}
		else if(button == GUI.INV)
		{
			gui.toInverse();
		}
		else if(button == GUI.RP)
		{
			equationLabel.setText(equationLabel.getText()+inputLabel.getText()+")");
			simpleEquation = simpleEquation + inputLabel.getText()+")";
			NumberKeyActionListener.clear = true;
			
		}
		else if(button == GUI.LP)
		{
			equationLabel.setText(equationLabel.getText()+"(");
			simpleEquation = simpleEquation + "(";
		}
		else if (button.is4Function() || button == GUI.MOD)
		{
			if(!NumberKeyActionListener.clear)
			{
				equationLabel.setText(equationLabel.getText()+inputLabel.getText());
				simpleEquation = simpleEquation + inputLabel.getText();
				System.out.println("simple = "+simpleEquation);
			}
			double ans = evaluateEquation(simpleEquation);
			if(!error)
				inputLabel.setText(ans+"");
			equationLabel.setText(equationLabel.getText()+" "+button.display+" ");
			simpleEquation = simpleEquation +" "+button.display+" ";
			System.out.println("simple = "+simpleEquation);
			NumberKeyActionListener.clear = true;
		}
		
		else
		{
		
			if(button == GUI.SIN)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"sinr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.sin(Double.parseDouble(inputLabel.getText()));
	
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"sind("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.sin(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.COS)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"cosr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.cos(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"cosd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.cos(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.TAN)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"tanr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.tan(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"tand("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.tan(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.SINH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"sinhr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.sinh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"sinhd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.sinh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.COSH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"coshr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.cosh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"coshd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.cosh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.TANH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"tanhr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.tanh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"tanhd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.tanh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVS)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"asinr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.asin(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"asind("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.asin(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVSH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"asinhr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + asinh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"asinhd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + asinh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVC)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"acosr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.acos(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"acosd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.acos(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVCH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"acoshr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + acosh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"acoshd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + acosh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVT)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"atanr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.atan(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"atand("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + Math.atan(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.INVTH)
			{
				if(RadioButtonListener.radians)
				{
					equationLabel.setText(equationLabel.getText()+"atanhr("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + atanh(Double.parseDouble(inputLabel.getText()));
				}
				else
				{
					equationLabel.setText(equationLabel.getText()+"atanhd("+inputLabel.getText()+")");
					simpleEquation = simpleEquation + atanh(Math.toRadians(Double.parseDouble(inputLabel.getText())));
				}
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			
			else if(button == GUI.EX)
			{
				equationLabel.setText(equationLabel.getText()+"powe("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.pow(Math.E,Double.parseDouble(inputLabel.getText()));
				gui.toInverse();
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.XSQ)
			{
	
				equationLabel.setText(equationLabel.getText()+"sqr("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.pow(Double.parseDouble(inputLabel.getText()),2);
				
				NumberKeyActionListener.clear = true;
			}
			
			else if(button == GUI.FACT)
			{
	
				equationLabel.setText(equationLabel.getText()+"fact("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + factorial(Double.parseDouble(inputLabel.getText()));
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.SQRT)
			{
	
				equationLabel.setText(equationLabel.getText()+"sqrt("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.sqrt(Double.parseDouble(inputLabel.getText()));
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.CUBE)
			{
	
				equationLabel.setText(equationLabel.getText()+"cube("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.pow(Double.parseDouble(inputLabel.getText()),3);
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.ROOT3)
			{
	
				equationLabel.setText(equationLabel.getText()+"cuberoot("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.pow(Double.parseDouble(inputLabel.getText()),(1.0/3.0));
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.TENX)
			{
	
				equationLabel.setText(equationLabel.getText()+"powten("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.pow(10,Double.parseDouble(inputLabel.getText()));
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.LOG)
			{
	
				equationLabel.setText(equationLabel.getText()+"log("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.log10(Double.parseDouble(inputLabel.getText()));
				
				NumberKeyActionListener.clear = true;
			}
			else if(button == GUI.LN)
			{
	
				equationLabel.setText(equationLabel.getText()+"ln("+inputLabel.getText()+")");
				simpleEquation = simpleEquation + Math.log(Double.parseDouble(inputLabel.getText()));
				
				NumberKeyActionListener.clear = true;
			}
			int lastSpace = simpleEquation.lastIndexOf(" ");
			if(lastSpace !=-1)
			{
				inputLabel.setText(simpleEquation.substring(lastSpace));
			}
			else
			{
				inputLabel.setText(simpleEquation);
			}
		}
		
		lastPressed = button;
	}

	
	private int factorial(double n) 
	{
		int intN = (int)n;
		if(n != intN)
		{
			inputLabel.setText("Error: n! of n not type int");
			error = true;
		}
		else if(n < 0)
		{
			inputLabel.setText("Error: n less than zero");
			error = true;
		}
		else
		{
	        int sol = 1; 
	        for (int i = 1; i <= n; i++) 
	        {
	            sol *= i;
	        }
	        return sol;
		}
		return -1;
    }
	
	//inverse hyperbolics from http://answers.yahoo.com/question/index?qid=20110613181448AATATVS
	
	static double asinh(double x) 
	{ 
	return Math.log(x + Math.sqrt(x*x + 1.0)); 
	} 

	static double acosh(double x) 
	{ 
	return Math.log(x + Math.sqrt(x*x - 1.0)); 
	} 

	static double atanh(double x) 
	{ 
	return 0.5*Math.log( (x + 1.0) / (x - 1.0) ); 
	} 
	
	// algorithm from http://stackoverflow.com/questions/1946896/conversion-from-infix-to-prefix
	private String convertToPrefix(String infix)
	{
		Stack<String> operators = new Stack<String>();
		Stack<String> operands = new Stack<String>();
		System.out.println("infix:" + infix);
		infix = infix.replaceAll("\\(","\\( ");
		infix = infix.replaceAll("\\)"," \\)");
		System.out.println("infix:" + infix);
		StringTokenizer tok = new StringTokenizer(infix," ");

		while(tok.hasMoreTokens())
		{
			String token = tok.nextToken();
			if(isNumeric(token))
			{
				operands.push(token+" ");
			}
			else if(token.equals("(") || operators.isEmpty() || precedence(operators.peek(),token))
			{
				operators.push(token);
			}
			else if(token.equals(")"))
			{
				while(!operators.peek().equals("("))
				{
					String operator = safePop(operators);
					String rightOperand = safePop(operands);
					String leftOperand = safePop(operands);
					operands.push(operator+" "+leftOperand+rightOperand);
				}
				operators.pop();
			}
			else if(!precedence(operators.peek(),token) )
			{
				while(!operators.empty() && !precedence(operators.peek(),token))
				{
					String operator = safePop(operators);
					String rightOperand = safePop(operands);
					String leftOperand = safePop(operands);
					
					
					operands.push(operator+" "+leftOperand+rightOperand);
				}
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
		if(potLower.equals("(") || potLower.equals(")"))
		{
			return true;
		}
		if(potHigher.equals("^"))
		{
			return true;
		}
		return false;
	}
	
	private double evaluateEquation(String equation)
	{
		Stack<String> progress = new Stack<String>();
		String infix = convertToPrefix(equation);
		System.out.println(infix);
		StringTokenizer tok = new StringTokenizer(infix," ");

		
		while(tok.hasMoreElements())
		{	
			String token = tok.nextToken();
			if(isNumeric(token))
			{
				progress.push(token);
			}
			else
			{
				progress.push(token);
			}
			while(progress.size()>=3 && readyToOperate(progress.subList(progress.size()-3, progress.size())))
			{
				String arg2 = progress.pop();
				String arg1 = progress.pop();
				String operator = progress.pop();
				String operationResult = doOperation(operator, arg1, arg2)+"";
				progress.push(operationResult);
			}
		}
		double result = 0;
		try
		{
			result = Double.parseDouble(progress.pop());
		}
		catch (NumberFormatException e)
		{
			error = true;
			inputLabel.setText("Error: expression syntax");
			return 0;
		}
		if(Double.isInfinite(result) || Double.isNaN(result))
		{
			error = true;
			inputLabel.setText("Error: infinite or divide by 0");
			return 0;
		}
		return round(result,15);
	}
	// from http://stackoverflow.com/questions/2808535/round-a-double-to-2-decimal-places
	private static double round(double value, int places) {
	    if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	private boolean readyToOperate(List<String> subject)
	{
		if(!isNumeric(subject.get(0)) && isNumeric(subject.get(1)) && isNumeric(subject.get(2)))
		{
			return true;
		}
		return false;
	}
	
	private double doOperation(String operator, String arg1, String arg2)
	{
		char op = operator.charAt(0);
		switch(op)
		{
			case '+': return Double.parseDouble(arg1) + Double.parseDouble(arg2);
			case '-': return Double.parseDouble(arg1) - Double.parseDouble(arg2);
			case '/': return Double.parseDouble(arg1) / Double.parseDouble(arg2);
			case '*': return Double.parseDouble(arg1) * Double.parseDouble(arg2);
			case '%': return Double.parseDouble(arg1) % Double.parseDouble(arg2);
			case '^': return Math.pow(Double.parseDouble(arg1), Double.parseDouble(arg2));
		}
		return 0;
	}
	
	

}
