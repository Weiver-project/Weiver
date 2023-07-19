package weiver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import weiver.interceptor.SessionInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer{
	
	@Autowired
	private SessionInterceptor sessionInterceptor;
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(sessionInterceptor)
					.addPathPatterns("/post/**")
					.addPathPatterns("/reply/**")
					.addPathPatterns("/like/**")
					.addPathPatterns("/reReply/**")
					.addPathPatterns("/mypage/**")
					.addPathPatterns("/setting/**")
					.addPathPatterns("/logout/**")
					.excludePathPatterns("/main/**")
					.excludePathPatterns("/login/**")
					.excludePathPatterns("/signup/**")
					.excludePathPatterns("/community/**")
					.excludePathPatterns("/musical/**")
					.excludePathPatterns("/actor/**");
						
					
	}
	
}
