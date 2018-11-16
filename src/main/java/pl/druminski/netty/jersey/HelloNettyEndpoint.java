package pl.druminski.netty.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.xml.ws.Response;

@Path("/hellonetty")
public class HelloNettyEndpoint {

    @GET
    @Path("/get")
    @Produces("text/plain")
    public String helloNetty() {
        return "Hello Netty"; }
        

}
