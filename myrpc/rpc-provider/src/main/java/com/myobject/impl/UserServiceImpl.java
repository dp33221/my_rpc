package com.myobject.impl;

import com.myobject.service.UserService;
import org.springframework.stereotype.Service;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/16 3:59 下午
 */
@Service
public class UserServiceImpl implements UserService {
    @Override
    public String sayHello(String word) {
        System.out.println("调用成功--参数：" + word);
        return "调用成功--参数：" + word;
    }

   /* public static void startServer(String hostName, int port) {
        try {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>(){
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline p = ch.pipeline();
                        p.addLast(new StringDecoder());
                        p.addLast(new StringEncoder());
                        p.addLast(new UserServerHandler());
                    }
                });
        bootstrap.bind(hostName, port).sync();
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }*/
}
