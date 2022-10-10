package co.itrip.prj.calendar.service;

import java.sql.Date;

//import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class CalendarVO {
	
	private int no; // 캘린더 번호
	private String guideId; // 가이드 아이디
	private String member_id; // 유저 아이디
	private String name; // 제목
	
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private Date conday; // 상담일
	//@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private String beginTime; // 시작시간
	
	
	

}
