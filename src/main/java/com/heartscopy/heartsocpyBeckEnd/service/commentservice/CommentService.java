package com.heartscopy.heartsocpyBeckEnd.service.commentservice;

import com.heartscopy.heartsocpyBeckEnd.domain.comment.Comment;
import com.heartscopy.heartsocpyBeckEnd.dto.comment.CommentRequestDto;
import com.heartscopy.heartsocpyBeckEnd.repository.commentrepository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 댓글 생성 시 writerId를 컨트롤러에서 전달받은 uid로 받음
     */
    public Comment createComment(CommentRequestDto dto, String uid) {
        Comment comment = Comment.builder()
                .lenzeId(dto.getLenzeId())
                .writerId(uid)  // 클라이언트가 아닌 서버에서 인증된 UID 사용
                .comment(dto.getComment())
                .agree(0)
                .disagree(0)
                .build();
        return commentRepository.save(comment);
    }

    @Transactional
    public void deleteComment(Long commentId, String uid) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));
        if (!comment.getWriterId().equals(uid)) {
            throw new SecurityException("권한이 없습니다.");
        }
        commentRepository.deleteById(commentId);
    }

    @Transactional
    public void agreeComment(Long commentId, String uid) {
        // 추가: 필요 시 권한 체크 또는 중복 동의 방지 로직 넣을 수 있음
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setAgree(comment.getAgree() + 1);
    }

    @Transactional
    public void disagreeComment(Long commentId, String uid) {
        // 추가: 필요 시 권한 체크 또는 중복 반대 방지 로직 넣을 수 있음
        Comment comment = commentRepository.findById(commentId).orElseThrow();
        comment.setDisagree(comment.getDisagree() + 1);
    }

    public Page<Comment> getCommentsByLenzeId(int lenzeId, int page, int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return commentRepository.findByLenzeId(lenzeId, pageRequest);
    }
}