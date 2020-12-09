package SpaceProtector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.Timer;

public class Game extends JPanel {
	int BOARD_WIDTH = 1024;
	int BOARD_HEIGHT = 768;
	int numRow = 2;
	int numCol = 3;
	int invaderX = 120;
	int invaderY = 10;
	int mission = numRow * numCol;
	
	private Protector protector;
	private List<Invader> invaders;
	private Bullet bullet;
	
	private Dimension d;
	private int pointIn = -1;
	private int kills = 0;
	
	private boolean start = true;
	private String str = "Mission Failed!";
	
	private Timer timer;
	
	public Game() {
		initializeGame();
		startGame();
	}
	
	private void initializeGame() {
		setBackground(Color.black);
		setFocusable(true);
		addKeyListener(new TypeAdapter());
		
		d = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		timer = new Timer(1, new GameCycle());
        timer.start();
		startGame();
	}
	
	private void startGame() {
		protector = new Protector();
		invaders = new ArrayList<>();
		for(int i = 0; i < numRow; i++) {
			for(int j = 0; j < numCol; j++) {
				var invader = new Invader(invaderX + 20*j, invaderY + 20*i);
				invaders.add(invader);
			}
		}
		bullet = new Bullet();
	}
	
	// draw action for Protector, Invaders, Bullet on game board
	private void drawProtector(Graphics g) {
		if(protector.isShow()) {
			g.drawImage(protector.getImage(), protector.getX(), protector.getY(), this);
		}
		if(protector.isDestroyed()) {
			protector.destroy();
			start = false;
		}
	}
	
	private void drawInvaders(Graphics g) {
		int distance = -30;
		for(int i = 0; i < invaders.size(); i++) {
			if(invaders.get(i).isShow()) {
				g.drawImage(invaders.get(i).getImage(), invaders.get(i).getX()+distance, invaders.get(i).getY()*3, this);
				if(i < numCol) {
					distance += 40;
				} else {
					distance -= 80;
				}
			}
			if(invaders.get(i).isDestroyed()) {
				invaders.get(i).destroy();
			}
		}
	}

	private void drawBullet(Graphics g) {
		if(bullet.isShow()) {
			g.drawImage(bullet.getImage(), bullet.getX(), bullet.getY(), this);
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		draw(g);
	}
	
	private void draw(Graphics g) {
		int ground = BOARD_HEIGHT - 50;
		
		g.setColor(Color.black);
		g.fillRect(0, 0, d.width, d.height);
		
		g.setColor(Color.white);
		if(start) {
			g.drawLine(0, ground, BOARD_WIDTH, ground);
			drawProtector(g);
			drawInvaders(g);
			drawBullet(g);
		} else {
			gameOver(g);
		}
	}
	
	private void gameOver(Graphics g) {
		g.setColor(Color.black);
		g.fillRect(0, 0, BOARD_WIDTH, BOARD_HEIGHT);
		
		g.setColor(Color.white);
		var ft = new Font("TimesRoman", Font.BOLD, 20);
		var msgFont = this.getFontMetrics(ft);
		g.setFont(ft);
		g.drawString(str, (BOARD_WIDTH - msgFont.stringWidth(str))/2, BOARD_WIDTH/2);
	}
	
	// game logic
	private void inGame() {
		int tmpInvX;
		int tmpInvY;
		int tmpBulX;
		int tmpBulY;
		int tmpBInvX;
		int tmpBInvY;
		int tmpInvX2;
		int velocity;
		int velocity2;
		
		if(kills == mission) {
			start = false;
			str = "Mission Acomplished!";
		}
		
		// protector
		protector.move();
		
		// invaders
		for(int i = 0; i < invaders.size(); i++) {
			tmpInvX = invaders.get(i).getX();
			if(tmpInvX >= BOARD_WIDTH - 100 && pointIn == 1) {
				pointIn = -1;
				Iterator<Invader> tmp1 = invaders.iterator();
				while(tmp1.hasNext()) {
					Invader inv2 = tmp1.next();
					inv2.setY(inv2.getY() + 5);
				}
			}
			if(tmpInvX <= 18 && pointIn == -1) {
				pointIn = 1;
				Iterator<Invader> tmp2 = invaders.iterator();
				while(tmp2.hasNext()) {
					Invader inv1 = tmp2.next();
					inv1.setY(inv1.getY() + 5);
				}
			}
		}
		Iterator<Invader> tmp = invaders.iterator();
		while(tmp.hasNext()) {
			Invader inv = tmp.next();
			if(inv.isShow()) {
				tmpInvY = inv.getY();
				if(tmpInvY > 180) {
					start = false;
				}
				tmpInvX2 = inv.getX();
				velocity = pointIn;
				velocity2 = pointIn;
				// System.out.println(pointIn);
				// System.out.println(tmpInvX2);
				velocity = steeringFlee(velocity, tmpInvX2);
				// System.out.println(velocity);
				velocity2 = steeringArrive(velocity2, tmpInvX2);
				// System.out.println(velocity2);
				steeringFlocking(pointIn, tmpInvX2);
				// System.out.println(pointIn);
				inv.move(pointIn);
				inv.move(velocity);
				inv.move(velocity2);
			}
		}
		
		// bullet
		if(bullet.isShow()) {
			tmpBulX = bullet.getX();
			tmpBulY = bullet.getY();
			for(int i = 0; i < invaders.size(); i++) {
				tmpBInvX = invaders.get(i).getX();
				tmpBInvY = invaders.get(i).getY();
				if(invaders.get(i).isShow()) {
					if(tmpBulX >= tmpBInvX && tmpBulX <= tmpBInvX+52 && tmpBulY >= tmpBInvY && tmpBulY <= tmpBInvY+52) {
						invaders.get(i).setDestroyed(true);
						bullet.destroy();
						kills++;
						if(kills == mission) {
							start = false;
							str = "Mission Acomplished!";
						}
					}
				}
			}
			if(bullet.getY() < 0) {
				bullet.destroy();
			} else {
				bullet.setY(bullet.getY()-2);
			}
		}
	}
	
	// The Flee Steering Behavior helps invaders flee from the chasing of 
	// protector. When the protector move around invaders, they will move
	// in opposite direction, and accelerate for a while.
	private int steeringFlee(int x, int tmpInvX) {
		int tmpPtcX;
		int steeringX;
		tmpPtcX = protector.getX();
		steeringX = 0;
		if(x <= 0) {
			if(tmpInvX < tmpPtcX && tmpInvX > tmpPtcX - 20) {
				steeringX = -1;
			}
		} else if(x > 0) {
			if(tmpInvX > tmpPtcX && tmpInvX < tmpPtcX + 20) {
				steeringX = 1;
			}
		}
		return steeringX;
	}
	
	// The Arrive Steering Behavior actions when the protector shoot a bullet.
	// When the protector shoots, invaders will still move in original direction,
	// and slow down for a while.
	private int steeringArrive(int x, int tmpInvX) {
		int steeringX;
		steeringX = 0;
		if(bullet.isShow()) {	
			int tmpBulX;
			tmpBulX = bullet.getX();
			if(tmpInvX < tmpBulX) {
				steeringX = -1;
			} else if(tmpInvX >= tmpBulX) {
				steeringX = 1;
			}
		}
		return steeringX;
	}
	
	// The Flocking Steering Behavior actions when the protector move close to
	// invaders, and it depends on the distance between the protector and
	// invaders. If the group of invader is far from the protector, two wingmans
	// gather to the lead plane, form a cohesion formation. If the group of 
	// invader is close to the protector, two wingmans separated from the lead 
	// plane, form a separation formation.
	private void steeringFlocking(int x, int tmpInvX) {
		int tmpPtcX;
		int tmpPtcY;
		int tmpInvY;
		tmpPtcX = protector.getX();
		tmpPtcY = protector.getY();
		Iterator<Invader> tmpInv = invaders.iterator();
		while(tmpInv.hasNext()) {
			Invader inv = tmpInv.next();
			if(inv.isShow()) {
				tmpInvY = inv.getY();
				if(tmpInvY <= tmpPtcY - 200) {
					if(x <= 0) {
						if(tmpInvX < tmpPtcX && tmpInv.hasNext()) {
							x = 1;
						}
					} else if(x > 0) {
						if(tmpInvX > tmpPtcX && !tmpInv.hasNext()) {
							x = -1;
						}
					}
				} else if(tmpInvY > tmpPtcY - 200) {
					if(x <= 0) {
						if(tmpInvX < tmpPtcX && tmpInv.hasNext()) {
							x = -1;
						}
					} else if(x > 0) {
						if(tmpInvX > tmpPtcX && !tmpInv.hasNext()) {
							x = 1;
						}
					}
				}
			}
		}
	}
    
	private void doGameCycle() {
		inGame();
        repaint();
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            doGameCycle();
        }
    }
    
	private class TypeAdapter extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			int x, y, key;
			
			protector.keyPressed(e);
			x = protector.getX();
			y = protector.getY();
			key = e.getKeyCode();
			
			if(key == KeyEvent.VK_J) {
				if(start) {
					if(!bullet.isShow()) {
						bullet = new Bullet(x, y);
					}
				}
			}
		}
		
		@Override
		public void keyReleased(KeyEvent e) {
			protector.keyReleased(e);
		}
	}
}
