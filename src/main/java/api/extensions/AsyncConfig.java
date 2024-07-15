package api.extensions;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.concurrent.DelegatingSecurityContextRunnable;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
@EnableAsync // Enable asynchronous execution
public class AsyncConfig implements AsyncConfigurer {

    @Bean("asyncExecutor") // Bean name for the Executor
    public TaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);  // Number of threads to keep alive in the pool, even if they are idle
        executor.setMaxPoolSize(10);   // Maximum number of threads to allow in the pool
        executor.setQueueCapacity(25); // Number of tasks that can be queued if all threads are busy
        executor.setThreadNamePrefix("AsyncExecutor-");
        executor.setThreadGroupName("pool-group");
//        executor.setTaskDecorator(new TaskDecorator() {
//            @NotNull
//            @Override
//            public Runnable decorate(@NotNull Runnable runnable) {
//                final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//                return () -> {
//                    try {
//                        SecurityContextHolder.getContext().setAuthentication(auth);
//                        runnable.run();
//                    } finally {
//                        SecurityContextHolder.clearContext();
//                    }
//                };
//            }
//        });
        executor.setTaskDecorator(DelegatingSecurityContextRunnable::new);
        executor.initialize();

        SecurityContextHolder.setStrategyName(SecurityContextHolder.MODE_INHERITABLETHREADLOCAL);

        return executor;
    }

//    private final ThreadPoolTaskExecutor defaultSpringBootAsyncExecutor;
//
//    public AsyncConfig(ThreadPoolTaskExecutor defaultSpringBootAsyncExecutor) {
//        this.defaultSpringBootAsyncExecutor = defaultSpringBootAsyncExecutor;
//    }
//
//    @Override
//    public Executor getAsyncExecutor() {
//        return new DelegatingSecurityContextAsyncTaskExecutor(defaultSpringBootAsyncExecutor);
//    }
}