/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bithumbhomework.member.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bithumbhomework.member.entity.UserLogin;
import com.bithumbhomework.member.entity.payload.LoginInfo;
//import com.bithumbhomework.member.entity.token.RefreshToken;
import com.bithumbhomework.member.exception.TokenRefreshException;
import com.bithumbhomework.member.repository.UserLoginRepository;

import java.util.Optional;

@Service
public class UserLoginService {

	private final UserLoginRepository userLoginRepository;

	@Autowired
	public UserLoginService(UserLoginRepository userLoginRepository) {
		this.userLoginRepository = userLoginRepository;
	}

	/**
	 * Find the user login info by user id
	 */
	public Optional<UserLogin> findByUserId(Long userId) {
		return userLoginRepository.findByUserId(userId);
	}

//    /**
//     * Find the user device info by refresh token
//     */
//    public Optional<UserLogin> findByRefreshToken(RefreshToken refreshToken) {
//        return userLoginRepository.findByRefreshToken(refreshToken);
//    }

	/**
	 * Creates a new user login and set the user to the current user login
	 */
	public UserLogin createUserLogin(LoginInfo loginInfo) {
		UserLogin userLogin = new UserLogin();
		userLogin.setLoginId(loginInfo.getLoginId());
//        userDevice.setDeviceType(deviceInfo.getDeviceType());
		userLogin.setNotificationToken(loginInfo.getNotificationToken());
		userLogin.setIsRefreshActive(true);
		return userLogin;
	}

//    /**
//     * Check whether the user device corresponding to the token has refresh enabled and
//     * throw appropriate errors to the client
//     */
//    void verifyRefreshAvailability(RefreshToken refreshToken) {
//        UserLogin userDevice = findByRefreshToken(refreshToken)
//                .orElseThrow(() -> new TokenRefreshException(refreshToken.getToken(), "No device found for the matching token. Please login again"));
//
//        if (!userDevice.getRefreshActive()) {
//            throw new TokenRefreshException(refreshToken.getToken(), "Refresh blocked for the device. Please login through a different device");
//        }
//    }
}
