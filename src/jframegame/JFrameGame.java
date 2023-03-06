package jframegame;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFrameGame extends JFrame {

    private JTextField guessField;
    private JLabel resultLabel, remainingLebal;
    private JButton checkButton, newButton;

    private int randomNumber, remainingGuesses;

    public JFrameGame() {

        setTitle("Juego de adivinar números");
        setSize(300, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        //CREAR ELEMENTOS DE INTERFAZ
        guessField = new JTextField(10);
        checkButton = new JButton("Verificar");
        newButton = new JButton("Nuevo Número");
        resultLabel = new JLabel("Ingrese un número y presione verificar ");
        remainingLebal = new JLabel("Intentos restantes: ");

        //AÑADIR ELEMENTOS A LA VENTANA
        JPanel content = new JPanel();
        
        content.add(guessField);
        content.add(checkButton);
        content.add(newButton);
        content.add(resultLabel);
        content.add(remainingLebal);

        add(content);

       randomNumber = new Random().nextInt(10) + 1;
        remainingGuesses = 5;

        //ACCIONES DE LOS BOTON
        checkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                int guess = Integer.parseInt(guessField.getText());

                if (guess == randomNumber) {

                    resultLabel.setText("Acertaste el número");
                    remainingLebal.setText("");
                    checkButton.setEnabled(false);
                } else {

                    if (guess < randomNumber) {
                        resultLabel.setText("El número que trata de adivinar es mayor ");
                    } else {
                        resultLabel.setText("El número que trata de adivinar es menor ");
                    }
                    remainingGuesses--;
                    remainingLebal.setText("Intentos restantes: " + remainingGuesses);

                    if (remainingGuesses == 0) {
                        resultLabel.setText("Se acabaron los intentos, el número oculto era: " + randomNumber);
                        checkButton.setEnabled(false);
                        
                    }

                }

            }

        });

        newButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                randomNumber = new Random().nextInt(10)+1;
                remainingGuesses = 5;
                remainingLebal.setText("\nIntentos restantes: " + remainingGuesses);
                checkButton.setEnabled(true);
                resultLabel.setText("Ingrese un número y presione verificar");
                guessField.setText( "");
            }

        });

    }

    public static void main(String[] args) {
        
        JFrameGame game = new JFrameGame();
        game.setVisible(true);

    }

}
