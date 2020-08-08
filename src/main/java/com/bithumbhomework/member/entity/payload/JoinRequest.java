package com.bithumbhomework.member.entity.payload;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;

import com.bithumbhomework.member.validation.annotation.NullOrNotBlank;

@ApiModel(value = "Registration Request", description = "The registration request payload")
public class JoinRequest {

    @NullOrNotBlank(message = "Registration username can be null but not blank")
    @ApiModelProperty(value = "A valid username", allowableValues = "NonEmpty String")
    private String username;

    @NullOrNotBlank(message = "Registration email can be null but not blank")
    @ApiModelProperty(value = "A valid email", required = true, allowableValues = "NonEmpty String")
    private String email;

    @NotNull(message = "Registration password cannot be null")
    @ApiModelProperty(value = "A valid password string", required = true, allowableValues = "NonEmpty String")
    private String password;

//    @NotNull(message = "Specify whether the user has to be registered as an admin or not")
//    @ApiModelProperty(value = "Flag denoting whether the user is an admin or not", required = true,
//            dataType = "boolean", allowableValues = "true, false")
//    private Boolean registerAsAdmin;

    public JoinRequest(String username, String email,
                               String password) {
        this.username = username;
        this.email = email;
        this.password = password;
//        this.registerAsAdmin = registerAsAdmin;
    }

    public JoinRequest() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public Boolean getRegisterAsAdmin() {
//        return registerAsAdmin;
//    }
//
//    public void setRegisterAsAdmin(Boolean registerAsAdmin) {
//        this.registerAsAdmin = registerAsAdmin;
//    }
}
