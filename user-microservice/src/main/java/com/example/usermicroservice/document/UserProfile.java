package com.example.usermicroservice.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Document   // MongoDB'de @Entity yerine @Document kullanılır
public class UserProfile {
    @Id
    private String id;  // MongoDB'de id için genellikle String kullanılır

    private Long authId;
    private String username;
    private String firstname;
    private String lastname;
    private String email;
    private String phone;
    private String avatar;
    private String instagram;
    private String twitter;
    private Boolean isActive;
    private Long createdAt;
}
