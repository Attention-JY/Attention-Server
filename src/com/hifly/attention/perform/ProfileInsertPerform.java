package com.hifly.attention.perform;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.Socket;
import java.util.UUID;

import com.hifly.attention.client.User;
import com.hifly.attention.dao.UserFriendsDAO;
import com.hifly.attention.dao.UserProfilesDAO;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.serverCore.MessageServer;
import com.hifly.attention.serverCore.SignalKey;
import com.hifly.attention.serverCore.SignalPerform;
import com.hifly.attention.values.Protocol;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileInsertPerform implements SignalPerform {
	
	private User user;
	
	public ProfileInsertPerform(User user) {
		this.user = user;
	}
	
	@Override
	public void performAction(SignalKey signalKey) {
		String bodyData = signalKey.getBodyData();
		Debuger.log(this.toString(), bodyData);
		Debuger.log(this.toString(), user.toString());
		
		Debuger.log(this.toString(), MessageServer.users.get(user.getUuid()).toString());
		//File Socket ���� + ��Ʈ�� ����
		user.setFileSocket(MessageServer.users.get(user.getUuid()).getFileSocket());
		
		String split[] = bodyData.split(Protocol.SPLIT_MESSAGE);
		
		String uuid = split[0];
		String profile_name = split[1];
		String profile_url = "C:/Users/hscom-018/git/Attention-Server/profiles";
		//write File
		
		Debuger.log(this.toString(), user.toString());
		user.profileSend(uuid, profile_url, profile_name);
		Debuger.log(this.toString(), "������ ������ ���� �� �������� ������ ������ ����");
		
		//���� ���� �ϴµ� ������ �� ���� �ְ� ���� �ϳ��� �� ���� �ִµ� �ϳ��� ���� url�̹Ƿ� update ���������
		if(UserProfilesDAO.getInstance().insertUserProfiles(uuid, profile_url, profile_name) == false) {
			UserProfilesDAO.getInstance().updateUserProfilesURL(uuid, profile_url);
		}
	}
}