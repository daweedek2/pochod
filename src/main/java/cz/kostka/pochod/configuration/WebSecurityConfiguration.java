package cz.kostka.pochod.configuration;

import cz.kostka.pochod.security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    public static final int REMEMBER_ME_TIME = 24 * 60 * 60;

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Value("${server.require-ssl}")
    private boolean requireSsl;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService)
                .passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(final HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();

        if (requireSsl) {
            http.requiresChannel().anyRequest().requiresSecure();
        }

        http.csrf().disable()
                .authorizeRequests().antMatchers("/h2-console/**")
                .permitAll().and()
                .authorizeRequests().antMatchers("/language")
                .hasAnyRole("USER", "ADMIN", "ORGANIZER").and()
                .authorizeRequests().antMatchers("/cypher**")
                .hasRole("USER").and()
                .authorizeRequests().antMatchers("/admin/**")
                .hasRole("ADMIN").and()
                .authorizeRequests().antMatchers("/game/**")
                .hasRole("ORGANIZER").and()
                .authorizeRequests().antMatchers("/finalplace")
                .hasRole("USER").and()
                .authorizeRequests().antMatchers("/contact**")
                .hasRole("USER").and()
                .formLogin()
                .loginPage("/login")
                .defaultSuccessUrl("/successfulLogin", true)
                .permitAll()
                .and()
                .logout()
                .deleteCookies("JSESSIONID")
                .logoutUrl("/logout")
                .permitAll()
                .and()
                .rememberMe().key("uniqueAndSecret").tokenValiditySeconds(REMEMBER_ME_TIME)
        ;
    }
}