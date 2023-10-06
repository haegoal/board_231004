package com.icia.board.dto;

import com.icia.board.entity.CommentEntity;
import com.icia.board.utill.UtillClass;
import lombok.Data;

@Data
public class CommentDTO {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private String createdAt;

    public static CommentDTO toDTO(CommentEntity entity) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(entity.getId());
        commentDTO.setCommentWriter(entity.getCommentWriter());
        commentDTO.setCommentContents(entity.getCommentContents());
        commentDTO.setCreatedAt(UtillClass.dateTimeFormat(entity.getCreatedAt()));
        return commentDTO;
    }
}
