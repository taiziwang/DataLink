package com.datalink.uaa.tenant;

import com.datalink.uaa.service.DefaultUserDetailsService;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.stereotype.Component;

/**
 * TenantAuthenticationSecurityConfig
 *
 * @author wenmo
 * @since 2021/5/11
 */
@Component
public class TenantAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private DefaultUserDetailsService userDetailsService;

    private PasswordEncoder passwordEncoder;

    public TenantAuthenticationSecurityConfig(DefaultUserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void configure(HttpSecurity http) {
        TenantAuthenticationProvider provider = new TenantAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        http.authenticationProvider(provider);
    }
}
