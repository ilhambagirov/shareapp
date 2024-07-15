//package api.extensions;
//
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
//import org.hibernate.boot.registry.StandardServiceRegistry;
//import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
//import org.hibernate.tool.hbm2ddl.SchemaExport;
//import org.hibernate.tool.schema.TargetType;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.core.io.Resource;
//import org.springframework.core.io.ResourceLoader;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.EnumSet;
//
//public class SchemaExportConfig {
//
//    private final ResourceLoader resourceLoader;
//
//    public SchemaExportConfig(ResourceLoader resourceLoader) {
//        this.resourceLoader = resourceLoader;
//    }
//
//    @Bean
//    public CommandLineRunner generateSchema() {
//        return args -> {
//
//            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder()
//                    .configure("hibernate.cfg.xml") // Ensure this file is in the classpath
//                    .build();
//
//            Metadata metadata = new MetadataSources(standardRegistry)
//                    .buildMetadata();
//
//            SchemaExport export = new SchemaExport();
//
//            String path = String.format("src/main/resources/db/migration/V%s__initial_schema.sql", countFilesInFolder("classpath:db/migration"));
//            export.setOutputFile(path);
//            export.setDelimiter(";");
//            export.setFormat(true);
//
//            export.execute(EnumSet.of(TargetType.SCRIPT), SchemaExport.Action.CREATE, metadata);
//
//            System.out.println("Schema export completed.");
//            StandardServiceRegistryBuilder.destroy(standardRegistry);
//        };
//    }
//
//    private int countFilesInFolder(String folderPath) throws IOException {
//        Resource resource = this.resourceLoader.getResource(folderPath);
//        File folder = resource.getFile();
//        File[] files = folder.listFiles();
//
//        return files != null ? files.length + 1 : 1;
//    }
//}
//
////private int readSchemaVersion() {
////    Flyway flyway = Flyway.configure().dataSource(dataSource).load();
////    MigrationInfo[] migrations = flyway.info().applied();
////
////    // Assuming the latest migration version represents the schema version
////    if (migrations.length > 0) {
////        return Integer.parseInt(migrations[migrations.length - 1].getVersion().getVersion());
////    } else {
////        return 0; // No migrations applied yet
////    }
////}
