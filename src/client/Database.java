package client;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {
	String driver="com.mysql.jdbc.Driver";
	String url= "jdbc:mysql://localhost:3306/student"; 
	String user="root";
	String pass="123asd";
	private Connection con;
	Boolean flag1=false,flag2=false;
	
	public void init()
	{
		if(con!=null)return;
		try{
		     Class.forName(driver);
		     con=DriverManager.getConnection(url,user,pass);
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void close()throws Exception
	{
		if(con!=null)con.close();
	}
	public void initTable()throws Exception
	{
		String sql="create table member(name varchar(120),password varchar(120),score bigint)";
		Statement stmt=con.createStatement();
		stmt.executeUpdate(sql);
		stmt.close();
    }
	
	public void insert(String name1,String key1,long score)
	{
		String sql="insert into member (name,password,score) values(?,?,?)";
		try{
			PreparedStatement statement=con.prepareStatement(sql);
			statement.setString(1,name1);
			statement.setString(2, key1);
			statement.setLong(3, score);
			statement.executeUpdate();
			statement.close();
		   }
		   catch(SQLException e)
		   {
			  e.printStackTrace(); 
		   }
	}
	public String query(String name1,String key1)
	{
		String sql="select * from  member";
		PreparedStatement statement=null;
		ResultSet rs=null;
		try
		 {
		   statement=con.prepareStatement(sql);
		   rs=statement.executeQuery();
		   while(rs.next())
		   {
			   String ss1=rs.getString("name").trim();
			   if(ss1.equals(name1))
			     {
				    String ss2=rs.getString("password").trim();
				    if(ss2.equals(key1)) return "**";  
				    return "*";
			     }
		   }
		   return "";
		 }
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		finally{
			if(rs!=null)
				try {
					 rs.close();statement.close();
				    } 
			    catch (SQLException e)
			        {
					  e.printStackTrace();
				    }
		}
		return "";
	}
    
	public int query(String name1)
	{
		String sql="select * from member where name=?";
		int ans=0;
		try{
			PreparedStatement statement=con.prepareStatement(sql);
			ResultSet rs=null;
			statement.setString(1, name1);
			rs=statement.executeQuery();
			rs.next();
			ans=rs.getInt("score");
			statement.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return ans;
	}
	  public void update(String name1,String name2)
	    {
	    	String sql="update member set name=?where name=?";
	    	try{
	    		PreparedStatement statement=con.prepareStatement(sql);
	    		statement.setString(1,name2);
	    		statement.setString(2,name1);
	    		statement.executeUpdate();
	    		statement.close();
	    	   }
	    	   catch(SQLException e)
	    	   {
	    		  e.printStackTrace(); 
	    	   }
	    	
	    	
	    }
	  public void update(String name1,String key1,int mark)
	    {
	    	String sql="update member set password=? where name=?";
	    	try{
	    		PreparedStatement statement=con.prepareStatement(sql);
	    		statement.setString(2,name1);
	    		statement.setString(1,key1);
	    		statement.executeUpdate();
	    		statement.close();
	    	   }
	    	   catch(SQLException e)
	    	   {
	    		  e.printStackTrace(); 
	    	   }
	    	
	    	
	    }
	  
	  public void updata(String name,int score)
	  {
		  String sql="update member set score=? where name=?";
		  try{
			  PreparedStatement statement=con.prepareStatement(sql);
			  statement.setInt(1,score);
			  statement.setString(2,name);
			  statement.executeUpdate();
			  statement.close();
		     }
		  catch(SQLException e)
		  {
			  e.printStackTrace();
		  }
		 
	  }
	  
	  public void delete(String name1)
	    {
	    	String sql="delete from member where name=?";
	    	try{
	    		PreparedStatement statement=con.prepareStatement(sql);
	    		statement.setString(1, name1);
	    		statement.executeUpdate();
	    		statement.close();
	    	   }
	    	   catch(SQLException e)
	    	   {
	    		  e.printStackTrace(); 
	    	   }
	    	
	    }
	  public static void main(String[] args)
	  {
		  Database data=new Database();
		  data.init();
		  data.updata("talewoods",100);
		  data.updata("marui",100);
	  }

}
