package server;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import javax.swing.*;

public class Server extends JFrame {
	 boolean bConnected=false;
	 ServerSocket server=null;
	 ArrayList<Chat>   chats=new ArrayList<Chat>();
	 ArrayList<DrawLocal>drawlocals =new ArrayList<DrawLocal>();
	 ArrayList<Player>players=new ArrayList<Player>();
	 ArrayList<GameStart>gamestarts=new ArrayList<GameStart>();
	 JTextArea  ta;
	 Player currentplay=null;
	 int currentword=-1;
	 String currentans;
	 AddDatabase adddatabase;
	 Database data;
	 String winner=null;
	 Server()
	 {
		 adddatabase=new AddDatabase ();
		 adddatabase.init();
		 data=new Database();
		 data.init();
		 currentans=adddatabase.query(1)[0];
	 }
	 public void launchFrame()
	 {
		 setBounds(0,0,300,300);
		 
		 setResizable(false);
		 Container container=this.getContentPane();
		 
		 ta=new JTextArea();
		 ta.setBackground(Color.orange);
		 JScrollPane scrollpane=new JScrollPane(ta);
		 ta.setText("login");
		 container.add(scrollpane,BorderLayout.CENTER);
		 
		 JLabel log=new JLabel("服务器日志");
		 JPanel logpanel=new JPanel();
		 logpanel.add(log);
		 container.add(logpanel,BorderLayout.NORTH);
		 
		 setVisible(true);
		 this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 
		
	 }
	public static void main(String[] args)
	{
		Server server=new Server();
		server.launchFrame();
		Thread gamemanager =new Thread(new GameManager(server));
		gamemanager.start();
		server.launchServer();
		
	}
	
	  public boolean find(int num)
	    {
	    	if(gamestarts.get(num)==null) return false; 
	    	if((num>=gamestarts.size())||(!gamestarts.get(num).prepared)||(!drawlocals.get(num).prepared)
	   	            ||(!chats.get(num).prepared)||(!players.get(num).prepared))
	    		    return false;
	    	return true;
	    }
	
	public void launchServer()
	{
		try {
			 server=new ServerSocket(8888);
			 bConnected=true;
		    } 
		catch (BindException e)
		{
			System.out.println("端口已使用！");
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		try {
			    Player player=null; Chat chat=null; DrawLocal drawlocal=null;GameStart gamestart=null;   
			    while(bConnected)
		           {
				      int num=0;
				      while(num<4)
				         {
				          Socket s=server.accept(); 
				          num++;
				          System.out.println("已连接！！！");
				          if(num==1){
                                 player=new Player(s,this);
                                 players.add(player);
                                 Thread thread=new Thread(player);
                                 thread.start();
                                 try {
									   thread.join();
								     }
                                 catch (InterruptedException e)
                                 {
					                e.printStackTrace();
								 }
                                   }
				          if(num==2){
					            chat=new Chat(s,this,player);
				                chats.add(chat);
				                new Thread(chat).start();
				                    }
				          if(num==3){ 
					             drawlocal=new DrawLocal(s,this,player);
					             drawlocals.add(drawlocal);
					             new Thread(drawlocal).start();
				                   }
				          if(num==4){ 
					             gamestart=new GameStart(s,this,player);
					             gamestarts.add(gamestart);
				                   }
				         }
			       } 
		     }
		catch (IOException e) 
		  {
				System.out.println("请重新登录服务器！");
		   }
	}

	




}

