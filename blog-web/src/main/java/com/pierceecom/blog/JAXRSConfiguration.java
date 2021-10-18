package com.pierceecom.blog;

import com.pierceecom.blog.resource.HelloPierceResource;
import com.pierceecom.blog.resource.PostsResource;

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
        classes.add(PostsResource.class);
        return classes;
    }
}
