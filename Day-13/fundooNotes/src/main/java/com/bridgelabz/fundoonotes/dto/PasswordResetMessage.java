package com.bridgelabz.fundoonotes.dto;

import java.io.Serializable;

public record PasswordResetMessage(
                String email,
                String resetToken) implements Serializable {
}
