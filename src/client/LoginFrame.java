package client;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.*;
import java.io.IOException;

import javax.swing.*;

public class LoginFrame extends JFrame {
	
	static final int X=500;
	static final int Y=200;
	static final int Size=400;
	JTextField tf=null;
	JPasswordField tf2=null;
	ImageIcon imag=new ImageIcon("loginbackground.gif");
	Database data;
	GameFrame gameframe;
 
	public LoginFrame()
	{
		try 
		{
			data=new Database();  
			data.init();
	    } 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	public LoginFrame(GameFrame gameframe) 
	{
		this();
		this.gameframe=gameframe;
	}

	public void launchFrame()
	{
		setLocation(X,Y);
		setSize(Size,Size);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
			  try {
				     data.close();
			      } 
			  catch (Exception e1)
			  {
				e1.printStackTrace();
			   }
			  
			  System.exit(0);
			}
			
		});
		Container ctn=this.getContentPane();
		ctn.setLayout(null);
		ctn.setBackground(Color.pink);;
		
		JLabel label=new JLabel(imag);
		label.setBounds(0,0,400,180);
		ctn.add(label);
		
		JLabel user=new JLabel("�û���");
		user.setBounds(50,200,50,40);
		ctn.add(user);
		
		tf=new JTextField(250);
		tf.setBounds(100,200,250,35);
		ctn.add(tf);
		
		JLabel key=new JLabel("����");
		key.setBounds(50,250,50,35);
		ctn.add(key);
		
		tf2=new JPasswordField(250);
		tf2.setBounds(100,250,250,35);
		ctn.add(tf2);
		
		JButton login=new JButton("��¼");
		login.setBounds(150,300,60,35);
		ctn.add(login);
		
		JButton register=new JButton("ע��");
		register.setBounds(250,300,60,35);
		ctn.add(register);
		
		JLabel relabel=new JLabel("��δע�᣿");
		relabel.setBounds(200,350,80,30);
		ctn.add(relabel);
		
		setVisible(true);
		
		login.addActionListener(new ActionListener()
				{
					public void actionPerformed(ActionEvent e) 
					{
					 String name1=tf.getText().trim();
					 System.out.println(name1);
					 String key1=new String(tf2.getPassword());
					 String re=data.query(name1,key1);
					 if(re.length()==0){
						               JOptionPane.showMessageDialog(null,"�޴��û���","����",JOptionPane.WARNING_MESSAGE);
						               tf.setText("");
						               tf2.setText("");
						               
					                  }
					 if(re.length()==1){
						               JOptionPane.showMessageDialog(null, "������󣡣�","����",JOptionPane.WARNING_MESSAGE);
						               tf2.setText("");
					                   }
					 if(re.length()==2){
						                 setVisible(false);
						                 gameframe.gameclient.name=name1;
						                 gameframe.gameclient.score=data.query(gameframe.gameclient.name);
						                 gameframe.launchFrame();
						                 try {
						                	   gameframe.manager.output.writeInt(1);
											   gameframe.manager.output.writeUTF(name1);
											   gameframe.gamestart.connect();
											   gameframe.gamestart.launchThread();
											   
											   System.out.println("�ѷ��ͣ�������");
										     }
						                 catch (IOException e1) 
						                     {
											    e1.printStackTrace();
										     }
					                   }
						
					}
				});
		
		register.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e) 
			{
			  String name1=tf.getText().trim();
			  String  key1=new String (tf2.getPassword());
			  String re=data.query(name1,key1);
			  if(re.length()==0){
				                  data.insert(name1,key1,100);
				                  JOptionPane.showMessageDialog(null,"ע��ɹ� !");
				                  setVisible(false);
				                  gameframe.gameclient.name=name1;
				                  gameframe.gameclient.score=data.query(gameframe.gameclient.name);
					              gameframe.launchFrame();
					              try {
				                	   gameframe.manager.output.writeInt(1);
									   gameframe.manager.output.writeUTF(name1);
									   gameframe.gamestart.connect();
									   gameframe.gamestart.launchThread();
									   
									   System.out.println("�ѷ��ͣ�������");
								     }
				                 catch (IOException e1) 
				                     {
									    e1.printStackTrace();
								     }
			                    }
			  if(re.length()>=1){
				                  JOptionPane.showMessageDialog(null,"�û��Ѵ���  �� �뻻���������֣�");
			                    }
				  
				
			}
			
		});
			
		
	}
	

}
