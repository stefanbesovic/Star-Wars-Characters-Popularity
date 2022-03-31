package com.axiomq.starwars.web.dtos.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteRequest {

    @NotNull
    @Range(min = 1, max = 10, message = "Value must be between 1 and 10.")
    private Integer value;

    @Size(max = 120, message = "Comment can hold only 120 characters.")
    private String comment;

    @NotNull
    @Range(min = 1, message = "Character id must be a positive value.")
    private Long characterId;
}