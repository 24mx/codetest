package com.pierceecom.blog;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;



@ApplicationPath("/")
public class JAXRSConfiguration  extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(HelloPierceResource.class);
        classes.add(com.pierceecom.resource.BlogPostResource.class);
        classes.add(com.pierceecom.exceptionmappers.IllegalArgumentExceptionMapper.class);
        classes.add(com.pierceecom.exceptionmappers.DataNotFoundExceptionMapper.class);
        classes.add(com.pierceecom.exceptionmappers.OptimisticLockExceptionMapper.class);
        classes.add(com.pierceecom.exceptionmappers.NotAllowedExceptionMapper.class);
        classes.add(com.pierceecom.exceptionmappers.GenericExceptionMapper.class);
        
        
        
        
        
        return classes;
    }
}
