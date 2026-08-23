package com.bridgelabz.fundoonotes.dto;

public record BatchJobResponse(
                long readCount,
                long writeCount,
                long skipCount,
                String status,
                String message) {
}
