package com.myspring.dsgproj.utils;


import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.UUID;

import javax.imageio.ImageIO;

import org.imgscalr.Scalr;
import org.springframework.util.FileCopyUtils;

public class UploadFileUtils {

	public static String uploadFile(String uploadPath, String originalName, byte[] fileData) throws Exception{
		
		UUID uuid = UUID.randomUUID();		// 범용고유 식별자, universally unique identifier
		
		String savedName = uuid.toString()+"_"+originalName;
		
		String savedPath = calcPath(uploadPath);  
		
		File target = new File(uploadPath + savedPath, savedName);		// parent: uploadPath + savedPath, child: savedName
		// uploadPath + savedPath 
		FileCopyUtils.copy(fileData, target);
		// FileCopyUtils.copy(inputStream in, outputStream out)을 사용하면 파일 자체를 웹브라우저에서 읽어들인다.
		// fileData : MultipartFile file.getBytes()
		// FileCopyUtils.copy(inputStream in, outputStream out)
		// FileCopyUtils.copy(바이트배열, 파일객체)
		// 썸네일을 생성하기 위한 파일의 확장자 검사
		
		String formatName = originalName.substring(originalName.lastIndexOf(".")+1);	
		String uploadedFileName = null;
		
		// 이미지 파일은 썸네일 사용
		if(MediaUtils.getMediaType(formatName) != null) {	
			// 썸네일 생성
			uploadedFileName = makeThumbnail(uploadPath, savedPath, savedName);
			// 나머지는 아이콘
		}else {
			// 아이콘 생성
			uploadedFileName = makeIcon(uploadPath, savedPath, savedName);
		}
		
		return uploadedFileName;
	}
	

	// 날짜별 디렉토리 추출
	private static String calcPath(String uploadPath) {

		Calendar cal = Calendar.getInstance();	
		// File.separator : 디렉토리 구분자(\\)
		// 연도, ex) \\2021
		String yearPath = File.separator + cal.get(Calendar.YEAR);
		System.out.println(yearPath);
		// 월, ex) \\2021\\03 ... Calendar.MONTH의 값은 0부터 시작 -> +1을 해서 변수에 담아줘야 한다.
		String monthPath = yearPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.MONTH)+1);
		System.out.println(monthPath);
		// 날짜, ex) \\2021\\03\\01
		String datePath = monthPath + File.separator + new DecimalFormat("00").format(cal.get(Calendar.DATE));
		System.out.println(datePath);
		
		// 디렉토리 생성 메서드 호출
		makeDir(uploadPath, yearPath, monthPath, datePath);
		
		return datePath;
	}

	// 디렉토리 생성
	private static void makeDir(String uploadPath, String... paths) {		// String... paths -> String yearPath, monthPath, datePath 
		// 디렉토리가 존재하면
		if(new File(paths[paths.length - 1]).exists()) {		
			return;
		}
		
		for(String path : paths) {
			File dirPath = new File(uploadPath+path);
			// 디렉토리가 존재하지 않으면
			if(!dirPath.exists()) {
				dirPath.mkdir();	//디렉토리 생성
			}
		}		
	}
	
	 // 썸네일 생성
	private static String makeThumbnail(String uploadPath, String path, String fileName) throws Exception{
		
		// 이미지를 읽기 위한 버퍼
		BufferedImage sourceImg = ImageIO.read(new File(uploadPath + path, fileName));	
		// 100픽셀 단위의 썸네일 생성
		BufferedImage destImg = Scalr.resize(sourceImg, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_HEIGHT, 100);		
		// 썸네일의 이름을 생성(원본파일명에 's_'를 붙임)
		String thumbnailName = uploadPath + path + File.separator + "s_"+fileName;
		File newFile = new File(thumbnailName);		
		String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
		// 썸네일 생성
		ImageIO.write(destImg, formatName.toUpperCase(), newFile);		
		// 썸네일의 이름을 리턴함
		

		return thumbnailName.substring(uploadPath.length()).replace(File.separatorChar, '\\');		
		// substring(n) : uploadPath 로
		
	}

	 // 아이콘 생성
	private static String makeIcon(String uploadPath, String path, String fileName)throws Exception {
		// 아이콘의 이름
		String iconName = uploadPath + path + File.separator + fileName;
		// 아이콘 이름을 리턴
		// File.separatorChar : 디렉토리 구분자
        // 윈도우 \ , 유닉스(리눅스) / 
		return iconName.substring(uploadPath.length()).replace(File.separatorChar, '\\');	// 윈도우 디렉토리 구분자로
	}

}
