import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GedComParserTest {
	private GedComParser parser;
	private StringBuilder s;
	private File goodFile;
	private File badFile;
	
	@Before
	public void init() throws Exception {
		parser = new GedComParser();
		s = new StringBuilder();
		goodFile = new File(GedComParser.absPath + "good.ged");
		badFile = new File(GedComParser.absPath + "bad.ged");
	}

	@Test
	public void testDatesNotInFuture() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.validateDatesNotInFuture(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testDatesInFuture() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.validateDatesNotInFuture(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testDatesInFuture.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testBirthBeforeMarriage() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.birthBeforeMarriage(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testBirthAfterMarriage() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.birthBeforeMarriage(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testBirthAfterMarriage.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testBirthBeforeDeath() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.birthBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testBirthAfterDeath() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.birthBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testBirthAfterDeath.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testMarriageBeforeDivorce() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.marriageBeforeDivorce(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testMarriageAfterDivorce() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.marriageBeforeDivorce(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testMarriageAfterDivorce.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testMarriageBeforeDeath() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.marriageBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testMarriageAfterDeath() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.marriageBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testMarriageAfterDeath.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testDivorceBeforeDeath() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.divorceBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testDivorceAfterDeath() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.divorceBeforeDeath(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testDivorceAfterDeath.txt"), s.toString().getBytes());
	}
	
	@Test
	public void testLessThan150() throws Exception {
		parser.parse(goodFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.lessThan150(entry.getValue());
			s.append(str);
		}
		Assert.assertEquals("", s.toString());
		writePersonInfo();
	}
	
	@Test
	public void testMoreThan150() throws Exception {
		parser.parse(badFile);
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			String str = parser.lessThan150(entry.getValue());
			s.append(str);
		}
		Assert.assertNotEquals("", s.toString());
		Files.write(Paths.get(GedComParser.absPath + "testMoreThan150.txt"), s.toString().getBytes());
	}
	
	private void writePersonInfo() throws Exception {
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : parser.getPersonMap().entrySet()) {
			s.append("Person: " + entry.getValue() + "\r\n");
		}
		Files.write(Paths.get(GedComParser.absPath + "goodData.txt"), s.toString().getBytes());
	}
}