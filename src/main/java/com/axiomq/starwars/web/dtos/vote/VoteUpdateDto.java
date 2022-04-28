package com.axiomq.starwars.web.dtos.vote;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Vote update object")
public class VoteUpdateDto {

    @Schema(description = "User's rate of Character")
    @Range(min = 1, max = 10, message = "Value must be between 1 and 10.")
    private Integer value;

    @Schema(description = "Comment about character")
    @Size(max = 120, message = "Comment can hold only 120 characters.")
    private String comment;
}
