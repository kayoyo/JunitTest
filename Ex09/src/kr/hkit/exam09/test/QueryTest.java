package kr.hkit.exam09.test;

import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import kr.hkit.exam09.BoardVO;
import kr.hkit.exam09.Query;

class QueryTest {

	@BeforeAll
	static void start() {
		Query.createTable(); //test가 실행하기전에 딱 한번 실행
	}
	
	@AfterAll
	static void end() { //test가 실행되고 난 뒤 실행
		Query.dropTable();
	}
	
	@BeforeEach
	void before() {
		Query.boardDelete(0); //전체삭제 후 아래의 값을 넣어줌
		
		BoardVO bv1 = new BoardVO();
		bv1.setBtitle("타이틀1");
		bv1.setBcontent("내용1");
		Query.boardInsert(bv1);
		
		BoardVO bv2 = new BoardVO();
		bv2.setBtitle("타이틀2");
		bv2.setBcontent("내용2");
		Query.boardInsert(bv2);
	}
	
	@Test
	void TestA() {
		List<BoardVO> list = Query.getAllBoardList();
		Assert.assertEquals(2, list.size()); //레코드의 수는 2와 같다
		
		BoardVO vo2 = list.get(0);
		Assert.assertEquals("타이틀2", vo2.getBtitle()); //bid 2 레코드의 컬럼 값
		Assert.assertEquals("내용2", vo2.getBcontent());
		
		BoardVO vo1 = list.get(1);
		Assert.assertEquals("타이틀1", vo1.getBtitle()); //bid 1 레코드의 컬럼 값
		Assert.assertEquals("내용1", vo1.getBcontent());
	}
	
	@Test
	void TestB() {
		
		List<BoardVO> list = Query.getAllBoardList();
		
		BoardVO vo1 = list.get(0);
		Query.boardDelete(vo1.getBid());
		
		BoardVO vo1Db = Query.getBoardDetail(vo1.getBid());
		Assert.assertEquals(0, vo1Db.getBid());
		Assert.assertNull(vo1Db.getBtitle());
		Assert.assertNull(vo1Db.getBcontent());
		
		Assert.assertEquals(1, Query.getAllBoardList().size());
		
		BoardVO vo2 = list.get(1);
		Query.boardDelete(vo2.getBid());
		
		BoardVO vo2Db = Query.getBoardDetail(vo1.getBid());
		Assert.assertEquals(0, vo2Db.getBid());
		Assert.assertNull(vo2Db.getBtitle());
		Assert.assertNull(vo2Db.getBcontent());
		
		Assert.assertEquals(0, Query.getAllBoardList().size());
	}
	
	@Test
	void TestC() {
		
		BoardVO vo4 = new BoardVO();
		vo4.setBid(2);
		vo4.setBtitle("수정타이틀2");
		vo4.setBcontent("수정내용2");
		Query.boardUpdate(vo4);
		
		BoardVO vo3 = new BoardVO();
		vo3.setBid(1);
		vo3.setBtitle("수정타이틀1");
		vo3.setBcontent("수정내용1");
		Query.boardUpdate(vo3);
		
		
		List<BoardVO> list = Query.getAllBoardList();
		BoardVO vo2 = list.get(0);
		BoardVO vo1 = list.get(1);
		
		Assert.assertEquals(vo3.getBid(), vo1.getBid());
		Assert.assertEquals(vo3.getBtitle(), vo1.getBtitle());
		Assert.assertEquals(vo3.getBcontent(), vo1.getBcontent());
		
		Assert.assertEquals(vo4.getBid(), vo2.getBid());
		Assert.assertEquals(vo4.getBtitle(), vo2.getBtitle());
		Assert.assertEquals(vo4.getBcontent(), vo2.getBcontent());
		
		
		
		
	}

}
