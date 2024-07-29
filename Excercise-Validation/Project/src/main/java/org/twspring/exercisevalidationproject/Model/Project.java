package org.twspring.exercisevalidationproject.Model;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

@Data
@AllArgsConstructor
public class Project {
    @NotEmpty(message="id cannot be empty")
    @Length(min=3, message = "ID length must be 3 or more")
    private String id;

    @NotEmpty(message="title cannot be empty")
    @Length(min = 9, message="title cannot be less than 9 letters")
    private String title;

    @NotEmpty(message="description cannot be empty")
    @Length(min = 16, message="description cannot have less than 16 letters")
    private String description;

    @NotEmpty(message="status cannot be empty")
    @Pattern(regexp="^(Not Started|In Progress|Completed)$",message="invalid status")
    private String status;

    @NotEmpty(message="company name cannot be empty")
    @Length(min = 7, message="company name cannot have less than 7 letters")
    private String company_name;
}
