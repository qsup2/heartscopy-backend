package com.heartscopy.heartsocpyBeckEnd.repository.commentrepository;

import com.heartscopy.heartsocpyBeckEnd.domain.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface CommentRepository extends JpaRepository<Comment, Long> {

    Page<Comment> findByLenzeId(long lenzeId, Pageable pageable);
}