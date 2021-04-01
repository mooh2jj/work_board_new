package com.myspring.dsgproj.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.myspring.dsgproj.dto.board.BoardDTO;

@Service
public class ExcelService {

	@Autowired
	SqlSession sqlSession;
	
	@Autowired
	HttpSession session;
	
	public List<BoardDTO> xlsExcelReader(MultipartHttpServletRequest req) {
		
		// 반환할 객체를 생성
		List<BoardDTO> list = new ArrayList<>();
		
		MultipartFile file = req.getFile("excelFile");
		HSSFWorkbook workbook = null;
		
		try {
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new HSSFWorkbook(file.getInputStream());
			
			// 탐색에 사용할 Sheet, Row, Cell 객체
			HSSFSheet curSheet;
			HSSFRow curRow;
			HSSFCell curCell;
			
			BoardDTO vo;
			
			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						vo = new BoardDTO();
						String value;
						
						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										// cell 스타일이 다르더라도 String으로 반환 받음
										switch (curCell.getCellType()) {
										case HSSFCell.CELL_TYPE_FORMULA:
										value = curCell.getCellFormula();
										break;
										case HSSFCell.CELL_TYPE_NUMERIC:
										value = curCell.getNumericCellValue() + "";
										break;
										case HSSFCell.CELL_TYPE_STRING:
										value = curCell.getStringCellValue() + "";
										break;
										case HSSFCell.CELL_TYPE_BLANK:
										value = curCell.getBooleanCellValue() + "";
										break;
										case HSSFCell.CELL_TYPE_ERROR:
										value = curCell.getErrorCellValue() + "";
										break;
										default:
										value = new String();
										break;
										} // end switch
										
										// 현재 colum index에 따라서 vo입력
										switch (cellIndex) {
										case 0: // 제목
										vo.setTitle(value);
										break;
										case 1: // 내용
										vo.setContent(value);
										break;

										default:
										break;
										}
									}	// end if
								}	// end for
								// cell 탐색 이후 vo 추가
								String sessionId = session.getId();
								
								vo.setSessionId(sessionId);
								// writer 로그인시 세션에서 name으로 받아오기
								String writer = (String) session.getAttribute("name");
								vo.setWriter(writer);
								list.add(vo);
							}	// end
						}	// end if
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 디비에 insert
//		excelMapper.insertExcelTest(list);
		sqlSession.insert("board.insertExcel", list);
		return list;
		
	}
	
	public List<BoardDTO> xlsxExcelReader(MultipartHttpServletRequest req) {
		
		// 반환할 객체를 생성
		List<BoardDTO> list = new ArrayList<>();
		
		MultipartFile file = req.getFile("excelFile");
		XSSFWorkbook workbook = null;
		
		try {
			// HSSFWorkbook은 엑셀파일 전체 내용을 담고 있는 객체
			workbook = new XSSFWorkbook(file.getInputStream());
			
			// 탐색에 사용할 Sheet, Row, Cell 객체
			XSSFSheet curSheet;
			XSSFRow curRow;
			XSSFCell curCell;
			
			BoardDTO vo;
			
			// Sheet 탐색 for문
			for (int sheetIndex = 0; sheetIndex < workbook.getNumberOfSheets(); sheetIndex++) {
				// 현재 sheet 반환
				curSheet = workbook.getSheetAt(sheetIndex);
				
				// row 탐색 for문
				for (int rowIndex = 0; rowIndex < curSheet.getPhysicalNumberOfRows(); rowIndex++) {
					// row 0은 헤더정보이기 때문에 무시
					if (rowIndex != 0) {
						curRow = curSheet.getRow(rowIndex);
						vo = new BoardDTO();
						String value;
						
						// row의 첫번째 cell값이 비어있지 않는 경우만 cell탐색
						if (curRow.getCell(0) != null) {
							if (!"".equals(curRow.getCell(0).getStringCellValue())) {
								// cell 탐색 for문
								for (int cellIndex = 0; cellIndex < curRow.getPhysicalNumberOfCells(); cellIndex++) {
									curCell = curRow.getCell(cellIndex);
									
									if (true) {
										value = "";
										// cell 스타일이 다르더라도 String으로 반환 받음
										switch (curCell.getCellType()) {
										case HSSFCell.CELL_TYPE_FORMULA:
											value = curCell.getCellFormula();
											break;
										case HSSFCell.CELL_TYPE_NUMERIC:
											value = curCell.getNumericCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_STRING:
											value = curCell.getStringCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_BLANK:
											value = curCell.getBooleanCellValue() + "";
											break;
										case HSSFCell.CELL_TYPE_ERROR:
											value = curCell.getErrorCellValue() + "";
											break;
										default:
											value = new String();
											break;
										} // end switch
										
										// 현재 colum index에 따라서 vo입력
										switch (cellIndex) {
										case 0: // 제목
											vo.setTitle(value);
											break;
										case 1: // 내용
											vo.setContent(value);
											break;
											
										default:
											break;
										}
									}	// end if
								}	// end for
								// cell 탐색 이후 vo 추가
								String sessionId = session.getId();
								
								vo.setSessionId(sessionId);
								// writer 로그인시 세션에서 name으로 받아오기
								String writer = (String) session.getAttribute("name");
								vo.setWriter(writer);
								list.add(vo);
							}	// end
						}	// end if
					}
					
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// 디비에 insert
//		excelMapper.insertExcelTest(list);
		sqlSession.insert("board.insertExcel", list);
		return list;
		
	}
}
