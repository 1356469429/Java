package com.pactera.javaCVapi;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.imageio.ImageIO;

import com.mysql.jdbc.Connection;

public class MysqlTest {
	public static Connection conn ;
	public static PreparedStatement statement = null;
	public static void main(String[] args) throws Exception {
		new MysqlTest();
	}
	public MysqlTest() throws Exception{
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		conn = (Connection) DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/lmq", "root", "root");
		System.out.println("连接成功");
		insert("insert into test(p,i) values('test',?)");
		//getPhotoBytes("select * from test");
	}
public byte[] getPhotoBytes(String sql) throws Exception{
	ResultSet rs;
	statement = conn.prepareStatement(sql);  
	rs = statement.executeQuery(sql);
	while(rs.next()){
		System.out.println(rs.getString("p"));
		return rs.getBytes("i");
		/*InputStream in = new ByteArrayInputStream(rs.getBytes("i"));
		BufferedImage saves=ImageIO.read(in);
		ImageIO.write(saves, "jpg", new File("tmp\\mysql\\test.jpg"));*/
	}
	return null;
}
/**
 * @description 将图片进行存储mysql
 * @param sql
 * @throws Exception
 */
public static void insert(String sql) throws Exception{
	statement =conn.prepareStatement(sql);
	FileInputStream in=new FileInputStream("tmp\\input\\mysql.jpg");
	byte[] bytes=new byte[in.available()];
	in.read(bytes);
	statement.setBytes(1,bytes);
	statement.executeUpdate();
	System.out.println("插入成功");
}

}
