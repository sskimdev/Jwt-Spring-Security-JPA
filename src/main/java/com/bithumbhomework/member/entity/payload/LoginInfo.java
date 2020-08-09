package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

//import com.bithumbhomework.member.entity.DeviceType;
import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

public class LoginInfo {

	@NotBlank(message = "Login id cannot be blank")
	@ApiModelProperty(value = "Login Id", required = true, dataType = "string", allowableValues = "Non empty string")
	private String loginId;

//    @NotNull(message = "Device type cannot be null")
//    @ApiModelProperty(value = "Device type Android/iOS", required = true, dataType = "string", allowableValues =
//            "DEVICE_TYPE_ANDROID, DEVICE_TYPE_IOS")
//    private DeviceType deviceType;

	@NullOrNotBlank(message = "Device notification token can be null but not blank")
	@ApiModelProperty(value = "Device notification id", dataType = "string", allowableValues = "Non empty string")
	private String notificationToken;

	public LoginInfo() {
	}

	public LoginInfo(String loginId, String notificationToken) {
		this.loginId = loginId;
//        this.deviceType = deviceType;
		this.notificationToken = notificationToken;
	}

//    public String getDeviceId() {
//        return deviceId;
//    }
//
//    public void setDeviceId(String deviceId) {
//        this.deviceId = deviceId;
//    }
//
////    public DeviceType getDeviceType() {
////        return deviceType;
////    }
////
////    public void setDeviceType(DeviceType deviceType) {
////        this.deviceType = deviceType;
////    }
//
//    public String getNotificationToken() {
//        return notificationToken;
//    }
//
//    public void setNotificationToken(String notificationToken) {
//        this.notificationToken = notificationToken;
//    }
//
//    @Override
//    public String toString() {
//        return "LoginInfo{" +
//                "deviceId='" + deviceId + '\'' +
////                ", deviceType=" + deviceType +
//                ", notificationToken='" + notificationToken + '\'' +
//                '}';
//    }

	/**
	 * @return the loginId
	 */
	public String getLoginId() {
		return loginId;
	}

	/**
	 * @param loginId the loginId to set
	 */
	public void setLoginId(String loginId) {
		this.loginId = loginId;
	}

	/**
	 * @return the notificationToken
	 */
	public String getNotificationToken() {
		return notificationToken;
	}

	/**
	 * @param notificationToken the notificationToken to set
	 */
	public void setNotificationToken(String notificationToken) {
		this.notificationToken = notificationToken;
	}

	@Override
	public String toString() {
		return "LoginInfo [loginId=" + loginId + ", notificationToken=" + notificationToken + "]";
	}
}
