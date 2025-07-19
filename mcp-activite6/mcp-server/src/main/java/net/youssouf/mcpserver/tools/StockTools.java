package net.youssouf.mcpserver.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockTools {

    private List<Company> companies = List.of(
            new Company("Maroc Télécom","Telecom",10600,3.6,"Maroc"),
            new Company("OCP","Minière",20000,5.7,"Maroc")
    );
    @Tool(description = "Get All Companies")
    public List<Company> getAllCompanies() {
        return companies;
    }
    @Tool
    public Company getCompanyByName(String name) {
        return companies.stream().filter(company -> company.name().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    @Tool
    public Stock getStockByCompanyName(String name) {
        return new Stock(name,LocalDate.now(),300 + Math.random()*300);
    }
}

record  Company(
        String name,
        String activity,
        int employesCount,
        @Description("The turnover In Millard MAD")
        double turnover,
        String country) {
}
record Stock(String campanyName, LocalDate date, double stock) {}