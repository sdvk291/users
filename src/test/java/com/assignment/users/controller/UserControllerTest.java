package com.assignment.users.controller;

import com.assignment.users.dto.UserDTO;
import com.assignment.users.request.CreateUserRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.net.http.HttpHeaders;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UserControllerTest {

    @LocalServerPort
    private int port;

    public TestRestTemplate restTemplate = new TestRestTemplate();

    @Test
    public void testCreateStudent() throws Exception {
//        HttpHeaders headers = HttpHeaders.of("Authorization", "Basic " + Base64.getEncoder().encodeToString("username:password".getBytes()));
//        headers.setBasicAuth("username", "password"); // Set your username and password

        CreateUserRequest createUserRequest = new CreateUserRequest("testRandom", null, null, null, "edookatilerA$123");
//        HttpEntity<CreateUserRequest> requestEntity = new HttpEntity<>(createUserRequest, headers);

        ResponseEntity<UserDTO> response = restTemplate.postForEntity(createURLWithPort("/users/create"), createUserRequest, UserDTO.class);
        assertEquals(200, response.getStatusCodeValue());

        ResponseEntity<UserDTO> userDTOResponseEntity = restTemplate.getForEntity(createURLWithPort("/users/testRandom"), UserDTO.class);
        assertEquals(200, userDTOResponseEntity.getStatusCode().value());
        assertEquals("testRandom", userDTOResponseEntity.getBody().getUsername());
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }

//
//    HttpHeaders createHeaders(String username, String password){
//        return new HttpHeaders() {{
//            String auth = username + ":" + password;
//            byte[] encodedAuth = Base64.encodeBase64(
//                    auth.getBytes(Charset.forName("US-ASCII")) );
//            String authHeader = "Basic " + new String( encodedAuth );
//            set( "Authorization", authHeader );
//        }};
//    }
}
