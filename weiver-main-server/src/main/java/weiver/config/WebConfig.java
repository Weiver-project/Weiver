package weiver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import weiver.config.interceptor.SessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private SessionInterceptor sessionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor)
					.addPathPatterns("/community/postlike/**")
					.addPathPatterns("/mypage/**")
					.addPathPatterns("/setting/**")
					.addPathPatterns("/logout/**")
					.addPathPatterns("/admin/**")
					.excludePathPatterns("/admin/login")
					.excludePathPatterns("/admin/signup")
					.excludePathPatterns("/ad/**")
					.excludePathPatterns("/main/**")
					.excludePathPatterns("/login/**")
					.excludePathPatterns("/signup/**")
					.excludePathPatterns("/community/**")
					.excludePathPatterns("/musical/**")
					.excludePathPatterns("/actor/**");
						
					
	}
	
}
