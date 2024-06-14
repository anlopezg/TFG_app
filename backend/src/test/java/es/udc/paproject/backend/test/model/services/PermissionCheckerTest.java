package es.udc.paproject.backend.test.model.services;

import es.udc.paproject.backend.model.daos.UserDao;
import es.udc.paproject.backend.model.entities.User;
import es.udc.paproject.backend.model.exceptions.DuplicateInstanceException;
import es.udc.paproject.backend.model.exceptions.InstanceNotFoundException;
import es.udc.paproject.backend.model.services.PermissionChecker;
import es.udc.paproject.backend.model.services.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PermissionCheckerTest {

    private final Long NON_EXISTENT_ID = Long.valueOf(-1);

    @Autowired
    private PermissionChecker permissionChecker;

    @Autowired
    private UserService userService;

    @Autowired
    private UserDao userDao;

    private static final Long USER_ID = 1L;
    private static final String USERNAME = "testUser";

    private User createNormalUser(String userName) throws DuplicateInstanceException {
        User user = new User(userName, userName + "@a.com","password", "firstName", "language",
                "country", "region", 1, 2, "long bio");

        userService.signUp(user);

        return user;
    }

    @Test
    void testCheckUserExists_UserExists() throws DuplicateInstanceException {

        User user = createNormalUser(USERNAME);

        assertDoesNotThrow(() -> permissionChecker.checkUserExists(user.getId()));
    }

    @Test
    void testcheckUserName_UserExists() throws DuplicateInstanceException {

        User user = createNormalUser(USERNAME);

        assertDoesNotThrow(() -> permissionChecker.checkUserName(user.getUsername()));
    }


    @Test
    void testCheckUserExists_UserDoesNotExist() {
        assertThrows(InstanceNotFoundException.class, () -> permissionChecker.checkUserExists(NON_EXISTENT_ID));
    }

    @Test
    void testCheckUserName_UserDoesNotExist() {
        assertThrows(InstanceNotFoundException.class, () -> permissionChecker.checkUserName("non_existent"));
    }

    @Test
    void checkProductNonExistent(){
        assertThrows(InstanceNotFoundException.class, ()-> permissionChecker.checkProduct(NON_EXISTENT_ID));
    }

    @Test
    void checkActiveProductNonExistent(){
        assertThrows(InstanceNotFoundException.class, ()-> permissionChecker.checkActiveProduct(NON_EXISTENT_ID));
    }


}
