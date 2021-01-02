import java.io.*;
import java.net.*; //proporciona la classe Socket
import java.io.BufferedReader;
import java.io.InputStreamReader; //necessari per utilitzar BufferedReader
import java.io.PrintWriter;
import java.io.OutputStreamWriter;  //necessari per utilitzar PrintWriter
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

//Ignacio Poza i Blanca Ruiz

//Classe equiv a Socket però amb streams de text BufferedReader/PrintWriter i exceptions

public class MySocket extends Socket {

  Socket socket;
  BufferedReader br;  //La declarem per poder utilitzar la classe BufferedReader
  PrintWriter pw;    //La declarem per poder utilitzar la classe PrintWriter

  //Constructors

  public MySocket (Socket sc) {
  //Socket determinat passat per paràmetre
    try {
      this.socket = sc;
      this.br = new BufferedReader(new InputStreamReader(sc.getInputStream()));
      this.pw = new PrintWriter(new OutputStreamWriter(sc.getOutputStream()));
    }catch(IOException e) {
      Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  //Haurem de coneixer direccio IP (host) i port de comunicació amb el servidor
  public MySocket (String host, int port) {
  //Creem socket i el connectem al port i a la direccio IP especificat
    try {
      this.socket = new Socket(host,port);
      this.br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      this.pw = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }catch(IOException e) {
      Logger.getLogger(MySocket.class.getName()).log(Level.SEVERE, null, e);
    }
  }

  //Metode escriptura tipus basic amb ajuda de PrintWriter
  public void println(String data) {
    this.pw.println(data);
    this.pw.flush();
  }

  //Metode lectura tipus basic amb ajuda de BufferedReader
  public String readLine() {
    String data = null;
    try {
      data = this.br.readLine();
    } catch(IOException e) {
      e.printStackTrace();
    }
    return data;
  }

  public String read() {
    //Per lectura socket
    String linia = br.readLine();
    return linia;

  }

  //Metode close
  public void close() {
    //Tanca el socket
    try {
      this.socket.close();
      this.br.close();
      this.pw.close();
    }catch (IOException e) {
      e.printStackTrace();
  }

  }
}
