package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.User;

public class UserConversor {
	
	private UserConversor() {}
	
	public final static UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUserName(), user.getEmail(), user.getFirstName(), user.getLanguage(),
				user.getCountry(), user.getCrochetLevel(), user.getKnitLevel(), user.getBio(),
				user.getRole().toString());
	}
	
	public final static User toUser(UserDto userDto) {
		
		return new User(userDto.getUserName(), userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
				userDto.getLanguage(), userDto.getCountry(), userDto.getCrochetLevel(), userDto.getKnitLevel(), userDto.getBio());
	}
	
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		
		return new AuthenticatedUserDto(serviceToken, toUserDto(user));
		
	}

}
