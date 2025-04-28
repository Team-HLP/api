package com.hlp.api.web.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hlp.api.common.auth.admin.AdminArgumentResolver;
import com.hlp.api.common.auth.admin.ExtractAdminAuthenticationInterceptor;
import com.hlp.api.common.auth.guardian.ExtractGuardianAuthenticationInterceptor;
import com.hlp.api.common.auth.guardian.GuardianArgumentResolver;
import com.hlp.api.common.auth.user.ExtractUserAuthenticationInterceptor;
import com.hlp.api.common.auth.user.UserArgumentResolver;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final CorsProperties corsProperties;

    private final AdminArgumentResolver adminArgumentResolver;
    private final UserArgumentResolver userArgumentResolver;
    private final GuardianArgumentResolver guardianArgumentResolver;

    private final ExtractAdminAuthenticationInterceptor extractAdminAuthenticationInterceptor;
    private final ExtractUserAuthenticationInterceptor extractUserAuthenticationInterceptor;
    private final ExtractGuardianAuthenticationInterceptor extractGuardianAuthenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(extractAdminAuthenticationInterceptor)
            .addPathPatterns("/admin/**");

        registry.addInterceptor(extractGuardianAuthenticationInterceptor)
            .addPathPatterns("/guardian/**");

        registry.addInterceptor(extractUserAuthenticationInterceptor)
            .addPathPatterns("/**")
            .excludePathPatterns("/admin/**")
            .excludePathPatterns("/guardian/**");
    }

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(adminArgumentResolver);
        resolvers.add(userArgumentResolver);
        resolvers.add(guardianArgumentResolver);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
            .allowedOrigins(corsProperties.allowedOrigins().toArray(new String[0]))
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH", "FETCH")
            .allowedHeaders("*")
            .allowCredentials(true)
            .maxAge(3600);
    }
}
