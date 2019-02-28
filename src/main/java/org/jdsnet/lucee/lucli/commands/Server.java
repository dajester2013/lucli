package org.jdsnet.lucee.lucli.commands;

import java.io.File;
import java.util.concurrent.Callable;

import org.jdsnet.lucee.lucli.commands.Server.Start;
import org.jdsnet.lucee.lucli.server.UndertowServer;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

/**
 * Server
 */
@Command(
	name="server"
	,aliases="srv"
	,description="LuCLI development server"
	,subcommands={
		Start.class
	}
)
public class Server implements Callable<Void> {

//#region Server Commands
	@Command(
		name="start"
	)
	public static class Start implements Callable<Void> {

		@Option(
			names={"-w", "--web-root"}
			,defaultValue=""
		)
		private File webroot;

		@Option(
			names={"-p", "--port"}
			,defaultValue="8080"
		)
		private Integer port;


		@Option(
			names={"-s", "--server"}
			,defaultValue="tomcat"
		)
		private String serverType;


		public Void call() throws Exception {
			System.out.println("Starting web server in " + webroot.getAbsolutePath() + " on port " + port + ".");
			
			new UndertowServer()
				.setPort(port)
				.setWebroot(webroot)
				.start();

			System.out.println("Server listening on " + port);

			return null;
		}

	}

//#endregion



	
	public Void call() {
		return null;
	}
}