package dev.gedfalk.astonproject.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEventDto {

    public enum EventType {
        USER_CREATED,
        USER_DELETED
    }

    private EventType eventType;
    private String email;
    private String userName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime eventTimestamp;

    private Integer userId;
}
