import java.util.Random;

public class Spell {
	private String name;
	private double maxDamage;
	private double minDamage;
	private double chance;

	/**
	 * @param name
	 * @param maxDamage
	 * @param minDamage
	 * @param chance
	 */
	public Spell(String name, double maxDamage, double minDamage, double chance) {
		super();
		this.name = name;
		this.maxDamage = maxDamage;
		this.minDamage = minDamage;
		this.chance = chance;
		if (minDamage < 0.0 || maxDamage < minDamage) {
			throw new IllegalArgumentException("Minimum Damage cannot be less than zero or Greater than Max Damage!");
		}
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the maxDamage
	 */
	public double getMaxDamage() {
		return maxDamage;
	}

	/**
	 * @return the minDamage
	 */
	public double getMinDamage() {
		return minDamage;
	}

	/**
	 * @return the chance
	 */
	public double getChance() {
		return chance;
	}

	public double getMagicDamage(int seed) {
		Random r = new Random(seed);
		double rand = r.nextDouble() + 0;
		if (rand > getChance()) {
			return 0.0;
		}
		return getMaxDamage() + (getMaxDamage() - getMinDamage()) * rand;
	}

	@Override
	public String toString() {
		return String.format("Name : %20s, MaxDmg : %3.2f, MinDmg : %3.2f, Chance to Cast : %2.1f%%", name, maxDamage,
				minDamage, (chance * 100));
	}

}
