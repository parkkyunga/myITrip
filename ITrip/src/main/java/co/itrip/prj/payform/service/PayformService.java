package co.itrip.prj.payform.service;


import co.itrip.prj.calendar.service.CalendarVO;
import co.itrip.prj.iclass.service.ClassAttendVO;

/**
* 결제 및 신청 기능 모아놓은 인터페이스
* @author 김하은
* @date 2022.09.27
* @version 1.0
*/
public interface PayformService {
	//결제처리
	public void addPay(PayformVO vo);

	//경아 - 클래스결제테이블insert
	int clPayformInsert(PayformVO vo,ClassAttendVO cvo, CalendarVO cavo);
	
	//은지 - 상담결제테이블insert
	int coPayformInsert(PayformVO vo, CalendarVO cvo);
	
	//은지 - 결제 카운트 
	int payCount(PayformVO vo); // 결제 횟수 체크
}
