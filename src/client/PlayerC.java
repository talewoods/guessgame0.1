package client;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
public class PlayerC {
	Socket playerc=null;
	DataInputStream input=null;
	DataOutputStream output=null;
	GameFrame gameframe=null;
	ArrayList<String> members=new ArrayList<String>();
	boolean bConnected=false;
	PlayerC(GameFrame gameframe)
	{
		this.gameframe=gameframe;
	}
	  public  void connect()
	    {
			try {
				  playerc=new Socket("127.0.0.1",8888);
				  input =new DataInputStream (playerc.getInputStream() );
				  output=new DataOutputStream(playerc.getOutputStream());
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
		  new Thread(new addMember()).start(); 
	  }
	    class addMember implements Runnable
	    {
	    	public void run()
			{
	    		try {
	    			while(bConnected)
				       {
						  int mark=input.readInt();
						  if(mark==1)
						  {
	    				     String name=input.readUTF().trim();
						     int cpscore=gameframe.loginframe.data.query(name);
						     gameframe.creatPlayer(gameframe.manager.members.size(), name, cpscore,1);
						     members.add(name);
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