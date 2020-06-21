package com.pet.user.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class DefaultConfig implements WebMvcConfigurer {
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("login.html").setViewName("login");
        registry.addViewController("index.html").setViewName("index");
        registry.addViewController("item.html").setViewName("item");
        registry.addViewController("cart.html").setViewName("cart");
        registry.addViewController("person.html").setViewName("person");
//        registry.addViewController("center-index.html").setViewName("center-index");
        registry.addViewController("all-Orders.html").setViewName("all-Orders");
        registry.addViewController("evaluate-Order.html").setViewName("evaluate-Order");
        registry.addViewController("pay-Order.html").setViewName("pay-Order");
        registry.addViewController("send-Orders.html").setViewName("send-Order");
        registry.addViewController("receive-Orders.html").setViewName("receive-Order");
        registry.addViewController("register.html").setViewName("register");
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
