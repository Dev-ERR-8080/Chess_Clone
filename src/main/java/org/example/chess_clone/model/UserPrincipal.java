package org.example.chess_clone.model;

import jakarta.annotation.Nullable;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserPrincipal implements UserDetails {
    private final User user;

    public boolean isOauthUser(){
        return user.isOauthUser();
    }


    @Override
    public @Nullable String getPassword() { return user.getPassword(); }

    @Override
    public String getUsername() { return user.getUserEmailId(); }

    public String getDisplayName(){ return user.getFullName(); }

    public Long getUserId(){ return user.getUserId();}

    public String getUserRole(){ return user.getRole(); }

    public String getPfpUrl(){ return user.getPfpUrl(); }

    public String getUserEmailId() { return user.getUserEmailId(); }

    public String getCountry(){ return user.getCountry(); }


    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(
                new SimpleGrantedAuthority("ROLE_"+user.getRole())
        );
    }

}
