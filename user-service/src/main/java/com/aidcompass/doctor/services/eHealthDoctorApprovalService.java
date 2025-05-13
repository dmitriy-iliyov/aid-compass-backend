//package com.aidcompass.doctor.services;
//
//import com.aidcompass.doctor.models.dto.doctor.PrivateDoctorResponseDto;
//import com.fasterxml.jackson.databind.JsonNode;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.springframework.web.client.RestClient;
//
//import java.io.IOException;
//
//public class eHealthDoctorApprovalService implements DoctorApprovalService {
//
//    private RestClient restClient = RestClient.builder().build();
//
//
//    public void login() {
//    }
//
//    public PrivateDoctorResponseDto getData(String employeeId) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        String jsonResponse = "";
//        try {
//            // Парсим JSON в дерево объектов JsonNode
//            JsonNode rootNode = objectMapper.readTree(jsonResponse);
//
//            // Извлекаем нужные данные
//            String id = rootNode.path("data").path("id").asText();
//            String firstName = rootNode.path("data").path("party").path("first_name").asText();
//            String lastName = rootNode.path("data").path("party").path("last_name").asText();
//            String position = rootNode.path("data").path("position").asText();
//            String startDate = rootNode.path("data").path("start_date").asText();
//            boolean isActive = rootNode.path("data").path("is_active").asBoolean();
//            String taxId = rootNode.path("data").path("party").path("tax_id").asText();
//            String phone = rootNode.path("data").path("party").path("phones").get(0).path("number").asText();
//
//            // Создаём объект, куда маппим данные
//            System.out.println("ID: " + id);
//            System.out.println("First Name: " + firstName);
//            System.out.println("Last Name: " + lastName);
//            System.out.println("Position: " + position);
//            System.out.println("Start Date: " + startDate);
//            System.out.println("Is Active: " + isActive);
//            System.out.println("Tax ID: " + taxId);
//            System.out.println("Phone: " + phone);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return new PrivateDoctorResponseDto();
//    }
//}
