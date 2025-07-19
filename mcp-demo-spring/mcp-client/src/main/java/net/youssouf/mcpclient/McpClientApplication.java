package net.youssouf.mcpclient;

import io.modelcontextprotocol.client.McpSyncClient;
import io.modelcontextprotocol.spec.McpSchema;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class McpClientApplication {

    public static void main(String[] args) {
        SpringApplication.run(McpClientApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(List<McpSyncClient> clients) {
        return args -> {
            clients.forEach(client -> {
                client.listTools().tools().forEach(tool -> {
                    System.out.println("==========================================");
                    System.out.println("Name: " + tool.name());
                    System.out.println("Description: " + tool.description());
                    System.out.println("Schema: " + tool.inputSchema());
                    System.out.println("==========================================");
                });
                String params = """
                                {
                                    "name": "OCP"
                                }
                                """;
                McpSchema.CallToolResult result = clients.get(0).callTool(
                        new McpSchema.CallToolRequest("getCompanyByName", params)
                );
                System.out.println("=== Contenu complet ===");
                System.out.println(result.content());

            });
        };
    }

}
