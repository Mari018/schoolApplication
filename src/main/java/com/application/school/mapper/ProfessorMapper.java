package com.application.school.mapper;

import com.application.school.dto.ProfessorDto;
import com.application.school.entity.Professor;
import org.mapstruct.factory.Mappers;

public interface ProfessorMapper {

    ProfessorMapper INSTANCE = Mappers.getMapper(ProfessorMapper.class);

    Professor professorDtoToProfessor(ProfessorDto professorDto);

    ProfessorDto professorToProfessorDto(Professor professor);
}
