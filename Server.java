import java.io.IOException;
import java.util.concurrent.ConcurrentHashMap;
import java.net.*;

/*Hem decidit utilitzar el ConcurrentHashMap per poder guardar els clients
que es connectin al nostre server. Hem optat per aquest tipus de Map perquè
després d'estar investigant les diferents posibilitats, hem arribat a la
conclusió que es el més eficient i elegant de tots.
*/

//Ignacio Poza i Blanca Ruiz

public class Server implements Runnable {

  public static ConcurrentHashMap<String, MySocket> userslist = new ConcurrentHashMap<String, MySocket>(); //Lista amb nom d'usuari i socket determinat
  MySocket mysck;
  String nickname;
  public static boolean next = false; //Estara en true quan l'usuari tingui un nom vàlid i no estigui utilitzat ja


  //Constructor
  public Server(String nickname, MySocket mysck) throws IOException {
    this.mysck = mysck;
    this.nickname = nickname;
  }

  public static void main(String[] args) throws Exception {
    MyServerSocket mssocket = new MyServerSocket(4000);	//Mateix host que esta declarat a client
    System.out.println("Servidor inicialitzat.");

    while(true){
      MySocket mysocket = mssocket.accept();
      while (!next) { //next == false
        //mysocket.println("Introdueixi el seu nou nom d'usuari: ");
        String read_nickname = mysocket.readLine();   //Es llegueix el nom d'usuari
        //Comprovem si esta disponible
        if (userslist.containsKey(read_nickname)) {   //Si el usuari introdueix un nom ja existent a la llista
          mysocket.println("El nom d'usuari " + read_nickname + " ja esta enregistrat. Esculli un altre. ");
          //next = false. Com no compleix condicio, tornara a preguntar.
        }else {
          System.out.println(read_nickname + " esta connectat ");
          mysocket.println("connectat");
          //Afegim el nom d'usuari i el socket a la llista i executem el thread
	        userslist.put(read_nickname, mysocket); //El nou usuari al xat s'afegeix a la llista de usuaris connectats
          new Thread(new Server(read_nickname, mysocket)).start(); //Executem el thread i inicialitzem
          next = true; //Posem a true el boolean perque ja ha complit la condicio de tenir un nom valid d'usuari
        }
      }
      next = false; //Ja el tornem al seu estat inicial quan surt del while per al proxim cop que s'introdueixi un nou usuari
    }
  }

  @Override
  public void run() {
    String linia;

          while ((linia = mysck.readLine()) != null) {
              boolean actiu = actiu(nickname, linia); //Informa de l'estat de l'usuari. Si està actiu i no escriu 'Marxo', es mostrara el que ha introduit pel terminal
              if (actiu) {    //actiu == true
                  for (MySocket ms : userslist.values()) {
                      if (ms != mysck) {
                          ms.println(nickname + ": " + linia);
                          System.out.println(nickname + " ha escrit: " + linia);
                      }
                  }
              }
          }
      }

      public boolean actiu(String nickname, String linia) {     //Retorna el estat de l'usuari, si esta connectat o vol deixar d'estar-ho
          boolean actiu = true;
          if (linia.equals("Marxo")) {    //Si un usuari escriu Marxo, el programa sabrà que l'usuari es vol desconnectar del xat
              userslist.remove(nickname); //L'eliminem de la llista de connectats al xat

              for (MySocket ms : userslist.values()) {
                  if (ms != mysck) {                    //Escriurà les seguents frases en els sockets creats connectats (usuaris) per informar que l'usuari 'x' ha marxat de la conversa
                                                        //Sortiran en la resta de clients, pero no en el de l'usuari que marxa
                                                        //També informara de la situació actual al server
                      //System.out.println(nickname + " es troba desconnectat. ");
    		              ms.println(nickname + ": Marxo");
                      ms.println(nickname + ": : ha abandonat la conversa.");
                      System.out.println(nickname + " ha escrit: Marxo");
                      System.out.println(nickname + " es troba desconnectat.");
                  }

              }
              actiu = false;    //Ara el usuari determinat estarà desconnectat, i per tant el seu estat actiu serà false.
          }
          return actiu; //Retronem el estat del boolean
    }
}
