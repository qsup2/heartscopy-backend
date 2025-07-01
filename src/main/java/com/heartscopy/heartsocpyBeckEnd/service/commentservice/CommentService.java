package com.heartscopy.heartsocpyBeckEnd.service.commentservice;

import com.heartscopy.heartsocpyBeckEnd.domain.comment.Comment;
import com.heartscopy.heartsocpyBeckEnd.dto.comment.CommentRequestDto;
import com.heartscopy.heartsocpyBeckEnd.repository.commentrepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    public Comment createComment(CommentRequestDto dto) {
        Comment comment = Comment.builder()
                .lenzeId(dto.getLenzeId())
                .writerId(dto.getWriterId())
                .comment(dto.getComment())
                .agree(0)
                .disagree(0)
                .build();
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void agreeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setAgree(comment.getAgree() + 1);
    }

    @Transactional
    public void disagreeComment(Long commentId) {
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setDisagree(comment.getDisagree() + 1);
    }

    public Page<Comment> getCommentsByLenzeId(int lenzeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return commentRepository.findByLenzeId(lenzeId, pageRequest);
    }
}