package server;
import java.io.*;
import java.net.*;
import java.util.Random;
public class GameStart {
	boolean bConnected;
	DataInputStream input;
	DataOutput output;
	Socket s;
	Server server;
	Player player;
	int num=0;
	boolean prepared=false;
	Random ran=new Random();
	String finished=null;
	GameStart(Socket s,Server server,Player player)
	{
	
		try {
			  this.s=s; 
			  this.server=server;
			  this.player=player;
			  input=new DataInputStream(s.getInputStream());
			  output=new DataOutputStream(s.getOutputStream());
			  bConnected =true;
			  prepared=true;
		    } 
		catch (IOException e)
		   {
			e.printStackTrace();
		   }
		
	}

}
