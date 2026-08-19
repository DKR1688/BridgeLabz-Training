package com.addressbook;

import static org.assertj.core.api.Assertions.assertThat;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AddressBookIntegrationTest {
    @LocalServerPort private int port;
    private final HttpClient httpClient = HttpClient.newHttpClient();

    @Test
    void allAddressEndpointsWork() throws Exception {
        HttpResponse<String> createResponse = request("POST", "/addresses", validAddressJson("Asha Sharma", "12 Lake Road", "Mumbai", "Maharashtra", "400001"));
        int createdId = Integer.parseInt(createResponse.body().replaceAll(".*\\\"addressId\\\":(\\d+).*", "$1"));
        assertThat(createdId).isPositive();

        HttpResponse<String> fetched = request("GET", "/addresses/" + createdId, null);
        assertThat(fetched.statusCode()).isEqualTo(200);
        assertThat(fetched.body()).contains("Asha Sharma");

        HttpResponse<String> all = request("GET", "/addresses", null);
        assertThat(all.statusCode()).isEqualTo(200);
        assertThat(all.body()).contains("\"addressId\":" + createdId);

        HttpResponse<String> cityMatches = request("GET", "/addresses?city=mumbai", null);
        assertThat(cityMatches.statusCode()).isEqualTo(200);
        assertThat(cityMatches.body()).contains("Asha Sharma");

        HttpResponse<String> updateResponse = request("PUT", "/addresses/" + createdId, validAddressJson("Asha Verma", "99 Park Street", "Pune", "Maharashtra", "411001"));
        assertThat(updateResponse.statusCode()).isEqualTo(200);
        assertThat(updateResponse.body()).contains("Pune");

        HttpResponse<String> nameMatches = request("GET", "/addresses?name=verma", null);
        assertThat(nameMatches.statusCode()).isEqualTo(200);
        assertThat(nameMatches.body()).contains("Asha Verma");

        assertThat(request("DELETE", "/addresses/" + createdId, null).statusCode()).isEqualTo(204);
        assertThat(request("GET", "/addresses/" + createdId, null).statusCode()).isEqualTo(404);
        assertThat(request("DELETE", "/addresses/99999", null).statusCode()).isEqualTo(404);
    }

    @Test
    void createRejectsInvalidAddress() throws Exception {
        assertThat(request("POST", "/addresses", "{}").statusCode()).isEqualTo(400);
    }

    private HttpResponse<String> request(String method, String path, String jsonBody) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path));
        if (jsonBody == null) builder.method(method, HttpRequest.BodyPublishers.noBody());
        else builder.method(method, HttpRequest.BodyPublishers.ofString(jsonBody))
                .header("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        HttpResponse<String> response = httpClient.send(builder.build(), HttpResponse.BodyHandlers.ofString());
        if (method.equals("POST") && !jsonBody.equals("{}")) assertThat(response.statusCode()).isEqualTo(201);
        return response;
    }

    private String validAddressJson(String name, String street, String city, String state, String zip) {
        return "{\"contactName\":\"" + name + "\",\"street\":\"" + street
                + "\",\"city\":\"" + city + "\",\"state\":\"" + state
                + "\",\"zipCode\":\"" + zip + "\"}";
    }
}
