package GUI;

public abstract class AIPlayer {
    protected int ROWS = GameMain.ROWS;
    protected int COLS = GameMain.COLS;

    protected Cell[][] cells;
    protected Seed mySeed;
    protected Seed oppSeed;


    public AIPlayer(Board board) {
        cells = board.cells;
    }

    public void setSeed(Seed seed) {
        this.mySeed = seed;
        oppSeed = (mySeed == Seed.CROSS) ? Seed.NOUGHT : Seed.CROSS;
    }

    abstract int[] move();  // to be implemented by subclasses
}