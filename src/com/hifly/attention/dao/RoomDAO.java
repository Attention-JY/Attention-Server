package com.hifly.attention.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import com.hifly.attention.client.Room;
import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;

public class RoomDAO 
{
	private Connection conn = null;
	private PreparedStatement pstmt = null;
	private ResultSet rs = null;
	
	private static RoomDAO instance;
		
	synchronized public static RoomDAO getInstance(){
		if(instance == null){
			instance = new RoomDAO();
		}
		return instance;
	}
	
	private RoomDAO()
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

		} catch (ClassNotFoundException e) {
			Debuger.printError(e);
		} catch (SQLException e) {
			Debuger.printError(e);
		}
	}	
	
	public void updateTime(String time, String room_uuid)
	{
		String sql = "update room set time=? where room_uuid=?";
		
		// select�� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.	
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, time);
			pstmt.setString(2, room_uuid);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			Debuger.printError(e);
		}
	}
	
	public Room getRoom(String room_uuid)
	{
		String sql = "select * from room where room_uuid=?";
		Room room = new Room();
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_uuid);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				room.setRoomUuid(rs.getString("room_uuid"));
				room.setChatContent(rs.getString("content"));
				room.setTime(rs.getString("time"));
			}
		} catch (SQLException e) {
			Debuger.printError(e);
		}
		return room;
	}
	
	
	public HashMap<String, Room> getRooms(){
		
		String sql = "select * from room";
		HashMap<String, Room> rooms = new HashMap<String, Room>();
			
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next())
			{
				Room room = new Room();
				room.setRoomUuid(rs.getString("room_uuid"));
				room.setChatContent(rs.getString("content"));
				room.setTime(rs.getString("time"));

				rooms.put(room.getRoomUuid(), room);
			}
		} catch (SQLException e) {
			Debuger.printError(e);
		}
		return rooms;
	}
	
	public boolean insertRoom(String room_uuid, String time, String sender_name, String chat_content, String sender_uuid)
	{
		String sql = "insert into room values(?,?,?,?,?)";
		
		// select �� �����ϸ� ������������ ResultSet Ŭ������ �ν��Ͻ��� ���ϵ�.		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, room_uuid);
			pstmt.setString(2, time);
			pstmt.setString(3, sender_name);
			pstmt.setString(4, chat_content);
			pstmt.setString(5, sender_uuid);
			
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			Debuger.printError(e);
			return false;
		}
		return true;
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