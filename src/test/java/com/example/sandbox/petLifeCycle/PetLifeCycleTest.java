package com.example.sandbox.petLifeCycle;

import com.example.sandbox.Common;
import com.example.sandbox.businessProcesses.PetLifeCycle;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static com.example.sandbox.util.constans.Tags.SMOKE;

public class PetLifeCycleTest extends Common {
    private final PetLifeCycle plc = new PetLifeCycle();
    @BeforeClass
    public void createNewPetTest() {
        Response response = plc.createPet("available", "Bukfenc", 100);
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");
    }

    @Test(enabled = true, groups = {SMOKE}, description = "description")
    public void updatePetTest() {
        Response responseOld = getUrl(petById, "100");
        String oldStatus = responseOld.jsonPath().get("status");
        Assert.assertEquals(oldStatus, "available", "Status must be available");

        Response response = plc.updatePet();
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");
        Response responseNew = getUrl(petById, "100");
        String newStatus = responseNew.jsonPath().get("status");
        Assert.assertEquals(newStatus, "sold", "Status must be sold");
    }

    @AfterClass
    public void deletePetTest() {
        Response responseBeforeDeletion = getUrl(petById, "100");
        Assert.assertEquals(responseBeforeDeletion.getStatusCode(), 200, "Invalid response code");

        Response response = plc.deletePet("100");
        Assert.assertEquals(response.getStatusCode(), 200, "Invalid response code");

        Response responseAfterDeletion = getUrl(petById, "100");
        Assert.assertEquals(responseAfterDeletion.getStatusCode(), 404, "Invalid response code");
    }
}
