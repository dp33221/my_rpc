package com.myobject.start;


import com.myobject.encoder.JSONSerializer;
import com.myobject.encoder.RpcDecoder;
import com.myobject.encoder.RpcEncoder;
import com.myobject.handler.UserServerHandler;
import com.myobject.pojo.RpcRequest;
import com.myobject.pojo.RpcResponse;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;


/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/16 4:05 下午
 */
public class StartServer implements ApplicationListener {

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        // 容器启动完成后开始初始化
        if (event instanceof ApplicationStartedEvent) {
            //启动服务器
            try {
                startServer("127.0.0.1", 8990);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    //创建一个方法启动服务器
    public static void startServer(String ip, int port) throws InterruptedException {
        System.out.println("start Rpc server...." + ip + ":" + port);
        //1.创建两个线程池对象
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();


        //2.创建服务端的启动引导对象
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //3.配置启动引导对象
        serverBootstrap.group(bossGroup, workGroup)
                //设置通道为NIO
                .channel(NioServerSocketChannel.class)
                //创建监听channel
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        //获取管道对象
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        //给管道对象pipeLine 设置编码
                        pipeline.addLast(new RpcDecoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new RpcEncoder(RpcResponse.class, new JSONSerializer()));
                        //把我们自定义ChannelHander添加到通道中
                        pipeline.addLast(new UserServerHandler());
                    }
                });

        //4.绑定端口
        ChannelFuture future = serverBootstrap.bind(ip, port).sync();
    }
}

