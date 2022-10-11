package co.itrip.prj.calendar.mapper;

import java.util.List;

import co.itrip.prj.calendar.service.CalendarVO;

public interface CalendarMapper {
	
	int calendarInsert(CalendarVO vo); // 상담 결제 후 가이드 캘린더 등록
	
	List<CalendarVO> myCalendarList(CalendarVO vo); // 가이드 나의 상담 리스트

}