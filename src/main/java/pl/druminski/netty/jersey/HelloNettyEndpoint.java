package pl.druminski.netty.jersey;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/hellonetty")
public class HelloNettyEndpoint {

    @GET
    @Produces("text/plain")
    public String helloNetty() {
        return "Hello Netty";
    }

}