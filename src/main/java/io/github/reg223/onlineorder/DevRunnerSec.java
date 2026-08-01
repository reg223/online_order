package io.github.reg223.onlineorder;

import io.github.reg223.onlineorder.services.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class DevRunnerSec implements ApplicationRunner {
    private static final Logger logger =  LoggerFactory.getLogger(DevRunnerSec.class);
    private final CustomerService customerService;

    public DevRunnerSec(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("Running DevRunnerSec");
        customerService.SignUp("foo","bar","John","Doe");
    }
}
