package com.axiomq.starwars.web.dtos.vote;

import com.axiomq.starwars.entities.Vote;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VoteMapper {
    VoteMapper INSTANCE = Mappers.getMapper(VoteMapper.class);

    Vote fromReqDto(VoteRequest voteRequest);

    Vote fromUpdDto(VoteUpdateDto voteUpdateDto);

    @Mapping(source = "character.id", target = "characterId")
    VoteResponse toResDto(Vote vote);
}