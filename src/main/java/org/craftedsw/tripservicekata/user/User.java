package org.craftedsw.tripservicekata.user;

import java.util.ArrayList;
import java.util.List;

public class User {

	private List<User> friends = new ArrayList<User>();

	public List<User> getFriends() {
		return friends;
	}

	public void setFriends(List<User> friends) {
		this.friends = friends;
	}

	public boolean areFriends(User loggedUser) {
		boolean isFriend = false;
		for (User friend : getFriends()) {
			if (friend.equals(loggedUser)) {
				isFriend = true;
				break;
			}
		}
		return isFriend;
	}
}
