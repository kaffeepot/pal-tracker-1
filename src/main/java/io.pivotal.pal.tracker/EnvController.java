package io.pivotal.pal.tracker;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class EnvController {

    private String port;
    private String memLimit;
    private String cfInstIndx;
    private String cfInstAddr;

    public EnvController(@Value("${PORT:NOT SET}")String port, @Value("${MEMORY_LIMIT:NOT SET}")String memLimit, @Value("${CF_INSTANCE_INDEX:NOT SET}")String cfInstIndx, @Value("${CF_INSTANCE_ADDR:NOT SET}")String cfInstAddr) {
        this.cfInstAddr = cfInstAddr;
        this.cfInstIndx = cfInstIndx;
        this.memLimit = memLimit;
        this.port = port;

    }

    @GetMapping("/env")
    public Map<String, String> getEnv() {
        Map<String, String> env = new HashMap<>();
        env.put("PORT", port);
        env.put("MEMORY_LIMIT", memLimit);
        env.put("CF_INSTANCE_INDEX", cfInstIndx);
        env.put("CF_INSTANCE_ADDR", cfInstAddr);

        return env;
    }
}
