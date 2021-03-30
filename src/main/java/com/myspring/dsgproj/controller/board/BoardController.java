package com.myspring.dsgproj.controller.board;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.myspring.dsgproj.dto.board.BoardDTO;

@Controller
@RequestMapping("/board/*")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	
	@Autowired
	SqlSession sqlSession; 
	
	@RequestMapping("home.do")
	public String home() {
		logger.info("home.do start...");
		return "board/home";
	}
	
	@RequestMapping("write.do")
	public String write() {
		logger.info("write.do start...");
		return "board/write";
	}
	
	@RequestMapping(value = "insert.do", method = RequestMethod.POST)
	public String insert(BoardDTO dto, Model model, HttpSession session) {
		logger.info("insert.do start...");
		String sessionId = session.getId();
		
		dto.setSessionId(sessionId);
		// writer 로그인시 세션에서 name으로 받아오기
		String writer = (String) session.getAttribute("name");
		logger.info("writer: "+ writer);
		dto.setWriter(writer);
		
		sqlSession.insert("board.insert", dto);
		
		logger.info("dto: "+ dto);
		
		return "redirect:list.do";
	}
	
	@RequestMapping("list.do")
	public String list(Model model) {
		
		logger.info("board/list.do start...");
		
		List<BoardDTO> selectAll = sqlSession.selectList("board.selectAll");
		
		logger.info("selectAll: "+ selectAll);
		
		model.addAttribute("selectAll", selectAll);
		return "board/list";
	}
	
	@RequestMapping("detail.do/{bno}")
	public String detail(@PathVariable("bno") int bno, Model model) {
		logger.info("detail.do start...");
		
		BoardDTO dto = sqlSession.selectOne("board.detail", bno);
		
		model.addAttribute("dto", dto);
		
		return "board/detail";
	}
	
	
	@RequestMapping("update.do")
	public String update(BoardDTO dto) {
		logger.info("update.do start...");
		
		sqlSession.update("board.update", dto);
		return "redirect:list.do";
	}
	
	@RequestMapping("delete.do")
	public String delete(@RequestParam("bno") int bno) {
		logger.info("delete.do start...");
		sqlSession.delete("board.delete", bno);
		return "redirect:list.do";
	}
	
}
