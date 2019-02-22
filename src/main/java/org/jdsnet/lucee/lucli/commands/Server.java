package org.jdsnet.lucee.lucli.commands;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.concurrent.Callable;

import org.jdsnet.lucee.lucli.commands.Server.Start;
import org.jdsnet.lucee.lucli.server.DevelopmentServer;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

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


		public Void call() throws Exception {
			System.out.println("Starting web server in " + webroot.getAbsolutePath() + " on port " + port + ".");
			
			new DevelopmentServer(port, webroot).start();


			System.out.println("Server listening on " + port);

			return null;
		}

	}

//#endregion



	
	public Void call() {
		return null;
	}
}