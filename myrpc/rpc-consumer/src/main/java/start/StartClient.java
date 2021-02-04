package start;


import com.myobject.encoder.JSONSerializer;
import com.myobject.encoder.RpcDecoder;
import com.myobject.encoder.RpcEncoder;
import com.myobject.pojo.RpcRequest;
import com.myobject.pojo.RpcResponse;
import handler.MiniRpcHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;


/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/16 4:17 下午
 */
public class StartClient {
    //2.声明一个自定义事件处理器  UserClientHandler
    private static MiniRpcHandler miniRpcHandler;

    //3.编写方法,初始化客户端  ( 创建连接池  bootStrap  设置bootstrap  连接服务器)
    private static void initClient() throws InterruptedException {
        //1) 初始化UserClientHandler
        miniRpcHandler = new MiniRpcHandler();
        //2)创建连接池对象
        EventLoopGroup group = new NioEventLoopGroup();

        //3)创建客户端的引导对象
        Bootstrap bootstrap = new Bootstrap();
        //4)配置启动引导对象
        bootstrap.group(group)
                //设置通道为NIO
                .channel(NioSocketChannel.class)
                //设置请求协议为TCP
                .option(ChannelOption.TCP_NODELAY, true)
                //监听channel 并初始化
                .handler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        //获取ChannelPipeline
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        //设置编码
                        pipeline.addLast(new RpcEncoder(RpcRequest.class, new JSONSerializer()));
                        pipeline.addLast(new RpcDecoder(RpcResponse.class, new JSONSerializer()));
                        //添加自定义事件处理器
                        pipeline.addLast(miniRpcHandler);
                    }
                });


        //5)连接服务端
        ChannelFuture future = bootstrap.connect("127.0.0.1", 8990).sync();

    }

    /**
     * 对外暴露服务，提供netty服务通信监听
     *
     * @return
     */
    public static synchronized MiniRpcHandler startListen() {
        //1)初始化客户端cliet
        if (miniRpcHandler == null) {
            try {
                initClient();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return miniRpcHandler;
    }
}
