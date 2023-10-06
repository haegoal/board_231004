package com.icia.board.service;

import com.icia.board.dto.CommentDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.entity.CommentEntity;
import com.icia.board.repository.BoardRepository;
import com.icia.board.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public void save(CommentDTO commentDTO) {
        BoardEntity saveEntity = boardRepository.findById(commentDTO.getBoardId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSave(commentDTO, saveEntity);
        commentRepository.save(commentEntity);
    }

    public List<CommentDTO> findByBoardId(Long boardId) {
        BoardEntity boardEntity = boardRepository.findById(boardId).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBoardEntityOrderByCreatedAtDesc (boardEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(entity -> {
            commentDTOList.add(CommentDTO.toDTO(entity));
        });
        return commentDTOList;
    }
}
