package com.dlowji.simple;

import com.dlowji.simple.command.api.data.ITableRepository;
import com.dlowji.simple.command.api.data.SeveredTable;
import com.dlowji.simple.command.api.enums.TableStatus;
import com.dlowji.simple.command.api.exception.OrderServiceEventsErrorHandler;
import com.dlowji.simple.command.api.model.dto.TableDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.axonframework.config.EventProcessingConfigurer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.util.UUID;

@SpringBootApplication
public class OrderServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(OrderServiceApplication.class, args);
    }
    @Autowired
    public void configure(EventProcessingConfigurer configurer) {
        configurer.registerListenerInvocationErrorHandler(
                "order",
                configuration -> new OrderServiceEventsErrorHandler()
        );
    }

    @Component
    @Order(1)
    static class setupTables implements CommandLineRunner {
        private final ITableRepository tableRepository;

        setupTables(ITableRepository tableRepository) {
            this.tableRepository = tableRepository;
        }

        @Override
        public void run(String... args) throws Exception {
            try {
                String path = "order-service/src/main/resources/tables.json";
                ObjectMapper objectMapper = new ObjectMapper();
                TableDTO[] tables = objectMapper.readValue(new File(path), TableDTO[].class);
                for (TableDTO table : tables) {
                    if (tableRepository.findByCode(table.getCode()).isEmpty()) {
                        String tableId = UUID.randomUUID().toString();
                        SeveredTable severedTable = SeveredTable.builder()
                                .tableId(tableId)
                                .code(table.getCode())
                                .capacity(table.getCapacity())
                                .tableStatus(TableStatus.FREE)
                                .build();
                        tableRepository.save(severedTable);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
}
