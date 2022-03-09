package board.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import board.dto.BoardDTO;
import board.service.BoardService;
import lombok.extern.log4j.Log4j2;

@RestController
@Log4j2
public class RestBoardApiController {

	@Autowired
	private BoardService boardService;
	
	
	@GetMapping("/api/board")
	public List<BoardDTO> openBoardList() throws Exception {
		log.debug("RestBoardApiController >>>>> openBoardList() invoked.");
		
		return boardService.selectBoardList();
	}
	
	@PostMapping("/api/board/write")
	public void insertBoard(@RequestBody BoardDTO board) throws Exception {
		log.debug("RestBoardApiController >>>>> insertBoard(@RequestBody board) invoked.");
		
		boardService.insertBoard(board, null);
	}
	
	@GetMapping("/api/board/{boardIdx}")
	public BoardDTO openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		log.debug("RestBoardApiController >>>>> openBoardDetail(@PathVariable(boardIdx) int boardIdx) invoked.");
		
		return boardService.selectBoardDetail(boardIdx);
	}
	
	@PutMapping("/api/board/{boardIdx}")
	public String updateBoard(@RequestBody BoardDTO board) throws Exception {
		log.debug("RestBoardApiController >>>>> updateBoard(@RequestBody board) invoked.");
		
		boardService.updateBoard(board);
		
		return "redirect:/board";
	}
	
	@DeleteMapping("/api/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		log.debug("RestBoardApiController >>>>> deleteBoard(@PathVariable(boardIdx) int boardIdx) invoked.");
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	
}
