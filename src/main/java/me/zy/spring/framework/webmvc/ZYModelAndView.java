package me.zy.spring.framework.webmvc;

import java.util.Map;

public class ZYModelAndView {
    private String viewName;
    private Map<String,?> model;

    public ZYModelAndView(String viewName) { this.viewName = viewName; }

    public ZYModelAndView(String viewName, Map<String, ?> model) {
        this.viewName = viewName;
        this.model = model;
    }

    public String getViewName() {
        return viewName;
    }

//    public void setViewName(String viewName) {
//        this.viewName = viewName;
//    }

    public Map<String, ?> getModel() {
        return model;
    }

//    public void setModel(Map<String, ?> model) {
//        this.model = model;
//    }
}
