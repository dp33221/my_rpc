package com.myobject;

import com.myobject.start.StartServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;

/**
 * @author dingpei
 * @Description: todo
 * @date 2021/1/17 10:05 上午
 */
@SpringBootApplication
@Import(StartServer.class)
public class Bootstrap {

    public static void main(String[] args) {
        SpringApplication.run(Bootstrap.class,args);
    }
}
