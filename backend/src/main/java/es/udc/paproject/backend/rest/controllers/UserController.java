package es.udc.paproject.backend.rest.controllers;

import static es.udc.paproject.backend.rest.dtos.UserConversor.toAuthenticatedUserDto;
import static es.udc.paproject.backend.rest.dtos.UserConversor.toUser;
import static es.udc.paproject.backend.rest.dtos.UserConversor.toUserDto;

import java.net.URI;
import java.util.Locale;

import es.udc.paproject.backend.model.exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.services.UserService;
import es.udc.paproject.backend.rest.common.ErrorsDto;
import es.udc.paproject.backend.rest.common.JwtGenerator;
import es.udc.paproject.backend.rest.common.JwtInfo;
import es.udc.paproject.backend.rest.dtos.AuthenticatedUserDto;
import es.udc.paproject.backend.rest.dtos.ChangePasswordParamsDto;
import es.udc.paproject.backend.rest.dtos.LoginParamsDto;
import es.udc.paproject.backend.rest.dtos.UserDto;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final static String INCORRECT_LOGIN_EXCEPTION_CODE = "project.exceptions.IncorrectLoginException";
	private final static String INCORRECT_PASSWORD_EXCEPTION_CODE = "project.exceptions.IncorrectPasswordException";
	private final static String USER_ALREADY_SELLER_CODE = "project.exceptions.UserAlreadySellerException";

	@Autowired
	private MessageSource messageSource;
	
	@Autowired
	private JwtGenerator jwtGenerator;
	
	@Autowired
	private UserService userService;
	
	@ExceptionHandler(IncorrectLoginException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectLoginException(IncorrectLoginException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_LOGIN_EXCEPTION_CODE, null,
				INCORRECT_LOGIN_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}
	
	@ExceptionHandler(IncorrectPasswordException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	public ErrorsDto handleIncorrectPasswordException(IncorrectPasswordException exception, Locale locale) {
		
		String errorMessage = messageSource.getMessage(INCORRECT_PASSWORD_EXCEPTION_CODE, null,
				INCORRECT_PASSWORD_EXCEPTION_CODE, locale);

		return new ErrorsDto(errorMessage);
		
	}

	@ExceptionHandler(UserAlreadySellerException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	public ErrorsDto handleUserAlreadySellerException(UserAlreadySellerException exception, Locale locale) {

		String errorMessage = messageSource.getMessage(USER_ALREADY_SELLER_CODE, null,
				USER_ALREADY_SELLER_CODE, locale);

		return new ErrorsDto(errorMessage);

	}

	@PostMapping("/signUp")
	public ResponseEntity<AuthenticatedUserDto> signUp(
		@Validated({UserDto.AllValidations.class}) @RequestBody UserDto userDto) throws DuplicateInstanceException {
		
		User user = toUser(userDto);
		
		userService.signUp(user);
		
		URI location = ServletUriComponentsBuilder
			.fromCurrentRequest().path("/{id}")
			.buildAndExpand(user.getId()).toUri();
	
		return ResponseEntity.created(location).body(toAuthenticatedUserDto(generateServiceToken(user), user));

	}
	
	@PostMapping("/login")
	public AuthenticatedUserDto login(@Validated @RequestBody LoginParamsDto params)
		throws IncorrectLoginException {
		
		User user = userService.login(params.getUserName(), params.getPassword());
			
		return toAuthenticatedUserDto(generateServiceToken(user), user);
		
	}
	
	@PostMapping("/loginFromServiceToken")
	public AuthenticatedUserDto loginFromServiceToken(@RequestAttribute Long userId, 
		@RequestAttribute String serviceToken) throws InstanceNotFoundException {
		
		User user = userService.loginFromId(userId);
		
		return toAuthenticatedUserDto(serviceToken, user);
		
	}

	@PutMapping("/{id}")
	public UserDto updateProfile(@Validated({UserDto.AllValidations.class}) @RequestAttribute Long userId, @PathVariable Long id,
		@Validated({UserDto.UpdateValidations.class}) @RequestBody UserDto userDto) 
		throws InstanceNotFoundException, PermissionException, DuplicateInstanceException {
				
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		return toUserDto(userService.updateProfile(id, userDto.getUserName(), userDto.getEmail(), userDto.getFirstName(), userDto.getLanguage(), userDto.getCountry(), userDto.getRegion(),userDto.getCrochetLevel(), userDto.getKnitLevel(), userDto.getBio()));
		
	}
	
	@PostMapping("/{id}/changePassword")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void changePassword(@RequestAttribute Long userId, @PathVariable Long id,
		@Validated @RequestBody ChangePasswordParamsDto params)
		throws PermissionException, InstanceNotFoundException, IncorrectPasswordException {
		
		if (!id.equals(userId)) {
			throw new PermissionException();
		}
		
		userService.changePassword(id, params.getOldPassword(), params.getNewPassword());
		
	}
	
	private String generateServiceToken(User user) {
		
		JwtInfo jwtInfo = new JwtInfo(user.getId(), user.getUsername(), user.getRole().toString());
		
		return jwtGenerator.generate(jwtInfo);
		
	}

	@PutMapping("/becomeSeller")
	public ResponseEntity<String> userBecomesSeller(@RequestAttribute Long userId) throws Exception {

		String accountId = userService.userBecomesSeller(userId);

		return ResponseEntity.ok(accountId);
	}

	@GetMapping("/{username}")
	public UserDto findUserByUsername(@PathVariable String username) throws InstanceNotFoundException{

		return toUserDto(userService.findUserByUsername(username));
	}

	/*@PutMapping("/updatePaypalAccount")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void updatePaypalAccount(@RequestAttribute Long userId, @RequestParam String paypalEmail) throws InstanceNotFoundException, UserNotSellerException {

		userService.updatePaypalAccount(userId, paypalEmail);

	}*/

	
}
