package gr.aueb.barrista.emulator.telnet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import gr.aueb.barrista.emulator.EmulatorException;

public class ConnectionManager {

	private static ConnectionManager theInstance;

	Map<String, TelnetConnection> emulatorConnections = new HashMap<>();
	private String emulatorConsoleAuthToken;
	private String emulatorConsoleFile;

	
	
	public ConnectionManager(String emulatorConsoleFile) {
		super();
		this.emulatorConsoleFile = emulatorConsoleFile;
	}

	public static ConnectionManager createInstance(String emulatorConsoleFile) {
		if (theInstance == null) {
			theInstance = new ConnectionManager(emulatorConsoleFile);
		}
		return theInstance;
	}

	public static ConnectionManager getInstance() {
		return theInstance;
	}
	
	private String getEmulatorConsoleAuthToken() throws EmulatorException {
		
		File file = new File(emulatorConsoleFile);
		
		if (!file.exists()){
			throw new EmulatorException("Emulator not started");
		}
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			String authToken = line.trim();
			if (authToken == null || authToken.isEmpty()){
				throw new EmulatorException("Auth token not found");
			}
			return authToken;
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new EmulatorException("Error opening emulator_console_auth_token file");
		}
		
	}

	public TelnetConnection connect(String deviceId, String host, int port) throws EmulatorException {

		if (emulatorConsoleAuthToken == null) {
			emulatorConsoleAuthToken = getEmulatorConsoleAuthToken();
		}

		TelnetConnection telnetConnection = emulatorConnections.get(deviceId);
		if (telnetConnection == null) {
			telnetConnection = new TelnetConnection(host, port, emulatorConsoleAuthToken);
			emulatorConnections.put(deviceId, telnetConnection);
		}
		return telnetConnection;
	}

}
