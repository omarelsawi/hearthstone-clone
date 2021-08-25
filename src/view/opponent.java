package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.lang.reflect.Array;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.heroes.Hero;

public class opponent extends JPanel implements MouseListener {
	private Image hp;
	private Hero hero;
	private Hero selected;
	private ArrayList<JLabel> h2;
	
	public opponent(Hero h,StartView view){
		this.hero=h;
		selected=null;
		h2=new ArrayList<JLabel>();
		JLabel hero=new JLabel(new ImageIcon("hero/"+h.getName()+".png"));
		this.add(hero);
		h2.add(hero);
		this.setBounds(557, 40, 200, 200);
		hero.setBounds(0, 0,200, 200);
		hp=new ImageIcon("Images/hp.png").getImage();
		hero.addMouseListener(view);
		hero.addMouseListener(this);
		this.addMouseListener(view);
		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}
	
	public void paintComponent(Graphics g ){
		g.drawImage(hp,135,115, 30, 40, this);
		g.setColor(Color.black);
		g.setFont(new Font("Times new roman ", Font.BOLD, 18));
		g.drawString(""+hero.getCurrentHP(), 142, 145);
		
	}

	public ArrayList<JLabel> getH2() {
		return h2;
	}
	

	public Hero getSelected() {
		return selected;
	}

	public void setSelected(Hero selected) {
		this.selected = selected;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		this.selected=this.hero;
		
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
