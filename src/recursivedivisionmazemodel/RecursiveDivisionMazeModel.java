package recursivedivisionmazemodel;

import java.util.ArrayList;
import java.util.List;

public class RecursiveDivisionMazeModel {

    private static char PASSAGE = ' ';
    private static char WALL = '#';
    private int rCellCount;
    private int cCellCount;
    private char[][] maze;
    private List<Integer[]> brokenWalls = new ArrayList<>();
    
    public RecursiveDivisionMazeModel(int h, int w) {
        rCellCount = h;
        cCellCount = w;
        maze = new char[2*h+1][2*w+1];
        initFreshMaze();
        breakWall(0, rCellCount, 0, cCellCount);
    }
    
    private void initFreshMaze() {
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                if (r%2 == 1 && c%2 == 1) {
                    maze[r][c] = PASSAGE;
                } else {
                    maze[r][c] = WALL;
                }
            }
        }
    }
    
    private void breakWall(int ra, int rb, int ca, int cb) {
        if (cb-ca <= 1 && rb-ra <= 1) return;
        if (rb-ra >= cb-ca) { //height > width, horizontal cut
            int midR = (ra+rb)/2;
            int offC = (int)(Math.random()*(cb-ca))+ca;
            maze[midR*2][offC*2+1] = PASSAGE;
            brokenWalls.add(new Integer[] {0, midR*2, offC*2+1, ca, cb});
            breakWall(ra, midR, ca, cb);
            breakWall(midR, rb, ca, cb);
        } else {    //width > height, vertical cut
            int midC = (ca+cb)/2;
            int offR = (int)(Math.random()*(rb-ra))+ra;
            maze[offR*2+1][midC*2] = PASSAGE;
            brokenWalls.add(new Integer[] {1, offR*2+1, midC*2, ra, rb});
            breakWall(ra, rb, ca, midC);
            breakWall(ra, rb, midC, cb);
        }
    }
    
    public void printMazeToStdout() {
        for (int i = 1; i <= 35; i++)
            System.out.println();
        for (int r = 0; r < maze.length; r++) {
            for (int c = 0; c < maze[0].length; c++) {
                System.out.print(maze[r][c]);
            }
            System.out.println();
        }
    }

    public List<Integer[]> getBrokenWalls() {
        return brokenWalls;
    }
}
