package consumer;

import com.myobject.pojo.RpcRequest;
import handler.MiniRpcHandler;
import start.StartClient;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/16 4:07 下午
 */
public class RpcConsumer {

    //1.创建一个线程池对象  -- 它要处理我们自定义事件
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    // 供消费端根据接口类获取远程调用sub
    public static Object remote(Class<?> serviceClass) {
        return createProxy(serviceClass);
    }

    // 使用JDK的动态代理创建对象
    private static Object createProxy(Class<?> serviceClass) {
        MiniRpcHandler miniRpcHandler = StartClient.startListen();
        //2)返回代理对象
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class[]{serviceClass}, (o, method, objects) -> {

                    //1> 封装请求参数对象
                    RpcRequest rpcRequest = new RpcRequest();
                    rpcRequest.setClassName(serviceClass.getName());
                    rpcRequest.setMethodName(method.getName());
                    rpcRequest.setParameterTypes(method.getParameterTypes());
                    rpcRequest.setParameters(objects);

                    //2)miniRPCHandler 设置param参数
                    miniRpcHandler.setRpcRequest(rpcRequest);

                    //3).使用线程池,开启一个线程处理处理call() 写操作,并返回结果
                    return executorService.submit(miniRpcHandler).get();
                });
    }
}
