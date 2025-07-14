package com.heartscopy.heartsocpyBeckEnd.repository.lenzerepository;

import com.heartscopy.heartsocpyBeckEnd.domain.lenze.Lenze;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface LenzeRepository extends JpaRepository<Lenze, Long> {
    @Query(value = "SELECT * FROM lenze ORDER BY RAND() LIMIT :#{#pageable.pageSize} OFFSET :#{#pageable.offset}",
            countQuery = "SELECT COUNT(*) FROM lenze",
            nativeQuery = true)
    Page<Lenze> findRandomLenzes(Pageable pageable);


    @Query("SELECT l FROM Lenze l WHERE l.topic LIKE CONCAT('%', :keyword, '%') ORDER BY l.createdAt DESC")
    Page<Lenze> findByTopicContainingOrderByCreatedAtDesc(@Param("keyword") String keyword, Pageable pageable);

    List<Lenze> findTop10ByOrderByCreatedAtDesc();
}

