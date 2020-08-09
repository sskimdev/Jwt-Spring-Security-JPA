package com.bithumbhomework.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@SpringBootApplication
@EntityScan(basePackageClasses = { MemberApplication.class, Jsr310JpaConverters.class })
public class MemberApplication {

	public static void main(String[] args) {
		SpringApplication.run(MemberApplication.class, args);
	}

}
