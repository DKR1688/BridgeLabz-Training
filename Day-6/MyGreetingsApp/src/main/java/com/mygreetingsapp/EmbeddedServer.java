package com.mygreetingsapp;

import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.Context;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.catalina.webresources.DirResourceSet;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class EmbeddedServer {

    public static void main(String[] args) throws Exception {
        int port = 8080;
        String portProp = System.getProperty("server.port");
        if (portProp != null) {
            try { port = Integer.parseInt(portProp); } catch (NumberFormatException ignored) {}
        }

        String webappDir = "src/main/webapp";
        File webapp = new File(webappDir);
        if (!webapp.exists()) {
            System.err.println("Webapp folder not found: " + webapp.getAbsolutePath());
            System.exit(1);
        }

        Tomcat tomcat = new Tomcat();
        tomcat.setPort(port);

        // Create context without letting Tomcat parse web.xml
        Context ctx = tomcat.addContext("", new File("target/tomcat-base").getAbsolutePath());

        // Set static resource directory to src/main/webapp
        WebResourceRoot resources = new StandardRoot(ctx);
        resources.addPreResources(new DirResourceSet(resources, "/", webapp.getAbsolutePath(), "/"));
        ctx.setResources(resources);

        // Create Spring context and DispatcherServlet
        AnnotationConfigWebApplicationContext appCtx = new AnnotationConfigWebApplicationContext();
        appCtx.scan("com.mygreetingsapp");

        DispatcherServlet dispatcher = new DispatcherServlet(appCtx);
        Tomcat.addServlet(ctx, "dispatcher", dispatcher).setLoadOnStartup(1);
        ctx.addServletMappingDecoded("/", "dispatcher");

        tomcat.getConnector();
        tomcat.start();
        System.out.println("Embedded Tomcat started on port: " + port);
        tomcat.getServer().await();
    }
}
