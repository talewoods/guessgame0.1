package client;
import java.io.*;
import java.net.*;
import java.util.ArrayList;


public class GameClient {
	GameFrame gameframe=null;
    String name=null;
    int score=0;
	int currentword=1;
	String currentplayer;
	String winner=null;
	int num=0;
	public void launchgame()
	{  
		gameframe=new GameFrame(this);
		gameframe.manager.connect();
		gameframe.guesschat.connect();
		gameframe.guessdraw.connect();
		gameframe.manager.launchThread();
		gameframe.guesschat.launchThread();
		gameframe.guessdraw.launchThread();
		
		
	}
	
		
    public static void main(String[] args)
    {
    	GameClient client=new GameClient();
    	client.launchgame();
    }

}


