package engine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

import javax.swing.*;

import view.CurrentFieldView;
import view.OpponentFieldView;
import view.StartView;
import model.cards.minions.Minion;
import model.cards.minions.MinionListener;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.*;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotYourTurnException;

public class Controller implements ActionListener, GameListener, HeroListener,
		MinionListener, MouseListener {
	private StartView view;
	private Game game;
	private Hero current;
	private Hero opponent;
	private Spell x;
	private Minion m1;
	private Minion m2;

	public Controller() {

		view = new StartView();
		game = view.getGame();
		current = view.getP1();
		opponent = view.getP2();
		m1 = null;
		m2 = null;
		view.setVisible(true);
		view.revalidate();
		view.repaint();
	}

	public void updatelisteners() {
		view.getStart().addActionListener(this);
		view.getNext().addActionListener(this);
		view.getStartgame().addActionListener(this);
		view.getHeropower().addActionListener(this);
		view.getEndturn().addActionListener(this);
		int x = view.getH().size();
		for (int i = 0; i < x; i++) {
			view.getH().get(i).addMouseListener(this);
		}
		for (int i = 0; i < view.getField1().getField2().size(); i++) {
			view.getField1().getField2().get(i).addMouseListener(this);
		}
		for (int i = 0; i < view.getField2().getField2().size(); i++) {
			view.getField2().getField2().get(i).addMouseListener(this);
		}
	}

	public void actionPerformed(ActionEvent e) {

	}

	public static void main(String[] args) {
		new Controller();

	}

	@Override
	public void onGameOver() {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMinionDeath(Minion m) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onHeroDeath() {
		// TODO Auto-generated method stub

	}

	@Override
	public void damageOpponent(int amount) {
		// TODO Auto-generated method stub

	}

	@Override
	public void endTurn() throws FullHandException, CloneNotSupportedException {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("hi");
		JLabel a = new JLabel();
		int r = view.getH().indexOf(e.getSource());
		this.updatelisteners();
		if (view.getField1().getSelectedminion() != null
				&& view.getField2().getSelectedminion() != null) {
			System.out.println(view.getField2().getSelectedminion());
			System.out.println(view.getField1().getSelectedminion());
			view.getField1().getSelectedminion()
					.attack(view.getField2().getSelectedminion());
			view.revalidate();
			view.repaint();
		}
		if (current.getHand().get(r) instanceof Minion) {

			try {
				a = (JLabel) e.getSource();
				current.playMinion((Minion) current.getHand().get(r));
				view.getField1().removeAll();
				view.getField2().removeAll();
				view.setField1(new CurrentFieldView(game.getCurrentHero()
						.getField(), view));
				view.setField2(new OpponentFieldView(game.getOpponent()
						.getField(), view));
				view.getH().remove(a);
				view.getCHand().remove(a);
				view.revalidate();
				view.repaint();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		} else if (current.getHand().get(r) instanceof Spell) {

			if (current.getHand().get(r) instanceof FieldSpell)
				try {
					a = (JLabel) e.getSource();
					current.castSpell((FieldSpell) current.getHand().get(r));
					view.getField1().removeAll();
					view.getField2().removeAll();
					view.setField1(new CurrentFieldView(game.getCurrentHero()
							.getField(), view));
					view.setField2(new OpponentFieldView(game.getOpponent()
							.getField(), view));
					view.getH().remove(a);
					view.getCHand().remove(a);
					view.revalidate();
					view.repaint();
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
			else if (current.getHand().get(r) instanceof AOESpell)
				try {
					System.out.println(view.getField2().getSelectedminion());
					a = (JLabel) e.getSource();
					current.castSpell((AOESpell) current.getHand().get(r),
							opponent.getField());
					view.getField1().removeAll();
					view.getField2().removeAll();
					view.setField1(new CurrentFieldView(game.getCurrentHero()
							.getField(),view));
					view.setField2(new OpponentFieldView(game.getOpponent()
							.getField(),view));
					view.getH().remove(a);
					view.getCHand().remove(a);
					view.revalidate();
					view.repaint();

				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}
			else if (current.getHand().get(r) instanceof HeroTargetSpell)

				try {
					a = (JLabel) e.getSource();
					current.castSpell((HeroTargetSpell) current.getHand()
							.get(r), game.getOpponent());
					System.out.println(game.getOpponent().getCurrentHP());
					view.getField1().removeAll();
					view.getField2().removeAll();
					view.setField1(new CurrentFieldView(game.getCurrentHero()
							.getField(),view));
					view.setField2(new OpponentFieldView(game.getOpponent()
							.getField(),view));
					view.getH().remove(a);
					view.getCHand().remove(a);
					view.revalidate();
					view.repaint();

				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
				}

			else if (current.getHand().get(r) instanceof LeechingSpell) {
				x = (Spell) current.getHand().get(r);
				System.out.println(x);

			} else if (current.getHand().get(r) instanceof MinionTargetSpell)
				x = (Spell) current.getHand().get(r);
		}
		if (x != null && view.getField2().getSelectedminion() != null) {

			System.out.println(view.getField2().getSelectedminion());
			try {
				a = (JLabel) e.getSource();
				current.castSpell((LeechingSpell) x, view.getField2()
						.getSelectedminion());
				view.getField1().removeAll();
				view.getField2().removeAll();
				view.setField1(new CurrentFieldView(game.getCurrentHero()
						.getField(),view));
				view.setField2(new OpponentFieldView(game.getOpponent()
						.getField(),view));
				view.getH().remove(a);
				view.getCHand().remove(a);
				view.revalidate();
				view.repaint();

			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		}
		if (x != null && view.getField2().getSelectedminion() != null) {
			System.out.println(view.getField2().getSelectedminion());
			try {
				a = (JLabel) e.getSource();
				current.castSpell((MinionTargetSpell) x, view.getField2()
						.getSelectedminion());
				view.getField1().removeAll();
				view.getField2().removeAll();
				view.setField1(new CurrentFieldView(game.getCurrentHero()
						.getField(),view));
				view.setField2(new OpponentFieldView(game.getOpponent()
						.getField(),view));
				view.getH().remove(a);
				view.getCHand().remove(a);
				view.revalidate();
				view.repaint();

			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null,e1.getMessage());
			}
		} else if (view.getField1().getField2().contains(e.getSource())) {
			m1 = current.getField().get(r);
			System.out.println(m1.getName());
		} else if (view.getField2().getField2().contains(e.getSource())) {
			if (m2 != null && m1.isSleeping() == false)
				m1.attack(m2);
			else
				try {
					m1.attack(opponent);
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null,e1.getMessage());
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
