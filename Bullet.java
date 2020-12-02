package SpaceProtector;

import javax.swing.*;

public class Bullet extends Sprite {
	
	public Bullet() {
	}
	
	public Bullet(int x, int y) {
		initialBullet(x, y);
	}
	
	private void initialBullet(int x, int y) {
		var bulletSprite = "src/SpaceProtector/res/laserBullet.png";
		var icon = new ImageIcon(bulletSprite);
		setImage(icon.getImage());
		setX(x);
		setY(y);
	}
}
