package com.heartscopy.heartsocpyBeckEnd.mapper;

import com.heartscopy.heartsocpyBeckEnd.dto.mate.LenzeChoiceDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ChoiceMapper {

    // Step 1: 특정 사용자의 선택 목록 조회
    @Select("SELECT lenze_id AS lenzeId, selected_value AS selectedValue " +
            "FROM choice WHERE user_id = #{userId}")
    List<LenzeChoiceDto> findChoicesByUserId(@Param("userId") String userId);

    // Step 2: 특정 lenzeId + selectedValue 에 해당하는 userId 목록 조회
    @Select("<script>" +
            "SELECT user_id FROM choice " +
            "WHERE <foreach collection='choices' item='c' separator=' OR '>" +
            "(lenze_id = #{c.lenzeId} AND selected_value = #{c.selectedValue})" +
            "</foreach> AND user_id != #{userId}" +  // 본인은 제외
            "</script>")
    List<String> findMatchingUserIds(@Param("choices") List<LenzeChoiceDto> choices,
                                     @Param("userId") String userId);
}