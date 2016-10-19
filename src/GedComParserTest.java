import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class GedComParserTest {
	private GedComParser parser;
	private File goodFile;
	private File badFile;
	
	@Before
	public void init() throws Exception {
		parser = new GedComParser();
		goodFile = new File(GedComParser.absPath + "good.ged");
		badFile = new File(GedComParser.absPath + "bad.ged");
	}

	@Test
	public void testDatesNotInFuture() throws Exception {
		parser.parse(goodFile);
		String s = parser.validateDatesNotInFuture();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testDatesNotInFuture.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testDatesInFuture() throws Exception {
		parser.parse(badFile);
		String s = parser.validateDatesNotInFuture();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testDatesInFuture.txt"), s.getBytes());
	}
	
	@Test
	public void testBirthBeforeMarriage() throws Exception {
		parser.parse(goodFile);
		String s = parser.birthBeforeMarriage();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testBirthBeforeMarriage.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testBirthAfterMarriage() throws Exception {
		parser.parse(badFile);
		String s = parser.birthBeforeMarriage();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testBirthAfterMarriage.txt"), s.getBytes());
	}
	
	@Test
	public void testBirthBeforeDeath() throws Exception {
		parser.parse(goodFile);
		String s = parser.birthBeforeDeath();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testBirthBeforeDeath.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testBirthAfterDeath() throws Exception {
		parser.parse(badFile);
		String s = parser.birthBeforeDeath();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testBirthAfterDeath.txt"), s.getBytes());
	}
	
	@Test
	public void testMarriageBeforeDivorce() throws Exception {
		parser.parse(goodFile);
		String s = parser.marriageBeforeDivorce();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testMarriageBeforeDivorce.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testMarriageAfterDivorce() throws Exception {
		parser.parse(badFile);
		String s = parser.marriageBeforeDivorce();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testMarriageAfterDivorce.txt"), s.getBytes());
	}
	
	@Test
	public void testMarriageBeforeDeath() throws Exception {
		parser.parse(goodFile);
		String s = parser.marriageBeforeDeath();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testMarriageBeforeDeath.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testMarriageAfterDeath() throws Exception {
		parser.parse(badFile);
		String s = parser.marriageBeforeDeath();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testMarriageAfterDeath.txt"), s.getBytes());
	}
	
	@Test
	public void testDivorceBeforeDeath() throws Exception {
		parser.parse(goodFile);
		String s = parser.divorceBeforeDeath();
		Assert.assertEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testDivorceBeforeDeath.txt"), parser.getPersonInfo().getBytes());
	}
	
	@Test
	public void testDivorceAfterDeath() throws Exception {
		parser.parse(badFile);
		String s = parser.divorceBeforeDeath();
		Assert.assertNotEquals("", s);
		Files.write(Paths.get(GedComParser.absPath + "testDivorceAfterDeath.txt"), s.getBytes());
	}
}