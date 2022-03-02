package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDTO;
import board.dto.BoardFileDTO;
import board.service.BoardService;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/board/openBoardList.do")
	public ModelAndView openBoardList() throws Exception {
		log.debug("BoardController --- openBoardList() invoked.");
		ModelAndView mv = new ModelAndView("/board/boardList");
		
		List<BoardDTO> list = boardService.selectBoardList();
		mv.addObject("list", list);
		
		return mv;
	}
	
	@RequestMapping("/board/openBoardWrite.do")
	public String openBoardWrite() throws Exception {
		log.debug("BoardController --- openBoardWrite() invoked.");
		
		return "/board/boardWrite";
	}
	
	@RequestMapping("/board/insertBoard.do")
	// MultipartHttpServletRequest는 ServletRequest를 상속받아 구현된 인터페이스로 업로드된 파일을 처리하기 위한 여러가지 메소드를 제공
	public String insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletReq) throws Exception {
		log.debug("BoardController --- insertBoard(board) invoked.");
		
		boardService.insertBoard(board, multipartHttpServletReq);
		
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/openBoardDetail.do")
	public ModelAndView openBoardDetail(@RequestParam int boardIdx) throws Exception {
		log.debug("BoardController --- openBoardDetail() invoked.");
		
		ModelAndView mv = new ModelAndView("/board/boardDetail");
		
		BoardDTO board = boardService.selectBoardDetail(boardIdx);
		mv.addObject("board", board);
		
		return mv;
	}
	
	@RequestMapping("/board/updateBoard.do")
	public String updateBoard(BoardDTO board) throws Exception {
		log.debug("BoardController --- updateBoard(board) invoked.");
		
		boardService.updateBoard(board);
		
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/deleteBoard.do")
	public String deleteBoard(int boardIdx) throws Exception {
		log.debug("BoardController --- deleteBoard(boardIdx) invoked.");
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board/openBoardList.do";
	}
	
	@RequestMapping("/board/downloadBoardFile.do")
	public void downloadFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse res) throws Exception {
		log.debug("downloadFile(idx, boardIdx, res) invoked.");
		
		BoardFileDTO boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false ) {
			
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			
			// response의 헤더에 콘텐츠 타입, 크기 및 형태 등을 설정
			// 파일의 이름은 반드시 UTF-8로 인코딩 한다
			// 인터넷에서 파일을 다운받을때 이름이 이상한 단어로 
			res.setContentType("application/octet-stream");
			res.setContentLength(files.length);
			res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8") + "\";");
			res.setHeader("Content-Transfer-Encoding", "binary");
			
			// 앞에서 읽어온 파일 정보의 바이트 배열 데이터를 res에 작성
			res.getOutputStream().write(files);
			
			// response의 버퍼를 정리하고 닫아줌
			res.getOutputStream().flush();
			res.getOutputStream().close();
			
		}
	}
	
}
