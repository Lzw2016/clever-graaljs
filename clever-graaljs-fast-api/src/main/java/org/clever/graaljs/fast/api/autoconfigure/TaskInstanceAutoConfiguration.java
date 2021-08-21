package org.clever.graaljs.fast.api.autoconfigure;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.fast.api.config.FastApiConfig;
import org.clever.task.core.TaskInstance;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 作者：lizw <br/>
 * 创建时间：2021/08/21 19:46 <br/>
 */
@AutoConfigureOrder(Ordered.LOWEST_PRECEDENCE)
@EnableConfigurationProperties({FastApiConfig.class})
@Configuration
@Slf4j
public class TaskInstanceAutoConfiguration implements CommandLineRunner {
    private final TaskInstance taskInstance;

    public TaskInstanceAutoConfiguration(TaskInstance taskInstance) {
        this.taskInstance = taskInstance;
    }

    @Override
    public void run(String... args) {
        taskInstance.start();
    }
}
