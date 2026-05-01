package com.servergarden.configserver;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("native") // use classpath config-repo, no GitHub required
class ConfigServerApplicationTests {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void contextLoads() {
        // Verifies the Spring context starts cleanly with the native profile
    }

    @Test
    void healthEndpointIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
               .andExpect(status().isOk());
    }

    @Test
    void configEndpointRequiresAuth() throws Exception {
        mockMvc.perform(get("/application/default"))
               .andExpect(status().isUnauthorized());
    }

    @Test
    @WithMockUser(roles = "CONFIG_CLIENT")
    void configEndpointServesDefaultProfile() throws Exception {
        mockMvc.perform(get("/application/default"))
               .andExpect(status().isOk());
    }

    @Test
    @WithMockUser(roles = "CONFIG_CLIENT")
    void configEndpointServesDevProfile() throws Exception {
        mockMvc.perform(get("/application/dev"))
               .andExpect(status().isOk());
    }
}
