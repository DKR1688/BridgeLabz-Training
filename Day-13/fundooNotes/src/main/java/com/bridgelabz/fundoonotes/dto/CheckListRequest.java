package com.bridgelabz.fundoonotes.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record CheckListRequest(
        @NotBlank(message = "Item name cannot be blank") @JsonProperty("itemName") @JsonAlias({
                "name", "item" }) String itemName,

        @JsonProperty("status") String status,

        @JsonProperty("isDeleted") Boolean isDeleted) {
    public String resolvedStatus() {
        return (status != null && !status.isBlank()) ? status : "PENDING";
    }

    public boolean resolvedIsDeleted() {
        return isDeleted != null && isDeleted;
    }
}
