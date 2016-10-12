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
		goodFile = new File(GedComParser.absPath + "P01_good.ged");
		badFile = new File(GedComParser.absPath + "P01_bad.ged");
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
}