package com.heartscopy.heartsocpyBeckEnd.repository.choicerepository;

import com.heartscopy.heartsocpyBeckEnd.domain.choice.Choice;
import com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeWithCountDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


import java.util.List;
import java.util.Optional;

public interface ChoiceRepository extends JpaRepository<Choice, Long> {

    Optional<Choice> findByUserIdAndLenzeId(String userId, Long lenzeId);

    void deleteByUserIdAndLenzeId(String userId, Long lenzeId);

    @Query("SELECT new com.heartscopy.heartsocpyBeckEnd.dto.lenze.LenzeWithCountDto(c.lenzeId, COUNT(c), c.topic) " +
            "FROM Choice c " +
            "GROUP BY c.lenzeId, c.topic " +
            "ORDER BY COUNT(c) DESC")
    List<LenzeWithCountDto> findTop10LenzesByChoiceCount(org.springframework.data.domain.Pageable pageable);

    void deleteAllByLenzeId(Long lenzeId);

    @Query("""
    SELECT c2.userId, COUNT(c2)
    FROM Choice c1
    JOIN Choice c2
      ON c1.lenzeId = c2.lenzeId
     AND c1.selectedValue = c2.selectedValue
    WHERE c1.userId = :userId
      AND c2.userId <> :userId
    GROUP BY c2.userId
    ORDER BY COUNT(c2) DESC
""")
    List<Object[]> findUsersWithMostMatchingChoices(@Param("userId") String userId);

    List<Choice> findByUserIdOrderByChooseAtDesc(String userId);
}