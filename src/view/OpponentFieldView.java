package view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.cards.minions.Minion;
import model.heroes.Hero;

public class OpponentFieldView extends JPanel implements MouseListener {
	private StartView view;
	private Minion selectedminion;
	private int b = 0;
	private Hero selectedHero;
	private Image hp;
	private Image attack;
	private Image sleep;
	private Image sheild;
	private Image mana;
	private ArrayList<Minion> field;
	private ArrayList<JLabel> field2;

	public StartView getView() {
		return view;
	}

	public Image getHp() {
		return hp;
	}

	public Image getAttack() {
		return attack;
	}

	public ArrayList<Minion> getField() {
		return field;
	}

	public OpponentFieldView(ArrayList<Minion> f, StartView view) {

		super(new FlowLayout(FlowLayout.LEFT));
		field2 = new ArrayList<JLabel>();
		this.setBounds(262, 240, 1000, 200);
		this.setOpaque(false);
		this.field = f;
		hp = new ImageIcon("Images/hp.png").getImage();
		attack = new ImageIcon("Images/attack.png").getImage();
		sleep = new ImageIcon("Images/sleeping.png").getImage();
		sheild = new ImageIcon("Images/sheild.png").getImage();
		mana = new ImageIcon("Images/mana.png").getImage();
		b = 0;
		selectedminion = null;
		selectedHero = null;

		for (int i = 0; i < f.size(); i++) {
			JLabel c = null;
			if (f.get(i).getName() == "Tirion Fordring")
				c = new JLabel(new ImageIcon("Minions/Tirion Fording.png"));
			else
				c = new JLabel(new ImageIcon("Minions/" + f.get(i).getName()
						+ ".png"));

			c.setBounds(b, 490, 70, 90);
			c.setSize(110, 200);
			c.setVisible(true);
			this.add(c);
			c.addMouseListener(this);
			c.addMouseListener(view);
			b += 116;
			field2.add(c);

		}

		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	public void paintComponent(Graphics g) {
		int x = 50;
		int k = 0;
		for (int i = 0; i < field.size(); i++) {
			if (field.get(i).isDivine())
				g.drawImage(sheild, x - 10, 0, 30, 40, this);
			if (field.get(i).isSleeping() == true) {
				g.drawImage(sleep, x + 10, 0, 20, 30, this);
			}
			g.drawImage(mana, k, 0, 30, 40, this);
			g.setFont(new Font("Times new roman ", Font.BOLD, 15));
			g.setColor(Color.white);
			g.drawString("" + field.get(i).getManaCost(), k + 7, 15);
			g.drawImage(hp, x - 5, 60, 30, 40, this);
			g.drawImage(attack, k, 60, 30, 40, this);
			g.setFont(new Font("Times new roman ", Font.BOLD, 20));
			g.setColor(Color.BLACK);
			g.drawString("" + field.get(i).getCurrentHP(), x + 5, 90);
			g.setColor(Color.YELLOW);
			g.drawString("" + field.get(i).getAttack(), k + 10, 90);
			x += 65;
			k += 65;
		}

	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	public Minion getSelectedminion() {
		return selectedminion;
	}

	public void setSelectedminion(Minion selectedminion) {
		this.selectedminion = selectedminion;
	}

	public Hero getSelectedHero() {
		return selectedHero;
	}

	public void setSelectedHero(Hero selectedHero) {
		this.selectedHero = selectedHero;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int r = field2.indexOf(e.getSource());
		selectedminion = field.get(r);

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

	public ArrayList<JLabel> getField2() {
		return field2;
	}

}
