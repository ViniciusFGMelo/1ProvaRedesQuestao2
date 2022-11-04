package udpserver00;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class Client {

    //Criando datagramSocket para e enviar e receber pacotes
    private DatagramSocket datagramSocket;
    //Criando o endereço ip do server em que o pacote vai ser enviado
    private InetAddress inetAddess;
    //Criando array de byte para armazenar os dados
    private byte[] buffer;

    //Construtor que inicializa o datagram e o ip
    public Client(DatagramSocket datagramSocket, InetAddress inetAddess) {
        this.datagramSocket = datagramSocket;
        this.inetAddess = inetAddess;
    }

    //metodo que vai receber e enviar pacotes
    public void recebeEnvia() {
        //lendo do teclado
        Scanner scanner = new Scanner(System.in);
        //Loop infinito para que o cliente continue enviando pacotes
        while (true) {
            //Usando try catch para tratar exceçoes
            try {
                //Lendo a mensagem
                String enviarMensagem = scanner.nextLine();
                //Convertendo a string para um array de byte
                //Assim podendo ser enviada como dado de um pacote
                buffer = enviarMensagem.getBytes();
                //Criando um datagramPacket com os dados e onde eles vao ser enviados
                DatagramPacket datagramPacket = new DatagramPacket(buffer, buffer.length, inetAddess, 2866);
                datagramSocket.send(datagramPacket);
                //recebendo os dados do server e reescrevendo os dados do buffer
                datagramSocket.receive(datagramPacket);
                //salvando a mensagem e imprimindo
                String mensagemServer = new String(datagramPacket.getData(), 0, datagramPacket.getLength());
                System.out.println("Eco do servidor: " + mensagemServer);
            } catch (IOException e) {
                //imprimindo um array de stack frames
                e.printStackTrace();
                break; //Para sair do loop
            }
        }
    }

    public static void main(String[] args) throws SocketException, UnknownHostException {
        //criando socket para enviar e receber dados
        DatagramSocket datagramSocket = new DatagramSocket();
        //Inetaddres representando o ip do server onde vai ser enviado datapacked
        InetAddress inetAddress = InetAddress.getByName("local host");
        //Criando objeto cliente e passando o datagramsocket e o inetaddress
        Client cliente = new Client(datagramSocket, inetAddress);
        System.out.println("Envie dados para o servidor");
        cliente.recebeEnvia();
    }

}
