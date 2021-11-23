import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileIO {

	public Character readCharacter(String fileName) {
		Character character = null;
		try (Scanner z = new Scanner(new FileReader(new File(fileName)))) {
			while (z.hasNextLine()) {
				String[] line = z.nextLine().trim().split(",");
				character = new Character(line[0].trim(), Double.parseDouble(line[1].trim()),
						Double.parseDouble(line[2].trim()), Integer.parseInt(line[3].trim()));
			}
		} catch (FileNotFoundException e) {
			System.err.println("CANNOT FILE THE FILE!");
		}
		return character;
	}

	public void writeCharacter(String fileName, Character c) {
		try (FileWriter fw = new FileWriter(fileName, false)) {
			fw.write(String.format("%s,%s,%s,%s", c.getName().trim(), c.getAttackValue(), c.getMaximumHealth(),
					c.getWins()));
		} catch (FileNotFoundException e) {
			System.err.println("COULD NOT WRITE TO FILE: FILE NOT FOUND!");
		} catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
	}

	public List<Spell> readSpells(String fileName) {
		List<Spell> spells = new ArrayList<>();
		try (Scanner z = new Scanner(new FileReader(new File(fileName)))) {
			Spell spell = null;
			while (z.hasNextLine()) {
				String[] line = z.nextLine().trim().split(",");
				spell = new Spell(line[0].trim(), Double.parseDouble(line[1].trim()),
						Double.parseDouble(line[2].trim()), Double.parseDouble(line[3].trim()));
				spells.add(spell);
			}
		} catch (FileNotFoundException e) {
			System.err.println("CANNOT FILE THE FILE!");
		}
		return spells;
	}

}
