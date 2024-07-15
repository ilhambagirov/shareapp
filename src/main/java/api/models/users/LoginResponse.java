package api.models.users;

import org.springframework.security.web.csrf.CsrfToken;

public class LoginResponse {
    public String token;
    public Long expiresIn;
    private CsrfToken csrfToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(Long expiresIn) {
        this.expiresIn = expiresIn;
    }

    public CsrfToken getCsrfToken() {
        return csrfToken;
    }

    public void setCsrfToken(CsrfToken csrfToken) {
        this.csrfToken = csrfToken;
    }
}
