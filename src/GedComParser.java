import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class GedComParser {
	public static final String absPath = new File("").getAbsolutePath() + "/resources/";
	
	public static void main(String[] args) throws Exception {
		Scanner input = new Scanner(System.in);
		System.out.print("Provide GEDCOM file name: ");
		File file = new File(absPath + input.nextLine());
		input.close();
		
		Set<String> eligibleTags = getEligibleTags();
		input = new Scanner(file);
		
		Map<String, Person> personMap = new HashMap<>();
		Map<String, Family> familyMap = new HashMap<>();
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
            System.out.println();
            
            if(tagName.equals("Invalid Tag"))
            	continue;
            
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
            if(tagName.equals("FAMS")) {
            	ni.setSpouseOfFamilyId(lineArr[2]);
            }
            if(nextDate != null) {
            	SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
            	Date dt = sdf.parse(lineArr[2] + " " + lineArr[3] + " " + lineArr[4]);
            	if(nextDate.equals("BIRT")) {
            		p.setBirthDate(dt);
            	}
            	if(nextDate.equals("DEAT")) {
            		p.setDeathDate(dt);
            	}
            	if(nextDate.equals("MARR")) {
            		ni = new NuptialInfo();
            		ni.setMarriageDate(dt);
            		p.addNuptials(ni);
            	}
            	if(nextDate.equals("DIV")) {
            		ni.setDivorceDate(dt);
            	}
            }
            if(tagName.equals("BIRT") || tagName.equals("DEAT") || tagName.equals("MARR") || tagName.equals("DIV")) {
            	nextDate = tagName;
            } else {
            	nextDate = null;
            }
        }
		input.close();
		System.out.println("End of parsing");
		
		for(Map.Entry<String, Family> entry : familyMap.entrySet()) {
			System.out.println(entry.getValue());
			System.out.println("----------------------------------------------------------");
		}
	}
		
	private static Set<String> getEligibleTags() throws Exception {
		Set<String> tags = new HashSet<>();
		Scanner i = new Scanner(new File(absPath + "tags.txt"));
		while(i.hasNextLine()) {
			tags.add(i.nextLine());
		}
		i.close();
		return tags;
	}
}