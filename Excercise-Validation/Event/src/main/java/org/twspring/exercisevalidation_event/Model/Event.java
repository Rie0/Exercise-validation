package org.twspring.exercisevalidation_event.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class Event {
    @NotNull(message = "ID cannot be empty")
    @Length(min=3, message = "ID length must be 3 or more")
    private String id;

    @NotEmpty(message = "Description cannot be empty")
    @Length(min = 16, message = "Description must be at least 16 letters")
    private String description;

    @NotNull(message = "Capacity cannot be empty")
    @NumberFormat(style = NumberFormat.Style.NUMBER)
    @Range(min=26, message = "Capacity must be at least 26")
    private int capacity;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "Start date cannot be in the past")
    public LocalDate start_date;

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "End date cannot be in the past or today")
    public LocalDate end_date;
}
