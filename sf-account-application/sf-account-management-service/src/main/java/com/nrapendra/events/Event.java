package com.nrapendra.events;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@Entity
@Table(name = "application_data")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Event {

    private Long Id;

    private LocalDate creationTime;

    @Column(columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<Object, Object> eventData = new HashMap<>();

}
