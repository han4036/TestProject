package board.service;

import java.util.List;

import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardDTO;
import board.dto.BoardFileDTO;

public interface BoardService {

	List<BoardDTO> selectBoardList() throws Exception;
	
	void insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletReq) throws Exception;
	
	BoardDTO selectBoardDetail(int boardIdx) throws Exception;
	
	void updateBoard(BoardDTO board) throws Exception;
	
	void deleteBoard(int boardIdx) throws Exception;
	
	BoardFileDTO selectBoardFileInformation(int idx, int boardIdx) throws Exception;
	
}
