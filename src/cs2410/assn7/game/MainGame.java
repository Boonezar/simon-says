package cs2410.assn7.game;

/**
 * @author Samuel Christiansen
 *@version 1.0
 *
 *A simple class to show that I have my IDE up and running
 *This displays my name to show that I did this myself
 */

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Random;

import javax.sound.midi.MidiChannel;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;

import cs2410.assn7.components.ColorPanel;
import cs2410.assn7.components.ScorePanel;

/**
 * @author 
 *
 */
public class MainGame implements MouseListener, ActionListener {
	private JFrame frame = new JFrame("Simonish SUPREME");
	private JPanel gameArea = new JPanel();
	private Color[] colors = {Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW};
	private ColorPanel[] panels = new ColorPanel[4];
	private ScorePanel scorePanel = new ScorePanel();
	private ArrayList<ColorPanel> compSeq = new ArrayList<ColorPanel>();
	private ArrayList<ColorPanel> reverseCompSeq = new ArrayList<ColorPanel>();
	private Random rand = new Random();
	private Iterator<ColorPanel> iter;
	private Iterator<ColorPanel> reverseIter;
	private boolean playerTurn = false;
	private boolean reverseGameMode = false;
	private boolean scrambleGameMode = false;
	private boolean incrimentSpeed = false;
	private boolean imageMode = false;
	private JMenuBar menuBar = new JMenuBar();
	private int speed = 500;
	private ImageIcon smiley = new ImageIcon();
    private ImageIcon frowny = new ImageIcon();
    private JLabel imageLabel = new JLabel();
    //private MidiChannel midi;

	
	private MainGame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JPanel pane = (JPanel)frame.getContentPane();
		pane.setLayout(new BorderLayout());
		frame.setJMenuBar(menuBar);
		
		JMenu settingsMenu = new JMenu("Settings");
		menuBar.add(settingsMenu);
		JMenu statsMenu = new JMenu("Stats");
		menuBar.add(statsMenu);
		JMenu helpMenu = new JMenu("Help");
		menuBar.add(helpMenu);

        try {
				smiley = new ImageIcon(new URL("https://img.buzzfeed.com/buzzfeed-static/static/2016-03/23/16/enhanced/webdr03/enhanced-buzz-18964-1458765816-19.jpg"));
				frowny = new ImageIcon(new URL("https://img.buzzfeed.com/buzzfeed-static/static/2015-10/30/9/enhanced/webdr15/enhanced-17219-1446211336-1.jpg"));
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        
		
		gameArea.setLayout(new GridLayout(2, 2));
		gameArea.setPreferredSize(new Dimension(400, 400));
		addListeners(gameArea);
		
		JMenuItem chooseColor = new JMenuItem("Choose Color");
		settingsMenu.add(chooseColor);
		chooseColor.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				// TODO Auto-generated method stub
				//choosingColor = true;
				JFrame colorSelectionFrame = new JFrame("Color Selection");
				JPanel directionsPane = (JPanel)colorSelectionFrame.getContentPane();
				directionsPane.setLayout(new BorderLayout());
				JLabel label = new JLabel("Click the box you want to change and choose a color.");
				directionsPane.add(label, BorderLayout.NORTH);
				JLabel label1 = new JLabel("Exit this window to save the changes.");
				directionsPane.add(label1, BorderLayout.SOUTH);
				JPanel gameArea = new JPanel();
				int len = (int) Math.sqrt(panels.length);
				gameArea.setLayout(new GridLayout(len, len));
				gameArea.setPreferredSize(new Dimension(200, 200));
				MouseListener[] listenerArray = new MouseListener[panels.length];
				
				for (int i = 0; i < panels.length; i++) {
					panels[i] = new ColorPanel(colors[i]);
					int num = i;
					//new MouseListener changeColorListener;
					//changingColors.add(changeColorListener);
					panels[i].addMouseListener(listenerArray[i] = new MouseListener(){

						@Override
						public void mouseClicked(MouseEvent arg0) {
							// TODO Auto-generated method stub
							//if(choosingColor)
							//{
								Color newColor = JColorChooser.showDialog(colorSelectionFrame, "Choose a Color", colors[num]);
								colors[num] = newColor; 
								panels[num].setBackground(newColor.darker());
							//}
							//pressPanel.setBackground(panelColor); //this is inside of a listener
							//frame.pack();
							//frame.update(frame.getGraphics());

						}

						@Override
						public void mouseEntered(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void mouseExited(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void mousePressed(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
						@Override
						public void mouseReleased(MouseEvent arg0) {
							// TODO Auto-generated method stub
							
						}
					}
							);
							
					
					gameArea.add(panels[i]);
					
					
				}
				colorSelectionFrame.add(gameArea);
				colorSelectionFrame.pack();
				colorSelectionFrame.setLocationRelativeTo(null);
				colorSelectionFrame.setVisible(true);
				
				colorSelectionFrame.addWindowListener(new WindowAdapter()
						{
							@Override
							public void windowClosing(WindowEvent e)
							{
								pane.removeAll();
								gameArea.removeAll();
								gameArea.setPreferredSize(new Dimension(400, 400));
								//removeListeners(gameArea);
								pane.add(gameArea);
								pane.add(scorePanel, BorderLayout.NORTH);
								for (int i = 0; i < panels.length; i++)
								{
									panels[i].removeMouseListener(listenerArray[i]);
								}
								addListeners(gameArea);                               //Problem
								frame.pack();
								//choosingColor = false;
							}
						});
			}
		});
		JMenu chooseModeMenu = new JMenu("Choose Mode");
		settingsMenu.add(chooseModeMenu);
		JMenu annoyanceMenu = new JMenu("Annoyances Options");
		chooseModeMenu.add(annoyanceMenu);
		JMenu images = new JMenu("Images");
		annoyanceMenu.add(images);
		JRadioButton imageYes = new JRadioButton("On");
		imageYes.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				imageMode = true;
				setImages();
				//imageLabel.enable();
			}
			
		});
		JRadioButton imageNo = new JRadioButton("Off");
		imageNo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				imageMode = false;
				//imageLabel.disable();
			}
			
		});
		images.add(imageYes);
		images.add(imageNo);
		ButtonGroup imageGroup = new ButtonGroup();
		imageGroup.add(imageNo);
		imageGroup.add(imageYes);
		JMenu sounds = new JMenu("Sounds");
		annoyanceMenu.add(sounds);
		JRadioButton soundsYes = new JRadioButton("On");
		soundsYes.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		JRadioButton soundsNo = new JRadioButton("Off");
		soundsNo.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		sounds.add(soundsYes);
		sounds.add(soundsNo);
		ButtonGroup soundsGroup = new ButtonGroup();
		soundsGroup.add(soundsNo);
		soundsGroup.add(soundsYes);
		JMenu speedMenu = new JMenu("Speed Options");
		chooseModeMenu.add(speedMenu);
		JMenuItem slow = new JMenuItem("Slow");
		speedMenu.add(slow);
		JRadioButton slowButton = new JRadioButton("Slow");
		slow.add(slowButton);
		slowButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(slowButton.isSelected())
				{
					incrimentSpeed=false;  
					speed=1000;
				}
			}
			
		});
		JMenuItem medium = new JMenuItem("Medium");
		speedMenu.add(medium);
		JRadioButton mediumButton = new JRadioButton("Medium");
		medium.add(mediumButton);
		mediumButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(mediumButton.isSelected())
				{
					incrimentSpeed=false;
					speed=500;
				}
			}
			
		});
		JMenuItem fast = new JMenuItem("Fast");
		speedMenu.add(fast);
		JRadioButton fastButton = new JRadioButton("Fast");
		fast.add(fastButton);
		fastButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(fastButton.isSelected())
				{
					incrimentSpeed=false;
					speed=150;
				}
			}
			
		});
		JMenuItem inc = new JMenuItem("Incrimental");
		speedMenu.add(inc);
		JRadioButton incButton = new JRadioButton("Incriment");
		inc.add(incButton);
		incButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(incButton.isSelected())
				{
					incrimentSpeed=true;
					speed=1000;
				}
			}
			
		});
		ButtonGroup speedGroup = new ButtonGroup();
		speedGroup.add(fastButton);
		speedGroup.add(slowButton);
		speedGroup.add(mediumButton);
		speedGroup.add(incButton);
		mediumButton.setSelected(true);
		JMenu sizeMenu = new JMenu("Size Options");
		chooseModeMenu.add(sizeMenu);
		JMenuItem two = new JMenuItem("2x2");
		sizeMenu.add(two);
		two.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameArea.removeAll();
				colors = new Color[4];
				panels = new ColorPanel[4];
				colors[0] = Color.RED;
				colors[1] = Color.GREEN;
				colors[2] = Color.BLUE;
				colors[3] = Color.YELLOW;
				gameArea.setLayout(new GridLayout(2, 2));
				addListeners(gameArea);
				frame.pack();
				
			}
			
		});
		JMenuItem three = new JMenuItem("3x3");
		sizeMenu.add(three);
		three.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameArea.removeAll();
				colors = new Color[9];
				panels = new ColorPanel[9];
				colors[0] = Color.RED;
				colors[1] = Color.GREEN;
				colors[2] = Color.BLUE;
				colors[3] = Color.YELLOW;
				colors[4] = Color.BLACK;
				colors[5] = Color.DARK_GRAY;
				colors[6] = Color.CYAN;
				colors[7] = Color.MAGENTA;
				colors[8] = Color.ORANGE;
				gameArea.setLayout(new GridLayout(3, 3));
				addListeners(gameArea);
				frame.pack();
				
			}
			
		});
		JMenuItem five = new JMenuItem("5x5");
		sizeMenu.add(five);
		five.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				gameArea.removeAll();
				colors = new Color[25];
				panels = new ColorPanel[25];
				colors[0] = Color.RED;
				colors[1] = Color.GREEN;
				colors[2] = Color.BLUE;
				colors[3] = Color.YELLOW;
				colors[4] = Color.BLACK;
				colors[5] = Color.DARK_GRAY;
				colors[6] = Color.CYAN;
				colors[7] = Color.MAGENTA;
				colors[8] = Color.ORANGE;
				colors[9] = Color.PINK;
				colors[10] = Color.RED;
				colors[11] = Color.GREEN;
				colors[12] = Color.BLUE;
				colors[13] = Color.YELLOW;
				colors[14] = Color.BLACK;
				colors[15] = Color.DARK_GRAY;
				colors[16] = Color.CYAN;
				colors[17] = Color.MAGENTA;
				colors[18] = Color.ORANGE;
				colors[19] = Color.PINK;
				colors[20] = Color.RED;
				colors[21] = Color.GREEN;
				colors[22] = Color.BLUE;
				colors[23] = Color.YELLOW;
				colors[24] = Color.BLACK;
				gameArea.setLayout(new GridLayout(5, 5));
				addListeners(gameArea);
				frame.pack();
				
			}
			
		});
		JMenu advancedMenu = new JMenu("Advanced Gameplay");
		chooseModeMenu.add(advancedMenu);
		JMenuItem scramble = new JMenuItem("Scramble Mode");
		advancedMenu.add(scramble);
		scramble.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				reverseGameMode = false;
				scrambleGameMode = true;
			}
			
		});
		JMenuItem reverse = new JMenuItem("Reverse-it Mode");
		advancedMenu.add(reverse);
		reverse.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scrambleGameMode = false;
				reverseGameMode = true;
			}
			
		});
		JMenuItem both = new JMenuItem("Mind-blown Mode");
		advancedMenu.add(both);
		both.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				scrambleGameMode = true;
				reverseGameMode = true;
			}
			
		});
		JMenuItem none = new JMenuItem("None");
		advancedMenu.add(none);
		
		ButtonGroup advancedGroup = new ButtonGroup();
		JRadioButton scrambleButton = new JRadioButton("Scramble Mode");
		scramble.add(scrambleButton);
		scrambleButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(scrambleButton.isSelected())
				{
					reverseGameMode = false;
					scrambleGameMode = true;
				}
			}
			
		});
		JRadioButton reverseButton = new JRadioButton("Reverse-it Mode");
		reverse.add(reverseButton);
		reverseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(reverseButton.isSelected())
				{
					scrambleGameMode = false;
					reverseGameMode = true;
				}
			}
			
		});
		JRadioButton bothButton = new JRadioButton("Mind-blown Mode");
		both.add(bothButton);
		bothButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(bothButton.isSelected())
				{
					reverseGameMode = true;
					scrambleGameMode = true;
				}
			}
			
		});
		advancedGroup.add(scrambleButton);
		advancedGroup.add(reverseButton);
		advancedGroup.add(bothButton);
		none.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				advancedGroup.clearSelection();
				reverseGameMode = false;
				scrambleGameMode = false;
			}
			
		});
		
		JMenuItem highScores = new JMenuItem("High Scores");
		statsMenu.add(highScores);
		JMenuItem history = new JMenuItem("History");
		statsMenu.add(history);
		JMenuItem about = new JMenuItem("About");
		helpMenu.add(about);
		about.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0){
				// TODO Auto-generated method stub
				String aboutGame = "This is a standard Simon game with some extra ways \n"
						+ "the player is able to play, including turning on and off \n"
						+ "fun images and sounds, playing at different speeds, sizes, \n"
						+ "and different play styles.\n";
				JOptionPane.showMessageDialog(frame, aboutGame);
			}
		});
		JMenuItem rules = new JMenuItem("Rules");
		helpMenu.add(rules);
		rules.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				String rules = "When the game starts, the computer and the player \n"
						+ "will alternate taking turns.  The computer will \n"
						+ "randomly choose a color on its turn, and the player's \n"
						+ "goal is to click on the corresponding color on his \n"
						+ "turn.  If the player successfully chooses the correct \n"
						+ "color, the computer will select the same first color \n"
						+ "and then an additional random color.  The player's goal \n"
						+ "is to select the same colors in the same order as \n"
						+ "the computer. This process will continue until the \n"
						+ "player incorrectly chooses a color. \n\n"
						+ "Scramble Rules: After the computer goes, the panels \n"
						+ "will change location and the player must still select \n"
						+ "the correct colors in order. \n\n"
						+ "Reverse-it Rules: After the computer goes, the player \n"
						+ "must recall the sequence in reverse order.\n\n"
						+ "Mind-blown Mode Rules: The player will play by both \n"
						+ "the Scramble and Reverse-it rules at the same time!\n\n"
						+ "Good Luck!";
				JOptionPane.showMessageDialog(frame, rules);
			}
		});
		
		scorePanel.addStartListener(this);
		
		pane.add(gameArea);		
		pane.add(scorePanel, BorderLayout.NORTH);
		
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
	
	private void reset() {
		scorePanel.disableStart();
		
		for (ColorPanel p : panels) {
			p.reset();
		}
		
		scorePanel.resetScore();
		compSeq.clear();
	}
	
	private void compTurn() {
		playerTurn = false;
		
		//pause briefly between rounds
		try {
			Thread.sleep(500);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
		
		compSeq.add(panels[rand.nextInt(panels.length)]);
		
		if(reverseGameMode)
		{
			reverseCompSeq = new ArrayList<ColorPanel>();
			for(int i = 0; i<compSeq.size();i++)
			{
				reverseCompSeq.add(compSeq.get(compSeq.size()-i-1));
			}
			reverseIter = reverseCompSeq.iterator();
		}
		iter = compSeq.iterator();
		
		while(iter.hasNext()) {
			ColorPanel tmp = iter.next();
			tmp.pressed();
			
			try {
				Thread.sleep(speed);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			tmp.released();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
			
			
		if(scrambleGameMode)
		{
			scramblePanels(gameArea);
		}
		iter = compSeq.iterator();
		playerTurn = true;
	}
	
	private void scramblePanels(JPanel gameArea)
	{
		gameArea.removeAll();
		ArrayList<ColorPanel> panelsArray = new ArrayList<ColorPanel>();
		for(int i=0; i<panels.length; i++)
		{
			panelsArray.add(panels[i]);
		}
		Collections.shuffle(panelsArray);
		for(int i=0; i<colors.length; i++)
		{
			panels[i]=panelsArray.get(i);
			gameArea.add(panels[i]);
			colors[i]=panels[i].getBackground();
		}
		gameArea.repaint();
		gameArea.revalidate();
	}
	
	private void addListeners(JPanel gameArea)
	{
		for (int i = 0; i < panels.length; i++) {
			panels[i] = new ColorPanel(colors[i]);
			panels[i].addMouseListener(this);
			gameArea.add(panels[i]);
		}
	}
	
	private boolean isCorrectClick(ColorPanel panel) {
		if(reverseGameMode)
		{
			return reverseIter.next()==panel;
		}
		else
		{
			return iter.next() == panel;
		}
		
	}
	
	private void roundWon() {
		scorePanel.incrScore();
		if(incrimentSpeed)
		{
			if(speed>50)
			{
				speed-=50;
			}
		}
	}

	private void gameOver() {
		playerTurn = false;
		if(incrimentSpeed)
		{
			speed = 1000;
		}
		String msg = "Game Over\n" + "Your Score was " + scorePanel.getScore();
		if (scorePanel.isNewHighScore()) {
			msg = msg + "\nCongratulations! You set a new high score!";
		}
		JOptionPane.showMessageDialog(frame, msg);
		scorePanel.enableStart();
	}
	
	private void setImages()
	{
		imageLabel = new JLabel(smiley);
        for(int i=0; i< panels.length; i++)
		{
			panels[i].add(imageLabel);
		}
        imageLabel.disable();
        frame.pack();
	}
	
	private void setSounds()
	{
		
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				new MainGame();				
			}});
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		//do nothing
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		if (!playerTurn) return;
		ColorPanel tmp = (ColorPanel)e.getSource();
		tmp.pressed();
		if (!isCorrectClick(tmp)) {
			this.gameOver();
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if (!playerTurn) return;

		((ColorPanel)e.getSource()).released();
		if(reverseGameMode)
		{
			if (!reverseIter.hasNext()) {
				this.roundWon();
				Thread t = new Thread(){
					public void run(){
						compTurn();
					}
				};
			//this.compTurn();
				t.start();
				try {
					t.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
		else
		{
			if (!iter.hasNext()) {
				this.roundWon();
				Thread t = new Thread(){
					public void run(){
						compTurn();
					}
				};
			//this.compTurn();
				t.start();
				try {
					t.wait();
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// do nothing
	}

	@Override
	public void mouseExited(MouseEvent e) {
		//do nothing		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.reset();
		Thread t = new Thread(){
			public void run(){
				compTurn();
			}
		};
		//this.compTurn();
		t.start();
		try {
			t.wait();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
