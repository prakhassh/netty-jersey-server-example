package pl.druminski.netty;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import org.glassfish.jersey.server.ApplicationHandler;
import org.glassfish.jersey.server.ResourceConfig;
import pl.druminski.netty.jersey.HelloNettyEndpoint;
import pl.druminski.netty.jersey.JerseyHandler;

public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {

    private static final int MESSAGE_BYTES_LIMIT = 128 * 1024;

    private final ChannelHandler jerseyHandler;

    public HttpServerInitializer() {
        jerseyHandler = createJerseyHandlerContainer();
    }

    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline p = ch.pipeline();

        p.addLast("encoder", new HttpResponseEncoder());
        p.addLast("decoder", new HttpRequestDecoder());
        p.addLast("aggregator", new HttpObjectAggregator(MESSAGE_BYTES_LIMIT));
        p.addLast("jerseyHandler", jerseyHandler);

    }

    private ChannelHandler createJerseyHandlerContainer() {

        ResourceConfig resourceConfig = new ResourceConfig().registerClasses(HelloNettyEndpoint.class);

        return new JerseyHandler(new ApplicationHandler(resourceConfig));

    }
}
