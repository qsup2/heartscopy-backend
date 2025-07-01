package com.heartscopy.heartsocpyBeckEnd.controller.commentcontroller;

import com.heartscopy.heartsocpyBeckEnd.domain.comment.Comment;
import com.heartscopy.heartsocpyBeckEnd.dto.comment.CommentRequestDto;
import com.heartscopy.heartsocpyBeckEnd.service.commentservice.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    @PostMapping
    public ResponseEntity<Comment> createComment(@RequestBody CommentRequestDto dto) {
        return ResponseEntity.ok(commentService.createComment(dto));
    }

    @GetMapping("/lenze/{lenzeId}")
    public ResponseEntity<Page<Comment>> getCommentsByLenzeId(
            @PathVariable int lenzeId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getCommentsByLenzeId(lenzeId, page, size));
    }


    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deleteComment(@PathVariable Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/agree")
    public ResponseEntity<Void> agreeComment(@PathVariable Long id) {
        commentService.agreeComment(id);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{id}/disagree")
    public ResponseEntity<Void> disagreeComment(@PathVariable Long id) {
        commentService.disagreeComment(id);
        return ResponseEntity.ok().build();
    }
}