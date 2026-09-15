package com.socialmoneymanager.shared.web;

public record ApiResponse<T>(T data, String requestId) {
}
