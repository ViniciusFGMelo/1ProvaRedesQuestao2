package udpserver00;


import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;


//Classe responsavel por esperar um datagram do cliente
//e enviar de volta um datagram com um eco da resposta
public class Server {
    //Vai ser usado DatagramSocket e DatagramPacket
    //pois é uma programação connection-less do socket usando UDP
    //Criando um datagram
    private DatagramSocket datagramSocket;
    //Array de bytes, buffer para armazenar as mensagens do cliente
    private byte[] buffer = new byte[256];
    
    //Construtor que inicializa o datagramSock
    public Server(DatagramSocket datagramSocket) {
        this.datagramSocket = datagramSocket;
    }
    
    //Metodo para receber a mensagem e ecoar de volta
    public void recebeEnvia()
    {
        //Loop infinito para que o server continue rodando
        while(true){
            //Usando try catch para tratar exceçoes
            try{
                //Criando um datagramPacket, cujo vai ser enviado pelo cliente
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length);
                //Armazendando os dados no buffer
                datagramSocket.receive(datagramPacket);
                //Recebendo o ip 
                InetAddress inetAddress = datagramPacket.getAddress();
                //Pegando o número do port
                int port = datagramPacket.getPort();
                //Criando uma string que armazena os dados do datagramPacket
                //Armazena o array de bytes
                //Convertendo para string e especificando onde começa a leitura e onde parar a leitura
                String ClienteMsg = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Mensagem do cliente: " + ClienteMsg);
                //Redesignação do datagram com o buffer preenchido, endereço ip e port
                datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddress, port);
                //Enviando o pacote udp
                datagramSocket.send(datagramPacket);
            } catch(IOException e){
                e.printStackTrace();
                break; //Para sair do loop
            }
                
        }
                
    }
    public static void main(String[] args) throws SocketException {
        //Criando um datagramsocket que vai ser passado para o construtor do servidor
        //passa o numero do port em que vai acontecer a comunicação
        DatagramSocket datagramSocket = new DatagramSocket(2866);
        //Recebendo o datagram
        Server servidor = new Server(datagramSocket);
        //Chamando o metodo recebeEnvia que vai rodar até acontecer um erro
        servidor.recebeEnvia();
    }
}
