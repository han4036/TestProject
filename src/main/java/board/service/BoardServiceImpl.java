package board.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.common.FileUtils;
import board.dto.BoardDTO;
import board.dto.BoardFileDTO;
import board.mapper.BoardMapper;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
@Transactional
public class BoardServiceImpl implements BoardService {

	@Autowired
	private BoardMapper boardMapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Override
	public List<BoardDTO> selectBoardList() throws Exception {
		
		log.debug("BoardServiceImpl --- selectBoardList invoked.");
		
		return boardMapper.selectBoardList();
	}
	
	@Override
	public void insertBoard(BoardDTO board, MultipartHttpServletRequest multipartHttpServletReq) throws Exception {
		log.debug("insertBoard(board) invoked.");
		
		boardMapper.insertBoard(board);
		
		List<BoardFileDTO> list = fileUtils.parseFileInfo(board.getBoardIdx(), multipartHttpServletReq);
		
		if(CollectionUtils.isEmpty(list) == false) {
			boardMapper.insertBoardFileList(list);
		}
		
		
//		if(ObjectUtils.isEmpty(multipartHttpServletReq) == false ) {
//			Iterator<String> iterator = multipartHttpServletReq.getFileNames();
//			
//			String name;
//			
//			while(iterator.hasNext()) {
//				
//				name = iterator.next();
//				
//				log.debug("file tag name : " + name);
//				
//				List<MultipartFile> list = multipartHttpServletReq.getFiles(name);
//				
//				for(MultipartFile multipartFile : list) {
//					log.debug("===================== start file information ======================");
//					log.debug("file name : " + multipartFile.getOriginalFilename());
//					log.debug("file size : " + multipartFile.getSize());
//					log.debug("file content : " + multipartFile.getContentType());
//					log.debug("===================== end file information.  ======================\n");
//				}
//			}
//		}
	}
	
	@Override
	public BoardDTO selectBoardDetail(int boardIdx) throws Exception {
		log.debug("selectBoardDetail(boardIdx) invoked.");
		
		BoardDTO board = boardMapper.selectBoardDetail(boardIdx);
		
		List<BoardFileDTO> fileList = boardMapper.selectBoardFileList(boardIdx);
		
		board.setFileList(fileList);
		
		boardMapper.updateHitCount(boardIdx);
		
		return board;
	}
	
	@Override
	public void updateBoard(BoardDTO board) throws Exception {
		log.debug("updateBoard(board) invoked.");
		
		boardMapper.updateBoard(board);
	}
	
	@Override
	public void deleteBoard(int boardIdx) throws Exception {
		log.debug("deleteBoard(boardIdx) invoked.");
		
		boardMapper.deleteBoard(boardIdx);
	}
	
	@Override
	public BoardFileDTO selectBoardFileInformation(int idx, int boardIdx) throws Exception {
		log.debug("selectBoardFileInformation(idx, boardIdx) invoked.");
		
		return boardMapper.selectBoardFileInformation(idx, boardIdx);
	}
}
