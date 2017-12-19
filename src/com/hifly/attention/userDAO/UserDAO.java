/*package com.hifly.attention.userDAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hifly.attention.client.User;

public class UserDAO 
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	public UserDAO()
	{
	    String jdbc_driver = "com.mysql.jdbc.Driver";
	    String jdbc_url = "jdbc:mysql://127.0.0.1/drone_user";
		String user = "root";
		String password = "1234";

		try {
			// JDBC ����̹� �ε�
			Class.forName(jdbc_driver);
			// �����ͺ��̽� ���������� �̿��� Connection �ν��Ͻ� Ȯ��
			conn = DriverManager.getConnection(jdbc_url, user, password);

		} catch (Exception e) 
		{
			System.out.println(e);
		}
		
	}
	
	public User search(String userID, String userPW)
	{
		String sql = "select * from user where userID=? and userPW=?";
		User user = new User();
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPW);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				if(rs.getString(4).equals("true"))	//���� �α��� ���̸� ����
					return null;
				
				user.setUserID(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
				
		return user;
	}
	
	public void signin(String userID, String userPW, String email)
	{
		String sql = "insert into user values(?,?,?,?)";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			pstmt.setString(2, userPW);
			pstmt.setString(3, email);
			pstmt.setString(4, "false");
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void login(String userID)
	{
		String sql = "update user set login_check=? where userID=?";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "true");
			pstmt.setString(2, userID);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void logout(String userID)
	{
		String sql = "update user set login_check=? where userID=?";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "false");
			pstmt.setString(2, userID);
			pstmt.executeUpdate();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public UserDTO search(String userID)
	{
		String sql = "select * from user where userID=?";
		User user = new UserDTO();
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userID);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				user.setUserID(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setEmail(rs.getString(3));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public void end()
	{
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}*/