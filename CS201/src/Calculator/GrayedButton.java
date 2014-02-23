package Calculator;

import javax.swing.JButton;

public class GrayedButton extends JButton
{
	GrayedButton()
	{
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(true);
	}
}
