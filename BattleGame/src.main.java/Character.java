import java.util.List;
import java.util.Optional;
import java.util.Random;

public class Character {

	private String name;
	private double attackValue;
	private double maximumHealth;
	private double currentHealth;
	private int wins;
	private List<Spell> spells;

	/**
	 * @param name
	 * @param attackValue
	 * @param maximumHealth
	 * @param wins
	 */
	public Character(String name, double attackValue, double maximumHealth, int wins) {
		super();
		this.name = name;
		this.attackValue = attackValue;
		this.maximumHealth = maximumHealth;
		this.wins = wins;
		this.currentHealth = maximumHealth;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the attackValue
	 */
	public double getAttackValue() {
		return attackValue;
	}

	/**
	 * @return the maximumHealth
	 */
	public double getMaximumHealth() {
		return maximumHealth;
	}

	/**
	 * @return the currentHealth
	 */
	public double getCurrentHealth() {
		return currentHealth;
	}

	/**
	 * @return the wins
	 */
	public int getWins() {
		return wins;
	}

	public double takeDamage(double attkValue) {
		this.currentHealth = this.currentHealth - attkValue;
		return this.currentHealth;
	}

	/**
	 * @return the attackDamage
	 */
	public double getAttackDamage(int seed) {
		Random r = new Random(seed);
		double rand = 0.9 + (.9 - 0.7) * r.nextDouble();
		System.err.println("RANDOM: " + rand);
		return attackValue * rand;
	}

	public void increaseWins() {
		wins++;
	}

	public double castSpell(String spell, int damage) {
		Optional<Spell> opt = spells.stream().filter(e -> (e.getName().equalsIgnoreCase(spell))).findFirst();
		if (opt.isPresent()) {
			Spell s = opt.get();
			return s.getMagicDamage(damage);
		}
		return -1;
	}

	public void displaySpells() {
		int i = 1;
		for (Spell s : spells) {
			System.out.printf("[%d] -> { %s }\n", i, s.toString());
			i++;
		}
	}

	/**
	 * @return the spells
	 */
	public List<Spell> getSpells() {
		return spells;
	}

	/**
	 * @param spells the spells to set
	 */
	public void setSpells(List<Spell> spells) {
		this.spells = spells;
	}

	@Override
	public String toString() {
		return String.format("%24s , %4.2f", name, currentHealth);
	}

}
