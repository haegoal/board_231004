package com.icia.board.service;

import com.icia.board.dto.BoardDTO;
import com.icia.board.entity.BoardEntity;
import com.icia.board.entity.BoardFileEntity;
import com.icia.board.repository.BoardFileRepository;
import com.icia.board.repository.BoardRepository;
import com.icia.board.utill.UtillClass;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;


    public Long save(BoardDTO boardDTO) throws IOException {
        if (boardDTO.getBoardFile().get(0).isEmpty()){
            BoardEntity boardEntity = BoardEntity.toSave(boardDTO);
            return boardRepository.save(boardEntity).getId();
        }else{
            // 첨부파일 있음
            BoardEntity boardEntity = BoardEntity.toSaveEntityWithFile(boardDTO);
            // 게시글 저장처리 후 저장한 엔티티 가져옴
            BoardEntity savedEntity = boardRepository.save(boardEntity);
            // 파일 이름 처리, 파일 로컬에 저장 등
            // DTO에 담긴 파일 꺼내기
            for (MultipartFile boardFile: boardDTO.getBoardFile()) {
            // 업로드한 파일 이름
            String originalFilename = boardFile.getOriginalFilename();
            // 저장용 파일 이름
            String storedFileName = System.currentTimeMillis() + "_" + originalFilename;
            // 저장경로+파일이름 준비
            String savePath = "D:\\springboot_img\\" + storedFileName;
            // 파일 폴더에 저장
            boardFile.transferTo(new File(savePath));
            // 파일 정보 board_file_table에 저장
            // 파일 정보 저장을 위한 BoardFileEntity 생성
            BoardFileEntity boardFileEntity =
                    BoardFileEntity.toSaveBoardFile(savedEntity, originalFilename, storedFileName);
            boardFileRepository.save(boardFileEntity);
            }
            return savedEntity.getId();
        }
    }

    public Page<BoardDTO> findAll(int page, String type, String q) {
        page = page - 1;
        int pageLimit = 5;
        Page<BoardEntity> boardEntities = null;
        // 검색인지 구분
        if (q.equals("")) {
            // 일반 페이징
            boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if (type.equals("boardTitle")) {
                boardEntities = boardRepository.findByBoardTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            } else if (type.equals("boardWriter")) {
                boardEntities = boardRepository.findByBoardWriterContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
            }
        }

        Page<BoardDTO> boardList = boardEntities.map(boardEntity ->
                BoardDTO.builder()
                        .id(boardEntity.getId())
                        .boardTitle(boardEntity.getBoardTitle())
                        .boardWriter(boardEntity.getBoardWriter())
                        .boardHits(boardEntity.getBoardHits())
                        .createdAt(UtillClass.dateTimeFormat(boardEntity.getCreatedAt()))
                        .build());
        return boardList;
    }

    @Transactional
    public BoardDTO findById(Long id) {
        BoardEntity boardEntity = boardRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return BoardDTO.toDTO(boardEntity);
    }

    public void deleteById(Long id) {
        boardRepository.deleteById(id);
    }

    public void update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdate(boardDTO);
        boardRepository.save(boardEntity);
    }

    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }
}
