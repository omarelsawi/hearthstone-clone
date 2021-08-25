package view;

import javax.swing.*;

import java.awt.*;

public class Graphic extends JPanel{
	
	public Graphic(){
		
	}
	public void paintComponent(Graphics g){
	     g.drawRect(0,0,100,50);
	     g.setColor(Color.black);
	     this.setSize(1400,900);
	     this.setVisible(true);
	     this.repaint();
	     this.revalidate();
	}
	public static void main(String [] args){
		new Graphic();
	}

}
