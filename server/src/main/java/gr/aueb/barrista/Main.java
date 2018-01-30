
package gr.aueb.barrista;

import java.io.File;
import java.io.IOException;
import java.net.URI;

import javax.ws.rs.core.UriBuilder;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import gr.aueb.barrista.emulator.EmulatorException;
import gr.aueb.barrista.emulator.telnet.ConnectionManager;
import gr.aueb.barrista.emulator.telnet.TelnetConnection;
import gr.aueb.barrista.emulator.telnet.command.Help;

public class Main {

	
	public static final String HOST = "192.168.1.5";
	
	private static URI getBaseURI() {
		return UriBuilder.fromUri("http://" + HOST + "/rest/").port(getPort(9998)).build();
	}

	public static final URI BASE_URI = getBaseURI();

	public static HttpServer startServer() {

		// Create resource configuration that scans for JAX-RS
		// resources and providers in gr.aueb.mscis.library.resource package
		final ResourceConfig rc = new ResourceConfig().packages("gr.aueb.barrista.resource");

		// Create and start a new instance of grizzly http server
		// exposing the Jersey application at BASE_URI
		HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);

		return server;
	}

	private static int getPort(int defaultPort) {
		// grab port from environment, otherwise fall back to default port 9998
		String httpPort = System.getProperty("jersey.test.port");
		if (null != httpPort) {
			try {
				return Integer.parseInt(httpPort);
			} catch (NumberFormatException e) {
			}
		}
		return defaultPort;
	}

	public static void main(String[] args) throws IOException, EmulatorException {

		// Read emulator console auth token
		String homeDirectory = System.getenv("HOME");
		if (homeDirectory == null){
			System.out.println("Please set the home variable");
			return;
		}
		
		ConnectionManager connectionManager = ConnectionManager.createInstance(homeDirectory + File.separatorChar + ".emulator_console_auth_token");
		// FIXME: By Default connects to a single emulator. Must find a way to identify the emulator that issues a request 
		TelnetConnection telnetConnection  = connectionManager.connect("emulator-5554", "localhost", 5554);
		
		// test connection
		telnetConnection.command(new Help());
		
		HttpServer httpServer = startServer();
		System.out.println(String.format(
				"Jersey app started with WADL available at " + "%sapplication.wadl\nHit enter to stop it...",
				BASE_URI));
		System.in.read();
		httpServer.stop();
	}
}
