package co.edu.uvp.pri.animation.ui.animation;

import co.edu.uvp.pri.animation.model.PacmanGameModel;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.Timer;


public class PacmanAnimation extends JComponent {

    private Timer timer = null;

    private PacmanGameModel model;

    private Image[] images;

    // Sprite
    private int direccion = 0;
    private int secuenciaImagenes = 0;
    private Image[][] pacmanSprite;

    private int pacmanIndex = 0;

    public PacmanAnimation() throws IOException {

        super.setFocusable(true);
        this.images = new Image[3];
        this.images[0] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/fondo.png"));
        this.images[1] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pared.png"));
        this.images[2] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/galleta.png"));

        this.pacmanSprite = new Image[4][28];
        this.pacmanSprite[0][0] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[0][1] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman02.png"));
        this.pacmanSprite[0][2] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman03.png"));
        this.pacmanSprite[0][3] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman04.png"));
        this.pacmanSprite[0][4] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman03.png"));
        this.pacmanSprite[0][5] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman02.png"));
        this.pacmanSprite[0][6] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[1][0] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[1][1] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman05.png"));
        this.pacmanSprite[1][2] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman06.png"));
        this.pacmanSprite[1][3] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman07.png"));
        this.pacmanSprite[1][4] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman06.png"));
        this.pacmanSprite[1][5] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman05.png"));
        this.pacmanSprite[1][6] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[2][0] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[2][1] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman08.png"));
        this.pacmanSprite[2][2] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman09.png"));
        this.pacmanSprite[2][3] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman10.png"));
        this.pacmanSprite[2][4] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman09.png"));
        this.pacmanSprite[2][5] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman08.png"));
        this.pacmanSprite[2][6] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[3][0] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));
        this.pacmanSprite[3][1] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman11.png"));
        this.pacmanSprite[3][2] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman12.png"));
        this.pacmanSprite[3][3] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman13.png"));
        this.pacmanSprite[3][4] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman12.png"));
        this.pacmanSprite[3][5] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman11.png"));
        this.pacmanSprite[3][6] = ImageIO.read(this.getClass().getResourceAsStream("/pacman/pacman01.png"));

        this.model = new PacmanGameModel();

        this.addKeyListener(new KeyAdapter() {

            @Override
            public void keyPressed(KeyEvent e) {
                int n = e.getKeyCode();
                switch (n) {
                    case 39:
                        model.move(PacmanGameModel.derecha);
                        setDireccion(0);
                        break;
                    case 40:
                        model.move(PacmanGameModel.abajo);
                        setDireccion(1);
                        break;
                    case 37:
                        model.move(PacmanGameModel.izquierda);
                        setDireccion(2);
                        break;
                    case 38:
                        model.move(PacmanGameModel.arriba);
                        setDireccion(3);
                        break;
                }

            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        int rows = this.model.getRows();
        int cols = this.model.getCols();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                int item = this.model.get(i, j);
                if (item == PacmanGameModel.pacman) {
                    g.drawImage(this.pacmanSprite[direccion][this.pacmanIndex], j * 20, i * 20, this);
                    continue;
                }
                g.drawImage(this.images[item], j * 20, i * 20, this);
            }
        }
    }

    public void nextFrame() {

        this.pacmanIndex = (this.pacmanIndex + 1) % 6;
    }

    public void init() {
        if (this.timer == null) {
            this.timer = new Timer(100, new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    nextFrame();
                    repaint();
                }
            });
        }
        if (!this.timer.isRunning()) {
            this.timer.start();
        }
    }

    public void pause() {
        this.timer.stop();
    }

    public void Continue() {
        this.pause();
        this.init();
    }

    public PacmanGameModel getModel() {
        return model;
    }

    public void setDireccion(int direccion) {
        this.direccion = direccion;
    }
}
