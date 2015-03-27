package zum.potal.dwlee;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import zum.potal.dwlee.config.DataSourceConfig;
import zum.potal.dwlee.config.WebConfig;

@Configuration
@EnableAutoConfiguration
@ComponentScan
@Import({
	WebConfig.class,
	DataSourceConfig.class
})
public class Application extends SpringBootServletInitializer{
		
    @Override
	public void onStartup(ServletContext servletContext) throws ServletException {
			
		final CharacterEncodingFilter characterEncodingFilter = new CharacterEncodingFilter();
		
		characterEncodingFilter.setEncoding("UTF-8");
		characterEncodingFilter.setForceEncoding(true);
		servletContext.addFilter("characterEncodingFilter",	characterEncodingFilter).addMappingForUrlPatterns(EnumSet.of(DispatcherType.REQUEST), false, "/*");
		
		super.onStartup(servletContext);
	}

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }
    
	public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }
}
