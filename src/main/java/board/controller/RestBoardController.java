package board.controller;

import java.io.File;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import board.dto.BoardDTO;
import board.dto.BoardFileDTO;
import board.service.BoardService;
import lombok.extern.log4j.Log4j2;

@Controller
@Log4j2
public class RestBoardController {

	
	@Autowired
	private BoardService boardService;
	
	@GetMapping("/board")
	public ModelAndView openBoardList() throws Exception {
		log.debug("openBoardList() invoked.");
		
		ModelAndView mv = new ModelAndView("/board/restBoardList");
		
		List<BoardDTO> list = boardService.selectBoardList();
		mv.addObject("list",list);
		
		return mv;
	};
	
	@GetMapping("/board/write")
	public String openBoardWrite() throws Exception {
		log.debug("openBoardWrite() invoked.");
		
		return "/board/restBoardWrite";
	}
	
	@PostMapping("/board/write")
	public String insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletReq) throws Exception {
		log.debug("insertBoard(board, multipartHttpServletReq) invoked.");
		
		boardService.insertBoard(board, multipartHttpServletReq);
		
		return "redirect:/board";
	}
	
	//					{boardIdx}는 아래의 @PathVariable의 값(boardIdx)값으로 매핑되어서 @PathVariable의 값으로 요청받을경우 처리하는 메소드가 되는것
	//	여기서는 게시물 번호(boardIdx)값을 받게되었으므로 게시물 번호를 받아 해당하는 게시물의 상세화면을 보여주기 위함임
	@GetMapping("/board/{boardIdx}")
	public ModelAndView openBoardDetail(@PathVariable("boardIdx") int boardIdx) throws Exception {
		log.debug("openBoardDetail(board) invoked.");
		
		ModelAndView mv = new ModelAndView("/board/restBoardDetail");
		
		BoardDTO board = boardService.selectBoardDetail(boardIdx);
		
		mv.addObject("board", board);
		
		return mv;
	}
	
	@PutMapping("/board/{boardIdx}")
	public String updateBoard(BoardDTO board) throws Exception {
		log.debug("updateBoard(board) invoked.");
		
		boardService.updateBoard(board);
		
		return "redirect:/board";
	}
	
	@DeleteMapping("/board/{boardIdx}")
	public String deleteBoard(@PathVariable("boardIdx") int boardIdx) throws Exception {
		log.debug("deleteBoard(boardIdx) invoked.");
		
		boardService.deleteBoard(boardIdx);
		
		return "redirect:/board";
	}
	
	@GetMapping("/board/file")
	public void downloadBoardFile(@RequestParam int idx, @RequestParam int boardIdx, HttpServletResponse res) throws Exception {
		log.debug("downloadBoardFile(@RequstParam int idx, @ReuqestParam int boardIdx, res) invoked.");
		
		BoardFileDTO boardFile = boardService.selectBoardFileInformation(idx, boardIdx);
		
		if(ObjectUtils.isEmpty(boardFile) == false ) {
			
			String fileName = boardFile.getOriginalFileName();
			
			byte[] files = FileUtils.readFileToByteArray(new File(boardFile.getStoredFilePath()));
			
			res.setContentType("application/octet-stream");
			res.setContentLength(files.length);
			res.setHeader("Content-Disposition", "attachment; fileName=\"" + URLEncoder.encode(fileName, "UTF-8")+ "\";");
			res.setHeader("Content-Transfer-Encoding", "binary");
			
			res.getOutputStream().write(files);
			res.getOutputStream().flush();
			res.getOutputStream().close();
		}
	}
}
