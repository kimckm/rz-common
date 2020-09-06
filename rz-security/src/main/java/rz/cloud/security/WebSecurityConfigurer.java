package rz.cloud.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class WebSecurityConfigurer extends WebSecurityConfigurerAdapter {

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Bean
	@Override
	public UserDetailsService userDetailsServiceBean() throws Exception {
		return super.userDetailsServiceBean();
	}

	@Override
	public void configure(HttpSecurity http) throws Exception {
		http    // 配置登陆页/login并允许访问
			.formLogin().permitAll()
			// 登出页
			.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
			// 其余所有请求全部需要鉴权认证
			.and().authorizeRequests().anyRequest().authenticated()
			// 由于使用的是JWT，我们这里不需要csrf
			.and().csrf().disable();
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.inMemoryAuthentication()
			.withUser("bkk")
			.password("{noop}bkk")
			.roles("USER", "ADMIN");
	}
}
