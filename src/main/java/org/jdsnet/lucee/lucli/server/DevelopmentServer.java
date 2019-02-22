package org.jdsnet.lucee.lucli.server;

import java.io.File;

import org.apache.catalina.LifecycleException;
import org.apache.catalina.Wrapper;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.StandardRoot;

import lucee.loader.servlet.CFMLServlet;

/**
 * Server
 */
public class DevelopmentServer {

	private int port;
	private File webroot;
	private File configDirectory;

	private boolean running = false;

	public DevelopmentServer(int port, File webroot) {
		this(port, webroot, new File(webroot.getAbsolutePath(), "/.lucli"));
	}

	public DevelopmentServer(int port, File webroot, File configDirectory) {
		this.port = port;
		this.webroot = webroot;
		this.configDirectory = configDirectory;
	}
	
	public boolean isRunning() {
		return running;
	}

	public void start() throws LifecycleException, InterruptedException {
		if (!webroot.exists()) webroot.mkdirs();
		if (!configDirectory.exists()) configDirectory.mkdirs();
		
		Tomcat tc = new Tomcat();
		tc.setBaseDir(configDirectory.getAbsolutePath());
		
		Connector http = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		http.setPort(port);
		tc.setConnector(http);
		
		StandardContext ctx = (StandardContext)tc.addContext("", webroot.getAbsolutePath());
		ctx.setResources(new StandardRoot(ctx));
		ctx.addWelcomeFile("index.cfm");

		Wrapper servletWrapper = Tomcat.addServlet(ctx, "CFMLServlet", new CFMLServlet());
		servletWrapper.setLoadOnStartup(1);
		servletWrapper.addMapping("*.cfm");
		servletWrapper.addMapping("*.cfc");
		servletWrapper.addMapping("/index.cfm/*");

		System.getProperties().setProperty("lucee.web.dir", configDirectory.getAbsolutePath());

		tc.init();
		tc.start();
	}

	public void stop() {
		if (!running) return;
	}
}