import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GedComParser {	
	public static final String absPath = new File("").getAbsolutePath() + "/resources/";
	private Set<String> eligibleTags = new HashSet<>();
	private Map<String, Person> personMap = new LinkedHashMap<>();
	private Map<String, Family> familyMap = new LinkedHashMap<>();
	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.print("Provide GEDCOM file name: ");
		File file = new File(absPath + input.nextLine());
		input.close();
		
		GedComParser parser = new GedComParser();
		parser.parse(file);
	}
	
	public GedComParser() throws Exception {
		Scanner i = new Scanner(new File(absPath + "tags.txt"));
		while(i.hasNextLine()) {
			eligibleTags.add(i.nextLine());
		}
		i.close();
	}
	
	public void parse(File file) throws Exception {
		Scanner input = new Scanner(file);
		Person p = null;
		Family f = null;
		NuptialInfo ni = null;
		String nextDate = null;
		while (input.hasNextLine()) {
            String line = input.nextLine();
            System.out.println("line:\t" + line);
            
            String[] lineArr = line.split(" ");
            String level = lineArr[0];
            System.out.println("level:\t" + level);
            
            String tagName = lineArr[1];
            if(level.equals("0") && tagName.startsWith("@")) {
            	tagName = lineArr[2];
            	String id = lineArr[1];
            	if(tagName.equals("INDI")) {
            		p = new Person();
            		p.setId(id);
            		personMap.put(id, p);
            	}
            	if(tagName.equals("FAM")) {
            		f = new Family();
            		f.setId(id);
            		familyMap.put(id, f);
            	}
            }
    		tagName = eligibleTags.contains(tagName) ? tagName : "Invalid tag";
            System.out.println("tag:\t" + tagName);
            
            if(tagName.equals("Invalid Tag"))
            	continue;
            
            if(level.equals("1")) {
            	if(tagName.equals("HUSB")) {
                	Person husband = personMap.get(lineArr[2]);
                	f.setHusband(husband);
                }
                if(tagName.equals("WIFE")) {
                	Person wife = personMap.get(lineArr[2]);
                	f.setWife(wife);
                }
                if(tagName.equals("CHIL")) {
                	Person child = personMap.get(lineArr[2]);
                	f.addChildren(child);
                }
                if(tagName.equals("NAME")) {
                	p.setfName(lineArr[2]);
                	p.setlName(lineArr[3]);
                }
                if(tagName.equals("SEX")) {
                	p.setSex(lineArr[2]);
                }
                if(tagName.equals("FAMC")) {
                	p.setChildOfFamilyId(lineArr[2]);
                }
                if(tagName.equals("MARR")) {
                	ni = new NuptialInfo();
                	p.addNuptials(ni);
                }
                if(tagName.equals("FAMS")) {
                	ni.setSpouseOfFamilyId(lineArr[2]);
                }
                if(tagName.equals("BIRT") || tagName.equals("DEAT") || tagName.equals("MARR") || tagName.equals("DIV")) {
                	nextDate = tagName;
                	continue;
                }
            }
            if(level.equals("2") && nextDate != null) {
            	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            	Date dt = sdf.parse(lineArr[2] + " " + lineArr[3] + " " + lineArr[4]);
            	if(nextDate.equals("BIRT")) {
            		p.setBirthDate(dt);
            	}
            	if(nextDate.equals("DEAT")) {
            		p.setDeathDate(dt);
            	}
            	if(nextDate.equals("MARR")) {
            		ni.setMarriageDate(dt);
            	}
            	if(nextDate.equals("DIV")) {
            		ni.setDivorceDate(dt);
            	}
            	nextDate = null;
            }
        }
		input.close();
		System.out.println("End of parsing");
	}

	public String validateDatesNotInFuture() {
		Date now = new Date();
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : personMap.entrySet()) {
			Person p = entry.getValue();
			if(p.getBirthDate() == null || p.getBirthDate().after(now))
				s.append("Birth cant be in the future for: " + p + "\r\n");
			if(p.getDeathDate() != null && p.getDeathDate().after(now))
				s.append("Death cant be in the future for: " + p + "\r\n");
			if(p.getNuptials() != null && !p.getNuptials().isEmpty()) {
				for(NuptialInfo ni : p.getNuptials()) {
					if(ni.getMarriageDate() != null && ni.getMarriageDate().after(now))
						s.append("Marriage cant be in the future for: " + p + "\r\n");
					if(ni.getDivorceDate() != null && ni.getDivorceDate().after(now))
						s.append("Divorce cant be in the future for: " + p + "\r\n");
				}
			}
		}
		return s.toString();
	}
	
	public String birthBeforeMarriage() {
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : personMap.entrySet()) {
			Person p = entry.getValue();
			if(p.getNuptials() != null && !p.getNuptials().isEmpty()) {
				for(NuptialInfo ni : p.getNuptials()) {
					if(ni.getMarriageDate() != null && ni.getMarriageDate().before(p.getBirthDate()))
						s.append("Marriage cant be before birth for: " + p + "\r\n");
				}
			}
		}
		return s.toString();
	}
	
	public String birthBeforeDeath() {
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : personMap.entrySet()) {
			Person p = entry.getValue();
			if(p.getDeathDate() != null && p.getDeathDate().before(p.getBirthDate())) {
					s.append("Death cant be before birth for: " + p + "\r\n");
			}
		}
		return s.toString();
	}
	
	public String marriageBeforeDivorce() {
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : personMap.entrySet()) {
			Person p = entry.getValue();
			if(p.getNuptials() != null && !p.getNuptials().isEmpty()) {
				for(NuptialInfo ni : p.getNuptials()) {
					if(ni.getMarriageDate() != null && ni.getDivorceDate() != null && 
							ni.getDivorceDate().before(ni.getMarriageDate()))
						s.append("Divorce cant be before marriage for: " + p + "\r\n");
				}
			}
		}
		return s.toString();
	}
	
	public String getPersonInfo() {
		StringBuilder s = new StringBuilder();
		for(Map.Entry<String, Person> entry : personMap.entrySet()) {
			s.append("Person: " + entry.getValue() + "\r\n");
		}
		return s.toString();
	}
}