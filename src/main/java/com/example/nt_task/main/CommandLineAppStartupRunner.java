package com.example.nt_task.main;


import com.example.nt_task.service.AppService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class CommandLineAppStartupRunner implements CommandLineRunner {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineAppStartupRunner.class);

    @Autowired
    protected AppService appService;

    @Autowired
    protected ConfigurableApplicationContext ctx;

    @Override
    public void run(String...args) throws Exception {
        logger.info("Application started with command-line arguments: {} . \n To kill this application, press Ctrl + C.", Arrays.toString(args));
        if (args.length != 0 && args[0].equals("-p")) {
            appService.printAllTimes().forEach(System.out::println);
            ctx.close();
        }
    }
}