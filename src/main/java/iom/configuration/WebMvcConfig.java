package iom.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.ResourceBundleViewResolver;
import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.spring4.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

	@Bean
	public BCryptPasswordEncoder passwordEncoder(){
		
		BCryptPasswordEncoder bCryptPasswordEncoder=new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}
	
	@Override
	public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
		super.configureContentNegotiation(configurer);
	}

    @Bean(name ="templateResolver")	
    public ServletContextTemplateResolver getTemplateResolver() {
    	ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver();
    	templateResolver.setPrefix("/WEB-INF/templates/");
    	templateResolver.setSuffix(".html");
    	templateResolver.setTemplateMode("XHTML");
	return templateResolver;
    }
    
    @Bean(name ="templateEngine")	    
    public SpringTemplateEngine getTemplateEngine() {
    	SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    	templateEngine.setTemplateResolver(getTemplateResolver());
	return templateEngine;
    }
    
    @Bean(name="viewResolver")
    public ThymeleafViewResolver getViewResolver(){
    	ThymeleafViewResolver viewResolver = new ThymeleafViewResolver(); 
    	viewResolver.setTemplateEngine(getTemplateEngine());
    	viewResolver.setOrder(2);
	return viewResolver;
    }
	
	@Bean  
    public ResourceBundleViewResolver resourceBundleViewResolver() {  
	    ResourceBundleViewResolver resolver = new ResourceBundleViewResolver();
	    resolver.setBasename("views");
	    resolver.setOrder(1);
        return resolver;  
    }
	
	
}
