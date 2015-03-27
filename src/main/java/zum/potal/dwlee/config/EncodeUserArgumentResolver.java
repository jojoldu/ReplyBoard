package zum.potal.dwlee.config;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import zum.potal.dwlee.utils.SHA256;
import zum.potal.dwlee.vo.EncodeUser;
import zum.potal.dwlee.vo.User;

public class EncodeUserArgumentResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterAnnotation(EncodeUser.class) != null;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		
		HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();

		User result = new User(); 

		Enumeration<String> paramNames = request.getParameterNames();

		while(paramNames.hasMoreElements()){
			String name = paramNames.nextElement();
			String value = request.getParameter(name);
			
			if(!PropertyUtils.isWriteable(result, name)){
				continue;
			}
			
			PropertyUtils.setProperty(result, name, value);
		}

		result.setPassword(SHA256.encode(result.getPassword()));
		
		return result;
	}

}
