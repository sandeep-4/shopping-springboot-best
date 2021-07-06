package sp.spring.io.shopping.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Configuration
@EnableWebMvc
@DependsOn("passwordEncoder")
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtFilter jwtFilter;

	@Autowired
	private JwtEntryPoint jwtEntryPoint;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication().withUser("user").password(passwordEncoder.encode("password"))
				.roles("ROLE_CUSTOMER").and().withUser("admin").password(passwordEncoder.encode("password"))
				.roles("ROLE_CUSTOMER", "ROLE_ADMIN");
	}

	@Override
	@Bean
	protected AuthenticationManager authenticationManager() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors().and().csrf().disable().authorizeRequests()
		.antMatchers("/profile/**").authenticated()
        .antMatchers("/cart/**").access("hasAnyRole('CUSTOMER')")
        .antMatchers("/order/finish/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
        .antMatchers("/order/**").authenticated()
        .antMatchers("/profiles/**").authenticated()
        .antMatchers("/seller/product/new").access("hasAnyRole('MANAGER')")
        .antMatchers("/seller/**/delete").access("hasAnyRole( 'MANAGER')")
        .antMatchers("/seller/**").access("hasAnyRole('EMPLOYEE', 'MANAGER')")
        .anyRequest().permitAll()
        
        .and()
        .exceptionHandling().authenticationEntryPoint(jwtEntryPoint)
        .and()
        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
		
		http.addFilterBefore(jwtFilter,UsernamePasswordAuthenticationFilter.class);
        
        
        
        
        
	}

}
