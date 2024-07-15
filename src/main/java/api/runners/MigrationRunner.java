//package api.runners;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//import org.springframework.context.annotation.AnnotationConfigApplicationContext;
//import org.springframework.context.annotation.ComponentScan;
//import org.springframework.core.env.Environment;
//import api.extensions.SchemaExportConfig;
//
//@SpringBootApplication
//@ComponentScan("api.*")
//public class MigrationRunner {
//
//    public static void main(String[] args) {
//        try (AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SchemaExportConfig.class)) {
//            CommandLineRunner schemaExportRunner = context.getBean(CommandLineRunner.class);
//            Environment environment = context.getBean(Environment.class);
//
//            if (environment.acceptsProfiles("generate-schema")) {
//                try {
//                    schemaExportRunner.run(args);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            } else {
//                System.out.println("Profile 'generate-schema' not active. Skipping schema generation.");
//            }
//        }
//    }
//}
//
//
