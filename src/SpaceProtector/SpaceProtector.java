package SpaceProtector;

import javax.swing.JFrame;
import java.awt.*;

public class SpaceProtector extends JFrame {
	
	int BOARD_WIDTH = 1024;
	int BOARD_HEIGHT = 768;
	
	public SpaceProtector() {
		initializeBoard();
	}
	
	private void initializeBoard() {
		add(new Game());
		setTitle("Space Protector");
		setSize(BOARD_WIDTH, BOARD_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
	}
	
	public static void main(String[] args) {
		EventQueue.invokeLater(() -> {
            var ex = new SpaceProtector();
            ex.setVisible(true);
        });
	}
}
