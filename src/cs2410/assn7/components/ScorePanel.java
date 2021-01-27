package cs2410.assn7.components;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author 
 *
 */
public class ScorePanel extends JPanel{
	private int score = 0;
	private int highScore = 0;
	private boolean newHighScore = false;
	private JLabel highScoreLabel = new JLabel();
	private JLabel scoreLabel = new JLabel();
	private JButton startBtn = new JButton("Start");
	
	public ScorePanel() {
		this.setLayout(new GridLayout(1,3));
		highScoreLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(highScoreLabel);
		startBtn.setHorizontalAlignment(JLabel.CENTER);
		this.add(startBtn);
		scoreLabel.setHorizontalAlignment(JLabel.CENTER);
		this.add(scoreLabel);
		this.updateScoreView();
	}
	
	public void resetScore() {
		score = 0;
		updateScoreView();
		newHighScore = false;
	}
	
	public void incrScore() {
		score++;
		if (score > highScore) {
			newHighScore = true;
			highScore = score;
		}
		updateScoreView();
	}
	
	public int getScore() {
		return score;
	}
	
	public boolean isNewHighScore() {
		return newHighScore;
	}
	
	public void addStartListener(ActionListener list) {
		startBtn.addActionListener(list);
	}
	
	public void disableStart() {
		startBtn.setEnabled(false);
	}
	
	public void enableStart() {
		startBtn.setEnabled(true);
	}
	
	private void updateScoreView() {
		highScoreLabel.setText("High Score\n" + highScore);
		scoreLabel.setText("Score\n" + score);
		this.update(this.getGraphics());
	}
}
