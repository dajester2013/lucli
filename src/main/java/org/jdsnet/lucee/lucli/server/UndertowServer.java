package org.jdsnet.lucee.lucli.server;

import javax.servlet.ServletException;

import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.server.handlers.resource.FileResourceManager;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;
import lucee.loader.servlet.CFMLServlet;

/**
 * Server
 */
public class UndertowServer extends Server {
	
	Undertow server;

	private boolean running = false;
	
	public boolean isRunning() {
		return running;
	}

	public void start() throws ServletException {
		System.getProperties().setProperty("lucee.web.dir", getConfigDirectory().getAbsolutePath());
		System.getProperties().setProperty("lucee.server.dir", getConfigDirectory().getAbsolutePath());

		DeploymentInfo servletBuilder = Servlets.deployment()
			// class loading and resources
			.setClassLoader(UndertowServer.class.getClassLoader())
			.setResourceManager(new FileResourceManager(getWebroot()))
			
			// webapp config
			.setContextPath("/")
			.setDeploymentName("ROOT.war")
			.addWelcomePage("index.cfm")
			
			// cfml servlet config
			.addServlets(
				Servlets.servlet("CFML", CFMLServlet.class)
					.setLoadOnStartup(1)
					.addMapping("*.cfm")
					.addMapping("*.cfc")
					.addMapping("/index.cfm/*")
			)
			;

		DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
		manager.deploy();

		PathHandler path = Handlers.path(Handlers.redirect("/"))
				.addPrefixPath("/", manager.start());

		server = Undertow.builder()
				.addHttpListener(getPort(), "localhost")
				.setHandler(path)
				.build();
				
		server.start();
	}

	public void stop() {
		if (!running) return;
	}
}