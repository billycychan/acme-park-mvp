package com.billycychan.acmepark.permit_service.dto;

public enum PermitValidatedEvent {
    VALIDATED {
        @Override
        public String getAssociatedMessage() {
            return "Permit has been successfully validated.";
        }
    },
    EXPIRED {
        @Override
        public String getAssociatedMessage() {
            return "Permit has expired.";
        }
    },
    NOT_REGISTERED {
        @Override
        public String getAssociatedMessage() {
            return "Permit is not registered.";
        }
    },
    TIMEOUT {
        @Override
        public String getAssociatedMessage() {
            return "Permit validation timed out.";
        }
    };

    // Abstract method to be implemented by each enum constant
    public abstract String getAssociatedMessage();
}