package SpaceProtector;

import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

public class Protector extends Sprite {
	
	int INIT_X_COORD = 512;
	int INIT_Y_COORD = 650;
	int BOARD_WIDTH = 1024;
	
	private int width;
	int step;
	
	public Protector() {
		initialProtector();
	}
	
	private void initialProtector() {
		var protectorSprite = "src/SpaceProtector/res/shuttle2.png";
		var icon = new ImageIcon(protectorSprite);
		width = icon.getImage().getWidth(null);
		setImage(icon.getImage());
		setX(INIT_X_COORD);
		setY(INIT_Y_COORD);
	}
	
	public void move() {
        x += step;
        
        // prevent our sprite out of edge
        if (x <= 2) {
            x = 2;
        }
        if (x >= BOARD_WIDTH - width - 18) {
            x = BOARD_WIDTH - width - 18;
        }
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_A) {
        	step = -2;
        }
        if (key == KeyEvent.VK_D) {
        	step = 2;
        }
    }

    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        
        if (key == KeyEvent.VK_A) {
        	step = 0;
        }
        if (key == KeyEvent.VK_D) {
        	step = 0;
        }
    }
}
