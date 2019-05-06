package pl.solutions.software.nowak.wawrzyniec.controller;

public class Response {

    public Response(Status status, String message) {
        this.status = status;
        this.message = message;
    }

    public enum Status {
        SUCCESS, FAILURE
    }

    private Status status;

    private String message;

    public Status getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
