package client;
import java.awt.Color;
import java.awt.Point;
import java.io.*;
import java.net.*;
public class GuessDraw {	
	Socket client=null;
	DataInputStream input=null;
	DataOutputStream output=null;
	boolean bConnected=false;
    GameFrame gameframe;
	
	public GuessDraw(GameFrame gameframe)
	{
		this.gameframe=gameframe;
	}

	public void connect()
	{
		try {
			Socket client=new Socket("127.0.0.1",8888);
			input=new DataInputStream(client.getInputStream());
			output=new DataOutputStream(client.getOutputStream());
			bConnected=true;
		   } 
		catch (UnknownHostException e)
		{
		  e.printStackTrace();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void launchThread()
	{
		new Thread(new DrawRe()).start();
	}
	class DrawRe implements Runnable
	{
		public void run() 
		{
			try {    
			      while(bConnected)
			        {
					      int mark=input.readInt();
					      if(mark==1)
					      {
					    	   
								gameframe.drawTable.color=new Color(input.readInt());
								gameframe.drawTable.r=input.readInt();
							    int x=input.readInt();
							    int y=input.readInt();
							    gameframe.drawTable.currentpoint=new Point(x,y);
					      }
			    	      if(mark==2)
			    	      {
			    	        int x=input.readInt();
					        int y=input.readInt();
					        Point point=gameframe.drawTable.currentpoint;
					        gameframe.drawTable.currentpoint=new Point(x,y);
					        gameframe.drawTable.draw(point,gameframe.drawTable.currentpoint,gameframe.drawTable.color,gameframe.drawTable.r);
			    	      }
			    	      if(mark==3)
			    	      {
			    	    	  gameframe.drawTable.draw(Color.blue, 5);
			    	      }
					     	 
				    } 
			   }
		 catch (IOException e)
			  {
				e.printStackTrace();
			  }
				
		}
		
	}

}
