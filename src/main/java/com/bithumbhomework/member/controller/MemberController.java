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
package com.bithumbhomework.member.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;

import com.bithumbhomework.member.annotation.CurrentUser;
import com.bithumbhomework.member.entity.CustomUserDetails;
import com.bithumbhomework.member.entity.payload.ApiResponse;
import com.bithumbhomework.member.entity.payload.JwtAuthenticationResponse;
import com.bithumbhomework.member.entity.payload.LoginRequest;
//import com.bithumbhomework.member.entity.payload.PasswordResetLinkRequest;
//import com.bithumbhomework.member.entity.payload.PasswordResetRequest;
import com.bithumbhomework.member.entity.payload.JoinRequest;
import com.bithumbhomework.member.entity.payload.TokenRefreshRequest;
//import com.bithumbhomework.member.entity.token.EmailVerificationToken;
import com.bithumbhomework.member.entity.token.RefreshToken;
//import com.bithumbhomework.member.event.OnGenerateResetLinkEvent;
//import com.bithumbhomework.member.event.OnRegenerateEmailVerificationEvent;
import com.bithumbhomework.member.event.OnUserAccountChangeEvent;
import com.bithumbhomework.member.event.OnUserRegistrationCompleteEvent;
import com.bithumbhomework.member.exception.InvalidTokenRequestException;
import com.bithumbhomework.member.exception.PasswordResetException;
import com.bithumbhomework.member.exception.PasswordResetLinkException;
import com.bithumbhomework.member.exception.TokenRefreshException;
import com.bithumbhomework.member.exception.UserLoginException;
import com.bithumbhomework.member.exception.UserJoinException;
import com.bithumbhomework.member.security.JwtTokenProvider;
import com.bithumbhomework.member.service.MemberAuthService;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("${rest.api.ver}/member")
@Api(value = "Authorization Rest API", description = "회원가입, 로그인, 회원 조회")

public class MemberController {

    private static final Logger logger = Logger.getLogger(MemberController.class);
    private final MemberAuthService memberService;
    private final JwtTokenProvider tokenProvider;
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public MemberController(MemberAuthService memberService, JwtTokenProvider tokenProvider, ApplicationEventPublisher applicationEventPublisher) {
        this.memberService = memberService;
        this.tokenProvider = tokenProvider;
        this.applicationEventPublisher = applicationEventPublisher;
    }

//    /**
//     * Checks is a given email is in use or not.
//     */
//    @ApiOperation(value = "Checks if the given email is in use")
//    @GetMapping("/checkEmailInUse")
//    public ResponseEntity checkEmailInUse(@ApiParam(value = "Email id to check against") @RequestParam("email") String email) {
//        Boolean emailExists = memberService.emailAlreadyExists(email);
//        return ResponseEntity.ok(new ApiResponse(true, emailExists.toString()));
//    }
//
//    /**
//     * Checks is a given username is in use or not.
//     */
//    @ApiOperation(value = "Checks if the given username is in use")
//    @GetMapping("/checkUsernameInUse")
//    public ResponseEntity checkUsernameInUse(@ApiParam(value = "Username to check against") @RequestParam(
//            "username") String username) {
//        Boolean usernameExists = memberService.usernameAlreadyExists(username);
//        return ResponseEntity.ok(new ApiResponse(true, usernameExists.toString()));
//    }
    
    
    /**
     * 1. 회원가입 URI는 다음과 같습니다. : /v1/member/join
     * 2. 회원가입 시 필요한 정보는 ID, 비밀번호, 사용자이름 입니다.
     * 3. ID는 반드시 email 형식이어야 합니다.
     * 4. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.
     * 5. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다.
     */
//    @PostMapping("/register")
    @PostMapping("/join")
    @ApiOperation(value = "회원 가입")
    public ResponseEntity joinUser(@ApiParam(value = "The JoinRequest payload") @Valid @RequestBody JoinRequest joinRequest) {

        return memberService.joinUser(joinRequest)
                .map(user -> {
//                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
//                    OnUserRegistrationCompleteEvent onUserRegistrationCompleteEvent = new OnUserRegistrationCompleteEvent(user);
//                    applicationEventPublisher.publishEvent(onUserRegistrationCompleteEvent);
                    logger.info("Registered User returned [API[: " + user);
                    return ResponseEntity.ok(new ApiResponse(true, "회원가입이 성공하였습니다."));
                })
                .orElseThrow(() -> new UserJoinException(joinRequest.getEmail(), "Missing user object in database"));
    }


    /**
     * 1. 로그인 URI는 다음과 같습니다. : /v1/member/login
     * 2. 사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다
     * 3. ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면 로그인이 되었다는 응답으로 AccessToken을 제공합니다
     */
    @PostMapping("/login")
    @ApiOperation(value = "회원 로그인")
    public ResponseEntity authenticateUser(@ApiParam(value = "The LoginRequest payload") @Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = memberService.authenticateUser(loginRequest)
                .orElseThrow(() -> new UserLoginException("Couldn't login user [" + loginRequest + "]"));

        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();
        logger.info("Logged in User returned [API]: " + customUserDetails.getUsername());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return memberService.createAndPersistRefreshTokenForDevice(authentication, loginRequest)
                .map(RefreshToken::getToken)
                .map(refreshToken -> {
                    String jwtToken = memberService.generateToken(customUserDetails);
                    return ResponseEntity.ok(new JwtAuthenticationResponse(jwtToken, refreshToken, tokenProvider.getExpiryDuration()));
                })
                .orElseThrow(() -> new UserLoginException("Couldn't create refresh token for: [" + loginRequest + "]"));
    }
    
    
    /**
     * 1. 회원정보 조회 URI는 다음과 같습니다. : /v1/member/info
     * 2. 로그인이 된 사용자에 대해서는 사용자이름, Email, 직전 로그인 일시를 제공합니다.
     * 3. 로그인이 안된 사용자는 HTTP Status Code를 401 (Unauthorized)로 응답합니다.
     */
    @GetMapping("/info")
//    @PreAuthorize("hasRole('USER')")
    @ApiOperation(value = "회원정보 조회")
    public ResponseEntity getUserProfile(@CurrentUser CustomUserDetails currentUser) {
        logger.info(currentUser.getEmail());
        return ResponseEntity.ok("Hello. This is about me. " + currentUser.getEmail());
    }



//    /**
//     * Receives the reset link request and publishes an event to send email id containing
//     * the reset link if the request is valid. In future the deeplink should open within
//     * the app itself.
//     */
//    @PostMapping("/password/resetlink")
//    @ApiOperation(value = "Receive the reset link request and publish event to send mail containing the password " +
//            "reset link")
//    public ResponseEntity resetLink(@ApiParam(value = "The PasswordResetLinkRequest payload") @Valid @RequestBody PasswordResetLinkRequest passwordResetLinkRequest) {
//
//        return memberService.generatePasswordResetToken(passwordResetLinkRequest)
//                .map(passwordResetToken -> {
//                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/password/reset");
//                    OnGenerateResetLinkEvent generateResetLinkMailEvent = new OnGenerateResetLinkEvent(passwordResetToken,
//                            urlBuilder);
//                    applicationEventPublisher.publishEvent(generateResetLinkMailEvent);
//                    return ResponseEntity.ok(new ApiResponse(true, "Password reset link sent successfully"));
//                })
//                .orElseThrow(() -> new PasswordResetLinkException(passwordResetLinkRequest.getEmail(), "Couldn't create a valid token"));
//    }
//
//    /**
//     * Receives a new passwordResetRequest and sends the acknowledgement after
//     * changing the password to the user's mail through the event.
//     */
//
//    @PostMapping("/password/reset")
//    @ApiOperation(value = "Reset the password after verification and publish an event to send the acknowledgement " +
//            "email")
//    public ResponseEntity resetPassword(@ApiParam(value = "The PasswordResetRequest payload") @Valid @RequestBody PasswordResetRequest passwordResetRequest) {
//
//        return memberService.resetPassword(passwordResetRequest)
//                .map(changedUser -> {
//                    OnUserAccountChangeEvent onPasswordChangeEvent = new OnUserAccountChangeEvent(changedUser, "Reset Password",
//                            "Changed Successfully");
//                    applicationEventPublisher.publishEvent(onPasswordChangeEvent);
//                    return ResponseEntity.ok(new ApiResponse(true, "Password changed successfully"));
//                })
//                .orElseThrow(() -> new PasswordResetException(passwordResetRequest.getToken(), "Error in resetting password"));
//    }

//    /**
//     * Confirm the email verification token generated for the user during
//     * registration. If token is invalid or token is expired, report error.
//     */
//    @GetMapping("/registrationConfirmation")
//    @ApiOperation(value = "Confirms the email verification token that has been generated for the user during registration")
//    public ResponseEntity confirmRegistration(@ApiParam(value = "the token that was sent to the user email") @RequestParam("token") String token) {
//
//        return memberService.confirmEmailRegistration(token)
//                .map(user -> ResponseEntity.ok(new ApiResponse(true, "User verified successfully")))
//                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", token, "Failed to confirm. Please generate a new email verification request"));
//    }

//    /**
//     * Resend the email registration mail with an updated token expiry. Safe to
//     * assume that the user would always click on the last re-verification email and
//     * any attempts at generating new token from past (possibly archived/deleted)
//     * tokens should fail and report an exception.
//     */
//    @GetMapping("/resendRegistrationToken")
//    @ApiOperation(value = "Resend the email registration with an updated token expiry. Safe to " +
//            "assume that the user would always click on the last re-verification email and " +
//            "any attempts at generating new token from past (possibly archived/deleted)" +
//            "tokens should fail and report an exception. ")
//    public ResponseEntity resendRegistrationToken(@ApiParam(value = "the initial token that was sent to the user email after registration") @RequestParam("token") String existingToken) {
//
//        EmailVerificationToken newEmailToken = memberService.recreateRegistrationToken(existingToken)
//                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "User is already registered. No need to re-generate token"));
//
//        return Optional.ofNullable(newEmailToken.getUser())
//                .map(registeredUser -> {
//                    UriComponentsBuilder urlBuilder = ServletUriComponentsBuilder.fromCurrentContextPath().path("/api/auth/registrationConfirmation");
//                    OnRegenerateEmailVerificationEvent regenerateEmailVerificationEvent = new OnRegenerateEmailVerificationEvent(registeredUser, urlBuilder, newEmailToken);
//                    applicationEventPublisher.publishEvent(regenerateEmailVerificationEvent);
//                    return ResponseEntity.ok(new ApiResponse(true, "Email verification resent successfully"));
//                })
//                .orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", existingToken, "No user associated with this request. Re-verification denied"));
//    }

//    /**
//     * Refresh the expired jwt token using a refresh token for the specific device
//     * and return a new token to the caller
//     */
//    @PostMapping("/refresh")
//    @ApiOperation(value = "Refresh the expired jwt authentication by issuing a token refresh request and returns the" +
//            "updated response tokens")
//    public ResponseEntity refreshJwtToken(@ApiParam(value = "The TokenRefreshRequest payload") @Valid @RequestBody TokenRefreshRequest tokenRefreshRequest) {
//
//        return memberService.refreshJwtToken(tokenRefreshRequest)
//                .map(updatedToken -> {
//                    String refreshToken = tokenRefreshRequest.getRefreshToken();
//                    logger.info("Created new Jwt Auth token: " + updatedToken);
//                    return ResponseEntity.ok(new JwtAuthenticationResponse(updatedToken, refreshToken, tokenProvider.getExpiryDuration()));
//                })
//                .orElseThrow(() -> new TokenRefreshException(tokenRefreshRequest.getRefreshToken(), "Unexpected error during token refresh. Please logout and login again."));
//    }
}
