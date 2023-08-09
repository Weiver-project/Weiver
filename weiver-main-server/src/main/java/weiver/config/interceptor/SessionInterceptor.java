package weiver.config.interceptor;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
@Component
public class SessionInterceptor implements HandlerInterceptor{

	@Override
	public boolean preHandle(HttpServletRequest request,
							 HttpServletResponse response,
							 Object handler) throws Exception{

		HttpSession session = request.getSession(false);
		if(!request.getRequestURI().contains("/admin")) {
			if(session == null || session.getAttribute("userId") == null) {
				response.sendRedirect("/login");
				return false;
			}
		} else {
			if(session == null || session.getAttribute("adminId") == null) {
				response.sendRedirect("/admin/login");
				return false;
			}
		}

		return true;
	}
}
