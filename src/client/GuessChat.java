package client;
import java.io.*;
import java.net.*;


public class GuessChat {
	DataOutputStream output; 
	DataInputStream  input;
	boolean bConnected=false;
	Socket client;
	GameFrame gameframe;
	public GuessChat(GameFrame gameframe) 
	{
		this.gameframe=gameframe;
	}
	public void connect()
	{
		try {
			 client=new Socket("127.0.0.1",8888);
			 output=new DataOutputStream(client.getOutputStream());
			 input=new DataInputStream(client.getInputStream()); 
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
		new Thread(new ChatClientSend()).start();
		new Thread(new ChatClientRe()).start();
	}
	

	class ChatClientSend implements Runnable
	{
		public void run()
		{
			gameframe.loginframe.launchFrame();
			
		}
		
	}
    
	class ChatClientRe   implements Runnable
	{
		public void run()
		{
			
			try {	
			       while(bConnected)
			         {
					    int mark=input.readInt();
					    if(mark==1)
					     {
			    	        String str=input.readUTF();
					        (gameframe.ta).append("\n"+str);
					        gameframe.ta.setCaretPosition(gameframe.ta.getText().length());
					     }
					    if(mark==2)
					     { 
					    	String str=input.readUTF().trim();  
					    	System.out.println(str+"kkkkkkkkkkkkkkkkkk");
					    	gameframe.manager.members.remove(str);
					    	
					    	//for(int i=0;i<gameframe.manager.members.size();i++)System.out.println(gameframe.manager.members.get(i));
					    	gameframe.recreatPlayer();
					    	
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
