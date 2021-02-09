package org.acme.dto;


import org.acme.dto.validationgroups.ForCreate;
import org.acme.dto.validationgroups.ForUpdate;

import javax.validation.Valid;
import javax.validation.constraints.*;

public class MyDTO {
    @NotNull(groups = ForCreate.class)
    @Null(groups = ForUpdate.class)
    private String id;
    @Size(min = 5)
    private String name;
    @Min(18)
    private int age;
    @Valid
    @NotNull(groups = ForCreate.class)
    private CredentialsDTO credentials;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public CredentialsDTO getCredentials() {
        return credentials;
    }

    public void setCredentials(CredentialsDTO credentials) {
        this.credentials = credentials;
    }
}
