package gr.aueb.barrista.emulator.telnet.command;

public class GeoFix implements TelnetCommand {

	public static final String GEO_FIX = "geo fix";
	
	private double latitude;
	private double longitude;
	
	
	public GeoFix(double latitude, double longitude) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
	}
	
	
	@Override
	public String toString() {
	
		StringBuffer buffer = new StringBuffer();
		buffer.append(GEO_FIX)
		.append(" ")
		.append(Double.toString(longitude))
		.append(" ")
		.append(Double.toString(latitude))
		.append("\r\n");
		
		String command = buffer.toString().replace('.', ',');
		return command;
	}
	
}
