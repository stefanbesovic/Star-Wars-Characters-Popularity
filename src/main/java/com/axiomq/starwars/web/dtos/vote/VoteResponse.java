package com.axiomq.starwars.web.dtos.vote;
import javax.persistence.Transient;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Base64;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Vote response")
public class VoteResponse {

    @Schema(description = "Vote's id")
    private Long id;

    @Schema(description = "Vote's value")
    private Integer value;

    @Schema(description = "Vote's comment")
    private String comment;

    @Schema(description = "Vote's icon")
    private String icon;

    @Schema(description = "Icon's full path")
    private String url;

    @Schema(description = "Id of character")
    private Long characterId;

}
