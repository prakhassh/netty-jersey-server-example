package pl.druminski.netty;

import org.junit.Test;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.assertj.core.api.Assertions.assertThat;

public class NettyJerseyServerTest {

    @Test
    public void shouldStartNettyJerseyServer() throws Exception {
        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(String.format("http://localhost:%s/hellonetty", HttpServer.PORT));
        HttpServer httpServer = new HttpServer();

        httpServer.startAsync();
        Response response = target.request(MediaType.TEXT_PLAIN_TYPE).get(Response.class);

        assertThat(response.getStatus()).isEqualTo(Response.Status.OK.getStatusCode());
        assertThat(response.readEntity(String.class)).isEqualTo("Hello Netty");
    }

}
