package zum.potal.dwlee.config;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import zum.potal.dwlee.utils.CommonConstants;

public class Interceptor extends HandlerInterceptorAdapter {

	private final Logger logger = LoggerFactory.getLogger(Interceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

		String reqUrl = request.getRequestURI().toString();
		String methodType = request.getMethod();
		try {
			
			if("/".equals(reqUrl)){
				return true;
			}
			
			if("GET".equals(methodType) && request.getSession().getAttribute(CommonConstants.LOGIN_SESSION) == null){
				response.sendRedirect("/");
				return false;
			}
			
		} catch (Exception e) {
			logger.error("인터셉터 오류 메세지: ", e);
		}
		return true;
	}

}
