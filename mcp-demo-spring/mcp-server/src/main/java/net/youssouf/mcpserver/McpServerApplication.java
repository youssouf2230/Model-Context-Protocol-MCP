package net.youssouf.mcpserver;

import net.youssouf.mcpserver.tools.StockTools;
import org.springframework.ai.tool.method.MethodToolCallback;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class McpServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpServerApplication.class, args);
    }

    @Bean
    public MethodToolCallbackProvider getMethodToolCallbackProvider() {
        return MethodToolCallbackProvider.builder()
                .toolObjects(new StockTools())
                .build();
    }

}
