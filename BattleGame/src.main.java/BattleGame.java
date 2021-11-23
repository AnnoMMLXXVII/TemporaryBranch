import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class BattleGame {

	private static Random r = new Random();
	private static Scanner z = new Scanner(System.in);
	private static Character primus, exPrimus;
	private static boolean isPlayersTurn = true;
	private static boolean didPlayerWin = false;
	private static List<Spell> spells;

	public static void main(String... args) {
		playGame("player.txt", "monster.txt", "spell.txt");
	}

	public static void playGame(String playerFile, String monsterFile, String spellFile) {
		FileIO fio = new FileIO();
		primus = fio.readCharacter(playerFile);
		exPrimus = fio.readCharacter(monsterFile);
		spells = fio.readSpells(spellFile);
		if (spells == null) {
			System.err.println("The Game Shall be Played WITHOUT Spells");
		} else {
			primus.setSpells(spells);
			exPrimus.setSpells(spells);
		}
		int rand = r.nextInt();
		printCharacters();
		while (primus.getCurrentHealth() > 0 || exPrimus.getCurrentHealth() > 0) {
			if (playingGame(rand)) {
				fio.writeCharacter(playerFile, primus);
				fio.writeCharacter(monsterFile, exPrimus);
				System.exit(0);
			}
			rand = r.nextInt();

		}
	}

	private static boolean playingGame(int rand) {
		double dmgTaken = 0.0;
		String answer = "Q";
		if (isPlayersTurn()) {
			promptCommands();
			answer = z.nextLine();
			if (answer.equalsIgnoreCase("A")) {
				dmgTaken = performAttackAction(rand);
			} else if (answer.equalsIgnoreCase("C")) {
				dmgTaken = performCastAction(rand);
			} else if (answer.equalsIgnoreCase("Q")) {
				goodbyeMessage();
			} else {
				printErrorInputMessage();
				return playingGame(rand);
			}
		} else {
			promptCommands();
			answer = z.nextLine();
			if (answer.equalsIgnoreCase("A")) {
				dmgTaken = performAttackAction(rand);
			} else if (answer.equalsIgnoreCase("C")) {
				dmgTaken = performCastAction(rand);
			} else if (answer.equalsIgnoreCase("Q")) {
				goodbyeMessage();
			} else {
				printErrorInputMessage();
				return playingGame(rand);
			}
		}
		printEndOfTurnResults(dmgTaken);
		if (hasGameEnded()) {
			System.out.println("---------------END-------------");
			printEndGameResultsMessage(isDidPlayerWin());
			goodbyeMessage();
			return true;
		}

		return false;
	}

	private static double playersTurn(int rand) {
		double primusAttack = primus.getAttackDamage(rand);
		print("Player Attack Dmg", primusAttack + "");
		return exPrimus.takeDamage(primusAttack);
	}

	private static double playersTurn(String spell, int damage) {
		double primusAttack = primus.castSpell(spell, damage);
		print("Player Attack Dmg", primusAttack + "");
		if (primusAttack == 0.0) {
			print("Player Failed to Cast Spell ", spell);
		}
		return exPrimus.takeDamage(primusAttack);
	}

	private static double monstersTurn(int rand) {
		double exPrimusAttack = exPrimus.getAttackDamage(rand);
		print("Monster Attack Dmg", exPrimusAttack + "");
		return primus.takeDamage(exPrimusAttack);
	}

	private static double monstersTurn(String spell, int damage) {
		double exPrimusAttack = exPrimus.castSpell(spell, damage);
		print("Monster Attack Dmg", exPrimusAttack + "");
		if (exPrimusAttack == 0.0) {
			print("Monster Failed to Cast Spell ", spell);
		}
		return primus.takeDamage(exPrimusAttack);
	}

	private static double performAttackAction(int rand) {
		double dmgTaken = 0.0;
		if (isPlayersTurn()) {
			dmgTaken = playersTurn(rand);
			setPlayersTurn(false);
		} else {
			dmgTaken = monstersTurn(rand);
			setPlayersTurn(true);
		}
		return dmgTaken;
	}

	private static double performCastAction(int damage) {
		System.out.println("Enter a Spell to Cast (Enter Spell Name : i.e fireball): ");
		double dmgTaken = 0.0;
		String input = "";
		if (isPlayersTurn()) {
			primus.displaySpells();
			input = z.nextLine();
			dmgTaken = playersTurn(input, damage);
			setPlayersTurn(false);
		} else {
			exPrimus.displaySpells();
			input = z.nextLine();
			dmgTaken = monstersTurn(input, damage);
			setPlayersTurn(true);
		}
		return dmgTaken;
	}

	private static void printEndOfTurnResults(double dmg) {
		System.out.printf("%s",
				String.format("%s health status %4.2f/%4.2f\n", (isPlayersTurn() ? "Player" : "Monster"), dmg,
						(isPlayersTurn() ? primus.getMaximumHealth() : exPrimus.getMaximumHealth())));
		printCharacters();
		System.out.println();
	}

	private static void printCharacters() {
		System.out.printf("Player : %s, %3.2f, %2d\n", primus.toString(), primus.getAttackValue(), primus.getWins());
		System.out.printf("Monster: %s, %3.2f, %2d\n", exPrimus.toString(), exPrimus.getAttackValue(),
				exPrimus.getWins());
	}

	private static void promptCommands() {
		System.out.printf("%s Turn : Attack [A], Cast Spell [C], or Quit [Q]\n",
				(isPlayersTurn() ? "Player's" : "Monster's"));
	}

	private static void print(String prompt, String message) {
		System.out.printf("%s -> %s\n", prompt, message);
	}

	private static boolean hasGameEnded() {
		if (primus.getCurrentHealth() == 0.0 || primus.getCurrentHealth() < 0.0) {
			setDidPlayerWin(false);
			exPrimus.increaseWins();
			return true;
		}
		if (exPrimus.getCurrentHealth() == 0.0 || exPrimus.getCurrentHealth() < 0.0) {
			setDidPlayerWin(true);
			primus.increaseWins();
			return true;
		}
		return false;
	}

	private static void goodbyeMessage() {
		System.out.println("*************************");
		System.out.println("*                       *");
		System.out.println("*                       *");
		System.out.println("* THANK YOU FOR PLAYING *");
		System.out.println("*                       *");
		System.out.println("*       GOOD BYE        *");
		System.out.println("*                       *");
		System.out.println("*************************");
	}

	private static void printErrorInputMessage() {
		System.err.println("*** ERROR ***");
		System.out.println("The answer provided did not match!");
		System.out.println("Please Try again!");
	}

	private static void printEndGameResultsMessage(boolean didPlayerWin) {
		System.out.printf("%s\n",
				didPlayerWin ? ("CONGRATS YOU HAVE WON!") : ("DEFEATED. YOU HAVE LOST! Your Health reached Zero!"));
		printCharacters();

	}

	/**
	 * @return the isPlayersTurn
	 */
	private static boolean isPlayersTurn() {
		return isPlayersTurn;
	}

	/**
	 * @param isPlayersTurn the isPlayersTurn to set
	 */
	private static void setPlayersTurn(boolean isPlayersTurn) {
		BattleGame.isPlayersTurn = isPlayersTurn;
	}

	/**
	 * @return the didPlayerWin
	 */
	private static boolean isDidPlayerWin() {
		return didPlayerWin;
	}

	/**
	 * @param didPlayerWin the didPlayerWin to set
	 */
	private static void setDidPlayerWin(boolean didPlayerWin) {
		BattleGame.didPlayerWin = didPlayerWin;
	}

}
