package com.icia.board.controller;

import com.icia.board.dto.CommentDTO;
import com.icia.board.repository.CommentRepository;
import com.icia.board.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;


    @GetMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDTO){
        commentService.save(commentDTO);
        List<CommentDTO> commentDTOList = commentService.findByBoardId(commentDTO.getBoardId());
        return new ResponseEntity<>(commentDTOList,HttpStatus.OK);
    }

}
