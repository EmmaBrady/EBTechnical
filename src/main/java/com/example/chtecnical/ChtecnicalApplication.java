package com.example.chtecnical;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.org.slf4j.internal.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

@SpringBootApplication
public class ChtecnicalApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChtecnicalApplication.class, args);
    }
}