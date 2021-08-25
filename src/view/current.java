package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.heroes.Hero;

public class current extends JPanel implements MouseListener {

	private Image hp;
	private Hero hero;
	private Hero selected;
	private ArrayList<JLabel> h1;
	
	public current(Hero h,StartView view){
		this.hero=h;
		selected=null;
		h1=new ArrayList<JLabel>();
		JLabel hero=new JLabel(new ImageIcon("hero/"+h.getName()+".png"));
		this.add(hero);
		h1.add(hero);
		this.setBounds(570, 450, 200, 200);
		hero.setBounds(0, 0,200, 200);
		hp=new ImageIcon("Images/hp.png").getImage();
		hero.addMouseListener(view);
		this.addMouseListener(view);
		hero.addMouseListener(this);
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
	

	public Hero getSelected() {
		return selected;
	}

	public void setSelected(Hero selected) {
		this.selected = selected;
	}

	public ArrayList<JLabel> getH1() {
		return h1;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		selected=this.hero;
		
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

