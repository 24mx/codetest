package com.pierceecom.blog;

import com.pierceecom.blog.controller.HelloPierceController;
import com.pierceecom.blog.controller.PostsController;

import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@ApplicationPath("/")
public class JAXRSConfiguration  extends Application {
    @Override
    public Set<Class<?>> getClasses() {
        HashSet<Class<?>> classes = new HashSet<>();
        classes.add(HelloPierceController.class);
        classes.add(PostsController.class);
        return classes;
    }
}
