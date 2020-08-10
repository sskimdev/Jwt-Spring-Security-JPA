## 빗썸코리아 회원인증 과제 API ##

#### 회원 가입, 로그인 및 조회 서비스를 위한 Back-End API.

주요특징:
* JWT (Json Web Token) authentication 과  Spring Security를 이용한 Backend API 인증
* JPA (Java Persistence API) 를 이용하여 database 테이블을 Entity class로 다양한 DBMS에 대응. 그리고, Entity를 이용한 DB 테이블 자동 생성.
* Swagger를 이용한 API Docs 자동화

지원기능들:
* 이메일, 패스워드, 이름을 기준으로 한 회원가입
* Spring Security와 JWT token 생성을 이용한 로그인
* JWT token을 이용한 회원정보(이메일/이름/직전로그인시간) 조회

![](https://cdn-images-1.medium.com/max/1334/1*7T41R0dSLEzssIXPHpvimQ.png)

---

## Swagger를 이용한 API Docs 자동화 ##
API 문서의 버전관리 및 현행화가 제대로 이루어지지 않는 이슈를 최소화하고 프론트엔드 개발자와의 원활한 커뮤니케이션을 위해 목적으로 Swagger를 활용.
* 소스코드에 적용된 API Spec을 추출하여 웹페이지로 제공함으로써 정확한 Request와 Response를 정확하고 신속하게 파악할 수 있음. 
![image](https://user-images.githubusercontent.com/12872673/45046897-24ded880-b095-11e8-8930-7b678e2843bb.png)


Swagger 작성예시)
```sql
package com.bithumbhomework.member.controller.api.v1;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/v1/member")
@Api(value = "Member Rest API", description = "회원가입, 로그인, 회원 조회 API")

public class MemberControllerV1 {

	private static final Logger logger = Logger.getLogger(MemberControllerV1.class);
	private final MemberAuthService memberService;
	private final UserService userService;
	private final UserLoginService userLoginService;
	private final JwtTokenProvider tokenProvider;

	@Autowired
	public MemberControllerV1(MemberAuthService memberService, UserService userService, UserLoginService userLoginService,
			JwtTokenProvider tokenProvider) {
		this.memberService = memberService;
		this.userService = userService;
		this.userLoginService = userLoginService;
		this.tokenProvider = tokenProvider;
	}

	/**
	 * 1. 회원가입 URI는 다음과 같습니다. : /v1/member/join 
	 * 2. 회원가입 시 필요한 정보는 ID, 비밀번호, 사용자이름 입니다. 
	 * 3. ID는 반드시 email 형식이어야 합니다. 
	 * 4. 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중 3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다. 
	 * 5. 비밀번호는 서버에 저장될 때에는 반드시 단방향 해시 처리가 되어야 합니다.
	 */
	@PostMapping("/join")
	@ApiOperation(value = "회원 가입")
	public ResponseEntity joinUser(
			@ApiParam(value = "The JoinRequest payload") @Valid @RequestBody JoinRequest joinRequest) {

		return memberService.joinUser(joinRequest).map(user -> {
			logger.info("joinUser ==> " + user);
			return ResponseEntity.ok(new ApiResponse(true, "회원가입이 성공하였습니다."));
		}).orElseThrow(() -> new UserJoinException(joinRequest.getEmail(), "Missing user object in database"));
	}
}

```



---

## 환경설정
# Database
테이블 생성
```sql
-- member_db.refresh_token_seq definition

CREATE TABLE `refresh_token_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- member_db.`user` definition

CREATE TABLE `user` (
  `user_id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `UK_ob8kqyqqgmefl0aco34akdtpe` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- member_db.user_login_seq definition

CREATE TABLE `user_login_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- member_db.user_seq definition

CREATE TABLE `user_seq` (
  `next_val` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- member_db.user_login definition

CREATE TABLE `user_login` (
  `user_login_id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `is_refresh_active` bit(1) DEFAULT NULL,
  `login_id` varchar(255) NOT NULL,
  `notification_token` varchar(255) DEFAULT NULL,
  `user_id` bigint(20) NOT NULL,
  PRIMARY KEY (`user_login_id`),
  KEY `FKpuv1tgwbg2fgmw93xktb9rjs5` (`user_id`),
  CONSTRAINT `FKpuv1tgwbg2fgmw93xktb9rjs5` FOREIGN KEY (`user_id`) REFERENCES `user` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- member_db.refresh_token definition

CREATE TABLE `refresh_token` (
  `token_id` bigint(20) NOT NULL,
  `created_at` datetime NOT NULL,
  `updated_at` datetime NOT NULL,
  `expiry_dt` datetime NOT NULL,
  `refresh_count` bigint(20) DEFAULT NULL,
  `token` varchar(255) NOT NULL,
  `user_login_id` bigint(20) NOT NULL,
  PRIMARY KEY (`token_id`),
  UNIQUE KEY `UK_7xw3t1qjql8we1oluftl4flv4` (`user_login_id`),
  UNIQUE KEY `UK_r4k4edos30bx9neoq81mdvwph` (`token`),
  CONSTRAINT `FK1afld87meo1qf4lhwge9iyqc9` FOREIGN KEY (`user_login_id`) REFERENCES `user_login` (`user_login_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
```


## Steps to Setup the Spring Boot Back end app

1. **Clone the application**

	```bash
	git clone https://github.com/isopropylcyanide/Jwt-Spring-Security-JPA.git
	cd AuthApp
	```

2. **Create MySQL database**

	```bash
	create database login_db
	```

3. **Change MySQL username and password as per your MySQL installation**

	+ open `src/main/resources/application.properties` file.

	+ change `spring.datasource.username` and `spring.datasource.password` properties as per your mysql installation
	
	+ open `src/main/resources/mail.properties` file.

	+ change `spring.mail.username` and `spring.mail.password` properties as per your mail installation

4. **Run the app**

	You can run the spring boot app by typing the following command -

	```bash
	mvn spring-boot:run
	```

	The server will start on port 9004. Token default expiration is 600000ms i.e 10 minutes.
	```

---

### Contribution ###
* Please fork the project and adapt it to your use case.
* Submit a pull request.
* The project is in a nascent stage. As such any issues you find should be reported in the issues section.

---
## Demo Screens ##

1. **Registering a user**
---
![image](https://user-images.githubusercontent.com/12872673/44460909-841c0200-a62c-11e8-96b6-996b8de6b2b8.png)


2. **Logging in a valid user**
---
![image](https://user-images.githubusercontent.com/12872673/45047478-c155aa80-b096-11e8-96e8-d7872a92ee03.png)

3. **Logging in an invalid user**
---
![image](https://user-images.githubusercontent.com/12872673/44461046-03a9d100-a62d-11e8-8073-fb6b32cec3de.png)

3. **Using the token in request header & accessing resource**
---
![image](https://user-images.githubusercontent.com/12872673/44461090-2e942500-a62d-11e8-8f05-8ecd1d2828e3.png)

4. **Accessing admin resource with invalid permissions/token**
---
![image](https://user-images.githubusercontent.com/12872673/44461159-68fdc200-a62d-11e8-9a8c-95a9c84d52cd.png)

5. **Logging out the user device**
---
![image](https://user-images.githubusercontent.com/12872673/45047550-f3ffa300-b096-11e8-8520-3eae03b6ef78.png)

6. **Resetting the password**
---
![image](https://user-images.githubusercontent.com/12872673/45047624-3628e480-b097-11e8-944f-c88b1cd0c231.png)

7. **Refreshing the authentication token**
---
![image](https://user-images.githubusercontent.com/12872673/45047676-5bb5ee00-b097-11e8-84d4-2dbbe1489157.png)

8. **Confirming the user email verification token**
---
![image](https://user-images.githubusercontent.com/12872673/45047715-76886280-b097-11e8-9ea6-e0c649eb6cbd.png)

