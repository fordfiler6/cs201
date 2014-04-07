package core;

import gui.GameDisplay;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class GameBoard extends JPanel
{
	public static final int NUM_SPACES = 10 * 4; // MUST BE DIVISIBLE BY 4 or
													// board will be wierd
	public static final int SPACE_WIDTH = GameDisplay.WIDTH
			/ ((NUM_SPACES / 4) + 1);
	public static final int SPACE_HEIGHT = GameDisplay.HEIGHT
			/ ((NUM_SPACES / 4) + 1);
	public static final int PADDING = 5;
	public static final int LINE_HEIGHT = 14;
	public static final int X_OFFSET = 10;
	public static final int Y_OFFSET = 10;
	private ArrayList<Space> boardSpaces;

	boolean sim = false;

	private Player[] players;

	private int curPlayer;

	private int numPlayers;

	public GameBoard(String boardDescFile, int in_numPlayers) {
		numPlayers = in_numPlayers;
		players = new Player[numPlayers];
		readFile(boardDescFile);
		initPlayers();
		repaint();

	}

	public void play() {
		while (!isGameover()) {
			if (players[curPlayer].stillPlaying()) {
				takeTurn();
			} else {
				if (curPlayer == players.length - 1) {
					curPlayer = 0;
				} else {
					curPlayer++;
				}
			}
			validate();
			repaint();
		}
		for (int i = 0; i < players.length; i++) {
			if (players[i].stillPlaying()) {
				JOptionPane.showMessageDialog(null, "Player " + (i + 1) + " wins");
				break;
			}
		}
	}

	private void takeTurn() {
		String input = null;
		if (sim)
			input = "Roll";
		while (input == null) {
			String[] choices = { "Roll", "Buy Upgrades", "Resign" };
			input = (String) JOptionPane.showInputDialog(null,
					"What would you like to do?", "Player " + (curPlayer + 1),
					JOptionPane.QUESTION_MESSAGE, null, // Use
														// default
														// icon
					choices, // Array of choices
					choices[0]); // Initial choice
		}
		if (input == "Roll") {
			int move = rollDice();
			makeMove(move);
			players[curPlayer].getCurrentLocation().landOnSpace(
					players[curPlayer]);
			if (curPlayer == players.length - 1) {
				curPlayer = 0;
			} else {
				curPlayer++;
			}
		} else if (input == "Buy Upgrades") {
			showUpgradeDialog();
		}
		else if (input == "Resign") {
			players[curPlayer].lose();
		} else
			makeMove(Integer.parseInt(input));
	}

	public void showUpgradeDialog() {
		ArrayList<CPU> choices = new ArrayList<CPU>();
		for (int i = 0; i < players[curPlayer].getOwnedProperties().size(); i++) {
			if (players[curPlayer].getOwnedProperties().get(i) instanceof CPU) {
				CPU temp = (CPU) players[curPlayer].getOwnedProperties().get(i);
				if (temp.canUpgrade(players[curPlayer])) {
					choices.add(temp);
				}
			}
		}
		String[] options = new String[choices.size()];
		if (choices.size() == 0) {
			JOptionPane.showMessageDialog(null,
					"No viable upgrades (lack of money or all maxed out)");
			return;
		}
		for (int i = 0; i < choices.size(); i++) {
			options[i] = choices.get(i).getName() + ". Cost: $"
					+ choices.get(i).getUpgradeCost();
		}
		String input = (String) JOptionPane.showInputDialog(null,
				"What would you like to do?", "Player " + (curPlayer + 1),
				JOptionPane.QUESTION_MESSAGE, null, // Use
													// default
													// icon
				options, // Array of choices
				options[0]); // Initial choice
		if (input != null) {
			for (int i = 0; i < choices.size(); i++) {
				if (options[i] == input) {
					choices.get(i).upgrade(players[curPlayer]);
					break;
				}
			}
		}
	}

	public void makeMove(int move) {
		int boardLoc = 0;
		for (int i = 0; i < boardSpaces.size(); i++) {
			if (players[curPlayer].getCurrentLocation() == boardSpaces.get(i)) {
				boardLoc = i;
				break;
			}
		}

		for (int i = 0; i < move; i++) {
			if (boardLoc == NUM_SPACES - 1) {
				boardLoc = 0;
			} else {
				boardLoc++;
			}
			players[curPlayer].setCurrentLocation(boardSpaces.get(boardLoc));
			if (players[curPlayer].getCurrentLocation() instanceof Distribution) {

				((Distribution) players[curPlayer].getCurrentLocation())
						.landOnSpace(players[curPlayer]);
			}
			validate();
			repaint();
			try {
				if (!sim)
					Thread.sleep(250);
				else
					Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private int rollDice() {
		Random rand = new Random(System.currentTimeMillis());
		int die1 = rand.nextInt(6) + 1;
		int die2 = rand.nextInt(6) + 1;
		if (!sim)
			JOptionPane
					.showMessageDialog(null, "You rolled a " + die1 + " and a "
							+ die2 + ". Move " + (die1 + die2) + " spaces.");
		return die1 + die2;
	}

	private boolean isGameover() {
		int stillIn = 0;
		for (Player p : players) {
			if (p.stillPlaying()) {
				stillIn++;
			}
		}
		return stillIn <= 1;
	}

	public void initPlayers() {
		for (int i = 0; i < numPlayers; i++) {
			players[i] = new Player(boardSpaces.get(0), players);
		}
		curPlayer = 0;
	}

	public void readFile(String boardDescFile) {
		boardSpaces = new ArrayList<Space>();
		Scanner scan = new Scanner("File Not Found");
		try {
			scan = new Scanner(new File(boardDescFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		scan.useDelimiter(",|\\n");
		while (scan.hasNext()) {
			String type = scan.next();
			Space temp = new Safe();
			if (type.equalsIgnoreCase("DIST")) {
				String name = scan.next();
				int amount = scan.nextInt();
				temp = new Distribution(name, amount);
			} else if (type.equalsIgnoreCase("CPU")) {
				String name = scan.next();
				int coreCount = Integer.parseInt(scan.next());
				int threadCount = Integer.parseInt(scan.next());
				int clockSpeed = Integer.parseInt(scan.next());
				int baseRent = Integer.parseInt(scan.next());
				int oc1 = Integer.parseInt(scan.next());
				int oc2 = Integer.parseInt(scan.next());
				int oc3 = Integer.parseInt(scan.next().trim());
				temp = new CPU(name, coreCount, threadCount, clockSpeed,
						baseRent, oc1, oc2, oc3);
			} else if (type.equalsIgnoreCase("GPU")) {
				String name = scan.next();
				int cost = scan.nextInt();
				temp = new GPU(name, cost);
			} else if (type.equalsIgnoreCase("MINE")) {
				temp = new Mine(players);
			} else if (type.equalsIgnoreCase("VIRUS")) {
				temp = new Virus(players);
			} else if (type.equalsIgnoreCase("SAFE")) {
				temp = new Safe();
			}
			boardSpaces.add(temp);
			scan.nextLine();
		}
		boardSpaces.get(0);
	}

	@Override
	public void paint(Graphics g) {
		BufferedImage image = new BufferedImage(GameDisplay.WIDTH,
				GameDisplay.HEIGHT, BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = image.getGraphics();
		g2.setColor(Color.BLACK);
		paintBoard(g2);
		paintPlayerInfo(g2);
		paintPlayers(g2);

		g.clearRect(0, 0, GameDisplay.WIDTH, GameDisplay.HEIGHT);
		g.drawImage(image, 0, 0, null);

	}

	public void paintPlayers(Graphics g) {
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			Space cur = p.getCurrentLocation();
			BufferedImage toDraw = scale(p.getPiece().getImage(),
					BufferedImage.TYPE_INT_ARGB, 25, 25, .5, .5);
			int x = cur.getX() + X_OFFSET;
			int y = cur.getY() + Y_OFFSET;
			if (i == 1 || i == 3) {
				x = x + X_OFFSET + p.getPiece().getIconWidth() / 2;
			}
			if (i == 2 || i == 3) {
				y = y + p.getPiece().getIconHeight() / 2 + Y_OFFSET;
			}
			g.drawImage(toDraw, x, y, null);
		}
	}

	public void paintPlayerInfo(Graphics g) {
		int x = SPACE_WIDTH * 2, y = SPACE_HEIGHT * 2;
		for (int i = 0; i < players.length; i++) {
			Player p = players[i];
			Image icon = p.getPiece().getImage();
			g.drawImage(icon, x, y, null);
			g.drawString("Player: " + (i + 1), x + p.getPiece().getIconWidth()
					+ PADDING, y + LINE_HEIGHT);
			g.drawString("Balance: $" + p.getBalance(), x
					+ p.getPiece().getIconWidth() + PADDING, y + LINE_HEIGHT
					* 2);
			ArrayList<Property> properties = p.getOwnedProperties();
			g.drawString("Properties: ", x, y + p.getPiece().getIconHeight()
					+ LINE_HEIGHT);
			for (int k = 0; k < properties.size(); k++) {
				String prop = properties.get(k).getName() + ". Rent: $"
						+ properties.get(k).calculateRent() + " ("
						+ properties.get(k).getUpgradeString() + ")";
				g.drawString(prop, x, y + p.getPiece().getIconHeight()
						+ LINE_HEIGHT * (k + 2));
			}
			if (i % 2 == 0) {
				x = x * 3;
			} else {
				y = y * 3;
				x = SPACE_WIDTH * 2;
			}

		}
	}

	public void paintBoard(Graphics g) {
		String display = "<html><body style='width:" + (SPACE_WIDTH - 20)
				+ " px; padding: 5px; text-align:center;'>"
				+ boardSpaces.get(0).getName();

		int spaceCount = 0;
		JLabel textLabel = new JLabel(display);
		textLabel.setSize(textLabel.getPreferredSize());
		BufferedImage bi = new BufferedImage(SPACE_WIDTH, SPACE_HEIGHT,
				BufferedImage.TYPE_INT_ARGB);
		Graphics g2 = bi.createGraphics();

		textLabel.paint(g2);
		g.drawImage(bi, GameDisplay.WIDTH_BOARD - SPACE_WIDTH,
				GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT, null);
		boardSpaces.get(0).setBoardLocation(++spaceCount);
		boardSpaces.get(0).setX(GameDisplay.WIDTH_BOARD - SPACE_WIDTH);
		boardSpaces.get(0).setY(GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT);

		// bottom
		g.drawLine(SPACE_WIDTH, GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT,
				GameDisplay.WIDTH_BOARD - SPACE_WIDTH, GameDisplay.HEIGHT_BOARD
						- SPACE_HEIGHT);
		for (int i = 1; i <= NUM_SPACES / 4; i++) {
			int width = SPACE_WIDTH * i - ((i - 1) * 2);
			if (i == NUM_SPACES / 4)
				width = width - 3;
			g.drawLine(width, GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT, width,
					GameDisplay.HEIGHT_BOARD + 100);

			int spaceNum = i;
			boardSpaces.get(spaceNum).setBoardLocation(++spaceCount);

			display = "<html><body style='width:" + (SPACE_WIDTH - 30)
					+ " px; padding: 5px; text-align:center;'>"
					+ boardSpaces.get(spaceNum).getName();
			if (boardSpaces.get(spaceNum) instanceof Property) {
				display += "<br>$"
						+ ((Property) boardSpaces.get(spaceNum)).getCost();
			}

			textLabel = new JLabel(display);
			textLabel.setSize(textLabel.getPreferredSize());
			bi = new BufferedImage(SPACE_WIDTH, SPACE_HEIGHT,
					BufferedImage.TYPE_INT_ARGB);
			g2 = bi.createGraphics();

			textLabel.paint(g2);
			g.drawImage(bi, GameDisplay.WIDTH - SPACE_WIDTH * (i + 1)
					- (((NUM_SPACES / 4) - i + 1) * 2),
					GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT, null);
			boardSpaces.get(spaceNum).setX(
					GameDisplay.WIDTH - SPACE_WIDTH * (i + 1)
							- (((NUM_SPACES / 4) - i + 1) * 2));
			boardSpaces.get(spaceNum).setY(
					GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT);

		}
		// left
		g.drawLine(SPACE_WIDTH, GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT,
				SPACE_WIDTH, SPACE_HEIGHT);
		for (int i = 1; i <= NUM_SPACES / 4; i++) {
			int width = SPACE_HEIGHT * i - ((i - 1) * 8);

			if (i == NUM_SPACES / 4)
				width = width + 4;

			g.drawLine(0, width, SPACE_WIDTH, width);

			int spaceNum = i + NUM_SPACES / 4;
			boardSpaces.get(spaceNum).setBoardLocation(++spaceCount);

			display = "<html><body style='width:" + (SPACE_WIDTH - 20)
					+ " px; padding: 5px; text-align:center;'>"
					+ boardSpaces.get(spaceNum).getName();

			if (boardSpaces.get(spaceNum) instanceof Property) {
				display += "<br>$"
						+ ((Property) boardSpaces.get(spaceNum)).getCost();
			}

			textLabel = new JLabel(display);
			textLabel.setSize(textLabel.getPreferredSize());
			bi = new BufferedImage(SPACE_WIDTH, SPACE_HEIGHT,
					BufferedImage.TYPE_INT_ARGB);
			g2 = bi.createGraphics();

			textLabel.paint(g2);
			g.drawImage(bi, 0, GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT
					* (i + 1) + ((i - 1) * 8), null);
			boardSpaces.get(spaceNum).setX(0);
			boardSpaces.get(spaceNum).setY(
					GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT * (i + 1)
							+ ((i - 1) * 8));
		}

		// TOP
		g.drawLine(SPACE_WIDTH, SPACE_HEIGHT, GameDisplay.WIDTH_BOARD
				- SPACE_WIDTH, SPACE_HEIGHT);
		for (int i = 0; i <= NUM_SPACES / 4; i++) {
			int width = SPACE_WIDTH * i - ((i - 1) * 2);
			if (i == NUM_SPACES / 4)
				width = width - 3;
			if (i != 0)
				g.drawLine(width, 0, width, SPACE_HEIGHT);
			if (i != NUM_SPACES / 4) {
				int spaceNum = (NUM_SPACES / 4) * 3 - i;
				boardSpaces.get(spaceNum).setBoardLocation(++spaceCount);

				display = "<html><body style='width:" + (SPACE_WIDTH - 30)
						+ " px; padding: 5px; text-align:center;'>"
						+ boardSpaces.get(spaceNum).getName();

				if (boardSpaces.get(spaceNum) instanceof Property) {
					display += "<br>$"
							+ ((Property) boardSpaces.get(spaceNum)).getCost();
				}

				textLabel = new JLabel(display);
				textLabel.setSize(textLabel.getPreferredSize());
				bi = new BufferedImage(SPACE_WIDTH, SPACE_HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
				g2 = bi.createGraphics();

				textLabel.paint(g2);
				g.drawImage(bi, GameDisplay.WIDTH - SPACE_WIDTH * (i + 1)
						- (((NUM_SPACES / 4) - i + 1) * 2), 0, null);
				boardSpaces.get(spaceNum).setX(
						GameDisplay.WIDTH - SPACE_WIDTH * (i + 1)
								- (((NUM_SPACES / 4) - i + 1) * 2));
				boardSpaces.get(spaceNum).setY(0);
			}
		}

		// right
		g.drawLine(GameDisplay.WIDTH_BOARD - SPACE_WIDTH,
				GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT,
				GameDisplay.WIDTH_BOARD - SPACE_WIDTH, SPACE_HEIGHT);
		for (int i = 1; i <= NUM_SPACES / 4; i++) {
			int width = SPACE_HEIGHT * i - ((i - 1) * 8);
			if (i == NUM_SPACES / 4)
				width = width + 4;
			g.drawLine(GameDisplay.WIDTH_BOARD - SPACE_WIDTH, width,
					GameDisplay.WIDTH_BOARD + 100, width);
			if (i != NUM_SPACES / 4) {
				int spaceNum = (NUM_SPACES / 4) * 4 - i;
				boardSpaces.get(spaceNum).setBoardLocation(++spaceCount);

				display = "<html><body style='width:" + (SPACE_WIDTH - 20)
						+ " px; padding: 5px; text-align:center;'>"
						+ boardSpaces.get(spaceNum).getName();

				if (boardSpaces.get(spaceNum) instanceof Property) {
					display += "<br>$"
							+ ((Property) boardSpaces.get(spaceNum)).getCost();
				}

				textLabel = new JLabel(display);
				textLabel.setSize(textLabel.getPreferredSize());
				bi = new BufferedImage(SPACE_WIDTH, SPACE_HEIGHT,
						BufferedImage.TYPE_INT_ARGB);
				g2 = bi.createGraphics();

				textLabel.paint(g2);
				g.drawImage(bi, GameDisplay.WIDTH_BOARD - SPACE_WIDTH,
						GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT * (i + 1)
								+ ((i - 1) * 8), null);
				boardSpaces.get(spaceNum).setX(
						GameDisplay.WIDTH_BOARD - SPACE_WIDTH);
				boardSpaces.get(spaceNum).setY(
						GameDisplay.HEIGHT_BOARD - SPACE_HEIGHT * (i + 1)
								+ ((i - 1) * 8));
			}
		}
	}

	// FROM STACKOVERFLOW
	public static BufferedImage scale(Image in, int imageType, int dWidth,
			int dHeight, double fWidth, double fHeight) {
		BufferedImage sbi = toBufferedImage(in);
		BufferedImage dbi = null;
		if (sbi != null) {
			dbi = new BufferedImage(dWidth, dHeight, imageType);
			Graphics2D g = dbi.createGraphics();
			AffineTransform at = AffineTransform.getScaleInstance(fWidth,
					fHeight);
			g.drawRenderedImage(sbi, at);
		}
		return dbi;
	}

	// FROM STACK OVERFLOW
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) {
			return (BufferedImage) img;
		}

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null),
				img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}

}
