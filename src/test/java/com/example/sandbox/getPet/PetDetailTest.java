package com.example.sandbox.getPet;

import com.example.sandbox.Common;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.TreeMap;

import static com.example.sandbox.util.constans.Tags.REGRESSION;

public class PetDetailTest extends Common {

    @Test(enabled = true, groups = {REGRESSION}, description = "description")
    public void getPetsWithAvailableStatusTest() {
        TreeMap<String, String> queryParams = new TreeMap<>();
        queryParams.put("status", "available");

        Response response = getUrl(findByStatus, queryParams);
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");

        if (response.jsonPath().get("[0].id") != null) {
            String id = response.jsonPath().get("[0].id").toString();

            Response response2 = getUrl(petById, id);
            Assert.assertEquals(response2.getStatusCode(), 200, "Invalid response code");

            String status = response2.jsonPath().get("status");
            Assert.assertEquals(status, "available", "Pet status must be available! Current status is " + status);
        } else {
            Assert.assertNull(response.jsonPath().get("[0].id"), "Response body not containing any queried pet");
        }
    }

    @Test(enabled = true, groups = {REGRESSION}, description = "description")
    public void getPetWithInvalidStatusValueTest() {
        TreeMap<String, String> queryParams = new TreeMap<>();
        queryParams.put("status", "invalid");

        Response response = getUrl(findByStatus, queryParams);
        Assert.assertEquals(response.getStatusCode(), 400, "response code must be 400 - Invalid status value");
    }
}
