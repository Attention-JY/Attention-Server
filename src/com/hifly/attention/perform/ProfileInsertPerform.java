package com.hifly.attention.perform;

import java.net.Socket;
import java.util.UUID;

import com.hifly.attention.client.User;
import com.hifly.attention.dao.UserFriendsDAO;
import com.hifly.attention.dao.UserProfilesDAO;
import com.hifly.attention.debuger.Debuger;

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
		
		String split[] = bodyData.split(Protocol.SPLIT_MESSAGE);
		
		String uuid = split[0];
		String profile_url = split[1];
		
		//write File
		
		//���� ���� �ϴµ� ������ �� ���� �ְ� ���� �ϳ��� �� ���� �ִµ� �ϳ��� ���� url�̹Ƿ� update ���������
		if(UserProfilesDAO.getInstance().insertUserProfiles(uuid, profile_url) == false){
			UserProfilesDAO.getInstance().updateUserProfilesURL(uuid, profile_url);
		}
	}
}
