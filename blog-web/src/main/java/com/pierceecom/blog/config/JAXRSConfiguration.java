package com.pierceecom.blog.config;

import com.pierceecom.blog.resource.PostResource;
import com.pierceecom.blog.resource.HelloPierceResource;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;

@ApplicationPath("/")
public class JAXRSConfiguration extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<>();
        addClasses(classes);

        return classes;
    }

    private void addClasses(Set<Class<?>> classes) {
        classes.add(HelloPierceResource.class);
        classes.add(PostResource.class);
    }
}
