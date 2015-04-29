package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

class Player implements Runnable
{
	Socket s=null;
	Server server;
	String name=null;
	DataInputStream  input=null; 
	DataOutputStream output=null;
	boolean bConnected=false;
	boolean death=false;
	boolean prepared=false;
	Player(Socket s,Server server)
	{
		try {
			  this.s=s;
			  this.server=server;
			  input=new DataInputStream(s.getInputStream());
			  output=new DataOutputStream(s.getOutputStream());
			  bConnected=true;
		    }
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}
	public void run() 
	{
		  try {  
			          int mark=input.readInt();
			          if(mark==1)
			           {
			    	       name=input.readUTF();
			               for(int i=0;i<server.gamestarts.size();i++)
			                   {
			            	    if(!server.find(i))continue;
			            	    Player player=server.players.get(i);
			                    output.writeInt(1);
			                    output.writeUTF(player.name);
			                    player.output.writeInt(1);
			                    player.output.writeUTF(name);
			                   } 
			                output.writeInt(1);
		                    output.writeUTF(name);
			           }
			          prepared=true;
			          Date date=new Date();
			          DateFormat d1 = DateFormat.getDateTimeInstance();;
			          server.ta.append("\n"+name+"µÇÂ¼·þÎñÆ÷£¡       "+d1.format(date));
			          date=null;
			          
			         
		     }
		  catch (IOException e) 
		       {
			     int index=server.players.indexOf(this);
		    	 server.gamestarts.get(index).prepared=false;
		    	 bConnected=false;
		       }
		    
	}
  }
