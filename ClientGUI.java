import java.awt.*;
import javax.swing.*;
import java.awt.event.*;

public class ClientGUI{
  JFrame intro;
  JFrame principal;
  MySocket mysocket;
  String username;
  JTextField usernamefield;
  JTextArea messages;
  String messageToSend;
  JTextField mtosend;
//  JList userslist;
  public static void main(String[] args) {
    try{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }catch (Exception e) {}
    ClientGUI newclient = new ClientGUI();
    newclient.introView();
  //  newclient.chatView();
  }

  public ClientGUI(){
    super();
    this.mysocket = new MySocket("localhost", 4000);
    this.intro = new JFrame("Log in");
    this.principal = new JFrame("Client Chat App");
  }

  public void introView(){
    intro.setLayout(new BorderLayout(5,5));
    intro.getRootPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    intro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel intropanel = new JPanel();
    intropanel.setLayout(new BoxLayout(intropanel,BoxLayout.LINE_AXIS));
    usernamefield = new JTextField(25);
    JButton button = new JButton("Enter");
    button.addActionListener(new enterUsername());
    JLabel uflabel= new JLabel("Enter Username:  ");
    intropanel.add(uflabel);
    intropanel.add(usernamefield);
    intropanel.add(Box.createHorizontalStrut(25));
    intropanel.add(button);
    intro.add(intropanel,BorderLayout.CENTER);
    intro.pack();
    intro.setLocationRelativeTo(null);
    intro.setVisible(true);
  }

  public void chatView(){
    principal.setLayout(new BorderLayout(5,5));
    principal.getRootPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    principal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel chatpanel = new JPanel();
    chatpanel.setLayout(new BoxLayout(chatpanel,BoxLayout.PAGE_AXIS));
    messages = new JTextArea(20,30);
    messages.setEditable(false);
    chatpanel.add(new JScrollPane(messages));
    messages.setLineWrap(true);
    messages.setWrapStyleWord(true);

    JPanel msgpanel = new JPanel();
    msgpanel.setLayout(new BoxLayout(msgpanel,BoxLayout.LINE_AXIS));
    mtosend = new JTextField(25);
    JButton sendbutton = new JButton("Send");
    sendbutton.addActionListener(new sendMessage());
    JLabel slabel= new JLabel("Write your message:  ");
    msgpanel.add(slabel);
    msgpanel.add(mtosend);
    msgpanel.add(sendbutton);


    principal.add(chatpanel,BorderLayout.CENTER);
    principal.add(msgpanel,BorderLayout.PAGE_END);
    principal.pack();
    principal.setLocationRelativeTo(null);
    principal.setVisible(true);
  }

  public class enterUsername implements ActionListener{
        public void actionPerformed(ActionEvent e){
        username = usernamefield.getText();
        if(username.length() > 0){
          mysocket.println(username);
          String servermsg = mysocket.readLine();
          if(servermsg.contains("connectat")){
            intro.setVisible(false);
            chatView();
            MessageThread msth = new MessageThread();
            msth.start();
          }
        }
    }
  }

  public class sendMessage implements ActionListener{
    public void actionPerformed(ActionEvent e){
      messageToSend = mtosend.getText();
      if(messageToSend.length() > 0){
        mysocket.println(messageToSend);
        messages.append(username + ": " + messageToSend + "\n");
      }
    }
  }

  public class MessageThread extends Thread {
  public void run() {
    String missatge;
    missatge = mysocket.readLine();
    switch(missatge) {
      case ".llistaActualitzada":
        //llistaActualitzada();
        break;
      case ".desconnexio":
        //userconnected = false;
        break;
      default:
      messages.append(missatge + " \n"); //Afegirà el contingut passat per el paràmetre StringBuffer missatge
    }
  }
  /*public void llistaActualitzada() {
    String line;
    line = mysocket.readLine();
    String[] list = line.split(" ");
    model.removeAllElements();
    for (String list1 : list) {
        model.addElement(list1);
      }
    }*/
  }
}
