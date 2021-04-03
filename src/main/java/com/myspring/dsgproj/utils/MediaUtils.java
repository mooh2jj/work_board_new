package com.myspring.dsgproj.utils;


import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

public class MediaUtils {

	private static Map<String, MediaType> mediaMap;
	
	// 클래스 초기화 블럭
	static {
		mediaMap = new HashMap<String, MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_PNG);
	}
	
	// 파일 타입
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());		
	}
	
//	UploadFileUtils클래스에서 추출한 파일의 확장자명을 대문자로 변환하고, 
//	mediaMap에 담긴 값을 호출한 뒤 리턴한다. 3가지(jpg,gif,png) 
//	이미지 파일일 경우에는 값이 복사되어 리턴되지만, 아닐경우에는 null상태로 리턴.
}
