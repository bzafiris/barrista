package gr.aueb.barrista.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import gr.aueb.barrista.dto.GeoFix;

@Path("telnet")
public class TelnetResource {
	
	@GET
	@Path("geo/fix")
	public Response testGeoFix(GeoFix command){
		System.out.println("Received request");
		return Response.ok().build();
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("geo/fix")
	public Response executeGeoFix(GeoFix command){
		System.out.println("Received request: " + command.latitude + ", " + command.longitude);
		
	
		return Response.ok().build();
	}
	
	

}
