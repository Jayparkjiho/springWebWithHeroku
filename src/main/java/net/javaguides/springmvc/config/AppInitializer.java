package net.javaguides.springmvc.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{

	@Override
	protected Class<?>[] getRootConfigClasses() {
		return new Class[] {
				AppContext.class
		};
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	protected Class<?>[] getServletConfigClasses() {
		return new Class[] {
				WebMvcConfig.class
		};
		// TODO Auto-generated method stub
		//return null;
	}

	@Override
	protected String[] getServletMappings() {
		return new String[] {
				"/"
		};
		// TODO Auto-generated method stub
		//return null;
	}

}
