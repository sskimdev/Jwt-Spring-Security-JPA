package com.accolite.pru.health.AuthApp.service;

import com.accolite.pru.health.AuthApp.exception.AppException;
import com.accolite.pru.health.AuthApp.exception.InvalidTokenRequestException;
import com.accolite.pru.health.AuthApp.exception.ResourceAlreadyInUseException;
import com.accolite.pru.health.AuthApp.exception.ResourceNotFoundException;
import com.accolite.pru.health.AuthApp.model.Role;
import com.accolite.pru.health.AuthApp.model.RoleName;
import com.accolite.pru.health.AuthApp.model.TokenStatus;
import com.accolite.pru.health.AuthApp.model.User;
import com.accolite.pru.health.AuthApp.model.payload.LoginRequest;
import com.accolite.pru.health.AuthApp.model.payload.RegistrationRequest;
import com.accolite.pru.health.AuthApp.model.token.EmailVerificationToken;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	private static final Logger logger = Logger.getLogger(AuthService.class);

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private EmailVerificationTokenService emailVerificationTokenService;

	/**
	 * Registers a new user in the database by performing a series of quick checks.
	 * @return A user object if successfully created
	 */
	public Optional<User> registerUser(RegistrationRequest newRegistrationRequest) {
		String newRegistrationRequestEmail = newRegistrationRequest.getEmail();
		Boolean emailAlreadyExists = emailAlreadyExists(newRegistrationRequestEmail);
		if (emailAlreadyExists) {
			logger.error("Email already exists: " + newRegistrationRequestEmail);
			throw new ResourceAlreadyInUseException("Email", "Address", newRegistrationRequestEmail);
		}
		logger.info("Trying to register new user [" + newRegistrationRequestEmail + "]");
		User newUser = new User();
		Boolean isNewUserAsAdmin = newRegistrationRequest.getRegisterAsAdmin();
		newUser.setEmail(newRegistrationRequestEmail);
		newUser.setPassword(passwordEncoder.encode(newRegistrationRequest.getPassword()));
		newUser.setUsername(newRegistrationRequestEmail);
		newUser.addRoles(getRolesForNewUser(isNewUserAsAdmin));
		newUser.setActive(true);
		newUser.setEmailVerified(false);
		User registeredNewUser = userService.save(newUser);
		return Optional.ofNullable(registeredNewUser);
	}

	/**
	 * Performs a quick check to see what roles the new user could benefit from
	 * @return list of roles for the new user
	 */
	private Set<Role> getRolesForNewUser(Boolean isAdmin) {
		Set<Role> newUserRoles = new HashSet<>();
		newUserRoles.add(roleService.findByRole(RoleName.ROLE_USER).orElseThrow(() -> new AppException("ROLE_USER " +
				" is not set in database.")));
		if (isAdmin) {
			newUserRoles.add(roleService.findByRole(RoleName.ROLE_ADMIN).orElseThrow(() -> new AppException(
					"ROLE_ADMIN" + "not set in database.")));
		}
		return newUserRoles;
	}

	/**
	 * Checks if the given email already exists in the database repository or not
	 * @return true if the email exists else false
	 */
	public Boolean emailAlreadyExists(String email) {
		return userService.existsByEmail(email);
	}

	/**
	 * Checks if the given email already exists in the database repository or not
	 * @return true if the email exists else false
	 */
	public Boolean usernameAlreadyExists(String username) {
		return userService.existsByUsername(username);
	}


	/**
	 * Authenticate user and log them in given a loginRequest
	 */
	public Optional<Authentication> authenticateUser(LoginRequest loginRequest) {
		return Optional.ofNullable(authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),
				loginRequest.getPassword())));
	}

	/**
	 * Confirms the user verification based on the token expiry and mark the user as active.
	 * If user is already registered, save the unnecessary database calls.
	 */
	public Optional<User> confirmRegistration(String emailToken) {
		Optional<EmailVerificationToken> emailVerificationTokenOpt =
				emailVerificationTokenService.findByToken(emailToken);
		emailVerificationTokenOpt.orElseThrow(() ->
				new ResourceNotFoundException("Token", "Email verification", emailToken));

		Optional<User> registeredUser = emailVerificationTokenOpt.map(EmailVerificationToken::getUser);
		//if user is already verified
		Boolean userAlreadyRegistered =
				emailVerificationTokenOpt.map(EmailVerificationToken::getUser).map(User::getEmailVerified).filter(x -> x == true).orElse(false);

		if (userAlreadyRegistered) {
			logger.info("User [" + registeredUser + "] already registered.");
			return registeredUser;
		}
		//filter only valid token
		Optional<Instant> validEmailTokenOpt =
				emailVerificationTokenOpt.map(EmailVerificationToken::getExpiryDate).filter(dt -> dt.compareTo(Instant.now()) >= 0);

		validEmailTokenOpt.orElseThrow(() -> new InvalidTokenRequestException("Email Verification Token", emailToken,
				"Expired token. Please issue a new request"));

		emailVerificationTokenOpt.ifPresent(token -> {
			token.setTokenStatus(TokenStatus.STATUS_CONFIRMED);
			emailVerificationTokenService.save(token);
			User user = registeredUser.get();
			user.setEmailVerified(true);
			userService.save(user);
		});
		return registeredUser;
	}
}
