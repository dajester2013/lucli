package org.jdsnet.lucee.lucli;

import java.io.File;
import java.util.concurrent.Callable;

import org.jdsnet.lucee.lucli.commands.Server;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Parameters;
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

	// @Parameters(index="0")
	// private File script;

	private static String[] mainArgs;

	public static void main(String[] args) {
		mainArgs = args;

		CommandLine cli = new CommandLine(new Main());
		cli.parseWithHandler(new RunAll(), args);
	}

	public Void call() throws Exception {
		// File cfgDir = new File(new File("./").getPath(), ".lucli");
		// cfgDir.mkdirs();

		// System.getProperties().setProperty("lucee.cli.config", cfgDir.getAbsolutePath());
		// lucee.cli.CLI.main(new String[] {script.getAbsolutePath()});
		return null;
	}

}