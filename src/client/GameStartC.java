package client;
import java.net.*;
import java.io.*;
public class GameStartC implements Runnable {
	GameFrame gameframe;
	Socket s;
	DataInputStream input;
	DataOutputStream output;
    boolean	bConnected=false;
   static final String STR="finished";
    
    public GameStartC(GameFrame gameframe) 
    {
		this.gameframe=gameframe;
	}
	public void connect()
    {
    	try {
			s=new Socket("127.0.0.1",8888);
			input=new DataInputStream(s.getInputStream());
			output=new DataOutputStream(s.getOutputStream());
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
	public void updatePlayer(String s1,int y1,String s2,int y2,int y3)
	{
		int x1=0,x2=0;
		if(gameframe.manager.members.contains(s1)&&(gameframe.manager.members.contains(s2)))
		  {
		    x1=gameframe.manager.members.indexOf(s1);
	        x2=gameframe.manager.members.indexOf(s2);
		
		    gameframe.score[x1].setText("Score "+y1);
		    gameframe.score[x2].setText("Score "+y2);
		    gameframe.myscore.setText("Score "+y3);
		    gameframe.myscore.repaint();
		    gameframe.score[x1].repaint();
		    gameframe.score[x2].repaint();
		  }
	}
	public void run()
	{
		
		try{
		      while (bConnected)
		       {
				    int mark=input.readInt();
				    if(mark==1)
				     {
		    	       String str=input.readUTF().trim();
				       gameframe.notice.setText(str+" '   turn!");
				       gameframe.gameclient.currentplayer=str;
				       gameframe.gameclient.currentword=input.readInt();
				       if(gameframe.gameclient.currentword!=-1)
				       {
				         String[] s=gameframe.adddatabase.query(gameframe.gameclient.currentword);
					     if(gameframe.gameclient.currentplayer.equals(gameframe.gameclient.name))
						   gameframe.tf2.setText("your turn        "+s[0]);
					     if(!gameframe.gameclient.currentplayer.equals(gameframe.gameclient.name))
						   gameframe.tf2.setText("答案提示        "+s[1]);
					     gameframe.gameclient.currentword=-1;
				       }
						 
				       gameframe.creatTimer();
				       gameframe.notice.repaint();
				     }
				    if(mark==2)
				    {
				    	String host=input.readUTF().trim();
				    	gameframe.gameclient.winner =input.readUTF().trim();
				    	int score1=gameframe.loginframe.data.query(host);
				    	int score2=gameframe.loginframe.data.query(gameframe.gameclient.winner);
				    	int score3=gameframe.loginframe.data.query(gameframe.gameclient.name);
				        gameframe.tf2.setText("游戏已结束！");
				        if(gameframe.timer!=null)gameframe.timer.stop(); 
				        gameframe.notice.setText(gameframe.gameclient.winner+"猜中！！！");
				        Thread.sleep(3000);
				        if(gameframe.gameclient.currentplayer!=null&&
				          gameframe.gameclient.currentplayer.equals(gameframe.gameclient.name))
				          {
				           gameframe.guessdraw.output.writeInt(3);
				           gameframe.manager.output.writeUTF(STR);
				          }
				    	updatePlayer(host,score1,gameframe.gameclient.winner,score2,score3);
				    }
		       } 
		   }
			catch (IOException e) 
			{
				e.printStackTrace();
			}
		    catch (InterruptedException e)
		      {
				e.printStackTrace();
			  }
		
	}
	public void launchThread()
	{
		new Thread(this).start();
		
	}

}
