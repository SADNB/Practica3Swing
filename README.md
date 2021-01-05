# PRÀCTICA 3 SAD
Pràctica 3: Xat gràfic amb Swing

# INTRODUCCIÓ
L'objectiu de la pràctica és dissenyar un xat gràfic amb la llibrería Swing, aprofitant el Client Textual creat a la Pràctica 2.

El servidor serà l'encarregat de reunir i mostrar els missatges de tots el clients (usuaris) connectats. Cadascun dels clients estarà representat per un nick vàlid amb el que seran identificats en el xat. Tot seguit s'afegirà a una llista d'ususaris connectats i podrà començar a parlar amb els altres usuaris. Quan algun usuari vulgui desconnectar-se del xat, haurà d'escriure la paraula 'Marxo'. És la forma que tindrà el programa de saber que aquest usuari vol deixar la conversa i l'eliminarà de la llista d'usuaris connectats al xat, així com informar als altres usuaris a través del servidor que s'ha desconnectat. 

En l'apartat gràfic, l'aplicació mostra primerament una finestra on introduïrem el nostre nick, encara que si es troba ja a la llista d'usuaris, l'aplicació no deixarà avançar al usuari fins que no escrigui un nick vàlid. Una vegada ho fagi, l'usuari accedirà a una nova finestra on trobarà un espai on es mostrarà els missatges del xat, un altre amb una llista dels usuaris connectats i un cuadre de text on escriure els nostres missatges.

# PARTS DE LA PRÀCTICA
--> MySocket: Classe equivalent a la classe de Java Socket però amb streams de text BufferedReader i PrintWritter i amb excepcions. Serà la manera de implementar mètodes de lectura i escriptura de tipus bàsic. Un socket permet l'implementació d'una arquitectura client-servidor, de tal manera de que puguin llegir i escriure la informació desitjada i sigui enviada i rebuda per les dues parts. Per crear un nou socket ha d'estar definit, i és necessari conèixer el host (adreça IP) i el número de port. També trobem el mètode close(), que s'encarrega d'alliberar els recursos assignats a un socket. És a dir, s'encarrega d'acabar la connexió existent amb el servidor.

--> MyServerSocket: Classe equivalent a la classe de Java ServerSocket però que encapsuli excepcions. Serà la classe que permetrà manipular la connexió desde la part del servidor. Aquí es un trobarem el mètode accept(), el qual s'encarregarà d'acceptar l'intent de creació d'una connexió des del client. Per tant, crearà un nou socket quan es realitzi una connexió servidor-client per establir una comunicació. Aquest mètode serà cridat al servidor quan localitzi que un client sol·liciti realitzar una connexió.

--> Server: És la classe que dirigeix el xat. És l'encarregat de proporcionar els recursos al client. Crearem una llista d'usuaris amb la classe ConcurrentHashMap que guardarà els nicks dels usuaris connectats. Un cop detectada la sol·licitud de connexió per part d'un client/usuari, es preguntarà per el nick del xat. Si aquest no existeix, es crearà una connexió amb la creació d'un nou fil d'execució amb una nova connexió entre el servidor i el client nou. Si pel contrari ja existeix el nick, es tornarà a demanar al usuari que introdueixi un altre. El servidor informarà del que escriu cada client en la seva interficie client i s'enviarà als altres usuaris els missatges que el processaran i l'incloïràn a l'espai relatiu als misstages.. Si en algun moment, algun usuari escriu la paraula 'Marxo', el programa interpretarà que vol marxar i deixar la conversació. Per tant, s'eliminarà aquest usuari de la llista de usuaris connectats i s'informarà a la resta de que l'usuari 'x' abandona el xat amb un missatge a l'espai de missatges.

--> ClientGUI: Aquesta es la classe on es trobem la part que ens interessa d'aquesta pràctica, ja que es on s'implementa la interficie gràfica. La classe esta dividida en dos JFrame, el primer referent a la entrada del xat, quan ingressem i comprovem el nick escollit i un segon JFrame, on trobem el xat amb els espais pels missatges, l'espai per escriure i la llista d'usuaris connectats. També s'inicialitzen els sockets pertinents per poder tenir connexió amb el servidor. 

En aquest primer frame, hem desenvolupat un únic panell (JPanel) , on hem creat un àrea per introduir el nostre nick (JTextField) i un botó (JButton) per validar aquest nick. A més, aquest botó donarà pas a la següent interficie, sempre i quan es validi el nick emprat. També hem definit una série d'etiquetes (JLabel) per ajudar a la comprensió i utilització de la interficie. 

En el segon frame, hem decidit crear 3 panells diferents (JPanel) per tal de diferenciar les 3 àrees que ens podem trobar. Dins del panell referent als missatges, hem creat un Àrea de Text (JTextArea) on anirant apareixent els missatges que ens arriben. Com a característiques, cal dir que hem bloquejat l'escriptura dins d'aquest espai i l'hem adaptat perqué es pugui baixar o pujar, si ens trobem en el cas en el que el chat es queda sense espai per continuar escrivint (JScrollPane). En el panell on escriurem els missatges que volem enviar, hem creat un espai de text (JTextField) amb un botó (JButton) que enviará el missatge. Aquest espai s'ubicarà a la zona inferior de la interficie. Per últim, hem creat, en el tercer panell, un sencill panell lliscant (JScrollPane) associat a una llista (JList) on veurem els nicks dels usuaris connectats.

També hem definit una série d'etiquetes (JLabel) per ajudar a la comprensió i utilització de la interficie. 

Pero també tenim una part de gestió, on per exemple hem creat diversos ActionListeners per respondre a les accions dels botons, hem creat métodes per actualitzar la llista d'usuaris i per tancar el socket en cas d'abandonament del xat. Per últim, cal mencionar que hem creat una classe dins del ClientGUI, que gestiona el fil d'entrada, on depenent del que li arribi, executarà un mètode o un altre.

# EXECUCIÓ DE LA PRÀCTICA
1. Executar Server.java. Sortirà uel missatge següent per pantalla: "Servidor inicialitzat.". Ara, el servidor està preparat.
2. Executar ClientGUI.java. Al fer-ho, apareixerà una interficie que demanarà introduïr un nom d'usuari pel xat. Un cop s'accepti el nick proposat, apareixerà una nova interficie on trobarem els missatges del xat, l'espai per escriure i una llista amb els usuaris connectats.
3. Tornar a executar Client.java. Així, crearem un altre usuari del xat que podrà intercanviar missatges amb el creat anteriorment.
4. Començar la conversa. Si el primer client escriu 'hola' a la seva interficie, apareixerà reflexat al servidor i es replicarà a tots els espais de missatges dels clients que estiguin connectats, fins que algun d'ells escrigui 'Marxo'. Llavors s'aturarà la conversa per part d'aquest client i s'informarà als usuaris i al servidor de que ha abandonat el xat.