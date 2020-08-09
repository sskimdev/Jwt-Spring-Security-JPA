package com.bithumbhomework.member.event;

import org.springframework.context.ApplicationEvent;
import com.bithumbhomework.member.entity.User;

public class OnUserJoinCompleteEvent extends ApplicationEvent {

	private User user;

	public OnUserJoinCompleteEvent(User user) {
		super(user);
		this.user = user;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
