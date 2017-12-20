package com.hifly.attention.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;

public class UserFriendsDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private static UserFriendsDAO instance;
		
	synchronized public static UserFriendsDAO getInstance(){
		if(instance == null){
			instance = new UserFriendsDAO();
		}
		return instance;
	}
	
	private UserFriendsDAO()
	{
	    String jdbc_driver = "com.mysql.jdbc.Driver";
	    String jdbc_url = "jdbc:mysql://127.0.0.1/attention";
		String user = "root";
		String password = "1234";

		try {
			// JDBC ����̹� �ε�
			Class.forName(jdbc_driver);
			// �����ͺ��̽� ���������� �̿��� Connection �ν��Ͻ� Ȯ��
			conn = DriverManager.getConnection(jdbc_url, user, password);

		} catch (ClassNotFoundException e) 
		{
			Debuger.printError(e);
		} catch (SQLException e)
		{
			Debuger.printError(e);
		}
	}
	
	public String[] getFirstRoomUuid(String p2p_chat_uuid)
	{
		String sql = "select * from user_friends where p2p_chat_uuid=?";
		String[] result = new String[2];
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p2p_chat_uuid);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				result[0] = rs.getString("uuid");
				result[1] = rs.getString("friend_uuid");
			}
		} catch (SQLException e) {
			Debuger.printError(e);
		}
		return result;
	}
	
	public void insertUserFriends(String uuid, String friend_uuid)
	{
		String sql = "insert into user_friends(uuid, friend_uuid) values(?,?)";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uuid);
			pstmt.setString(2, friend_uuid);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			Debuger.printError(e);
		}
	}
	public String getP2PChatUuid(String uuid, String friend_uuid)
	{
		String sql = "select p2p_chat_uuid from user_friends where uuid=? and friend_uuid=?";
		
		String result = null;
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, uuid);
			pstmt.setString(2, friend_uuid);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				result = rs.getString("p2p_chat_uuid");
			}
		} catch (SQLException e) {
			Debuger.printError(e);
		}
		return result;
	}
	
	public void insertP2PChatUuid(String uuid, String friend_uuid, String p2pChatUuid) {
		
		String sql = "update user_friends set p2p_chat_uuid=? where uuid=? and friend_uuid=?";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, p2pChatUuid);
			pstmt.setString(2, uuid);
			pstmt.setString(3, friend_uuid);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			Debuger.printError(e);
		}
	}
	
	public void end()
	{
		try {
			rs.close();
			pstmt.close();
			conn.close();
		} catch (SQLException e) {
			Debuger.printError(e);
		}
	}

}