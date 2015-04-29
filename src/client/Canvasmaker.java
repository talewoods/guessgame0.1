package client;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.*;

public class Canvasmaker extends JPanel
{  
	Color color=Color.blue;
	ArrayList<Line> lines=new ArrayList<Line>();
	int r=5;
	int HIGHT=450;
	int WIDTH=600;
	JPopupMenu pop=new JPopupMenu();
	BufferedImage image=new BufferedImage(WIDTH,HIGHT,BufferedImage.TYPE_INT_RGB);
	Graphics2D g=(Graphics2D)image.getGraphics();
	GameFrame gameframe=null;
	boolean bDrawed=false;
	Point currentpoint=null;
	Canvasmaker(GameFrame gameframe)
	{   
		this.gameframe=gameframe;
		JMenuItem colorChoose =new JMenuItem("Color");
		JMenuItem pen   =new JMenuItem("pen");
		JMenuItem eraser=new JMenuItem("eraser");
		JMenuItem clear=new JMenuItem("clear");
		pop.add(colorChoose);
		pop.addSeparator();
		pop.add(pen);
		pop.addSeparator();
		pop.add(eraser);
		pop.addSeparator();
		pop.add(clear);
		this.add(pop);
		colorChoose.addActionListener(new MonitorColor(this));
		clear.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
				lines.clear();
				try {
					 gameframe.guessdraw.output.writeInt(3);
				    } 
				catch (IOException e1) 
				{
					e1.printStackTrace();
				}
				repaint();
				
			}
			
		});
		pen.addActionListener(new MonitorPen(this));
		eraser.addActionListener(new EraserImageSet(this));
		
		this.addMouseListener(new MonitorJPanel(this));
		
	}
	
	public void paint(Graphics g)
	{
		    g.drawImage(image, 0, 0, null);
	}
	
	public void draw(Point point1,Point point2,Color c,int r)
	{
		
		g.setColor(c);
		g.setStroke(new BasicStroke(r,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.drawLine(point1.x,point1.y,point2.x,point2.y);
		this.repaint();
	}
	
	public void draw(Color c,int r)
	{
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(r,BasicStroke.CAP_ROUND,BasicStroke.JOIN_BEVEL));
		g.fillRect(0, 0, WIDTH,HIGHT);
		g.setColor(c);
		this.repaint();
	}
	
	class MonitorColor implements ActionListener
	{
		Canvasmaker canvasmaker;
		MonitorColor(Canvasmaker canvasmaker)
		{
			this.canvasmaker=canvasmaker;
		}
		public void actionPerformed(ActionEvent e) 
		{
			  final JColorChooser colorPane=new JColorChooser(color);
			  JDialog jdialog=JColorChooser.createDialog(canvasmaker,"²ÂÄãÏ²»¶£¡£¡",false, colorPane,new ActionListener()
			  {
				public void actionPerformed(ActionEvent e) 
				{
				   color=colorPane.getColor();	
				}
				  
			  },null);
			  jdialog.setVisible(true);
		}
		
	}
	
	class MonitorJPanel extends MouseAdapter
	{
		Canvasmaker canvasmaker;
		MonitorJPanel(Canvasmaker canvasmaker)
		{
			this.canvasmaker=canvasmaker;
		}
		public void mouseReleased(MouseEvent e)
		{
			super.mouseReleased(e);
			if(e.isPopupTrigger())pop.show(canvasmaker, e.getX(),e.getY());
		}
	}
    
	class MonitorPen implements ActionListener
	{
		Canvasmaker canvasmaker=null;
		MonitorPen(Canvasmaker canvasmaker)
		{
			 this.canvasmaker= canvasmaker;
		}
		public void actionPerformed(ActionEvent e) 
		{
			r=5; color=Color.blue;
			canvasmaker.setCursor(Cursor.getDefaultCursor());
			JDialog jdialog=new JDialog(gameframe,true);
			jdialog.setLayout(new GridLayout(3,3));
			for(int i=1;i<=9;i++)
			{
				JButton btn=new JButton(String.valueOf(i));
				jdialog.add(btn);
				btn.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
						r=Integer.parseInt(e.getActionCommand());
						jdialog.setDefaultCloseOperation(JDialog.EXIT_ON_CLOSE);
					}
					
				});
			}
			jdialog.pack();
			jdialog.setVisible(true);
			
		}
		
	}
	
	class EraserImageSet implements ActionListener
	{
		Canvasmaker canvasmaker=null;
		EraserImageSet(Canvasmaker canvasmaker)
		{
			this.canvasmaker=canvasmaker;
		}
		public void actionPerformed(ActionEvent e) 
		{
			    Image eraserimage=new ImageIcon("eraser.png").getImage();
			    Toolkit tk=Toolkit.getDefaultToolkit();
			    Cursor cursor=tk.createCustomCursor(eraserimage,new Point(10,10),"norm");
				color=Color.black;
				r=15;
				canvasmaker.setCursor(cursor);
			
		}
		
	}
	/*public static void main(String args[])
	{
		JFrame f=new JFrame();
		Canvasmaker p=new Canvasmaker();
		f.setVisible(true);
		f.setBounds(0,0,200,200);
		f.add(p);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}*/

	
}

