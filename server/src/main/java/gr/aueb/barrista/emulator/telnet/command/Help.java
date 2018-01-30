package gr.aueb.barrista.emulator.telnet.command;

public class Help implements TelnetCommand {

	public static final String HELP = "help";
	
	@Override
	public String toString() {
	
		StringBuffer buffer = new StringBuffer();
		buffer.append(HELP)
		.append("\r\n");
		return buffer.toString();
	}
	
}
