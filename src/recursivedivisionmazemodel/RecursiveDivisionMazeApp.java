package recursivedivisionmazemodel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.List;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
public class RecursiveDivisionMazeApp extends JPanel {
    
    private final static Color PASSAGE_COLOR = Color.BLACK;
    private final static Color WALL_COLOR = Color.CYAN;
    private final static long DELAY = 50;
    private final static int SMALL_SIDE = 3;
    private final static int LARGE_SIDE = 30;
    private final static int R_CELL_COUNT = 12+(int)(Math.random()*8);
    private final static int C_CELL_COUNT = 15+(int)(Math.random()*10);
    private int canvasHeight;
    private int canvasWidth;
    private RecursiveDivisionMazeModel model;
    private Rectangle[][] mazeRect;
    private Color[][] mazeColor;
    private List<Integer[]> brokenWalls;
    
    public RecursiveDivisionMazeApp() {
        model = new RecursiveDivisionMazeModel(R_CELL_COUNT, C_CELL_COUNT);
        brokenWalls = model.getBrokenWalls();
        mazeRect = new Rectangle[2*R_CELL_COUNT+1][2*C_CELL_COUNT+1];
        initMazeRect();
        mazeColor = new Color[2*R_CELL_COUNT+1][2*C_CELL_COUNT+1];
        initMazeColor();
        setSize(canvasWidth, canvasHeight);
    }
    
    private void initMazeRect() {
        int rPos = 0;
        int cPos = 0;
        for (int r = 0; r < mazeRect.length; r++) {
            for (int c = 0; c < mazeRect[r].length; c++) {
                if (r%2 == 0) {
                    if (c%2==0) {
                        mazeRect[r][c] = new Rectangle(cPos, rPos, SMALL_SIDE, SMALL_SIDE);
                        cPos += SMALL_SIDE;
                    } else {
                        mazeRect[r][c] = new Rectangle(cPos, rPos, LARGE_SIDE, SMALL_SIDE);
                        cPos += LARGE_SIDE;
                    }
                } else {
                    if (c%2==0) {
                        mazeRect[r][c] = new Rectangle(cPos, rPos, SMALL_SIDE, LARGE_SIDE);
                        cPos += SMALL_SIDE;
                    } else {
                        mazeRect[r][c] = new Rectangle(cPos, rPos, LARGE_SIDE, LARGE_SIDE);
                        cPos += LARGE_SIDE;
                    }
                }
            }
            canvasWidth = cPos;
            cPos = 0;
            if (r%2 == 0) rPos += SMALL_SIDE;
            else rPos += LARGE_SIDE;
            canvasHeight = rPos;
        }
    }
    
    private void initMazeColor() {
        for (int r = 0; r < mazeColor.length; r++) {
            for (int c = 0; c < mazeColor[r].length; c++) {
                if (r == 0 || c == 0 || r == mazeColor.length-1 || c == mazeColor[r].length-1) {
                    mazeColor[r][c] = WALL_COLOR;
                } else {
                    mazeColor[r][c] = PASSAGE_COLOR;
                }
            }
        }
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int r = 0; r < mazeRect.length; r++) {
            for (int c = 0; c < mazeRect[r].length; c++) {
                g.setColor(mazeColor[r][c]);
                g.fillRect(
                        mazeRect[r][c].x,
                        mazeRect[r][c].y,
                        mazeRect[r][c].width,
                        mazeRect[r][c].height
                );
            }
        }
    }
    
    private static void sleep(long delay) {
        try {
            Thread.sleep(delay);
        } catch (Exception ex) {}
    }
    
    public void startAlgoThread() {
        new Thread(() -> {
            for (int i = 0; i < brokenWalls.size(); i++) {
                Integer[] cur = brokenWalls.get(i);
                if (cur[0] == 0) { //horizontal cut
                    for (int c = 2*cur[3]; c <= 2*cur[4]; c++) {
                        if (c == cur[2]) continue;
                        mazeColor[cur[1]][c] = WALL_COLOR;
                    }
                } else if (cur[0] == 1) { //vertical cut
                    for (int r = 2*cur[3]; r <= 2*cur[4]; r++) {
                        if (r == cur[1]) continue;
                        mazeColor[r][cur[2]] = WALL_COLOR;
                    }
                }
                sleep(DELAY);
                repaint();
            }
        }).start();
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            RecursiveDivisionMazeApp canvas = new RecursiveDivisionMazeApp();
            CreativeFrame frame = new CreativeFrame(canvas);
            frame.setVisible(true);
            canvas.startAlgoThread();
        });
    }
}
