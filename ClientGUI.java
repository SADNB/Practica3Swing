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
  JTextArea userstextarea;
  JList<String> list;
  DefaultListModel<String> dlmodel;
  boolean connected;

  public static void main(String[] args) {
    try{
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }catch (Exception e) {}
    ClientGUI newclient = new ClientGUI();
    newclient.introView();
  }

  public ClientGUI(){
    super();
    this.mysocket = new MySocket("localhost", 4000);
    this.intro = new JFrame("Log in");
    this.principal = new JFrame("Client Chat App");
    this.dlmodel = new DefaultListModel<String>();
    this.list = new JList<String>(this.dlmodel);
    this.connected=false;
  }

  public void introView(){
    intro.setLayout(new BorderLayout(5,5));
    intro.getRootPane().setBorder(BorderFactory.createEmptyBorder(20,20,20,20));
    intro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    JPanel intropanel = new JPanel();
    intropanel.setLayout(new BoxLayout(intropanel,BoxLayout.LINE_AXIS));
    usernamefield = new JTextField(25);
    JButton button = new JButton("Enregistrat!");
    button.addActionListener(new enterUsername());
    JLabel uflabel= new JLabel("Introdueixi nom d'usuari:  ");
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
    messages = new JTextArea(20,50);
    messages.setEditable(false);
    chatpanel.add(new JScrollPane(messages));
    messages.setLineWrap(true);
    messages.setWrapStyleWord(true);

    JPanel msgpanel = new JPanel();
    msgpanel.setLayout(new BoxLayout(msgpanel,BoxLayout.LINE_AXIS));
    mtosend = new JTextField(25);
    JButton sendbutton = new JButton("Envia");
    sendbutton.addActionListener(new sendMessage());
    JLabel slabel= new JLabel("Escriu el teu missatge:  ");
    msgpanel.add(slabel);
    msgpanel.add(mtosend);
    msgpanel.add(sendbutton);

    JPanel userpanel = new JPanel();
    userpanel.setLayout(new BoxLayout(userpanel,BoxLayout.PAGE_AXIS));
    JScrollPane scrollpanel = new JScrollPane(list);
    scrollpanel.setPreferredSize(new Dimension(100, 400));
    userpanel.add(scrollpanel);


    principal.add(chatpanel,BorderLayout.LINE_START);
    principal.add(userpanel,BorderLayout.LINE_END);
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
            connected=true;
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
  while(connected){
    String missatge;
    missatge = mysocket.readLine();
    switch (missatge) {
      case "Marxo":
        connected = false;
        break;
      case ".actualitza":
        llistaActualitzada();
        break;
      default:
        messages.append(missatge + " \n"); //Afegirà el contingut passat per el paràmetre StringBuffer missatge
        break;
        }
      }
    }
  }
  public void llistaActualitzada() {
    String line;
    line = mysocket.readLine();
    String[] listofusers = line.split(" ");
    dlmodel.removeAllElements();
    for (String list1 : listofusers) {
        dlmodel.addElement(list1);
      }
    /*userstextarea.removeAll();
    userstextarea.append(this.list.getComponents().toString());*/
  }

  public void close() {
      mysocket.close();
      principal.dispose();
  }
}
