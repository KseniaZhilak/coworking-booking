package com.kseniazilak.coworkingbooking.exception;

public record ApiError(
        String status, String reason, String message, String timestamp
) {
}
