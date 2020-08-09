package com.bithumbhomework.member.event;

import org.springframework.context.ApplicationEvent;
import org.springframework.web.util.UriComponentsBuilder;

import com.bithumbhomework.member.entity.User;

public class OnUserJoinCompleteEvent extends ApplicationEvent {

//    private transient UriComponentsBuilder redirectUrl;
	private User user;

	public OnUserJoinCompleteEvent(User user) {
		super(user);
		this.user = user;
//        this.redirectUrl = redirectUrl;
	}

//    public UriComponentsBuilder getRedirectUrl() {
//        return redirectUrl;
//    }
//
//    public void setRedirectUrl(UriComponentsBuilder redirectUrl) {
//        this.redirectUrl = redirectUrl;
//    }

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
}
