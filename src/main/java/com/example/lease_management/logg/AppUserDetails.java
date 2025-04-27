package com.example.lease_management.logg;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class AppUserDetails implements UserDetails {

    private String email;
    private String password;
    private String role;

    public AppUserDetails(ExternalUser externalUser) {
        this.email = externalUser.getEmail();
        this.password = externalUser.getPassword();
        this.role = "ROLE_EXTERNAL_USER";
    }
    public AppUserDetails(Employee employee) {
        this.email = employee.getEmail();
        this.password = employee.getPassword();
        this.role = "ROLE_EMPLOYEE";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role)); //klasa w springu do zwracania roli
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
