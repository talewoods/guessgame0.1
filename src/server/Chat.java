package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.DateFormat;
import java.util.Date;

class Chat implements Runnable
{
	DataInputStream input;
	DataOutputStream output;
	Socket client;
	Server server=null;
	boolean bConnected=false; 
	boolean prepared=false;
	Player player=null;
	Chat(Socket client,Server server,Player player)
	{
		this.server=server;
		this.client=client;
		this.player=player;
		try {
			input=new DataInputStream(client.getInputStream());
			output=new DataOutputStream(client.getOutputStream());
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
		     while(bConnected)
		      {
		    	    if(prepared==false) prepared=true;
		    	    
		    	    
		    	    String str=input.readUTF().trim();
		    	    int pos=str.indexOf(" ");
				    String s1=(str.substring(0,pos)).trim();
				    String s2=str.substring(pos,str.length());
				    if((server.currentplay!=null)&&(!s1.equals(server.currentplay.name))&&server.winner==null&&s2.contains(server.currentans))
				    	{
				    	   server.winner=s1;
				    	   int score1=server.data.query(s1);
				    	   int score2=server.data.query(server.currentplay.name);
				    	   server.data.updata(s1, score1+10);
				    	   server.data.updata(server.currentplay.name, score2+5);
				    	   for(int i=0;i<server.gamestarts.size();i++)
					         {
				    		     if(!server.find(i))continue;
					        	 server.gamestarts.get(i).output.writeInt(2);
					        	 server.gamestarts.get(i).output.writeUTF(server.currentplay.name);
					        	 server.gamestarts.get(i).output.writeUTF(server.winner);
					         }
				    	}
				    
				    
				    for(int i=0;i<server.gamestarts.size();i++)
				       {
				    	  if(!server.find(i))continue;
					      Chat client=(server.chats).get(i);
					      if(client!=null){  client.output.writeInt(1);client.output.writeUTF(str); }
				        }
						
			   } 
		     }
			 catch (IOException e)
			 {
				
			 }
			finally{
				    try{
				    	 int index=server.chats.indexOf(this);
				    	 server.gamestarts.get(index).prepared=false;
				    	 bConnected=false;
				    	 player.death=true;
				    	 System.out.println(player.name+"已经退出！");
				    	 Date date=new Date();
				         DateFormat d1 = DateFormat.getDateTimeInstance();
				    	 server.ta.append("\n"+player.name+"退出服务器！         "+d1.format(date));
				    	 date=null;
				    	  for(int i=0;i<server.gamestarts.size();i++)
					       {
					    	  if(!server.find(i))continue;
						      Chat client=(server.chats).get(i);
						      if(client!=null)
						    	  {
						    	       client.output.writeInt(2);
						    	       client.output.writeUTF(player.name);
						    	  }
					        }
				       }
				      catch(Exception e)
				       {
				    	 e.printStackTrace(); 
				       }
			        }

		
	}
	
}