package server;

import java.awt.Color;
import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import client.Line;

class DrawLocal implements Runnable
{
	boolean bConnected=false;
	DataInputStream input=null;
	DataOutputStream output=null;
	Socket s;
	Server server;
	Player player;
	boolean prepared=false;
	DrawLocal(Socket s,Server server,Player player)
	{
		
		try {
			bConnected=true;
			this.server=server;
			this.s=s;
			this.player=player;
			input=new DataInputStream(s.getInputStream());
			output=new DataOutputStream(s.getOutputStream());
		    } 
		catch (IOException e)
		{
			e.printStackTrace();
		}	
		
	}
	
	public void run() 
	{
		   
	     try{
		         while (bConnected)
		           {
				        if (prepared==false) prepared=true;
		        	    int mark=input.readInt();
				        if(mark==1)
				        {
				           int c=input.readInt();
				           int r=input.readInt();
				           int x=input.readInt();
				           int y=input.readInt();
				           for(int i=0;i<server.gamestarts.size();i++)
				              {
				        	    if(!server.find(i))continue;
					            DrawLocal drawlocal=server.drawlocals.get(i);
					            drawlocal.output.writeInt(1);
					            drawlocal.output.writeInt(c);
					            drawlocal.output.writeInt(r);
					            drawlocal.output.writeInt(x);
					            drawlocal.output.writeInt(y); 
				              }
				        }
				        if(mark==2)
				        {
				        	   int x=input.readInt();
					           int y=input.readInt();
					           for(int i=0;i<server.gamestarts.size();i++)
					              {
					        	    if(!server.find(i))continue;
						            DrawLocal drawlocal=server.drawlocals.get(i);
						            drawlocal.output.writeInt(2);
						            drawlocal.output.writeInt(x);
						            drawlocal.output.writeInt(y); 
					              }
				        }
				        if(mark==3)
				        {
				        	 for(int i=0;i<server.gamestarts.size();i++)
				              {
				        		if(!server.find(i))continue;
					            DrawLocal drawlocal=server.drawlocals.get(i);
					            drawlocal.output.writeInt(3);
				              }
				        }
		           }
	       } 
	    catch (IOException e) 
			{
		     
			}
	     finally{
			    try{
			    	 int index=server.drawlocals.indexOf(this);
			    	 server.gamestarts.get(index).prepared=false;
			    	 bConnected=false;
			       }
			      catch(Exception e)
			       {
			    	 e.printStackTrace(); 
			       }
		        }

	
	}
		
	}
	
