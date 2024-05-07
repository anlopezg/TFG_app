package es.udc.paproject.backend.rest.dtos;

import es.udc.paproject.backend.model.entities.User;

import java.util.List;
import java.util.stream.Collectors;

import static es.udc.paproject.backend.rest.dtos.ShoppingCartConversor.toShoppingCartDto;

public class UserConversor {
	
	private UserConversor() {}
	
	public final static UserDto toUserDto(User user) {
		return new UserDto(user.getId(), user.getUsername(), user.getEmail(), user.getFirstName(), user.getLanguage(),
				user.getCountry(), user.getRegion(), user.getCrochetLevel(), user.getKnitLevel(), user.getBio(),
				user.getRole().toString());
	}
	
	public final static User toUser(UserDto userDto) {
		
		return new User(userDto.getUserName(), userDto.getEmail(), userDto.getPassword(), userDto.getFirstName(),
				userDto.getLanguage(), userDto.getCountry(), userDto.getRegion(),userDto.getCrochetLevel(), userDto.getKnitLevel(), userDto.getBio());
	}
	
	public final static AuthenticatedUserDto toAuthenticatedUserDto(String serviceToken, User user) {
		
		return new AuthenticatedUserDto(serviceToken, toUserDto(user), toShoppingCartDto(user.getShoppingCart()));
		
	}

	public final static List<UserDto> toUserDtos(List<User> users){
		return users.stream().map(u-> toUserDto(u)).collect(Collectors.toList());
	}

}
