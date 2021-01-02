import java.awt.*;
import javax.swing.*;

/**
 * general purpose powerful free layouts:
 * JGoodies' FormLayout
 * MigLayout
 * DesignGridLayout
 */

public class Xat {
    private static void createAndShowGUI() {
        //Set the look and feel.
        try{
          UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }catch (Exception e) {}

        //Make sure we have nice window decorations.
        JFrame.setDefaultLookAndFeelDecorated(true);

        // Create and set up the window.
        JFrame frame= new JFrame("Xat");
        frame.setLayout(new BorderLayout(5,5));
        frame.getRootPane().setBorder(BorderFactory.createEmptyBorder(100,100,100,100));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create an output JPanel and add a JTextArea(20, 30) inside a JScrollPane
        JPanel out = new JPanel();
        out.setLayout(new BoxLayout(out,BoxLayout.PAGE_AXIS));
        JTextArea messages = new JTextArea(75,75);
        messages.setEditable(false);
        out.add(new JScrollPane(messages));

        // Create an input JPanel and add a JTextField(25) and a JButton
        JPanel inp = new JPanel();
        inp.setLayout(new BoxLayout(inp,BoxLayout.LINE_AXIS));
        JTextField text = new JTextField(20);
        JButton button = new JButton("Enviar");
        inp.add(text);
        inp.add(Box.createHorizontalStrut(5));
        inp.add(button);

        // add panels to main frame
        frame.add(out,BorderLayout.CENTER);
        frame.add(inp,BorderLayout.PAGE_END);
        //Display the window centered.
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
