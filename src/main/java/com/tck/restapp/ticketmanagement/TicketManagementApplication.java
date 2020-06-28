package com.tck.restapp.ticketmanagement;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.tck")
@SpringBootApplication
public class TicketManagementApplication {
    public static void main(String[] args) {
        SpringApplication.run(TicketManagementApplication.class, args);
    }
}
