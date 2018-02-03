package gr.aueb.barrista.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import gr.aueb.barrista.dto.GeoFixDto;
import gr.aueb.barrista.emulator.EmulatorException;
import gr.aueb.barrista.emulator.telnet.ConnectionManager;
import gr.aueb.barrista.emulator.telnet.TelnetConnection;
import gr.aueb.barrista.emulator.telnet.command.GeoFixCommand;

@Path("telnet/emulator/{port:[0-9]+}/")
public class TelnetResource {

	public static final String DEFAULT_HOST_NAME = "localhost";
	public static final String EMULATOR_NAME_PREFIX = "emulator-";
	
	@Context
	HttpHeaders httpHeaders;

	@GET
	@Path("geo/fix")
	public Response testGeoFix(){
		System.out.println("Received request");
		return Response.ok().build();
	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("geo/fix")
	public Response executeGeoFix(GeoFixDto geoFixDto, @PathParam("port")int port){
		System.out.println("Received request: " + geoFixDto.latitude + ", " + geoFixDto.longitude);

		String emulatorName = EMULATOR_NAME_PREFIX + port;
		try {
			TelnetConnection connection = ConnectionManager.getInstance().connect(emulatorName, DEFAULT_HOST_NAME, port);
			if (connection == null){
				return Response.serverError().build();
			}
			
			if (!connection.command(new GeoFixCommand(geoFixDto))){
				return Response.status(Status.NOT_FOUND).build();
			}
		} catch (EmulatorException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.serverError().build();
		}
		
		return Response.ok().build();
	}



}
