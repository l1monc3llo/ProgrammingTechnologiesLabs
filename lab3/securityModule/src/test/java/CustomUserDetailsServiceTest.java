import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import users.CustomUserDetailsService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Import(CustomUserDetailsService.class)
@SpringBootTest
public class CustomUserDetailsServiceTest {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Test
    public void loadUserByUsernameUserFoundReturnsUserDetails() {
        UserDetails userDetails = userDetailsService.loadUserByUsername("admin");
        assertEquals("admin", userDetails.getUsername());
    }

    @Test
    public void loadUserByUsernameUserNotFoundThrowsException() {
        assertThrows(UsernameNotFoundException.class, () -> {
            userDetailsService.loadUserByUsername("nonExistingUser");
        });
    }
}