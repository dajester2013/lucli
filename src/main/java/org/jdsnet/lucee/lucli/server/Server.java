package org.jdsnet.lucee.lucli.server;

import java.io.File;

public abstract class Server {

	private int port = 8080;
	private File webroot = null;
	private File configDirectory = null;

	public Server setPort(int port) {
		this.port = port;
		return this;
	}
	public Server setWebroot(File webroot) {
		this.webroot = webroot;
		return this;
	}
	public Server setConfigDirectory(File configDirectory) {
		this.configDirectory = configDirectory;
		return this;
	}

	public int getPort() {
		return port;
	}
	public File getWebroot() {
		return webroot == null ? new File(".").getAbsoluteFile() : webroot;
	}
	public File getConfigDirectory() {
		return configDirectory == null ? new File(getWebroot(), ".lucli") : configDirectory;
	}

	abstract public void start() throws Exception;
	abstract public void stop() throws Exception;
}