package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.*;

import engine.Game;
import engine.GameListener;
import exceptions.CannotAttackException;
import exceptions.FullFieldException;
import exceptions.FullHandException;
import exceptions.HeroPowerAlreadyUsedException;
import exceptions.InvalidTargetException;
import exceptions.NotEnoughManaException;
import exceptions.NotSummonedException;
import exceptions.NotYourTurnException;
import exceptions.TauntBypassException;
import model.cards.*;
import model.cards.minions.*;
import model.cards.spells.AOESpell;
import model.cards.spells.FieldSpell;
import model.cards.spells.HeroTargetSpell;
import model.cards.spells.LeechingSpell;
import model.cards.spells.MinionTargetSpell;
import model.cards.spells.Spell;
import model.heroes.Hero;
import model.heroes.HeroListener;

public class StartView extends JFrame implements ActionListener, MouseListener,
		GameListener, HeroListener, MinionListener {

	private JLabel img;
	private JTextArea great;
	private JButton endgame;
	private JButton start;
	private JButton next;
	private JButton startgame;
	private JButton heropower;
	private Minion m2;
	private Minion m1;
	private JRadioButton Hunter;
	private JRadioButton Mage;
	private JRadioButton Paladin;
	private JRadioButton Priest;
	private JRadioButton Warlock;
	private Hero p1;
	private Hero p2;
	private Game game;
	private Graphic g;
	private JPanel radioPanel;
	private ButtonGroup heroes;
	private CurrentFieldView field1;
	private OpponentFieldView field2;
	private ArrayList<JLabel> h;
	private ArrayList<JLabel> h2;
	private JButton endturn;
	private JPanel CHand;
	private JPanel OHand;
	private JPanel CHand2;
	private JPanel OHand2;
	private CurrentHand currenthand;
	private OpponentHand opponenthand;
	private Spell x;
	private Mana CMana;
	private opponentmanaview OMana;
	private current c;
	private opponent o;
	private int CHandSize;
	private Card selectedCard;
	private Hero heroPlaying;
	private JTextArea d1;
	private JTextArea d2;
	private int m;
	private Hero heroOpposing;

	public StartView() {
		super("Hearth Stone Team 306");
		this.setBounds(0, 0, 1365, 735);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		start = new JButton("Start");
		start.setFont(new Font("TimesRoman", Font.BOLD, 30));
		start.setVisible(true);
		start.addActionListener(this);
		start.setBounds(286, 460, 200, 65);
		next = new JButton("Next");
		next.setBounds(812, 340, 100, 50);
		next.setFont(new Font("TimesRoman", Font.BOLD, 30));
		next.setVisible(true);
		next.addActionListener(this);
		startgame = new JButton("Start Game");
		startgame.setBounds(812, 340, 300, 100);
		startgame.setFont(new Font("TimesRoman", Font.BOLD, 30));
		startgame.setFont(new Font("TimesRoman", Font.BOLD, 30));
		startgame.setVisible(true);
		startgame.addActionListener(this);
		m1 = null;
		m2 = null;
		selectedCard = null;
		heroPlaying = null;

		endturn = new JButton("End Turn");
		endturn.addActionListener(this);
		endturn.setVisible(true);
		endturn.setBounds(1062, 290, 100, 100);
		endturn.setForeground(new Color(0, 0, 0, 0));
		endturn.setBackground(new Color(0, 0, 0, 0));
		endturn.setOpaque(false);
		heropower = new JButton("Hero power", new ImageIcon(
				"Images/hero power.png"));
		heropower.addActionListener(this);
		heropower.setVisible(true);
		heropower.setBounds(732, 490, 100, 100);
		heropower.setForeground(new Color(0, 0, 0, 0));
		heropower.setBackground(new Color(0, 0, 0, 0));
		heropower.setOpaque(false);

		CHand = new JPanel();
		CHand.setBounds(0, 550, 745, 200);
		CHandSize = 0;
		OHand = new JPanel();
		OHand.setBounds(75, 20, 559, 200);
		CHand2 = new JPanel();
		CHand2.setBounds(800, 550, 500, 200);
		OHand2 = new JPanel();
		OHand2.setBounds(700, 20, 500, 200);

		img = new JLabel(new ImageIcon("Images/Start_Screen.png"));
		this.setContentPane(img);
		this.add(start);

		Hunter = new JRadioButton("Hunter", new ImageIcon("Images/Rexxar.png"));
		Hunter.setSelected(true);
		Mage = new JRadioButton("Mage", new ImageIcon(
				"Images/Jaina Proudmoore.png"));
		Paladin = new JRadioButton("Paladin", new ImageIcon(
				"Images/Uther Lightbringer.png"));
		Priest = new JRadioButton("Priest", new ImageIcon(
				"Images/Anduin Wrynn.png"));
		Warlock = new JRadioButton("Warlock", new ImageIcon(
				"Images/Gul'dan.png"));
		heroes = new ButtonGroup();

		heroes.add(Hunter);
		heroes.add(Mage);
		heroes.add(Paladin);
		heroes.add(Priest);
		heroes.add(Warlock);

		h = new ArrayList<JLabel>();
		h2 = new ArrayList<JLabel>();

		this.setVisible(true);
		this.revalidate();
		this.repaint();
	}

	public Minion selectedminion() {
		if (field1.getSelectedminion() == null
				&& field2.getSelectedminion() == null)
			return null;
		else if (field1.getSelectedminion() == null)
			return field2.getSelectedminion();
		else
			return field1.getSelectedminion();
	}

	public Hero selectedhero() {
		if (c.getSelected() == null && o.getSelected() == null)
			return null;
		else if (c.getSelected() == null)
			return o.getSelected();
		else
			return c.getSelected();
	}

	public static void main(String[] args) {
		new StartView();
		Toolkit.getDefaultToolkit().getScreenResolution();
	}

	public void actionPerformed(ActionEvent e) {

		JButton b = (JButton) e.getSource();
		if (b.getActionCommand().equals("End Game!"))
			this.dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
		if (b.getActionCommand().equals("Start")) {
			start.setVisible(false);
			this.remove(start);
			playsound("Sounds/click.wav");
			radioPanel = new JPanel();
			radioPanel.setBounds(12, 0, 1000, 250);
			radioPanel.setLayout(new GridBagLayout());
			radioPanel.add(Hunter);
			radioPanel.add(Mage);
			radioPanel.add(Paladin);
			radioPanel.add(Priest);
			radioPanel.add(Warlock);
			radioPanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(), "Choose your hero!"));
			radioPanel.setVisible(true);
			this.add(radioPanel);
			this.add(next);

			this.setContentPane(img);

		} else if (b.getActionCommand().equals("Next")) {
			p1 = null;

			if (Hunter.isSelected() == true) {
				try {
					p1 = new model.heroes.Hunter();
					playsound("Sounds/"+p1.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}

			} else if (Mage.isSelected() == true) {
				try {
					p1 = new model.heroes.Mage();
					playsound("Sounds/"+p1.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			} else if (Paladin.isSelected() == true) {
				try {
					p1 = new model.heroes.Paladin();
					playsound("Sounds/"+p1.getName()+"_Start.wav");
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {

					e1.printStackTrace();
				}

			} else if (Priest.isSelected() == true) {
				try {
					p1 = new model.heroes.Priest();
					playsound("Sounds/"+p1.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			} else if (Warlock.isSelected() == true) {
				try {
					p1 = new model.heroes.Warlock();
					playsound("Sounds/"+p1.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}

			next.setVisible(false);
			radioPanel.setVisible(false);
			this.remove(radioPanel);
			this.remove(next);
			radioPanel = new JPanel();
			radioPanel.setBounds(12, 0, 1000, 250);

			radioPanel.setLayout(new GridBagLayout());
			radioPanel.add(Hunter);
			radioPanel.add(Mage);
			radioPanel.add(Paladin);
			radioPanel.add(Priest);
			radioPanel.add(Warlock);
			radioPanel.setBorder(BorderFactory.createTitledBorder(
					BorderFactory.createEtchedBorder(),
					"Choose your opponent's hero!"));
			this.add(radioPanel);
			this.add(startgame);
			System.out.println("player 1 = ");
			System.out.println(p1.getName());
			this.setContentPane(img);

		} else if (b.getActionCommand().equals("Start Game")) {
			img = new JLabel(new ImageIcon("Images/battleground.png"));
			this.setContentPane(img);
			this.add(endturn);
			this.add(heropower);
			JTextArea cindicator = new JTextArea("Current :");
			JTextArea oindicator = new JTextArea("Opponent :");
			cindicator.setForeground(Color.WHITE);
			oindicator.setForeground(Color.WHITE);
			cindicator.setFont(new Font("Times new roman ", Font.BOLD, 20));
			oindicator.setFont(new Font("Times new roman ", Font.BOLD, 20));
			cindicator.setOpaque(false);
			oindicator.setOpaque(false);
			cindicator.setBounds(0, 620, 200, 200);
			oindicator.setBounds(0, 80, 200, 200);
			cindicator.setEditable(false);
			oindicator.setEditable(false);
			this.add(cindicator);
			this.add(oindicator);

			p2 = null;
			if (Hunter.isSelected() == true) {
				try {
					p2 = new model.heroes.Hunter();
					playsound("Sounds/"+p2.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			} else if (Mage.isSelected() == true) {
				try {
					p2 = new model.heroes.Mage();
					playsound("Sounds/"+p2.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			} else if (Paladin.isSelected() == true) {
				try {
					p2 = new model.heroes.Paladin();
					playsound("Sounds/"+p2.getName()+"_Start.wav");
				} catch (IOException e1) {

					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {

					e1.printStackTrace();
				}
			} else if (Priest.isSelected() == true) {
				try {
					p2 = new model.heroes.Priest();
					playsound("Sounds/"+p2.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			} else if (Warlock.isSelected() == true) {
				try {
					p2 = new model.heroes.Warlock();
					playsound("Sounds/"+p2.getName()+"_Start.wav");
				} catch (IOException e1) {
					e1.printStackTrace();
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
			}

			startgame.setVisible(false);
			radioPanel.setVisible(false);
			this.remove(radioPanel);
			this.remove(startgame);
			System.out.println("player 2 = ");
			System.out.println(p2.getName());
			try {
				game = new Game(p1, p2);
				game.setListener(this);
			} catch (FullHandException e1) {
				e1.printStackTrace();
			} catch (CloneNotSupportedException e1) {
				e1.printStackTrace();
			}
			p1 = game.getCurrentHero();
			p2 = game.getOpponent();
			d1 = new JTextArea("" + p1.getDeck().size());
			d2 = new JTextArea("" + p2.getDeck().size());
			d1.setFont(new Font("Times new roman ", Font.BOLD, 20));
			d2.setFont(new Font("Times new roman ", Font.BOLD, 20));
			d1.setOpaque(false);
			d2.setOpaque(false);
			d1.setBounds(1167, 410, 100, 100);
			d2.setBounds(1162, 233, 100, 100);
			d1.setEditable(false);
			d2.setEditable(false);
			this.add(d1);
			this.add(d2);
			field1 = new CurrentFieldView(game.getCurrentHero().getField(),
					this);
			field1.setVisible(true);
			this.add(field1);
			field1.addMouseListener(this);
			field2 = new OpponentFieldView(game.getOpponent().getField(), this);
			field2.setVisible(true);
			this.add(field2);
			field2.addMouseListener(this);
			CMana = new Mana(game);
			this.add(CMana);
			OMana = new opponentmanaview(game);
			this.add(OMana);
			c = new current(p1, this);
			o = new opponent(p2, this);
			c.addMouseListener(this);
			o.addMouseListener(this);
			this.add(c);
			this.add(o);

			int x = 0;
			for (int i = 0; i < game.getCurrentHero().getHand().size(); i++) {
				String s;
				if (p1.getHand().get(i).getName().equals("Shadow Word: Death"))
					s = "Images/Shadow Word Death.png";
				else if (p1.getHand().get(i).getName().equals("Stonetusk Boar"))
					s = "Images/Stonetusk Boars.png";
				else
					s = "Images/" + p1.getHand().get(i).getName() + ".png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + x, 490, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				CHand.add(c);
				CHandSize++;
				CHand.setVisible(true);
				CHand.setOpaque(false);
				CHand.addMouseListener(this);
				CHand2.setVisible(true);
				CHand2.setOpaque(false);
				CHand2.addMouseListener(this);
				x += 116;
				h.add(c);
			}
			int y = 0;
			for (int i = 0; i < game.getOpponent().getHand().size(); i++) {

				String s = "Images/card back.png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + y, 40, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				OHand.add(c);
				OHand.setVisible(true);
				OHand.setOpaque(false);
				OHand.addMouseListener(this);
				OHand2.setVisible(true);
				OHand2.setOpaque(false);
				OHand2.addMouseListener(this);
				y += 116;
				h2.add(c);
			}
			this.add(CHand);
			this.add(OHand);

		}

		else if (b.getActionCommand().equals("burned")) {
			this.remove(b);
		} else if (b.getActionCommand().equals("End Turn")) {

			try {
				game.endTurn();
				playsound("Sounds/EndTurn_Down.wav");
				playsound("Sounds/EndTurn_up.wav");
			} catch (FullHandException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				JButton burned = new JButton("burned", new ImageIcon("Images/"
						+ e1.getBurned().getName() + ".png"));
				burned.addActionListener(this);
				burned.setBounds(712, 340, 88, 130);
				burned.setOpaque(false);
				burned.setBackground(new Color(0, 0, 0, 0));
				this.add(burned);

			} catch (CloneNotSupportedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			this.remove(field1);
			this.remove(field2);
			this.remove(c);
			this.remove(o);
			this.remove(d1);
			this.remove(d2);
			field1.setSelectedminion(null);
			field2.setSelectedminion(null);
			field1 = new CurrentFieldView(game.getCurrentHero().getField(),
					this);
			field2 = new OpponentFieldView(game.getOpponent().getField(), this);
			c = new current(game.getCurrentHero(), this);
			o = new opponent(game.getOpponent(), this);
			d1 = new JTextArea("" + game.getCurrentHero().getDeck().size());
			d2 = new JTextArea("" + game.getOpponent().getDeck().size());
			d1.setFont(new Font("Times new roman ", Font.BOLD, 20));
			d2.setFont(new Font("Times new roman ", Font.BOLD, 20));
			d1.setOpaque(false);
			d2.setOpaque(false);
			d1.setBounds(1167, 410, 100, 100);
			d2.setBounds(1162, 233, 100, 100);
			d1.setEditable(false);
			d2.setEditable(false);
			this.add(d1);
			this.add(d2);
			this.add(c);
			this.add(o);
			this.add(field1);
			this.add(field2);
			this.remove(CHand);
			this.remove(OHand);
			this.remove(CHand2);
			this.remove(OHand2);
			CHand.removeAll();
			OHand.removeAll();
			CHand2.removeAll();
			OHand2.removeAll();
			p1 = game.getCurrentHero();
			p2 = game.getOpponent();
			h = new ArrayList<JLabel>();
			h2 = new ArrayList<JLabel>();
			this.remove(CMana);
			this.remove(OMana);
			CMana = new Mana(game);
			OMana = new opponentmanaview(game);
			this.add(CMana);
			this.add(OMana);

			int x = 0;
			for (int i = 0; i < game.getCurrentHero().getHand().size() && i < 5; i++) {
				String s;
				if (p1.getHand().get(i).getName().equals("Shadow Word: Death"))
					s = "Images/Shadow Word Death.png";
				else if (p1.getHand().get(i).getName().equals("Stonetusk Boar"))
					s = "Images/Stonetusk Boars.png";
				else
					s = "Images/" + p1.getHand().get(i).getName() + ".png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + x, 490, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				CHand.add(c);
				CHandSize++;
				CHand.addMouseListener(this);
				x += 116;
				h.add(c);
			}
			int x2 = 0;
			for (int i = 5; i < game.getCurrentHero().getHand().size(); i++) {
				String s;
				if (p1.getHand().get(i).getName().equals("Shadow Word: Death"))
					s = "Images/Shadow Word Death.png";
				else if (p1.getHand().get(i).getName().equals("Stonetusk Boar"))
					s = "Images/Stonetusk Boars.png";
				else
					s = "Images/" + p1.getHand().get(i).getName() + ".png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + x2, 490, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				CHand2.add(c);
				CHand2.addMouseListener(this);
				x2 += 116;
				h.add(c);
			}
			int y = 0;
			for (int i = 0; i < game.getOpponent().getHand().size() && i < 5; i++) {

				String s = "Images/card back.png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + y, 40, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				OHand.add(c);
				OHand.addMouseListener(this);
				y += 116;
				h2.add(c);
			}
			int y2 = 0;
			for (int i = 5; i < game.getOpponent().getHand().size(); i++) {

				String s = "Images/card back.png";
				JLabel c = new JLabel(new ImageIcon(s));
				c.setBounds(12 + y2, 40, 116, 20);
				c.setSize(110, 200);
				c.setVisible(true);
				c.addMouseListener(this);
				OHand2.add(c);
				OHand2.addMouseListener(this);
				y2 += 116;
				h2.add(c);
			}
			this.add(CHand);
			this.add(OHand);
			this.add(CHand2);
			this.add(OHand2);

		} else if (b.getActionCommand().equals("Hero power")) {
			switch (game.getCurrentHero().getName()) {
			case "Rexxar":
				try {
					((model.heroes.Hunter) game.getCurrentHero())
							.useHeroPower();
					playsound("Sounds/"+p1.getName()+"_Attack.wav");
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FullHandException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FullFieldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				break;
			case "Jaina Proudmoore":
				if (field1.getSelectedminion() == null && field2.getSelectedminion() == null) {
					if(this.selectedhero()==null)
						JOptionPane.showMessageDialog(null, "you must choose a target first");
					else
					try {
						((model.heroes.Mage) game.getCurrentHero()).useHeroPower(this.selectedhero());
						playsound("Sounds/"+p1.getName()+"_Attack.wav");
						if(c.getSelected()!=null){
							c.setSelected(null);
							this.remove(c);
							c=new current(p1, this);
							this.add(c);
						}
						else{
							o.setSelected(null);
							this.remove(o);
							o=new opponent(p2, this);
							this.add(o);
						}
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;

				} else if (field1.getSelectedminion() == null) {
					try {
						((model.heroes.Mage) game.getCurrentHero()).useHeroPower(field2.getSelectedminion());
						playsound("Sounds/"+p1.getName()+"_Attack.wav");
						this.remove(field1);
						this.remove(field2);
						field1=new CurrentFieldView(p1.getField(), this);
						field2=new OpponentFieldView(p2.getField(), this);
						this.add(field1);
						this.add(field2);
						field2.setSelectedminion(null);
						this.revalidate();
						this.repaint();
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				} else if (field2.getSelectedminion() == null) {
					try {
						((model.heroes.Mage) game.getCurrentHero()).useHeroPower(field1.getSelectedminion());
						playsound("Sounds/"+p1.getName()+"_Attack.wav");
						this.remove(field1);
						this.remove(field2);
						field1=new CurrentFieldView(p1.getField(), this);
						field2=new OpponentFieldView(p2.getField(), this);
						this.add(field1);
						this.add(field2);
						field1.setSelectedminion(null);
						this.revalidate();
						this.repaint();
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				}
			case "Uther Lightbringer":
				try {
					((model.heroes.Paladin) game.getCurrentHero()).useHeroPower();
					playsound("Sounds/"+p1.getName()+"_Attack.wav");
					this.remove(field2);
					this.remove(field1);
					field1 = new CurrentFieldView(game.getCurrentHero()
							.getField(), this);
					field2 = new OpponentFieldView(game.getOpponent()
							.getField(), this);
					this.add(field2);
					this.add(field1);
					this.revalidate();
					this.repaint();
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FullHandException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FullFieldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				break;
			case "Anduin Wrynn":
				if (field1.getSelectedminion() == null&& field2.getSelectedminion() == null) {
					if(selectedhero()==null)
						JOptionPane.showMessageDialog(null, "you must choose a target first");
					else
					try {
						((model.heroes.Priest) game.getCurrentHero())
								.useHeroPower(selectedhero());
						playsound("Sounds/"+p1.getName()+"_Attack.wav");
						System.out.println("balabizo");

					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				} else if (field1.getSelectedminion() == null) {
					try {
						((model.heroes.Priest) game.getCurrentHero()).useHeroPower(field2.getSelectedminion());
						this.remove(field1);
						this.remove(field2);
						field1 = new CurrentFieldView(p1.getField(), this);
						field2 = new OpponentFieldView(p2.getField(), this);
						this.add(field1);
						this.add(field2);
						this.revalidate();
						this.repaint();
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				} else if (field2.getSelectedminion() == null) {
					try {
						((model.heroes.Priest) game.getCurrentHero())
								.useHeroPower(field1.getSelectedminion());
						this.remove(field1);
						this.remove(field2);
						field1 = new CurrentFieldView(p1.getField(), this);
						field2 = new OpponentFieldView(p2.getField(), this);
						this.add(field1);
						this.add(field2);
						this.revalidate();
						this.repaint();
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (HeroPowerAlreadyUsedException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullHandException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (FullFieldException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
					} catch (CloneNotSupportedException e1) {
						e1.printStackTrace();
					}
					break;
				}
			case "Gul'dan":
				try {
					((model.heroes.Warlock) game.getCurrentHero())
							.useHeroPower();
					playsound("Sounds/"+p1.getName()+"_Attack.wav");
					this.remove(CHand);
					this.remove(CHand2);
					CHand.removeAll();
					CHandSize = 0;
					CHand2.removeAll();
					h = new ArrayList<JLabel>();
					h2 = new ArrayList<JLabel>();
					d1 = new JTextArea(""
							+ game.getCurrentHero().getDeck().size());
					d1.setFont(new Font("Times new roman ", Font.BOLD, 20));
					d1.setOpaque(false);
					d1.setBounds(1167, 410, 100, 100);
					d1.setEditable(false);
					int x = 0;
					for (int i = 0; i < game.getCurrentHero().getHand().size()
							&& i < 5; i++) {
						String s;
						if (p1.getHand().get(i).getName()
								.equals("Shadow Word: Death"))
							s = "Images/Shadow Word Death.png";
						else if (p1.getHand().get(i).getName()
								.equals("Stonetusk Boar"))
							s = "Images/Stonetusk Boars.png";
						else
							s = "Images/" + p1.getHand().get(i).getName()
									+ ".png";
						JLabel c = new JLabel(new ImageIcon(s));
						c.setBounds(12 + x, 490, 116, 20);
						c.setSize(110, 200);
						c.setVisible(true);
						c.addMouseListener(this);
						CHand.add(c);
						CHandSize++;
						CHand.addMouseListener(this);
						x += 116;
						h.add(c);
					}
					int x2 = 0;
					for (int i = 5; i < game.getCurrentHero().getHand().size(); i++) {
						String s;
						if (p1.getHand().get(i).getName()
								.equals("Shadow Word: Death"))
							s = "Images/Shadow Word Death.png";
						else
							s = "Images/" + p1.getHand().get(i).getName()
									+ ".png";
						JLabel c = new JLabel(new ImageIcon(s));
						c.setBounds(12 + x2, 490, 116, 20);
						c.setSize(110, 200);
						c.setVisible(true);
						c.addMouseListener(this);
						CHand2.add(c);
						CHand2.addMouseListener(this);
						x2 += 116;
						h.add(c);
					}
					this.add(CHand);
					this.add(CHand2);
					this.revalidate();
					this.repaint();
				} catch (NotEnoughManaException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (HeroPowerAlreadyUsedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (FullHandException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					JButton burned = new JButton("burned", new ImageIcon(
							"Images/" + e1.getBurned().getName() + ".png"));
					burned.setBounds(712, 340, 88, 130);
					burned.setBackground(new Color(0, 0, 0, 0));
					burned.setOpaque(false);
					burned.addActionListener(this);
					this.add(burned);
				} catch (FullFieldException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (CloneNotSupportedException e1) {
					e1.printStackTrace();
				}
				break;
			}

		}
		this.revalidate();
		this.repaint();
	}

	public Hero getP1() {
		return p1;
	}

	public Hero getP2() {
		return p2;
	}

	public CurrentFieldView getField1() {
		return field1;
	}

	public OpponentFieldView getField2() {
		return field2;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		JLabel a = new JLabel();
		int r = 0;
		System.out.println(e.getSource());
		if (h.contains(e.getSource()) || h2.contains(e.getSource())) {
			if (h.contains(e.getSource())) {
				r = h.indexOf(e.getSource());
				selectedCard = p1.getHand().get(r);
				heroPlaying = p1;
				heroOpposing=p2;
			} else if (h2.contains(e.getSource())) {
				r = h2.indexOf(e.getSource());
				selectedCard = p2.getHand().get(r);
				heroPlaying = p2;
				heroOpposing=p1;
			}
			if (selectedCard instanceof Minion && x == null) {
				m1 = (Minion) selectedCard;
				m = r;
				playsound("Sounds/click.wav");
			} else if (selectedCard instanceof LeechingSpell) {
				x = (Spell) selectedCard;
				playsound("Sounds/click.wav");
				System.out.println(x);
			} else if (selectedCard instanceof MinionTargetSpell){
				x = (Spell) selectedCard;
				playsound("Sounds/click.wav");
			}
			else if (selectedCard instanceof Spell) {

				if (selectedCard instanceof FieldSpell)
					try {
						a = (JLabel) e.getSource();
						heroPlaying.castSpell((FieldSpell) selectedCard);
						playsound("Sounds/spell.wav");
						this.remove(field1);
						this.remove(field2);
						field1 = new CurrentFieldView(game.getCurrentHero()
								.getField(), this);
						field2 = new OpponentFieldView(game.getOpponent()
								.getField(), this);
						field1.setVisible(true);
						this.add(field1);
						this.add(field2);

						h.remove(a);
						CHand2.remove(a);
						CHand.remove(a);

						x = null;
						this.revalidate();
						this.repaint();
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						x = null;
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						x = null;
					}
				else if (selectedCard instanceof AOESpell)
					try {
						System.out.println(field2.getSelectedminion());
						a = (JLabel) e.getSource();
						heroPlaying.castSpell((AOESpell) selectedCard,
								p2.getField());
						playsound("Sounds/spell.wav");
						this.remove(field1);
						this.remove(field2);
						field1 = new CurrentFieldView(game.getCurrentHero()
								.getField(), this);
						field2 = new OpponentFieldView(game.getOpponent()
								.getField(), this);
						field1.setVisible(true);
						this.add(field1);
						this.add(field2);
						h.remove(a);
						CHand.remove(a);
						CHand2.remove(a);
						x = null;
						this.revalidate();
						this.repaint();
					} catch (NotYourTurnException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						x = null;
					} catch (NotEnoughManaException e1) {
						JOptionPane.showMessageDialog(null, e1.getMessage());
						x = null;
					}
				else if (selectedCard instanceof HeroTargetSpell)
					if (this.selectedminion() != null)

						try {
							a = (JLabel) e.getSource();
							heroPlaying.castSpell(
									(MinionTargetSpell) selectedCard,
									this.selectedminion());
							playsound("Sounds/spell.wav");
							System.out.println(game.getOpponent()
									.getCurrentHP());
							this.remove(field1);
							this.remove(field2);
							field1 = new CurrentFieldView(game.getCurrentHero()
									.getField(), this);
							field2 = new OpponentFieldView(game.getOpponent()
									.getField(), this);
							field1.setVisible(true);
							this.add(field1);
							this.add(field2);
							h.remove(a);
							CHand.remove(a);
							CHand2.remove(a);
							x = null;
							this.revalidate();
							this.repaint();
						} catch (NotYourTurnException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							x = null;
						} catch (NotEnoughManaException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							x = null;
						} catch (InvalidTargetException e1) {
							JOptionPane.showMessageDialog(null, e1.getMessage());
							x = null;
						}
					else
						x = (Spell) selectedCard;
			}
		}
		if (m1 != null && field1.isSelected() == true) {

			try {
				heroPlaying.playMinion(m1);
				switch (m1.getName()){
				case "Chilwind Yeti": playsound("Sounds/"+m1.getName()+"_Enter.wav");break;
				case "Core Hound":playsound("Sounds/"+m1.getName()+"_Enter.wav");break;
				case "Stonetusk Boar":playsound("Sounds/"+m1.getName()+"_Enter.wav");break;
				case "King Krush":playsound("Sounds/"+m1.getName()+"_Enter.wav");
				default:playsound("Sounds/playminion.wav");break;
				}
				this.remove(field1);
				this.remove(field2);
				field1 = new CurrentFieldView(game.getCurrentHero().getField(),
						this);
				field2 = new OpponentFieldView(game.getOpponent().getField(),
						this);
				field1.setVisible(true);
				this.add(field1);
				this.add(field2);
				CHand.remove(h.get(m));
				CHand2.remove(h.get(m));
				h.remove(m);
				m1 = null;
				field1.setSelected(false);
				this.revalidate();
				this.repaint();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				m1 = null;
				field1.setSelected(false);
				return;
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				m1 = null;
				field1.setSelected(false);
			} catch (FullFieldException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				m1 = null;
				field1.setSelected(false);
			}
		}
		if (x != null && this.selectedminion() != null
				&& x instanceof MinionTargetSpell) {
			System.out.println(this.selectedminion());
			try {
				int b = p1.getHand().indexOf(selectedCard);
				heroPlaying.castSpell((MinionTargetSpell) x,this.selectedminion());
				playsound("Sounds/spell.wav");
				CHand.remove(h.get(b));
				CHand2.remove(h.get(b));
				h.remove(b);
				this.remove(field1);
				this.remove(field2);
				field1 = new CurrentFieldView(game.getCurrentHero().getField(),
						this);
				field2 = new OpponentFieldView(game.getOpponent().getField(),
						this);
				field1.setVisible(true);
				this.add(field1);
				this.add(field2);

				x = null;
				h.remove(a);
				CHand.remove(a);
				CHand2.remove(a);
				this.revalidate();
				this.repaint();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				x = null;
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				x = null;
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				x = null;
			}
		}
		if (c.getH1().contains(e.getSource())
				|| o.getH2().contains(e.getSource()))
			if (x != null && x instanceof HeroTargetSpell)
				
				try {
					int b = p1.getHand().indexOf(selectedCard);
					heroPlaying.castSpell((HeroTargetSpell) x, heroOpposing);
					playsound("Sounds/spell.wav");
					CHand.remove(h.get(b));
					CHand2.remove(h.get(b));
					h.remove(b);
					x = null;
					this.revalidate();
					this.repaint();
				} catch (NotYourTurnException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				} catch (NotEnoughManaException e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
		if (x != null && this.selectedminion() != null
				&& x instanceof LeechingSpell) {

			System.out.println(this.selectedminion());
			try {
				int b = p1.getHand().indexOf(selectedCard);
				heroPlaying.castSpell((LeechingSpell) x,field2.getSelectedminion());
				playsound("Sounds/spell.wav");
				this.remove(field1);
				this.remove(field2);
				field1 = new CurrentFieldView(game.getCurrentHero().getField(),
						this);
				field2 = new OpponentFieldView(game.getOpponent().getField(),
						this);
				field1.setVisible(true);
				this.add(field1);
				this.add(field2);
				x = null;								
				CHand.remove(h.get(b));
				CHand2.remove(h.get(b));
				h.remove(b);
				this.revalidate();
				this.repaint();
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				x = null;
			} catch (NotEnoughManaException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
				x = null;
			}
		}
		if (field2.getSelectedminion() != null && m1 != null) {

			try {
				heroPlaying.attackWithMinion(m1, field2.getSelectedminion());
				switch (m1.getName()){
				case "Chilwind Yeti": playsound("Sounds/"+m1.getName()+"_Attack.wav");break;
				case "Core Hound":playsound("Sounds/"+m1.getName()+"_Attack.wav");break;
				case "Stonetusk Boar":playsound("Sounds/"+m1.getName()+"_Attack.wav");break;
				default:playsound("Sounds/minionattack.wav");break;
				}
			} catch (CannotAttackException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (NotYourTurnException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (TauntBypassException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (InvalidTargetException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			} catch (NotSummonedException e1) {
				JOptionPane.showMessageDialog(null, e1.getMessage());
			}
			m1 = null;
			field2.setSelectedminion(null);
		}
		if (field1.getSelectedminion() == null
				&& field2.getSelectedminion() != null)
			field2.setSelectedminion(null);
		if (field1.getSelectedminion() != null) {
			if (field2.getSelectedminion() != null) {
				try {
					System.out.println(field1.getSelectedminion().isSleeping());
					System.out.println(field2.getSelectedminion().getName());
					p1.attackWithMinion(field1.getSelectedminion(),field2.getSelectedminion());
					switch (field1.getSelectedminion().getName()){
					case "Chilwind Yeti": playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Core Hound":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Stonetusk Boar":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					default:playsound("Sounds/minionattack.wav");break;
					}
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
					this.remove(field1);
					this.remove(field2);
					field1 = new CurrentFieldView(p1.getField(), this);
					field2 = new OpponentFieldView(p2.getField(), this);
					this.add(field1);
					this.add(field2);
					field2.setSelectedminion(null);
					field1.setSelectedminion(null);
				} catch (CannotAttackException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
				} catch (TauntBypassException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
				} catch (NotSummonedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
					field1.setSelectedminion(null);
					field2.setSelectedminion(null);
				}
				this.revalidate();
				this.repaint();
			}
			if (c.getH1().contains(e.getSource())) {
				try {
					p1.attackWithMinion(field1.getSelectedminion(), p1);
					switch (m1.getName()){
					case "Chilwind Yeti": playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Core Hound":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Stonetusk Boar":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					default:playsound("Sounds/minionattack.wav");break;
					}
					this.remove(c);
					this.remove(o);
					c = new current(p1, this);
					o = new opponent(p2, this);
					this.add(c);
					this.add(o);
					field1.setSelectedminion(null);
					System.out.println(p2.getCurrentHP());
					System.out.println(p1.getCurrentHP());
				} catch (CannotAttackException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (TauntBypassException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotSummonedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				revalidate();
				repaint();
			}
			if (o.getH2().contains(e.getSource())) {
				try {
					p1.attackWithMinion(field1.getSelectedminion(), p2);
					switch (field1.getSelectedminion().getName()){
					case "Chilwind Yeti": playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Core Hound":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					case "Stonetusk Boar":playsound("Sounds/"+field1.getSelectedminion().getName()+"_Attack.wav");break;
					default:playsound("Sounds/minionattack.wav");break;
					}
					this.remove(c);
					this.remove(o);
					c = new current(p1, this);
					o = new opponent(p2, this);
					this.add(c);
					this.add(o);
					System.out.println(p2.getCurrentHP());
					System.out.println(p1.getCurrentHP());
				} catch (CannotAttackException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotYourTurnException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (TauntBypassException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (InvalidTargetException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				} catch (NotSummonedException e1) {
					JOptionPane.showMessageDialog(null, e1.getMessage());
				}
				revalidate();
				repaint();
			}
		}

	}

	public JButton getStart() {
		return start;
	}

	public JButton getNext() {
		return next;
	}

	public JButton getStartgame() {
		return startgame;
	}

	public JRadioButton getPaladin() {
		return Paladin;
	}

	public JRadioButton getPriest() {
		return Priest;
	}

	public JRadioButton getWarlock() {
		return Warlock;
	}

	public JPanel getRadioPanel() {
		return radioPanel;
	}

	public JPanel getOHand() {
		return OHand;
	}

	public OpponentHand getOpponenthand() {
		return opponenthand;
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

	@Override
	public void onMinionDeath(Minion m) {

		this.remove(field1);
		this.remove(field2);
		field1 = new CurrentFieldView(game.getCurrentHero().getField(), this);
		field2 = new OpponentFieldView(game.getOpponent().getField(), this);
		this.add(field1);
		this.add(field2);
		switch (m.getName()){
		case "Chilwind Yeti": playsound("Sounds/"+m.getName()+"_Death.wav");break;
		case "Core Hound":playsound("Sounds/"+m.getName()+"_Death.wav");break;
		case "Stonetusk Boar":playsound("Sounds/"+m.getName()+"_Death.wav");break;
		default:playsound("Sounds/miniondeath.wav");break;
		}
		this.revalidate();
		this.repaint();
	}

	@Override
	public void onHeroDeath() {

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
	public void onGameOver() {
		this.remove(c);
		this.remove(o);
		JLabel winner;
		if (p1.getCurrentHP() == 0) {
			this.dispose();
			playsound("Sounds/"+p1.getName()+"_Death.wav");
			this.setContentPane(new JLabel(new ImageIcon("Images/battleground.png")));
			winner = new JLabel(
					new ImageIcon("Images/" + p2.getName() + ".png"));
			winner.setBounds(400, 325, 200, 200);
			winner.setVisible(true);
			this.add(winner);
			great = new JTextArea("Well done " + p2.getName() + "! You won!");
			great.setEditable(false);
			great.setBounds(265, 260, 1000, 120);
			great.setVisible(true);
			great.setOpaque(false);
			great.setForeground(Color.BLACK);
			great.setFont(new Font("TimesRoman", Font.BOLD, 50));
			this.add(great);
			this.removeMouseListener(this);
			endgame = new JButton("End Game!");
			endgame.setBounds(700, 360, 200, 100);
			endgame.addActionListener(this);
			this.add(endgame);
		} else if (p2.getCurrentHP() == 0) {
			this.dispose();
			playsound("Sounds/"+p1.getName()+"_Death.wav");
			this.setContentPane(new JLabel(new ImageIcon("Images/battleground.png")));
			winner = new JLabel(
					new ImageIcon("Images/" + p1.getName() + ".png"));
			winner.setBounds(400, 325, 200, 200);
			this.add(winner);
			great = new JTextArea("Well done " + p1.getName() + "! You won!");
			great.setEditable(false);
			great.setBounds(265, 260, 1000, 120);
			great.setVisible(true);
			great.setOpaque(false);
			great.setForeground(Color.BLACK);
			great.setFont(new Font("TimesRoman", Font.BOLD, 50));
			this.add(great);
			endgame = new JButton("End Game!");
			endgame.setBounds(700, 360, 200, 100);
			endgame.addActionListener(this);
			this.add(endgame);

		}
		this.revalidate();
		this.repaint();

	}

	public JButton getHeropower() {
		return heropower;
	}

	public Graphic getG() {
		return g;
	}

	public ArrayList<JLabel> getH() {
		return h;
	}

	public JButton getEndturn() {
		return endturn;
	}

	public JPanel getCHand() {
		return CHand;
	}

	public Game getGame() {
		return game;
	}

	public void setField1(CurrentFieldView field1) {
		this.field1 = field1;
	}

	public void setField2(OpponentFieldView field2) {
		this.field2 = field2;
	}
	public void playsound(String filepath) {
		try {
			AudioInputStream audio=AudioSystem.getAudioInputStream(new File(filepath).getAbsoluteFile());
			Clip clip=AudioSystem.getClip();
			clip.open(audio);
			clip.start();
		} catch (IOException |UnsupportedAudioFileException | LineUnavailableException e) {
			e.printStackTrace();
		} 
	}
}
