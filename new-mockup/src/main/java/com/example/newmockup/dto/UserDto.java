package com.example.newmockup.dto;

import com.example.newmockup.model.Category;
import com.example.newmockup.model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String userName;
    private String password;

    @JsonIgnore
    private List<CategoryDto> categories;

    public static User toEntity(UserDto userDto) {
        final User user = new User();

        user.setId(userDto.getId());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setCategories(
                userDto.getCategories() != null ? userDto.getCategories().stream().map(CategoryDto::toEntity).collect(Collectors.toList()) : null
        );

        return user;
    }

    public static UserDto fromEntity(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .password(user.getPassword())
                .email(user.getEmail())
                .categories(
                        user.getCategories() != null ? user.getCategories().stream().map(CategoryDto::fromEntity).toList() : null
                )
                .build();
    }

}
