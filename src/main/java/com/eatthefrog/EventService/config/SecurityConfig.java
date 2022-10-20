package com.eatthefrog.EventService.config;

import com.okta.spring.boot.oauth.Okta;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.filter.AbstractRequestLoggingFilter;

import javax.servlet.http.HttpServletRequest;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .oauth2ResourceServer().jwt();

        Okta.configureResourceServer401ResponseBody(http);
    }

    @Bean
    public RequestLoggingFilter requestLoggingFilter() {
        RequestLoggingFilter loggingFilter = new RequestLoggingFilter();
        loggingFilter.setIncludeClientInfo(true);
        loggingFilter.setIncludeQueryString(true);
        loggingFilter.setIncludePayload(true);
        loggingFilter.setMaxPayloadLength(100000);
        return loggingFilter;
    }

    public class RequestLoggingFilter extends AbstractRequestLoggingFilter {
        public RequestLoggingFilter() {
        }

        protected boolean shouldLog(HttpServletRequest request) {
            return this.logger.isInfoEnabled();
        }

        protected void beforeRequest(HttpServletRequest request, String message) {
            // Empty to avoid printing duplicate
        }

        protected void afterRequest(HttpServletRequest request, String message) {
            this.logger.info(message);
        }
    }
}
