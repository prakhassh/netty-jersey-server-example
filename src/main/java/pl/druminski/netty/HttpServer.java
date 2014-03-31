package pl.druminski.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class HttpServer {
    public static final int PORT = 8080;

    private final EventLoopGroup bossGroup;
    private final EventLoopGroup workerGroup;

    private final ServerBootstrap bootstrap;

    public HttpServer() {
        bossGroup = new NioEventLoopGroup();
        workerGroup = new NioEventLoopGroup();

        bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new HttpServerInitializer());
    }

    public void startSync() throws Exception {

        try {
            ChannelFuture channelFuture = bootstrap.bind(PORT).sync();
            //wait until the server socket is closed.
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public void startAsync() {
        bootstrap.bind(PORT);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                bossGroup.shutdownGracefully();
                workerGroup.shutdownGracefully();
            }

        });
    }

    public static void main(String[] args) throws Exception {
        new HttpServer().startSync();
    }
}
