package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import engine.Game;

public class opponentmanaview extends JPanel {
	private Image mana;
	private Game game;
	
	public opponentmanaview(Game g){
		this.setBounds(842, 140, 300, 100);
		this.setOpaque(false);
		game=g;
		mana=new ImageIcon("Images/mana.png").getImage();
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	public void paintComponent(Graphics g){
		int x=55;
		g.setFont(new Font("Times new roman ", Font.BOLD, 20));
		g.setColor(Color.blue);
		g.drawString(game.getOpponent().getCurrentManaCrystals()+"/"+game.getCurrentHero().getTotalManaCrystals(), 0, 40);
		for(int i=0;i<game.getOpponent().getCurrentManaCrystals();i++){
			g.drawImage(mana,x,25, 20, 30, this);
			x+=25;
		}
		this.revalidate();
		this.repaint();
	}

}

