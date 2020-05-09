package dev.oscaz.deadmansswitch.pojo;

public class WebSocketMessage {

    // POJO for containing socket message (username, message, leave, join, etc)

    private String contentType;
    private String contents;

    private String rawJson;

    public WebSocketMessage(String type, String contents) {
        this.contentType = type;
        this.contents = contents;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public void setRawJson(String rawJson) {
        this.rawJson = rawJson;
    }

    public String getRawJson() {
        return this.rawJson;
    }

}
