package com.bloomtech.userCreation.models;

public class User {
    private String username;
    private String email;
    private String password;
    private Role role;
    private String info;
    private boolean allowSlackIntegration;

    public User() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public boolean isAllowSlackIntegration() {
        return allowSlackIntegration;
    }

    public void setAllowSlackIntegration(boolean allowSlackIntegration) {
        this.allowSlackIntegration = allowSlackIntegration;
    }

    public User(Builder builder) {
        this.username = builder.username;
        this.email = builder.email;
        this.password = builder.password;
        this.role = builder.role;
        this.info = builder.info;
        this.allowSlackIntegration = builder.allowSlackIntegration;
    }

    public static Builder builder() {return new Builder();}

    public static final class Builder {
        private String username;
        private String email;
        private String password;
        private Role role;
        private String info;
        private boolean allowSlackIntegration;

        private Builder() {

        }

        public Builder withUsername(String usernameToUse) {
            this.username = usernameToUse;
            return this;
        }

        public Builder withEmail(String emailToUse) {
            this.email = emailToUse;
            return this;
        }

        public Builder withPassword(String passwordToUse) {
            this.password = passwordToUse;
            return this;
        }


        public Builder withRole(Role roleToUse) {
            this.role = roleToUse;
            return this;
        }

        public Builder withInfo(String infoToUse) {
            this.info = infoToUse;
            return this;
        }

        public Builder withAllowSlackIntegration(boolean allowSlackIntegrationToUse) {
            this.allowSlackIntegration = allowSlackIntegrationToUse;
            return this;
        }

        public User build() { return new User(this); }
    }
}
