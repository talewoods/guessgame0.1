package client;
import java.awt.Button;
import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.plaf.basic.BasicInternalFrameUI;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;
public class GameFrame extends JFrame 
{
   Container container=this.getContentPane();
   JTextArea ta;
   JTextField tf;
   Canvasmaker drawTable;
   JInternalFrame mymessage;
   int WIDTH=900;
   int HIGHT=669;
   GuessChat guesschat=new GuessChat(this);
   GuessDraw guessdraw=new GuessDraw(this);
   LoginFrame loginframe=new LoginFrame(this);
   PlayerC manager=new PlayerC(this);
   GameStartC gamestart=new GameStartC(this);
   GameClient gameclient;
   AddDatabase adddatabase;
   int num=1;
   Timer timer;
   int time=0;
   JPanel[] players=new JPanel[4];
   JInternalFrame[] jInternal=new JInternalFrame[4];
   JPanel playBlock;
   JLabel notice,myname,myscore;
   JLabel[] name= new  JLabel[4];
   JLabel[] score=new  JLabel[4];
   JTextField tf1,tf2;
   JPanel jpanel0,jpanel1;
   JLayeredPane jlayeredpane;
   boolean voiced=false;
   AudioStream as;
   Thread voicestart=null;
   public GameFrame(GameClient gameclient)
   {
      this.gameclient=gameclient;
      adddatabase=new AddDatabase();
      adddatabase.init();
      String path="dream.mid";
      try{
          InputStream file = new FileInputStream(new File(path));
          as = new AudioStream(file);
         }
      catch(Exception e)
      {
    	  e.printStackTrace();
      }
   }

public void close()
   {
	   try
	   {
		   System.exit(0);
	   }
	   catch(Exception e)
	   {
		   adddatabase.close();
	   }
   }

   public  void creatPlayer(int id,String cpname,int cpscore,int lel)
   {
	    jInternal[id]=new JInternalFrame(cpname+"level "+lel,false,false,false);
		jInternal[id].getContentPane().setBackground(Color.GREEN);
		jInternal[id].setLayout(new GridLayout(1,2,0,0));
	        
	    JLabel photo=new JLabel(new ImageIcon("root.png"));
	    jInternal[id].add(photo);
	      
	    Font font=new Font("Default",Font.PLAIN,10);
	    JPanel panel=new JPanel(new GridLayout(2,1));
	    panel.setBackground(Color.green);
	    name[id]=new JLabel(cpname);
	    name[id].setFont(font);
	    score[id]=new JLabel("Score "+cpscore);
	    score[id].setFont(font);
	    panel.add(name[id]);
	    panel.add(score[id]);
	    jInternal[id].add(panel);
		jInternal[id].show();
        players[id].add(jInternal[id]);
   }
   
   public void recreatPlayer()
   {
	   for(int i=0;i<players.length;i++)
	   {
		   if(jInternal[i]!=null) { players[i].remove(jInternal[i]); jInternal[i]=null;}
	   }
	   for(int i=0;i<manager.members.size();i++)
	   {
		   String name1=manager.members.get(i);
		   int score1=loginframe.data.query(name1);
		   creatPlayer(i,name1,score1,1);
	   }
	   repaint();
   }
   public void creatdrawTable()
   {
	    drawTable=new Canvasmaker(this);
		drawTable.setBounds(3,50,drawTable.WIDTH-10,drawTable.HIGHT-10);
		Monitor monitor=new Monitor();
		drawTable.addMouseMotionListener(monitor);
		drawTable.addMouseListener(monitor);
		jpanel1.add(drawTable);
   }
   
   public void buttonlocate()
   {
	   
	    JButton add=new JButton(new ImageIcon("add.gif"));
	    add.addActionListener(new AddMonitor(this));
				
			
		add.setBounds(drawTable.WIDTH,0,75,50);
		
		JButton help=new JButton(new ImageIcon("help.gif"));
		help.setBounds(drawTable.WIDTH+75,0,75,50);
		
		JButton voice=new JButton(new ImageIcon("voice.png"));
		voice.setBounds(drawTable.WIDTH+150,0,75,50);
		voice.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				if(voiced==true)
				{
					    voiced=false;  AudioPlayer.player.stop(as);
				}
				else {
					    voiced=true;  new Thread(new VoiceStart()).start();
				     }
				
			}
			
		});
		
		JButton quit=new JButton(new ImageIcon("quit.png"));
		quit.setBounds(drawTable.WIDTH+225,0,68,50);
		quit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
			    try {
					  manager.output.writeUTF("quite");
					  manager.members.remove(gameclient.name);
					  close();
				    } 
			    catch (IOException e1)
			    {
					e1.printStackTrace();
				}
			
			}
			
		});
		
		tf1=new JTextField(10);
		tf1.setBackground(Color.GREEN);
		tf1.setEditable(false);
		tf1.setBounds(3,0,100,50);
		
		notice=new JLabel("waint...wait....");
		notice.setBounds(100,0,130,60);
		 
		String ans0=adddatabase.query(1)[1];
		tf2=new JTextField("",40);
	    tf2.setEditable(false);
	    tf2.setBackground(Color.GREEN);
		tf2.setBounds(230,0,370,50);
	    jpanel1.add(tf1);
		jpanel1.add(notice);
		jpanel1.add(tf2);
		jpanel1.add(add);
	    jpanel1.add(help);
	    jpanel1.add(voice);
	    jpanel1.add(quit);
   }
   
   public void myMessage()
   {
	    JPanel message=new JPanel(new GridLayout(1,1));
        mymessage=new JInternalFrame();
        ((BasicInternalFrameUI)mymessage.getUI()).setNorthPane(null);

        mymessage.setBackground(Color.GREEN);
        mymessage.setLayout(new GridLayout(1,2,10,10));
        
        JLabel photo=new JLabel(new ImageIcon("mine.png"));
        mymessage.add(photo);
        
        JPanel panel=new JPanel(new GridLayout(3,1));
        panel.setBackground(Color.green);
        myname=new JLabel("我的名字"+gameclient.name);
        myscore=new JLabel("我的得分"+gameclient.score);
        JLabel le=new JLabel("我的等级"+1);
        panel.add(myname);
        panel.add(myscore);
        panel.add(le);
        mymessage.add(panel);
		mymessage.show();
		message.add(mymessage);
		message.setBounds(600,50,293,255);
		jpanel1.add(message);
   }
   
   public void creatText()
   {
		ta=new JTextArea();
		ta.setEditable(false);
		JScrollPane scrollpane=new JScrollPane(ta);
		scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
		scrollpane.setBounds(drawTable.WIDTH,306,294,295);
		
		
		tf=new JTextField();
		tf.addActionListener(new ActionListener(){

			public void actionPerformed(ActionEvent e)
			{
				String str=tf.getText().trim();
				if(str.equals("")) return;
				tf.setText("");
			    try {
			    	String name=gameclient.name;
					guesschat.output.writeUTF(name+" :###"+"\n"+str);
				    } 
			    catch (IOException e1) 
			    {
			    	e1.printStackTrace();
				}
				
			}
			
		});
	    tf.setBounds(drawTable.WIDTH,600,200,40);
	    Button confirm=new Button("发送");
	    confirm.addActionListener(new ActionListener()
	    {
			public void actionPerformed(ActionEvent e)
			{
				String str=tf.getText().trim();
				if(str.equals("")) return;
				tf.setText("");
				try {
			    	String name=gameclient.name;
					guesschat.output.writeUTF(name+" :###"+"\n"+str);
				    } 
			    catch (IOException e1) 
			    {
			    	e1.printStackTrace();
				}
				
			}
	    	
	    });
	    confirm.setBounds(800,600,94,40); 
	    jpanel1.add(confirm);
	    jpanel1.add(scrollpane);
		jpanel1.add(tf);
   }
   
   public void createBoder()
   {
	    JLabel label1=new JLabel(new ImageIcon("D://1.png"));
	    label1.setBounds(drawTable.WIDTH,0,10,drawTable.HIGHT);
	    jpanel1.add(label1);
   }
   
   public void creatTimer()
   {   
	  
	   time=50;
	   if(timer==null){
			        timer=new Timer(1000,new TimerMonitor(tf1));
			       }
			timer.setDelay(1000);
			timer.start();
	 
   }
   
   public void creatplayBlock()
   {
	   playBlock=new JPanel(new GridLayout(1,4));
	   playBlock.setBounds(3,490,591,150);
	   for(int i=0;i<4;i++)
	   {
		 players[i]=new JPanel(new GridLayout(1,1));
		 players[i].setBackground(Color.red);
		 playBlock.add(players[i]);
	   }
	   jpanel1.add(playBlock);
   }
   
   public void launchFrame()
   {
	    setBounds(0,0,WIDTH+200,HIGHT);
		setLayout(null);
		setResizable(false);
		
		
		jpanel0=new JPanel();
		jpanel0.setBounds(0, 0, 1100,HIGHT);
		JLabel background=new JLabel(new ImageIcon("gamebackground.jpg"));
		background.setBounds(0, 0, 1100, HIGHT);
		jpanel0.add(background);
		
		
		jpanel1=new JPanel();
		jpanel1.setBounds(100,0,WIDTH-3,HIGHT);
		jpanel1.setBackground(Color.green);
		jpanel1.setLayout(null);
		
		jlayeredpane=this.getLayeredPane();
	    jlayeredpane.add(jpanel0,new Integer(0));
	    jlayeredpane.add(jpanel1,new Integer(1));
	    jlayeredpane.setVisible(true);
		
		
		jpanel1.setBorder(BorderFactory.createLineBorder(Color.yellow,5));
		
		
		
		creatdrawTable();
		buttonlocate();
		myMessage();
		creatText();
		creatplayBlock();  
		createBoder();
		setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}
	
	 class Monitor extends MouseAdapter
	{
		public void mousePressed(MouseEvent e) 
		{
			
			super.mousePressed(e);
			drawTable.currentpoint=e.getPoint();
			int c=drawTable.color.getRGB();
			try{
				guessdraw.output.writeInt(1);
				guessdraw.output.writeInt(c);
				guessdraw.output.writeInt(drawTable.r);
				guessdraw.output.writeInt(drawTable.currentpoint.x);
				guessdraw.output.writeInt(drawTable.currentpoint.y);
				
			   }
			catch(Exception e1)
			{
				e1.printStackTrace();
			}
		}

		public void mouseReleased(MouseEvent e)
		{
			super.mouseReleased(e);
		}

		public void mouseDragged(MouseEvent e)
		{
			super.mouseDragged(e);
			Point point=new Point();
			point.x=e.getX();
			point.y=e.getY();
			try {
				guessdraw.output.writeInt(2);
				(guessdraw).output.writeInt(point.x);
				(guessdraw).output.writeInt(point.y);
			   } 
			catch (IOException e1) 
			{
				e1.printStackTrace();
			}
		}

	}
    
	 class AddMonitor implements ActionListener
	    {
			GameFrame gameframe;
			AddMonitor(GameFrame gameframe)
			{
				this.gameframe=gameframe;
			}
	    	public void actionPerformed(ActionEvent e) 
			{
				JTextField tf1=new JTextField(20);
				JTextField tf2=new JTextField(20);
				JButton ok=new JButton("确定");
				ok.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e)
					{
					  String s1=tf1.getText().trim();
					  String s2=tf2.getText().trim();
					  if(s1.length()==0){ tf1.setText("请输入你的词语！！");} 
					  if(s1.length()>0) {
						                  boolean flag=adddatabase.query(s1);
						                  if(flag==false) {num++; adddatabase.insert(adddatabase.querynum()+1,s1,s2);tf1.setText("操作成功，请关闭对话框！");}
						                  if(flag==true)  { tf1.setText("该词已在词库中！");}
						                 }
					  
						
					}
					
				});
				
	    		JDialog jdialog=new JDialog(gameframe,true);
				Container c=jdialog.getContentPane();
				c.setLayout(new GridLayout(4,1,0,10));
				JPanel panel1=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
				panel1.add(new Label("我的 word :"));
				
				JPanel panel2=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
				panel2.add(new Label("词语"));
				panel2.add(tf1);
				
				JPanel panel3=new JPanel(new FlowLayout(FlowLayout.LEFT,10,10));
				panel3.add(new Label("提示"));
				panel3.add(tf2);
				
				JPanel panel4=new JPanel(new FlowLayout(FlowLayout.CENTER,10,10));
				panel4.add(ok);
				
				c.add(panel1);
				c.add(panel2);
				c.add(panel3);
				c.add(panel4);
				
				jdialog.setBounds(300,300,300,300);
				jdialog.show();
			}
	    	
	    }
    
	 class TimerMonitor implements ActionListener
	 {
        JTextField tf;
        
        TimerMonitor( JTextField tf)
        {
        	this.tf=tf;
        }
        
		public void actionPerformed(ActionEvent e) 
		{
			tf.setText("时间     还有"+time+"秒");
			time--;
			if(time<0){
				     tf.setText("时间已结束");
				     timer.stop();
				     notice.setText("本 局 已 结 束！！！！");
				     try {
				    	     if(gameclient.currentplayer!=null&&gameclient.currentplayer.equals(gameclient.name))
				    	         {
				    	             guessdraw.output.writeInt(3);
				    	             manager.output.writeUTF("finished");
				    	         }
					     } 
				     catch (IOException e1) 
				         {
						   e1.printStackTrace();
					     }
				    }
			
		}
		 
	 }

     class VoiceStart implements Runnable
       {

		  public void run()
		   {
			
			  while(voiced)
			   {
				AudioPlayer.player.start(as);
			   }
		  }
    	 
        }
}
