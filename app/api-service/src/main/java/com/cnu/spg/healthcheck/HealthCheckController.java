package com.cnu.spg.healthcheck;

import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @ApiOperation("health check용도로 사용")
    @GetMapping("/api/v1/health-check")
    public String healthCheck() {
        return "healthcheck";
    }
}
