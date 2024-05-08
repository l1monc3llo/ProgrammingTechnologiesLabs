import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SecurityConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @WithMockUser(username = "admin", roles = "ADMIN")
    public void adminShouldAccessAllEndpoints() throws Exception {
        mockMvc.perform(get("/admin/all"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/cats/all"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/owners/all"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void userShouldAccessFilteredEndpoints() throws Exception {
        mockMvc.perform(get("/cats/filter"))
                .andExpect(status().isOk());

        mockMvc.perform(get("/owners/filter"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(username = "user", roles = "USER")
    public void userShouldNotAccessAdminEndpoints() throws Exception {
        mockMvc.perform(get("/admin/all"))
                .andExpect(status().isForbidden());
    }

}