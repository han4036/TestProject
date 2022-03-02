package board;

import org.junit.jupiter.api.Test;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.log4j.Log4j2;

@SpringBootTest
@Log4j2
class BoardApplicationTests {
	
	@Autowired
	private SqlSessionTemplate sqlSession;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void testSqlSession() throws Exception {
		log.debug(sqlSession.toString());
	}

}
