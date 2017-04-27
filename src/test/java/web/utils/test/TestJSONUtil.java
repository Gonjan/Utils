package web.utils.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import web.utils.JSONUtil;
import POJO.Person;

public class TestJSONUtil {

	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	@Ignore
	public void testProxyCheck() {
		
	}

	@Test
	public void testToJSONString() {
		List<Person> list = new ArrayList<>();
		Map<String, List<Person>> map = new HashMap<>();
	    list.add(new  Person("wangwu"));
		list.add(new Person("liuliu"));
		map.put("list", list);
		String result = JSONUtil.toJSONString(map);
		String expected = "{\"list\":[{\"name\":\"wangwu\",\"age\":0},{\"name\":\"liuliu\",\"age\":0}]}";
		System.out.println(result);
		assertEquals(expected, result);
		
		String[] strings = {"hello","world"};
		String expected1 = "[\"hello\",\"world\"]";
		String result1 = JSONUtil.toJSONString(strings);
		assertEquals(expected1, result1);
		
		
		
		
	}
}
