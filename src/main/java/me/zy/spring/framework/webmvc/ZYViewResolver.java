package me.zy.spring.framework.webmvc;

import java.io.File;
import java.util.Locale;

public class ZYViewResolver {
    private final String DEFAULT_TEMPLATE_SUFFX = ".html";

    private File templateRootDir;
//    private String viewName;

    public ZYViewResolver(String templateRoot) {
        String templateRootPath = this.getClass().getClassLoader().getResource(templateRoot).getFile();
        templateRootDir = new File(templateRootPath);
    }

    public ZYView resolveViewName(String viewName, Locale locale) throws Exception{
        if(null == viewName || "".equals(viewName.trim())){return null;}
        viewName = viewName.endsWith(DEFAULT_TEMPLATE_SUFFX) ? viewName : (viewName + DEFAULT_TEMPLATE_SUFFX);
        File templateFile = new File((templateRootDir.getPath() + "/" + viewName).replaceAll("/+","/"));
        return new ZYView(templateFile);
    }

//    public String getViewName() {
//        return viewName;
//    }
}
