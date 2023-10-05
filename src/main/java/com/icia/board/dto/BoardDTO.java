package com.icia.board.dto;

import com.icia.board.entity.BoardEntity;
import com.icia.board.utill.UtillClass;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardTitle;
    private String boardPass;
    private String boardContents;
    private String createdAt;
    private int boardHits;

    public static BoardDTO toDTO(BoardEntity entity) {
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(entity.getId());
        boardDTO.setBoardWriter(entity.getBoardWriter());
        boardDTO.setBoardPass(entity.getBoardPass());
        boardDTO.setBoardTitle(entity.getBoardTitle());
        boardDTO.setBoardContents(entity.getBoardContents());
        boardDTO.setBoardHits(entity.getBoardHits());
        boardDTO.setCreatedAt(UtillClass.dateTimeFormat(entity.getCreatedAt()));
        return boardDTO;
//        BoardDTO boardDTO = BoardDTO.builder()
//                .id(entity.getId())
//                .boardWriter(entity.getBoardWriter())
//                .boardContents(entity.getBoardContents())
//                .boardPass(entity.getBoardPass())
//                .boardTitle(entity.getBoardTitle())
//                .boardHits(entity.getBoardHits())
//                .createdAt(UtillClass.dateTimeFormat(entity.getCreatedAt()))
//                .build();
    }
}
