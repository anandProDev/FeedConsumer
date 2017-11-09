package com.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({
        "msgId",
        "operation",
        "type",
        "timestamp"
})

public class Header {

    @JsonProperty(value = "msgId", required=true)
    private Integer msgId;
    @JsonProperty(value = "operation", required=true)
    private String operation;
    @JsonProperty(value = "type", required=true)
    private String type;
    @JsonProperty(value = "timestamp", required=true)
    private String timestamp;

    public int getMsgId() {
        return msgId;
    }

    public String getOperation() {
        return operation;
    }

    public String getType() {
        return type;
    }

    public String getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "{" +
                "msgId=" + msgId +
                ", operation='" + operation + '\'' +
                ", type='" + type + '\'' +
                ", timestamp='" + timestamp + '\'' +
                '}';
    }

    public static final class HeaderBuilder {
        private int msgId;
        private String operation;
        private String type;
        private String timestamp;

        private HeaderBuilder() {
        }

        public static HeaderBuilder aHeader() {
            return new HeaderBuilder();
        }

        public HeaderBuilder withMsgId(Integer msgId) {
            this.msgId = msgId;
            return this;
        }

        public HeaderBuilder withOperation(String operation) {
            this.operation = operation;
            return this;
        }

        public HeaderBuilder withType(String type) {
            this.type = type;
            return this;
        }

        public HeaderBuilder withTimestamp(String timestamp) {
            this.timestamp = timestamp;
            return this;
        }

        public Header build() {
            Header header = new Header();
            header.timestamp = this.timestamp;
            header.type = this.type;
            header.operation = this.operation;
            header.msgId = this.msgId;
            return header;
        }
    }
}
