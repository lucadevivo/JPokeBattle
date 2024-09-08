package battle.logic;

/**
 * Interface for observing the end of a battle.
 */
public interface BattleObserver {	
	/**
     * Method called when a battle ends.
     * @param result The result of the battle:
     *               - 0 if the battle is ongoing.
     *               - 1 if the first trainer wins.
     *               - 2 if the second trainer wins.
     */
	void onBattleEnd(int i);
}
