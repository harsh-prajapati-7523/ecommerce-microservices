package com.ecom.harsh.authservice.model;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    
    UserMapper INSTANCE = org.mapstruct.factory.Mappers.getMapper(UserMapper.class);

    UsersDTO toDTO(Users user);
    Users toEntity(UsersDTO userDTO);
    List<UsersDTO> toDTOs(List<Users> users);
    List<Users> toEntities(List<UsersDTO> userDTOs);
}
