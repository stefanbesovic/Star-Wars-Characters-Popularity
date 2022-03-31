package com.axiomq.starwars.web.dtos.vote;

import com.axiomq.starwars.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    VoteRequest toReqDto(Vote vote);
    Vote fromReqDto(VoteRequest voteRequest);

    VoteResponse toResDto(Vote vote);
    Vote fromResDto(VoteResponse voteResponse);
}