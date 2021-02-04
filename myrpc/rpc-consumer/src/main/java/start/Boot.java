package start;

import com.myobject.service.UserService;
import consumer.RpcConsumer;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 10:17 上午
 */
public class Boot {

    public static void main(String[] args) throws InterruptedException {

        //1.创建代理对象
        UserService service = (UserService) RpcConsumer.remote(UserService.class);

        //2.循环给服务器写数据
        while (true) {
            String result = service.sayHello("测试链接");
            System.out.println(result);
            Thread.sleep(1000);
        }

    }

}
