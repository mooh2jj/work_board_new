package com.myspring.dsgproj.controller.member;

import java.io.IOException;
import java.util.Map;
import java.util.Random;

import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.myspring.dsgproj.dto.member.MemberDTO;

@Controller
@RequestMapping("/member/*")
public class MemberController {

	private static final Logger logger = LoggerFactory.getLogger(MemberController.class);
	
	@Autowired
	private SqlSession sqlSession;
	
//	@Autowired
//	private UserMailSendService mailsender;
	
	@Autowired
	private JavaMailSender mailSender;
	
	@RequestMapping("/loginmain")
	public String loginmain() {
		return "member/loginmain";
	}
	
	
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public String login(MemberDTO dto, HttpSession session, Model model) {
		logger.info("member/login.do start...");
		logger.info("MemberDTO: "+ dto);
		
		
		String name = sqlSession.selectOne("member.idcheck", dto);
		logger.info("name: "+ name);
		
		if(name != null) {		// 로그인 성공
			session.setAttribute("email", dto.getEmail());
			session.setAttribute("name", name);
			
			model.addAttribute("message", "success");
			return "sessionCheck";
		} else {
			model.addAttribute("message", "error");
			return "member/loginmain";
		}
		
	}
	
	@ResponseBody
	@RequestMapping(value= "pwcheck.do", method = RequestMethod.POST)
	public String pwcheck(HttpServletRequest request) {
		String pw = request.getParameter("pw").trim();
		
		logger.info("pw: "+ pw);
		int resultCount = sqlSession.selectOne("member.pwcheck", pw);
		
		String result = String.valueOf(resultCount);
		
		logger.info("pwcheck- result :"+ result);
		
		return result;
	}
	
	@RequestMapping("/logout.do")
	public String logout(HttpSession session, Model model) {
		session.invalidate();
		
		model.addAttribute("message", "logout");
		
		return "member/loginmain";
	}
	
	@RequestMapping("/join")
	public String join() {
		return "member/join";
	}
	
	@RequestMapping("/joinRegiser.do")
	public String joinRegiser(MemberDTO dto, HttpServletRequest request) {
		
		logger.info("dto: "+ dto);
		
		sqlSession.insert("member.joinRegiser", dto);
//		mailsender.mailSendWithUserKey(dto.getEmail(), dto.getName(), request);
		
		return "member/emailAuth";
		
	}
	
	@RequestMapping("/emailAuth.do")
	@ResponseBody
	public String emailAuth(@RequestParam("email") String email, Model model) throws IOException {
		
		logger.info("emailAuth.do start...");
		logger.info("email: " + email);
		Random r = new Random();
		int dice = r.nextInt(4589362) + 49311;	// 이메일로 받는 인증코드 난수
		
		String setfrom = "ehtjd33@gmail.com";
		String tomail = email;
		String title = "회원가입 인증메일입니다.";
		String content = 
				
        System.getProperty("line.separator")+ //한줄씩 줄간격을 두기위해 작성
        System.getProperty("line.separator")+
        "안녕하세요 회원님 저희 홈페이지를 찾아주셔서 감사합니다"
        +System.getProperty("line.separator")+
        System.getProperty("line.separator")+
        " 인증번호는 " +dice+ " 입니다. "
        +System.getProperty("line.separator")+
        System.getProperty("line.separator")+
        "받으신 인증번호를 홈페이지에 입력해 주시면 됩니다."; // 내용
		
		try {
			 MimeMessage message = mailSender.createMimeMessage();
             MimeMessageHelper messageHelper = new MimeMessageHelper(message,
                     true, "UTF-8");

             messageHelper.setFrom(setfrom); // 보내는사람 생략하면 정상작동을 안함
             messageHelper.setTo(tomail); // 받는사람 이메일
             messageHelper.setSubject(title); // 메일제목은 생략이 가능하다
             messageHelper.setText(content); // 메일 내용
             
             mailSender.send(message);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String dice_s = String.valueOf(dice);
        
        return dice_s;
	}
	
	@RequestMapping("dicecheck.do")
	@ResponseBody
	public String dicecheck(@RequestParam Map<String, Object> param) {
		
		String email_injeung = (String)param.get("email_injeung");
		String dice_s = (String)param.get("dice_s");
		
		String result = "";
        logger.info("마지막 : email_injeung : "+ email_injeung);
        logger.info("마지막 : dice : "+ dice_s);
        
        if(email_injeung.equals(dice_s)) {
        	result = "success";
        } else {
        	result = "fail";
        }
        logger.info("result :" + result);
        
        return result;
	}
	
}
