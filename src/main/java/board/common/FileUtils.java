package board.common;

import java.io.File;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import board.dto.BoardFileDTO;
import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class FileUtils {

	
	public List<BoardFileDTO> parseFileInfo(int boardIdx, MultipartHttpServletRequest multipartHttpServletReq) throws Exception{
		
		if(ObjectUtils.isEmpty(multipartHttpServletReq)) {
			return null;
		}
		
		List<BoardFileDTO> fileList = new ArrayList<>();
		
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		ZonedDateTime current = ZonedDateTime.now();
		String path = "images/"+current.format(format);
		
		File file= new File(path);
		
		if(file.exists() == false) {
			file.mkdirs();
		}
		
		Iterator<String> iterator = multipartHttpServletReq.getFileNames();
		
		String newFileName, originalFileExtension, contentType;
		
		while(iterator.hasNext()) {
			
			List<MultipartFile> list = multipartHttpServletReq.getFiles(iterator.next());
			
			for(MultipartFile multipartFile : list) {
				
				if(multipartFile.isEmpty() == false ) {
					
					contentType = multipartFile.getContentType();
					
					if(ObjectUtils.isEmpty(contentType)) {
						
						break;
						
					} else {
						
						if(contentType.contains("image/jpeg")) {
							
							originalFileExtension = ".jpg";
							
						} else if (contentType.contains("image/png")) {
							
							originalFileExtension = ".png";
							
						} else if (contentType.contains("image/gif")) {
							
							originalFileExtension = ".gif";
							
						} else {
							
							break;
							
						} // if-else-if
						
					} // inner if-else
					
					newFileName = Long.toString(System.nanoTime()) + originalFileExtension;
					
					BoardFileDTO boardFile = new BoardFileDTO();
					
					boardFile.setBoardIdx(boardIdx);
					boardFile.setFileSize(multipartFile.getSize());
					boardFile.setOriginalFileName(multipartFile.getOriginalFilename());
					boardFile.setStoredFilePath(path + "/" + newFileName);
					
					fileList.add(boardFile);
					
					file = new File(path + "/" + newFileName);
					
					multipartFile.transferTo(file);
					
				} // if
				
			} // for
			
		} // while
		
		return fileList;
		
	} // parseFileInfo
	
} // end class
