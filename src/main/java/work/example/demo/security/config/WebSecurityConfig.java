package work.example.demo.security.config;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import work.example.demo.services.userservice;


@Configuration
@AllArgsConstructor
@EnableWebSecurity
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api/v1/registration/**")


public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final userservice Userservice ;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
protected void configure(HttpSecurity http) throws Exception{
    http.csrf().disable()
            .authorizeRequests()
            .antMatchers("/api/v1/registration/**").permitAll()
//            .anyRequest()
//            .authenticated().and()
            .and().httpBasic();
          ;
}

    @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        auth.authenticationProvider(daoAuthenticationProvider());

}
@Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
    DaoAuthenticationProvider provider =
            new DaoAuthenticationProvider();
    provider.setPasswordEncoder(bCryptPasswordEncoder);
    provider.setUserDetailsService(Userservice);
    return provider;
    }
    @Configuration
    public class CorsConfig implements WebMvcConfigurer {
        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/api/v1/**")
                    .allowedOrigins("http://localhost:4200") // Replace with the URL of your Angular app
                    .allowedMethods("GET", "POST", "PUT", "DELETE")
                    .allowedHeaders("*")
                    .allowCredentials(true);
        }
    }
}
