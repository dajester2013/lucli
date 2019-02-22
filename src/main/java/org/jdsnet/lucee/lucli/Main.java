package org.jdsnet.lucee.lucli;

import java.util.concurrent.Callable;

import org.jdsnet.lucee.lucli.commands.Server;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.RunAll;

/**
 * Main
 */
@Command(
	 name="lucli"
	,description="The Lucee CLI"
	,mixinStandardHelpOptions=true
	,version="0.0.1"

	,subcommands = {
		Server.class
	}
)
public class Main implements Callable<Void> {

	public static void main(String[] args) {
		CommandLine cli = new CommandLine(new Main());
		cli.parseWithHandler(new RunAll(), args);
		//CommandLine.call(new Main(), args);
	}

	public Void call() {
		return null;
	}

}