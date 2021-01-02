import java.io.*;
import java.net.*;  //proporciona la classe ServerSocket
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

// Ignacio Poza i Blanca Ruiz

//Classe equiv a ServerSocket pero que encapsuli excepcions
public class MyServerSocket extends ServerSocket {

  MySocket socket;

  //Constructor

  public MyServerSocket (int port) throws IOException {
    //crear socket local al que se li enllaça el port especificat
    super(port);
  }

  //Metodes necessaris
  @Override
  public MySocket accept() /*throws IOException*/ {
    try {
      this.socket = new MySocket(super.accept());
    }catch(IOException e) {
      Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, e);
    }
      return socket;
  }

  public void close() /*throws IOException*/ {
  //Tancament de la connexió del socket amb el servidor que existia
  //El registre a tancar haurà d'haver estat creat per getLogger
    try {
      super.close();
    }catch(IOException e) {
      Logger.getLogger(MyServerSocket.class.getName()).log(Level.SEVERE, null, e);
    }
  }
}
