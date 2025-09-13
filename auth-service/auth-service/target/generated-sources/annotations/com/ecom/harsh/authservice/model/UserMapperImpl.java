package com.ecom.harsh.authservice.model;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-09-10T00:21:42+0530",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.43.0.v20250819-1513, environment: Java 21.0.8 (Eclipse Adoptium)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UsersDTO toDTO(Users user) {
        if ( user == null ) {
            return null;
        }

        UsersDTO usersDTO = new UsersDTO();

        usersDTO.setName( user.getName() );
        usersDTO.setPassword( user.getPassword() );
        usersDTO.setRole( user.getRole() );
        usersDTO.setUsername( user.getUsername() );

        return usersDTO;
    }

    @Override
    public Users toEntity(UsersDTO userDTO) {
        if ( userDTO == null ) {
            return null;
        }

        Users users = new Users();

        users.setName( userDTO.getName() );
        users.setPassword( userDTO.getPassword() );
        users.setRole( userDTO.getRole() );
        users.setUsername( userDTO.getUsername() );

        return users;
    }

    @Override
    public List<UsersDTO> toDTOs(List<Users> users) {
        if ( users == null ) {
            return null;
        }

        List<UsersDTO> list = new ArrayList<UsersDTO>( users.size() );
        for ( Users users1 : users ) {
            list.add( toDTO( users1 ) );
        }

        return list;
    }

    @Override
    public List<Users> toEntities(List<UsersDTO> userDTOs) {
        if ( userDTOs == null ) {
            return null;
        }

        List<Users> list = new ArrayList<Users>( userDTOs.size() );
        for ( UsersDTO usersDTO : userDTOs ) {
            list.add( toEntity( usersDTO ) );
        }

        return list;
    }
}
