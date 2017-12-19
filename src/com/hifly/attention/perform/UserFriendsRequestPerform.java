package com.hifly.attention.perform;

import java.util.Iterator;

import com.hifly.attention.client.User;
import com.hifly.attention.debuger.Debuger;
import com.hifly.attention.serverCore.Server;
import com.hifly.attention.serverCore.SignalKey;
import com.hifly.attention.serverCore.SignalPerform;
import com.hifly.attention.userDAO.UserDAO;
import com.hifly.attention.userDAO.UserFriendsDAO;
import com.hifly.attention.values.Protocol;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class UserFriendsRequestPerform implements SignalPerform {
	
	private User user;
	
	public UserFriendsRequestPerform(User user) {
		this.user = user;

	}
	
	@Override
	public void performAction(SignalKey signalKey) {
		
		String bodyData = signalKey.getBodyData();
		Debuger.log(this.toString(), bodyData);
		
		String split[] = bodyData.split(Protocol.SPLIT_MESSAGE);
		StringBuilder sb = new StringBuilder("");
		sb.append(Protocol.USER_FRIENDS_RESPONSE_PROTOCOL + Protocol.SPLIT_MESSAGE); //Make header
		Debuger.log(toString(), "ģ�� �� : " + Integer.toString(Server.users.size()));
		
		for(int i=0; i<split.length; i++) {
			Iterator<String> it = Server.users.keySet().iterator();
			while(it.hasNext()) {
				String key = it.next();
				User muser = Server.users.get(key);

				if(muser.getTel().equals(split[i]) && !user.getTel().equals(split[i])) { //���� �ƴϱ��� ģ�����	
					
					String userUuid = user.getUuid();					
					String mUuid = muser.getUuid();
					String mName = muser.getName();
					String mTel = muser.getTel();
					String mStateMessage = muser.getStateMessage();

					sb.append(userUuid + Protocol.SPLIT_MESSAGE + mName + Protocol.SPLIT_MESSAGE + mTel
					+ Protocol.SPLIT_MESSAGE + mStateMessage + Protocol.SPLIT_MESSAGE);
					
					UserFriendsDAO.getInstance().insertUserFriends(userUuid, mUuid);
				}
			}
		}
		
		Debuger.log(this.toString(), "�����ݽ� �޽���   " + sb.toString());
		user.sendUTF(sb.toString());
	}
}