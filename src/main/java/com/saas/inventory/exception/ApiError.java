package com.saas.inventory.exception;

import lombok.Data;

@Data
public class ApiError {
        private int status;
        private String message;
        private String timestamp;

        public ApiError(int status, String message) {
            this.status = status;
            this.message = message;
            this.timestamp = java.time.LocalDateTime.now().toString();
        }
    }

