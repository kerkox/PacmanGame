package co.edu.uvp.pri.animation.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PacmanGameModel {
    
    public static final int vacio = 0;
    public static final int pared = 1;
    public static final int galleta = 2;
    public static final int pacman = 3;
    
    public static final int izquierda = 0;
    public static final int derecha = 1;
    public static final int arriba = 2;
    public static final int abajo = 3;
    
    private byte[][] entorno; 

    // Posición de pacman
    private int pi, pj;
    
    private int nivel = 1;
    private int puntos = 0;
    private int galletasDisponibles = 0;
    
    public PacmanGameModel() {
        this.initGame(nivel);
    }
    
    private ResourceBundle bundle = ResourceBundle.getBundle("pacman/Levels");
    
    public void initGame(int level) {
        
        String string = bundle.getString("level" + level);
        String[] arrayStringEntorno = string.split(" ");
        
        int rows = Integer.parseInt(arrayStringEntorno[0]);
        int cols = Integer.parseInt(arrayStringEntorno[1]);
        
        pi = Integer.parseInt(arrayStringEntorno[2]);
        pj = Integer.parseInt(arrayStringEntorno[3]);
        galletasDisponibles = Integer.parseInt(arrayStringEntorno[4]);
        
        this.entorno = new byte[rows][cols];
        
        int k = 5;
        
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < rows; j++) {
                entorno[i][j] = Byte.parseByte(arrayStringEntorno[k]);
                k++;
            }
        }
        
        this.entorno[pi][pj] = pacman;
    }
    
    public int get(int row, int col) {
        return this.entorno[row][col];
    }
    
    public void move(int direction) {
        this.entorno[pi][pj] = vacio;
        
        int oldPj = pj;
        int oldPi = pi;
        
        switch (direction) {
            case derecha:
                this.pj++;
                if (this.pj == this.entorno[0].length) {
                    this.pj = 0;
                }
                break;
            case izquierda:
                this.pj--;
                if (this.pj == -1) {
                    this.pj = this.entorno[0].length - 1;
                }
                break;
            case arriba:
                this.pi--;
                if (this.pi == -1) {
                    this.pi = this.entorno.length - 1;
                }
                break;
            case abajo:
                this.pi++;
                if (this.pi == this.entorno.length) {
                    this.pi = 0;
                }
                break;
        }
        
        if (this.get(pi, pj) == pared) {
            this.pi = oldPi;
            this.pj = oldPj;
        }
        if (this.get(pi, pj) == galleta) {
            incrementarPuntos();
        }
        if (galletasDisponibles == 0) {
            if (nivel == 5) {
                nivel = 1;
                RestartPoints(0);
                ChangeLevel(nivel);
            } else {
                nivel++;
                ChangeLevel(nivel);
            }
            
        }
        
        this.entorno[pi][pj] = pacman;
    }
    
    public int getRows() {
        return this.entorno.length;
    }
    
    public int getCols() {
        return this.entorno[0].length;
    }
    
    public int getPuntos() {
        return puntos;
    }
    
    public void incrementarPuntos() {
        this.puntos++;
        this.galletasDisponibles--;
        for (PropertyChangeListener Listener : this.listeners) {
            Listener.propertyChange(new PropertyChangeEvent(this, "puntos", puntos - 1, puntos));
        }
    }
    
    public void RestartPoints(int puntos) {
        this.puntos = puntos;
        for (PropertyChangeListener Listener : this.listeners) {
            Listener.propertyChange(new PropertyChangeEvent(this, "puntos", puntos, 0));
        }
    }
    
    private List<PropertyChangeListener> listeners = new ArrayList<>();
    
    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        this.listeners.add(pcl);
    }
    
    public void ChangeLevel(int level) {
        this.nivel = level;
        this.initGame(nivel);
    }
}
