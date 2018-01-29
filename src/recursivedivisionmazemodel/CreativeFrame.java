package recursivedivisionmazemodel;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class CreativeFrame extends JFrame {

    private int offsetX;
    private int offsetY;
    private static final int TITLE_BAR_HEIGHT = 20;
    
    public CreativeFrame(JPanel canvas) {
        setLayout(null);
        setUndecorated(true);
        setSize(canvas.getWidth(), TITLE_BAR_HEIGHT+canvas.getHeight());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        addAll(canvas);
        JPanel pnlTitle = new JPanel();
        pnlTitle.setBounds(0, 0, canvas.getWidth(), TITLE_BAR_HEIGHT);
        pnlTitle.setBackground(new Color(0, 200, 200));
        add(pnlTitle);
        pnlTitle.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                offsetX = e.getX();
                offsetY = e.getY();
            }
        });
        pnlTitle.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(
                        e.getLocationOnScreen().x-offsetX,
                        e.getLocationOnScreen().y-offsetY
                );
            }
        });
    }
    
    private void addAll(JPanel canvas) {
        canvas.setBounds(0, TITLE_BAR_HEIGHT, canvas.getWidth(), canvas.getHeight());
        add(canvas);
    }
}
