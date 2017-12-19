package com.hifly.attention.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.hifly.attention.debuger.Debuger;

public class RoomUsersDAO
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private static RoomUsersDAO instance;
		
	synchronized public static RoomUsersDAO getInstance(){
		if(instance == null){
			instance = new RoomUsersDAO();
		}
		return instance;
	}
	
	private RoomUsersDAO()
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
	
	public void insertRoomUsers(String room_uuid, String user_uuid)
	{
		String sql = "insert into room_users values(?,?)";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_uuid);
			pstmt.setString(2, user_uuid);
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