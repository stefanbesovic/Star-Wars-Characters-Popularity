package com.axiomq.starwars.web.dtos.film;

import com.axiomq.starwars.entities.Film;
import com.axiomq.starwars.services.FilmService;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface FilmMapper {
    FilmMapper INSTANCE = Mappers.getMapper(FilmMapper.class);

    FilmDto toDto(Film film);
    Film fromDto(FilmDto filmDto);
}
