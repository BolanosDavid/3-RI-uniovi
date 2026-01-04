package uo.ri.cws.application.persistence;

import java.time.LocalDateTime;

public class BasicRecord {
    public String id;
    public String entityState;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public Long version;
}
