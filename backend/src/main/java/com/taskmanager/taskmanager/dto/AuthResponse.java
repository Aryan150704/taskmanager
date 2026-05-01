package com.taskmanager.taskmanager.dto;

public class AuthResponse {

    private String token;
    private String email;
    private String name;
    private Long id;

    public AuthResponse() {}

    public AuthResponse(String token, String email, String name, Long id) {
        this.token = token;
        this.email = email;
        this.name = name;
        this.id = id;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public static Builder builder() { return new Builder(); }

    public static class Builder {
        private String token;
        private String email;
        private String name;
        private Long id;

        public Builder token(String token) { this.token = token; return this; }
        public Builder email(String email) { this.email = email; return this; }
        public Builder name(String name) { this.name = name; return this; }
        public Builder id(Long id) { this.id = id; return this; }

        public AuthResponse build() {
            return new AuthResponse(token, email, name, id);
        }
    }
}
