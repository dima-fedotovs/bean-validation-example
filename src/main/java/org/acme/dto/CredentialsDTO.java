package org.acme.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class CredentialsDTO {
    @NotBlank
    @NotNull
    private String login;
    @NotBlank
    @NotNull
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
