package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import engine.Game;
import exceptions.FullFieldException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;
import model.cards.minions.Minion;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;

public class CurrentHand extends JPanel implements MouseListener {

	private ArrayList<JLabel> ch;
	private Hero p;
	private Hero p2;
	private JTextField error;
	private CurrentFieldView field1;
	private OpponentFieldView field2;
	private JButton ok;

	public CurrentHand(Game g) {

		ch = new ArrayList<JLabel>();
		this.p = g.getCurrentHero();
		this.p2 = g.getOpponent();
		error = new JTextField();
		ok = new JButton("Okay!");
		this.setSize(1000, 300);
		this.setBounds(200, 700, 1000, 300);
		int x = 0;
		for (int i = 0; i < p.getHand().size(); i++) {
			String s;
			if (p.getHand().get(i).getName().equals("Shadow Word: Death"))
				s = "Images/Shadow Word Death.png";
			else
				s = "Images/" + p.getHand().get(i).getName() + ".png";
			JLabel c = new JLabel(new ImageIcon(s));
			c.setBounds(300 + x, 650, 116, 200);
			c.setSize(110, 200);
			c.setVisible(true);
			c.addMouseListener(this);
			this.add(c);
			if (p.getHand().size() == 6)
				x += 316;
			else
				x += 116;
			ch.add(c);
		}
		this.setVisible(true);
		this.setOpaque(false);
	}

	public ArrayList<JLabel> getH() {
		return ch;
	}

	public void setH(ArrayList<JLabel> h) {
		this.ch = h;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel a = new JLabel();
		int r = ch.indexOf(e.getSource());
		if (p.getHand().get(r) instanceof Minion) {

			try {
				p.playMinion((Minion) p.getHand().get(r));
				a = (JLabel) e.getSource();
				// field1=new CurrentFieldView(a);
				this.add(field1);
				field1.setB(field1.getB() + 120);
				ch.remove(a);
				this.remove(a);
				this.revalidate();
				this.repaint();
			} catch (NotYourTurnException e1) {
				error = new JTextField("wait for your turn");
				error.setEditable(false);
				error.setVisible(true);
				error.setBounds(800, 400, 300, 100);
				error.setOpaque(false);
				error.setFont(new Font("TimesRoman", Font.BOLD, 20));
				error.setForeground(Color.red);
				ok.setOpaque(false);
				this.add(ok);
				this.add(error);
			} catch (NotEnoughManaException e1) {
				error = new JTextField("you don't have enough mana");
				error.setEditable(false);
				error.setVisible(true);
				error.setOpaque(false);
				error.setBounds(800, 400, 300, 100);
				error.setFont(new Font("TimesRoman", Font.BOLD, 20));
				error.setForeground(Color.red);
				ok.setOpaque(false);
				this.add(error);
				this.add(ok);
			} catch (FullFieldException e1) {
				error = new JTextField("your field is already full");
				error.setEditable(false);
				error.setVisible(true);
				error.setOpaque(false);
				error.setFont(new Font("TimesRoman", Font.BOLD, 20));
				error.setForeground(Color.red);
				error.setBounds(800, 400, 300, 100);
				ok.setOpaque(false);
				this.add(error);
				this.add(ok);
			}

		} else if (p.getHand().get(r) instanceof Spell) {

			if (p.getHand().get(r) instanceof FieldSpell)
				try {
					p.castSpell((FieldSpell) p.getHand().get(r));
				} catch (NotYourTurnException e1) {
					error = new JTextField("wait for your turn");
				} catch (NotEnoughManaException e1) {
					error = new JTextField("you don't have enough mana");
				}
			else if (p.getHand().get(r) instanceof AOESpell)
				try {
					p.castSpell((AOESpell) p.getHand().get(r), p2.getField());
				} catch (NotYourTurnException e1) {
					error = new JTextField("wait for your turn");
					error.setEditable(false);
					error.setVisible(true);
					error.setBounds(800, 400, 300, 100);
					error.setOpaque(false);
					error.setFont(new Font("TimesRoman", Font.BOLD, 20));
					error.setForeground(Color.red);
					ok.setOpaque(false);
					this.add(ok);
					this.add(error);
				} catch (NotEnoughManaException e1) {
					error = new JTextField("you don't have enough mana");
					error.setEditable(false);
					error.setVisible(true);
					error.setBounds(800, 400, 300, 100);
					error.setOpaque(false);
					error.setFont(new Font("TimesRoman", Font.BOLD, 20));
					error.setForeground(Color.red);
					ok.setOpaque(false);
					this.add(ok);
					this.add(error);
				}
			else if (p.getHand().get(r) instanceof HeroTargetSpell)
				try {
					p.castSpell((HeroTargetSpell) p.getHand().get(r), p2);
				} catch (NotYourTurnException e1) {
					error = new JTextField("wait for your turn");
					error.setEditable(false);
					error.setVisible(true);
					error.setBounds(800, 400, 300, 100);
					error.setOpaque(false);
					error.setFont(new Font("TimesRoman", Font.BOLD, 20));
					error.setForeground(Color.red);
					ok.setOpaque(false);
					this.add(ok);
					this.add(error);
				} catch (NotEnoughManaException e1) {
					error = new JTextField("you don't have enough mana");
					error.setEditable(false);
					error.setVisible(true);
					error.setBounds(800, 400, 300, 100);
					error.setOpaque(false);
					error.setFont(new Font("TimesRoman", Font.BOLD, 20));
					error.setForeground(Color.red);
					ok.setOpaque(false);
					this.add(ok);
					this.add(error);
				}
			else if (p.getHand().get(r) instanceof LeechingSpell)
				if (field1.getSelectedminion() == null) {
					try {
						p.castSpell((LeechingSpell) p.getHand().get(r),
								field2.getSelectedminion());
					} catch (NotYourTurnException e1) {
						error = new JTextField("wait for your turn");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					} catch (NotEnoughManaException e1) {
						error = new JTextField("you don't have enough mana");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					}
				} else {
					try {
						p.castSpell((LeechingSpell) p.getHand().get(r),
								field1.getSelectedminion());
					} catch (NotYourTurnException e1) {
						error = new JTextField("wait for your turn");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					} catch (NotEnoughManaException e1) {
						error = new JTextField("you don't have enough mana");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					}
				}

			else if (p.getHand().get(r) instanceof MinionTargetSpell)
				if (field1.getSelectedminion() == null) {
					try {
						p.castSpell((LeechingSpell) p.getHand().get(r),
								field2.getSelectedminion());
					} catch (NotYourTurnException e1) {
						error = new JTextField("wait for your turn");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					} catch (NotEnoughManaException e1) {
						error = new JTextField("you don't have enough mana");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					}
				} else {
					try {
						p.castSpell((LeechingSpell) p.getHand().get(r),
								field1.getSelectedminion());
					} catch (NotYourTurnException e1) {
						error = new JTextField("wait for your turn");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					} catch (NotEnoughManaException e1) {
						error = new JTextField("you don't have enough mana");
						error.setEditable(false);
						error.setVisible(true);
						error.setBounds(800, 400, 300, 100);
						error.setOpaque(false);
						error.setFont(new Font("TimesRoman", Font.BOLD, 20));
						error.setForeground(Color.red);
						ok.setOpaque(false);
						this.add(ok);
						this.add(error);
					}
				}
		}
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
