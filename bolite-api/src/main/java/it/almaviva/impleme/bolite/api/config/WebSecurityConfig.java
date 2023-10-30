package it.almaviva.impleme.bolite.api.config;

import it.almaviva.eai.ljsa.sdk.core.bootstrap.EnableLjsa;
import it.almaviva.eai.ljsa.sdk.core.security.LjsaFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
  @EnableLjsa
  @EnableWebSecurity
  public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private LjsaFilter ljsaProfileManagerFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
      http.csrf().disable().authorizeRequests()
          .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).
           authenticated()
          .and().exceptionHandling().accessDeniedHandler(accessDeniedHandler()).and()
          .sessionManagement()
          .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
          .and().addFilterBefore(ljsaProfileManagerFilter, BasicAuthenticationFilter.class);

      http.cors();
    }


    @Override
    public void configure(WebSecurity web) {
      web.ignoring()
          .antMatchers(
              "/v2/api-docs","/actuator/**",
              "/swagger-resources/**",
              "/swagger-ui.html**",
              "/webjars/**");
    }



  @Bean
  public AccessDeniedHandler accessDeniedHandler() {
    return new CustomAccessDeniedHandler();
  }


  }
