package com.aidcompass.avatar.configs;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CloudStorageConfig {

    @Value("${spring.cloud.azure.storage.blob.connection-string}")
    private String blobConnectionString;
    //      MinlO
    //      https://habr.com/ru/companies/veeam/articles/517392/
    //      https://min.io/docs/minio/linux/integrations/using-minio-with-veeam.html?ref=docs-redirect
//    @Bean
//    public BlobServiceClient blobServiceClient() {
//        return new BlobServiceClientBuilder().connectionString(connectionString).buildClient();
//    }
}
