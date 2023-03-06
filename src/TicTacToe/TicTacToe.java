package TicTacToe;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Random;

public class TicTacToe extends JFrame implements ChangeListener, ActionListener {

    //El objeto JSlider "slider" es utilizado para cambiar el grosor de las líneas en el juego.
    private JSlider slider;

    //Los objetos JButton "oButton" y "xButton" son utilizados para cambiar los colores de las fichas O y X.
    private JButton oButton, xButton;

    //El objeto Board "board" es el panel donde se dibuja el juego.
    private Board board;

    //La variable "lineThickness" es utilizada para almacenar el grosor de las líneas en el juego.
    private int lineThickness = 4;

    //Las variables "oColor" y "xColor" almacenan los colores de las fichas O y X.
    private Color oColor = Color.BLUE, xColor = Color.RED;

    //Las constantes BLANK, O y X son utilizadas para representar los estados vacío, O y X en el tablero.
    static final char BLANK = ' ', O = 'O', X = 'X';

    //La matriz "position" representa el estado actual del tablero con BLANK, O y X.
    private char position[] = {
        BLANK, BLANK, BLANK,
        BLANK, BLANK, BLANK,
        BLANK, BLANK, BLANK};

    //Las variables "wins", "losses" y "draws" almacenan el número de victorias, derrotas y empates en el juego.
    private int wins = 0, losses = 0, draws = 0;

    // Inicialización  
    public TicTacToe() {
        // Esta es la inicialización de la ventana principal del juego. Se establece el título del juego como "Tic Tac Toe"
        super("Tic Tac Toe");

        // Se crea un panel llamado topPanel, se establece el layout como FlowLayout y se añaden los elementos gráficos necesarios para el juego.
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());

        // El primero es un JLabel con el texto "Grosor de la línea:" seguido de un JSlider para cambiar el grosor de las líneas.
        topPanel.add(new JLabel("Grosor de la línea:"));

        // El JSlider es configurado con un rango de 1 a 20 y un valor inicial de 4.
        topPanel.add(slider = new JSlider(SwingConstants.HORIZONTAL, 1, 20, 4));

        // Se activa la función de marcas en el JSlider y se agrega un listener para detectar cambios en el valor del JSlider.
        slider.setMajorTickSpacing(1);
        slider.setPaintTicks(true);
        slider.addChangeListener(this);

        // Se añaden dos botones, uno para cambiar el color de O y otro para cambiar el color de X, y se agrega un listener a cada uno de ellos para detectar cuando son presionados.
        topPanel.add(oButton = new JButton("O Color"));
        topPanel.add(xButton = new JButton("X Color"));
        oButton.addActionListener(this);
        xButton.addActionListener(this);

        // Se agrega el topPanel al layout NORTH de la ventana principal, y se agrega el panel board (que es donde se dibuja el juego) al layout CENTER.
        add(topPanel, BorderLayout.NORTH);
        add(board = new Board(), BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Se establece la operación al cerrar la ventana como JFrame.EXIT_ON_CLOSE, se establece el tamaño de la ventana como 500x500 y se hace visible la ventana.
        setSize(500, 500);
        setVisible(true);
    }

    // El método "stateChanged" se activa cada vez que el valor del deslizador (slider) cambia. El valor actual del deslizador se asigna a la variable "lineThickness" y se llama al método "repaint" del panel de juego (board) para redibujar la pantalla con el nuevo grosor de las líneas.
    public void stateChanged(ChangeEvent e) {
        lineThickness = slider.getValue();
        board.repaint();
    }

    // Cambiar el color de O o X
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == oButton) {
            Color newColor = JColorChooser.showDialog(this,
                    "Elige un nuevo color para O", oColor);
            if (newColor != null) {
                oColor = newColor;
            }
        } else if (e.getSource() == xButton) {
            Color newColor = JColorChooser.showDialog(this,
                    "Elija un nuevo color para X", xColor);
            if (newColor != null) {
                xColor = newColor;
            }
        }
        board.repaint();
    }

    // Creamos el trablero de juego
    private class Board extends JPanel implements MouseListener {

        private Random random = new Random();
        private int rows[][] = {{0, 2}, {3, 5}, {6, 8}, {0, 6}, {1, 7}, {2, 8}, {0, 8}, {2, 6}};
        // Puntos finales de las 8 filas en posición[] (a lo ancho, abajo, en diagonal)

        public Board() {
            addMouseListener(this);
        }
        //Redibujar el tablero

        public void paintComponent(Graphics g) {
            super.paintComponent(g);
            int w = getWidth();
            int h = getHeight();
            Graphics2D g2d = (Graphics2D) g;

            // dibujar la cuadrícula
            g2d.setPaint(Color.WHITE);
            g2d.fill(new Rectangle2D.Double(0, 0, w, h));
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(lineThickness));
            g2d.draw(new Line2D.Double(0, h / 3, w, h / 3));
            g2d.draw(new Line2D.Double(0, h * 2 / 3, w, h * 2 / 3));
            g2d.draw(new Line2D.Double(w / 3, 0, w / 3, h));
            g2d.draw(new Line2D.Double(w * 2 / 3, 0, w * 2 / 3, h));

            // Dibuja las Os y las X
            for (int i = 0; i < 9; ++i) {
                double xpos = (i % 3 + 0.5) * w / 3.0;
                double ypos = (i / 3 + 0.5) * h / 3.0;
                double xr = w / 8.0;
                double yr = h / 8.0;
                if (position[i] == O) {
                    g2d.setPaint(oColor);
                    g2d.draw(new Ellipse2D.Double(xpos - xr, ypos - yr, xr * 2, yr * 2));
                } else if (position[i] == X) {
                    g2d.setPaint(xColor);
                    g2d.draw(new Line2D.Double(xpos - xr, ypos - yr, xpos + xr, ypos + yr));
                    g2d.draw(new Line2D.Double(xpos - xr, ypos + yr, xpos + xr, ypos - yr));
                }
            }
        }

        // Dibuja una O donde se hace clic con el mouse
        public void mouseClicked(MouseEvent e) {
            int xpos = e.getX() * 3 / getWidth();
            int ypos = e.getY() * 3 / getHeight();
            int pos = xpos + 3 * ypos;
            if (pos >= 0 && pos < 9 && position[pos] == BLANK) {
                position[pos] = O;
                repaint();
                putX();  // computer plays
                repaint();
            }
        }

        // Ignorar otros eventos del mouse
        public void mousePressed(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }

        // La computadora juega X
        void putX() {
            // Comprobar si el juego ha terminado
            if (won(O)) {
                newGame(O);
            } else if (isDraw()) {
                newGame(BLANK);
            } // Juega X, posiblemente terminando el juego
            else {
                nextMove();
                if (won(X)) {
                    newGame(X);
                } else if (isDraw()) {
                    newGame(BLANK);
                }
            }
        }

        // Devuelve verdadera si la jugadora ha ganado
        boolean won(char player) {
            for (int i = 0; i < 8; ++i) {
                if (testRow(player, rows[i][0], rows[i][1])) {
                    return true;
                }
            }
            return false;
        }

        // ¿Ha ganado el jugador en la fila desde la posición [a] hasta la posición [b]?
        boolean testRow(char player, int a, int b) {
            return position[a] == player && position[b] == player
                    && position[(a + b) / 2] == player;
        }

        // Juega X en el mejor lugar
        void nextMove() {
            int r = findRow(X);  // completa una fila de X y gana si es posible
            if (r < 0) {
                r = findRow(O);  // o intenta bloquear a O para que no gane
            }
            if (r < 0) {  // de lo contrario, muévase al azar
                do {
                    r = random.nextInt(9);
                } while (position[r] != BLANK);
            }
            position[r] = X;
        }

        // Devuelve 0-8 para la posición de un espacio en blanco en una fila si el
        // otros 2 lugares están ocupados por el jugador, o -1 si no existe ningún lugar
        int findRow(char player) {
            for (int i = 0; i < 8; ++i) {
                int result = find1Row(player, rows[i][0], rows[i][1]);
                if (result >= 0) {
                    return result;
                }
            }
            return -1;
        }

        // Si 2 de 3 puntos en la fila desde la posición [a] hasta la posición [b]
        // están ocupados por el jugador y el tercero está en blanco, luego devuelva el
        // índice del espacio en blanco, de lo contrario devuelve -1.
        int find1Row(char player, int a, int b) {
            int c = (a + b) / 2;  // middle spot
            if (position[a] == player && position[b] == player && position[c] == BLANK) {
                return c;
            }
            if (position[a] == player && position[c] == player && position[b] == BLANK) {
                return b;
            }
            if (position[b] == player && position[c] == player && position[a] == BLANK) {
                return a;
            }
            return -1;
        }

        // ¿Están llenos los 9 lugares? empate 
        boolean isDraw() {
            for (int i = 0; i < 9; ++i) {
                if (position[i] == BLANK) {
                    return false;
                }
            }
            return true;
        }

        // Iniciar un nuevo juego
        void newGame(char winner) {
            // Anunciar el resultado del último juego. Pídele al usuario que vuelva a jugar.
            repaint();
            String result;
            if (winner == O) {
                ++wins;
                result = "Ganaste!";
            } else if (winner == X) {
                ++losses;
                result = "Yo gano!";
            } else {
                result = "Empate";
                ++draws;
            }
            if (JOptionPane.showConfirmDialog(null,
                    "Tú tienes " + wins + " gana, " + losses + " pérdidas, " + draws + " empates\n"
                    + "Juega de nuevo?", result, JOptionPane.YES_NO_OPTION)
                    != JOptionPane.YES_OPTION) {
                System.exit(0);
            }
            
            // Despejamos el tablero para comenzar un nuevo juego.
            for (int j = 0; j < 9; ++j) {
                position[j] = BLANK;
            }
            
             //La computadora comienza primero cada dos juegos
            if ((wins + losses + draws) % 2 == 1) {
                nextMove();
            }
        }
    }
    // Comenzar el juego
    public static void main(String args[]) {
        new TicTacToe();
    }
}
