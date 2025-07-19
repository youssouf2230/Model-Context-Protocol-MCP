package net.youssouf.mcpserver.tools;


import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Description;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StockTools {
    private List<Company> companies = List.of(
            new Company("Maroc Telecom","Telecom",3.6,10600,"Maroc"),
            new Company("OCP","Extraction minière",5.6,2000,"Maroc")
    );
    @Tool(description = "get all companies")
    public List<Company> getAllCompanies() {
        return companies;
    }
    @Tool
    public Company getCompanyByName(String name){
        return companies.stream().filter(company -> company.name().equals(name)).findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found")) ;
    }
    @Tool
    public Stock getStockByCompany(String name){
        return new Stock(name,LocalDate.now(),300+Math.random()*300);
    }

}

record Company(
        String name,
        String activity,
        @Description("The turnover In Milliard MAD")
        double turnover,
        int employeesCount,
        String country) {
}

record Stock(String companyName, LocalDate date,double stock) {}
