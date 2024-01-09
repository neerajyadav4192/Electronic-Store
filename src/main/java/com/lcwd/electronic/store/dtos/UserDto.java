package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Role;
import com.lcwd.electronic.store.validate.ImageNameValid;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private String userId;
    @Size(min=3,max=20,message="Invalid name!!")
    @ApiModelProperty(value="user_name",name="username",required = true,notes = "user name of new user")
    private String name;

    @Pattern(regexp="^[a-z0-9][-a-z0-9._]+@([-a-z0-9]+\\.)+[a-z]{2,5}$",message="Invalid userEmail!!")
    @NotBlank(message = "Email is required!!")
    private String email;
    @NotBlank(message="password is required!!")
    private String password;
    @Size(min=4,max=6,message="Invalid gender!!")
    private String gender;
    @NotBlank(message = "Write something about yourself!!")
    private String about;
//@Pattern
    //Custom Validator
    @ImageNameValid
    private String imageName;

    private Set<RoleDto> roles=new HashSet<>();

}
