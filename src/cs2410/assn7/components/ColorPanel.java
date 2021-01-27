package cs2410.assn7.components;

import java.awt.Color;

import javax.swing.JPanel;

/**
 * @author
 *
 */
public class ColorPanel extends JPanel {
	private Color color;
	
	public ColorPanel(Color color) {
		this.color = color.darker().darker();
		this.setBackground(this.color);
	}
	
	public void pressed() {
		this.setBackground(color.brighter().brighter());
		this.update(this.getGraphics());
	}
	
	public void released() {
		this.setBackground(color);
		this.update(this.getGraphics());
	}
	
	public void reset() {
		this.setBackground(this.color);
		this.update(this.getGraphics());
	}
}
