package client;
import java.sql.*;

public class AddDatabase {
	
	String url="jdbc:mysql://localhost:3306/student";
	String user="root";
	String password="123asd";
	String driver="com.mysql.jdbc.Driver";
	java.sql.Connection con=null;
	int num=2;
	
	public void init()
	{
		if(con!=null) return;
		try 
		{
			Class.forName(driver);
			con=DriverManager.getConnection(url, user, password);
			
		} 
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void creatTable()
	{
		String sql="create table words(id bigint,word varchar(120),message varchar(120))";
		try{
		    Statement statement=con.createStatement();
		    statement.executeUpdate(sql);
		    statement.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}
	
	public void insert(int id,String word,String message)
	{
		String sql="insert into words (id,word,message) values (?,?,?)";
		try{
		    PreparedStatement statement=con.prepareStatement(sql);
		    statement.setInt(1, id);
		    statement.setString(2, word);
		    statement.setString(3, message);
		    statement.executeUpdate();
		    statement.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
	}

	public String[]  query(int id)
	{
		String sql="select * from words where id=?";
		ResultSet rs=null;
		String[] str=new String[2];
		int k=0;
		try{
		    PreparedStatement statement=con.prepareStatement(sql);
		    statement.setInt(1,id);
		    rs=statement.executeQuery();
		    rs.next();
		    str[0]=rs.getString("word");
		    str[1]=rs.getString("message");
		    statement.close();
		    rs.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return str;
	}
	
	public boolean query(String s)
	{
		String sql="select * from words where word=?";
		ResultSet rs=null;
		int len=0;
		PreparedStatement statement=null;
		try{
			statement=con.prepareStatement(sql);
			statement.setString(1,s);
			rs=statement.executeQuery();
		    while(rs.next())
		    {
		       int a=rs.getInt("id");
		       return true;
		    }
			rs.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		finally{
			    try {
					statement.close();
				    rs.close();
				   } 
			    catch (SQLException e)
			    {
					e.printStackTrace();
				}
		       }
		return false; 
	}
	
	public void close()
	{
		if(con!=null)
			try {
				 con.close();
			    } 
		   catch (SQLException e)
		       {
				e.printStackTrace();
			   }
	}
	
	public int querynum()
	{   
		int total=0;
		String sql="select count(*) as total from words";
		try{
		     Statement statement=con.createStatement();
		     ResultSet rs=statement.executeQuery(sql);
		     if(rs.next()){ total=rs.getInt("total");}
		     statement.close();
		   }
		catch(SQLException e)
		{
			e.printStackTrace();
		}
		return total;
	}
	public static void main(String[] args)
	{
		AddDatabase add=new AddDatabase();
		add.init();
		add.creatTable();
		add.insert(add.querynum(),"星星","在夜晚出现");
		add.insert(add.querynum(), "好渴哦！","");
		String[] str=add.query(1);
		System.out.println(str[0]+" "+str[1]);
		add.close();
	}

}
