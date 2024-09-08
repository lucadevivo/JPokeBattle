package battle.logic;

/**
 * Interface for observing updates in the game.
 */
public interface GameObserver {

    /**
     * Called to notify the observer of a game update.
     *
     * @param firstTrainer The first trainer participating in the battle.
     * @param secondTrainer The second trainer participating in the battle.
     * @param whichTrainer Indicates whose turn it is (0 for first trainer, 1 for second trainer).
     */
    void onGameUpdate(Trainer firstTrainer, Trainer secondTrainer, int whichTrainer);
}

