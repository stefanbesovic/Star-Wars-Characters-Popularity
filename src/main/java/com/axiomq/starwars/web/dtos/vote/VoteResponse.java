package com.axiomq.starwars.web.dtos.vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteResponse {
    private Long id;
    private Integer value;
    private String comment;
    private String icon;
    private String url;
}