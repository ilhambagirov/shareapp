gradle clean build dynamicRun -> standard application build + run.
gradle clean build dynamicRun -PmainClass=api.runners.MigrationRunner -PspringProfiles=generate-schema -> Schema exporter for changes related to persistence. Seperate function to generate sql script based on Hibernate.
"# shareapp" 
