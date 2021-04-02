package com.myspring.dsgproj.controller.board;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myspring.dsgproj.dto.board.BoardDTO;
import com.myspring.dsgproj.service.ExcelService;
import com.myspring.dsgproj.utils.MediaUtils;
import com.myspring.dsgproj.utils.UploadFileUtils;

@Controller
@RequestMapping("/fileUpload/*")
public class FileUploadController {

	private static final Logger logger = LoggerFactory.getLogger(FileUploadController.class);
	
	@Resource(name = "uploadPath")
	String uploadPath;
	
	@Autowired
	SqlSession sqlSession; 
	
	
	@Autowired
	ExcelService excelService;
	
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
	
	@RequestMapping("write_ajax3.do")
	public String write_ajax3() {
		logger.info("write_ajax3.do start...");
		return "file/write_ajax3";
	}
	
	@RequestMapping("write_excel.do")
	public String write_excel() {
		logger.info("write_excel.do start...");
		return "file/write_excel";
	}
	
	// 단일파일 업로드
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
		
		String savedName = "";
		
		if(!file.isEmpty()) {	// 파일이 있다면,
			savedName = file.getOriginalFilename();	
			
			logger.info("originalName: " + savedName);
			logger.info("extension: " + FilenameUtils.getExtension(savedName));
			logger.info("size:" + file.getSize());
			logger.info("contentType:" + file.getContentType());
			
			savedName = UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes());
			
			
			model.addAttribute("msg", "fileUpladed");
		
		}
		return "file/uploadResult";
	}
	
	// 다중파일 업로드
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
				savedName = mf.getOriginalFilename();	
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
	
	// ajax방식 파일업로드 write_ajax, write_ajax2 공통
	// uploadAjaxAction, produces = "text/plain;charset=utf-8" : 파일한글처리
	@ResponseBody
	@RequestMapping(value = "uploadAjaxAction.do", method = RequestMethod.POST, produces = "text/plain;charset=utf-8")
	public ResponseEntity<String> uploadAjaxAction(MultipartHttpServletRequest mtfRequest) throws Exception {
		logger.info("uploadAjaxAction.do start...");
		// MultipatFile[] x
		String savedName = "";
		List<MultipartFile> fileList = mtfRequest.getFiles("uploadFile");
		
		if(!fileList.isEmpty()) {	// 파일이 있다면,
			for(MultipartFile mf : fileList) {
				savedName = mf.getOriginalFilename();	
				logger.info("originalName: " + savedName);
				logger.info("extension: " + FilenameUtils.getExtension(savedName));
				logger.info("size:" + mf.getSize());
				logger.info("contentType:" + mf.getContentType());
				
				savedName = UploadFileUtils.uploadFile(uploadPath, mf.getOriginalFilename(), mf.getBytes());
			}
		}
		return new ResponseEntity<String>("fileUpload success!", HttpStatus.CREATED);	//HttpStatus.CREATED : 201, dataType : text로 해야..
	}
	
	
    @ResponseBody // view가 아닌 data리턴
    @RequestMapping(value="uploadAjaxAction2.do", method=RequestMethod.POST, produces="text/plain;charset=utf-8")
    public ResponseEntity<String> uploadAjax(MultipartFile file) throws Exception {
        logger.info("originalName : "+file.getOriginalFilename());
        logger.info("size : "+file.getSize());
        logger.info("contentType : "+file.getContentType());
        
        return new ResponseEntity<String>(UploadFileUtils.uploadFile(uploadPath, file.getOriginalFilename(), file.getBytes()), HttpStatus.OK);
    }
	
	
    // 6. 이미지 표시 매핑
    @ResponseBody // view가 아닌 data리턴
    @RequestMapping("displayFile")
    public ResponseEntity<byte[]> displayFile(String fileName) throws Exception {
        // 서버의 파일을 다운로드하기 위한 스트림
        InputStream in = null; //java.io
        ResponseEntity<byte[]> entity = null;
        try {
            // 확장자를 추출하여 formatName에 저장
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            // 추출한 확장자를 MediaUtils클래스에서  이미지파일여부를 검사하고 리턴받아 mType에 저장
            MediaType mType = MediaUtils.getMediaType(formatName);
            // 헤더 구성 객체(외부에서 데이터를 주고받을 때에는 header와 body를 구성해야하기 때문에)
            HttpHeaders headers = new HttpHeaders();
            // InputStream 생성
            in = new FileInputStream(uploadPath + fileName);
            // 이미지 파일이면
            if (mType != null) { 
                headers.setContentType(mType);
            // 이미지가 아니면 : 일반파일 다운로드
            } else { 
                fileName = fileName.substring(fileName.indexOf("_") + 1);
                // 다운로드용 컨텐트 타입
                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                // 
                // 바이트배열을 스트링으로 : new String(fileName.getBytes("utf-8"),"iso-8859-1") * iso-8859-1 서유럽언어, 큰 따옴표 내부에  " \" 내용 \" "
                // 파일의 한글 깨짐 방지
                headers.add("Content-Disposition", "attachment; filename=\""+new String(fileName.getBytes("utf-8"), "iso-8859-1")+"\"");
                //headers.add("Content-Disposition", "attachment; filename='"+fileName+"'");
            }
            // 바이트배열, 헤더, HTTP상태코드
            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            // HTTP상태 코드()
            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
        } finally {
            in.close(); //스트림 닫기
        }
        return entity;
    }
    
    // 7. 파일 삭제 매핑
    @ResponseBody // view가 아닌 데이터 리턴
    @RequestMapping(value = "deleteFile", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(String fileName) {
        // 파일의 확장자 추출
        String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 이미지 파일 여부 검사
        MediaType mType = MediaUtils.getMediaType(formatName);
        // 이미지의 경우(썸네일 + 원본파일 삭제), 이미지가 아니면 원본파일만 삭제
        // 이미지 파일이면
        if (mType != null) {
            // 썸네일 이미지 파일 추출
            String front = fileName.substring(0, 12);
            String end = fileName.substring(14);
            // 썸네일 이미지 삭제
            new File(uploadPath + (front + end).replace('/', File.separatorChar)).delete();
        }
        // 원본 파일 삭제
        new File(uploadPath + fileName.replace('/', File.separatorChar)).delete();

        // 데이터와 http 상태 코드 전송
        return new ResponseEntity<String>("deleted", HttpStatus.OK);
    }
    
    
	// excelUploadAjax : 엑셀 업로드
	@ResponseBody
	@RequestMapping(value = "excelUploadAjax.do", method = RequestMethod.POST)
	public ResponseEntity excelUploadAjax(MultipartHttpServletRequest mtfRequest, Model model) throws Exception {
		logger.info("excelUploadAjax.do start...");
		
		List<BoardDTO> list = new ArrayList<>();
		
		String excelType = mtfRequest.getParameter("excelType");
		MultipartFile excelFile = mtfRequest.getFile("excelFile");

		String savedName = "";
		
		// 엑셀 읽기 service
		if(excelType.equals("xlsx")) {
			list = excelService.xlsxExcelReader(mtfRequest);
		} else if (excelType.equals("xls")) {
			list = excelService.xlsExcelReader(mtfRequest);
		}
		
		if(!excelFile.isEmpty()) {	// 파일이 있다면,
			savedName = new String(excelFile.getOriginalFilename().getBytes("8859_1"), "UTF-8");		// 한글깨짐 방지
			
			logger.info("originalName: " + savedName);
			logger.info("extension: " + FilenameUtils.getExtension(savedName));
			logger.info("size:" + excelFile.getSize());
			logger.info("contentType:" + excelFile.getContentType());
			
			savedName = UploadFileUtils.uploadFile(uploadPath, excelFile.getOriginalFilename(), excelFile.getBytes());
			
			model.addAttribute("msg", "fileUpladed");
		}
		
		return new ResponseEntity(model, HttpStatus.OK);
	}

}
