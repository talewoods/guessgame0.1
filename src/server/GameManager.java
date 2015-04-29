package server;

import java.io.IOException;
import java.util.Random;

public class GameManager implements Runnable 
{
	Server server=null;
	Random ran=new Random();
	int num=0;
	boolean started=false;
	int begin;
	String finished=null;
    GameManager(Server server)
    {
    	this.server=server;
    	started=true;
    }
	
    public boolean judge()
    {
    	 begin =0;
         for(int i=0;i<server.gamestarts.size();i++)
             {
    	       if(server.find(i))begin++;
    	       if(begin>1)break;
              }
         System.out.println(begin+"ppppppppp");
         if(begin>1)return true; else return false;
    }
	public void run() 
	{
       
			while(started)
				{
				  
				          boolean start=judge();
				          try{
		                       while(start)
	                             {
		    	                       int max=server.adddatabase.querynum();
		    	                       server.currentword=ran.nextInt(max)+1;
		    	                       String[] ss=server.adddatabase.query(server.currentword);
		    	                       server.currentans=ss[0];
		    	            
		    	            
		    	                       if(num>=server.gamestarts.size())num=0;
		                               while(!server.find(num))
		                                 { 
		                    	           num++; 
		                    	           if(num>=server.gamestarts.size())num=0;
		                                 }
		                               server.currentplay=server.players.get(num);
		                               String str=server.currentplay.name;
		                    
		                    
		                    
		                               for(int i=0;i<server.gamestarts.size();i++)
		                                {
		                                  if(!server.find(i))continue;
		        	                      server.gamestarts.get(i).output.writeInt(1);
		        	                      server.gamestarts.get(i).output.writeUTF(str);
		        	                      server.gamestarts.get(i).output.writeInt(server.currentword);
		                                }
		                    
		                    
		                    
			                            finished=server.currentplay.input.readUTF().trim();
			                            server.winner=null;
			                            server.currentword=-1;
			                            finished=null;
			                            num++; 
			                            start=judge();
			                 
			                            for(int i=0;i<server.gamestarts.size();i++)
			                             {
			                	            if(server.gamestarts.get(i).player.death)
			                	             {
			                		          server.gamestarts.remove(i);
			                		          server.chats.remove(i);
			                		          server.drawlocals.remove(i);
			                		          server.players.remove(i);
			                	             }
			                             }
	                               }      
	                           }
				            catch(IOException e)
                               {
				    	          System.out.println("ÈËÊı²»¹»Å¶ £¡");
                               }
				  }
		
	     }
}
