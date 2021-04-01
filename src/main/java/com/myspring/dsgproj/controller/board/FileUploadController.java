package com.myspring.dsgproj.controller.board;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myspring.dsgproj.dto.board.BoardDTO;
import com.myspring.dsgproj.utils.UploadFileUtils;

@Controller
@RequestMapping("/fileUpload/*")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Resource(name = "uploadPath")
	String uploadPath;
	
	@Autowired
	SqlSession sqlSession; 
	
	@RequestMapping("write.do")
	public String write() {
		logger.info("write.do start...");
		return "file/write";
	}
	
	@RequestMapping("write_multi.do")
	public String write_multi() {
		logger.info("write_multi.do start...");
		return "file/write_multi";
	}
	
	@RequestMapping("write_ajax.do")
	public String write_ajax() {
		logger.info("write_ajax.do start...");
		return "file/write_ajax";
	}
	
	@RequestMapping("write_ajax2.do")
	public String write_ajax2() {
		logger.info("write_ajax2.do start...");
		return "file/write_ajax2";
	}
	
	@RequestMapping("write_excel.do")
	public String write_excel() {
		logger.info("write_excel.do start...");
		return "file/write_excel";
	}
	
	@RequestMapping(value = "insert.do", method = RequestMethod.POST)
	public String uploadFileSet(MultipartFile file, BoardDTO dto, HttpSession session, Model model) throws Exception {
		
		logger.info("insert.do start...");
		String sessionId = session.getId();
		
		dto.setSessionId(sessionId);
		// writer 로그인시 세션에서 name으로 받아오기
		String writer = (String) session.getAttribute("name");
		logger.info("writer: "+ writer);
		dto.setWriter(writer);
		
		sqlSession.insert("board.insert", dto);
		
		logger.info("dto: "+ dto);
		
//		String savedName =  file.getOriginalFilename();
		String savedName = "";
		
		if(!file.isEmpty()) {	// 파일이 있다면,
			savedName = new String(file.getOriginalFilename().getBytes("8859_1"), "UTF-8");		// 한글깨짐 방지
			
			logger.info("originalName: " + savedName);
			logger.info("extension: " + FilenameUtils.getExtension(savedName));
			logger.info("size:" + file.getSize());
			logger.info("contentType:" + file.getContentType());
			
			savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			
			
			model.addAttribute("msg", "fileUpladed");
		
		}
		return "file/uploadResult";
	}
	
	@RequestMapping(value="insert_multi.do", method = RequestMethod.POST)
	public String insert_multi(MultipartHttpServletRequest mtfRequest, BoardDTO dto, HttpSession session, Model model) throws Exception {
		
		logger.info("insert_multi.do start...");
		String sessionId = session.getId();
		
		dto.setSessionId(sessionId);
		// writer 로그인시 세션에서 name으로 받아오기
		String writer = (String) session.getAttribute("name");
		logger.info("writer: "+ writer);
		dto.setWriter(writer);
		
		sqlSession.insert("board.insert", dto);
		
		logger.info("dto: "+ dto);
		
		List<MultipartFile> fileList = mtfRequest.getFiles("file");
		
		String savedName = "";
		
		if(!fileList.isEmpty()) {	// 파일이 있다면,
			
			for(MultipartFile mf : fileList) {
				savedName = new String(mf.getOriginalFilename().getBytes("8859_1"), "UTF-8");
				logger.info("originalName: " + savedName);
				logger.info("extension: " + FilenameUtils.getExtension(savedName));
				logger.info("size:" + mf.getSize());
				logger.info("contentType:" + mf.getContentType());
				
				savedName = UploadFileUtils.uploadFile(uploadPath, mf.getOriginalFilename(), mf.getBytes());
				
			}
			
			model.addAttribute("msg", "fileUpladed");
		
		}
		
		return "file/uploadResult";
	}
	
//	uploadAjaxAction, produces = "text/plain;charset=utf-8" : 파일한글처리
	@ResponseBody
	@RequestMapping(value = "uploadAjaxAction.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public ResponseEntity<String> uploadAjaxAction(MultipartHttpServletRequest mtfRequest) throws Exception {
		logger.info("uploadAjaxAction.do start...");
		// MultipatFile[] x
		String savedName = "";
		List<MultipartFile> fileList = mtfRequest.getFiles("uploadFile");
		
		if(!fileList.isEmpty()) {	// 파일이 있다면,
			for(MultipartFile mf : fileList) {
				savedName = new String(mf.getOriginalFilename().getBytes("8859_1"), "UTF-8");	// 아직도 한글이 깨짐...
				logger.info("originalName: " + savedName);
				logger.info("extension: " + FilenameUtils.getExtension(savedName));
				logger.info("size:" + mf.getSize());
				logger.info("contentType:" + mf.getContentType());
				
				savedName = UploadFileUtils.uploadFile(uploadPath, mf.getOriginalFilename(), mf.getBytes());
			}
		}
		return new ResponseEntity<String>("fileUpload success!", HttpStatus.OK);	// dataType : text로 해야..
	}
	
//	excelUploadAjax
	@RequestMapping(value = "excelUploadAjax.do", method = RequestMethod.POST)
	public String excelUploadAjax(MultipartFile testFile, MultipartHttpServletRequest request) throws Exception {
		logger.info("excelUploadAjax.do start...");
		
		MultipartFile excelFile = request.getFile("excelFile");
		
        if(excelFile == null || excelFile.isEmpty()) {
            throw new RuntimeException("엑셀파일을 선택해 주세요");
        }
        
        File destFile = new File("uploadPath\\"+excelFile.getOriginalFilename());
        
        try {
            //내가 설정한 위치에 내가 올린 파일을 만들고 
            excelFile.transferTo(destFile);
        }catch(Exception e) {
            throw new RuntimeException(e.getMessage(),e);
        }
		
		return "file/uploadResult";
	}

}
