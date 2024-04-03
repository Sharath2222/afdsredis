import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class JwtSecurityTests {

    @Autowired
    private MockMvc mockMvc;

    private String generateValidJwtToken() {
        // Implement JWT token generation logic here
        return "valid-jwt-token";
    }

    private String generateExpiredJwtToken() {
        // Implement JWT token generation logic with expired token here
        return "expired-jwt-token";
    }

    @Test
    public void givenValidJwtToken_whenAccessProtectedResource_thenSuccess() throws Exception {
        String token = generateValidJwtToken();

        mockMvc.perform(get("/protected-resource")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoJwtToken_whenAccessProtectedResource_thenUnauthorized() throws Exception {
        mockMvc.perform(get("/protected-resource"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenInvalidJwtToken_whenAccessProtectedResource_thenUnauthorized() throws Exception {
        String invalidToken = "invalid-jwt-token";

        mockMvc.perform(get("/protected-resource")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + invalidToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenExpiredJwtToken_whenAccessProtectedResource_thenUnauthorized() throws Exception {
        String expiredToken = generateExpiredJwtToken();

        mockMvc.perform(get("/protected-resource")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + expiredToken))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void givenTamperedJwtToken_whenAccessProtectedResource_thenUnauthorized() throws Exception {
        String tamperedToken = generateValidJwtToken();
        tamperedToken = tamperedToken.replace("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9", "tampered");

        mockMvc.perform(get("/protected-resource")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer " + tamperedToken))
                .andExpect(status().isUnauthorized());
    }
}
