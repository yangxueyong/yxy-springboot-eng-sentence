package com.example.eng.config.interceptor;
 
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
 
 
@Configuration
public class JwtInterceptorConfig extends WebMvcConfigurationSupport {
 
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册拦截器，要声明拦截器对象和要拦截的请求
        registry.addInterceptor(jwtInterceptor()) //配置jwt的拦截器规则
                .addPathPatterns("/**")//所有路径都被拦截
                .excludePathPatterns("/user/**");
        super.addInterceptors(registry);
    }
 
    @Bean
    public JwtInterceptor jwtInterceptor() {
        return new JwtInterceptor();
    }
 
}
 