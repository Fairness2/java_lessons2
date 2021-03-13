package lesson_125344.chat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.function.Consumer;

public class Chat extends JFrame {
    private JPanel chatPanel;
    private JTextField messageTextField;
    private JScrollPane scrollPane;

    public Chat(Consumer<String> consumer){
        setTitle("Чат");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        createWindow(consumer);
        setVisible(true);
        setBounds(50, 50, 300, 500);
    }


    private void createWindow(Consumer<String> consumer){
        setLayout(new BorderLayout());

        chatPanel = new JPanel();
        BoxLayout layout = new BoxLayout(chatPanel, BoxLayout.Y_AXIS);
        chatPanel.setLayout(layout);
        scrollPane = new JScrollPane(chatPanel);
        //scrollPane.add(chatPanel);
        add(scrollPane, BorderLayout.CENTER);

        JPanel messagePanel = new JPanel(new BorderLayout());
        messageTextField = new JTextField();
        messageTextField.addKeyListener(new MessageSendListener(consumer));
        JButton messageSendButton = new JButton("Отправить");
        messageSendButton.addActionListener(new MessageSendListener(consumer));
        messagePanel.add(messageTextField, BorderLayout.CENTER);
        messagePanel.add(messageSendButton, BorderLayout.EAST);
        add(messagePanel, BorderLayout.SOUTH);

        JMenuBar menu = new JMenuBar();
        JMenu chatMenu = new JMenu("Окно");
        JMenuItem clearButton = new JMenuItem("Очистить");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                chatPanel.removeAll();
                //chatPanel.repaint();
                scrollPane.repaint();
            }
        });
        chatMenu.add(clearButton);
        menu.add(chatMenu);
        add(menu, BorderLayout.NORTH);
    }

    public void showMessage (String message, boolean selfMessage) {
        JPanel messagePanel = new JPanel(new BorderLayout());
        JLabel messageLabel = new JLabel(message, !selfMessage ? SwingConstants.LEFT : SwingConstants.RIGHT);
        messagePanel.add(messageLabel, !selfMessage ? BorderLayout.WEST : BorderLayout.EAST);
        chatPanel.add(messagePanel);
        scrollPane.validate();
    }

    private class MessageSendListener implements ActionListener, KeyListener {

        private Consumer<String> consumer;

        public MessageSendListener(Consumer<String> consumer) {
            this.consumer = consumer;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            processAction();
        }

        @Override
        public void keyTyped(KeyEvent e) { }

        @Override
        public void keyPressed(KeyEvent e) { }

        @Override
        public void keyReleased(KeyEvent e) {
            if (e.getKeyCode() == 10){
                processAction();
            }
        }

        private void processAction(){
            String messageText = messageTextField.getText();
            messageTextField.setText(null);
            consumer.accept(messageText);
        }
    }

}
