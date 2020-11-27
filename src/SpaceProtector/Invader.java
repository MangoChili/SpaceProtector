package SpaceProtector;

import javax.swing.*;

public class Invader extends Sprite {
	
	public Invader(int x, int y) {
		initialInvader(x, y);
	}
	
	private void initialInvader(int x, int y) {
		this.x = x;
		this.y = y;
		var invaderSprite = "res/shuttle.png";
		var icon = new ImageIcon(invaderSprite);
		setImage(icon.getImage());
	}
	
	public void move(int step) {
        this.x += step;
	}
}
