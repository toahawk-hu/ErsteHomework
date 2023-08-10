package com.example.sandbox.businessProcesses;

import com.example.sandbox.Common;
import io.restassured.response.Response;
import model.Pet;

public class PetLifeCycle extends Common {
    public Response createPet(String status, String name, int petId) {
        Pet createdPet = new Pet(status, name, petId);
        String jsonOfPet = generateJsonOfPet(createdPet.getName(), createdPet.getStatus(), createdPet.getId());
        return postUrl(newPet, jsonOfPet);
    }

    public Response updatePet() {
        Pet updatedPet = new Pet("sold", "Bukfenc", 100);
        String jsonOfPet = generateJsonOfPet(updatedPet.getName(), updatedPet.getStatus(), updatedPet.getId());
        return putUrl(newPet, jsonOfPet);
    }

    public Response deletePet(String petId) {
        return deleteUrl(petById, petId);
    }

    public static String generateJsonOfPet(String name, String status, int id) {
        return "{\"id\": " + id + " , \"name\": \""
                + name + "\", \"photoUrls\": [], \"status\": \""
                + status + "\"}";
    }
}
