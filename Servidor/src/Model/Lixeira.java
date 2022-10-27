
package Model;

import java.net.DatagramSocket;
import java.net.Socket;

/**
 *
 * @author Aurelio
 */
public class Lixeira {
    private DatagramSocket socket;
    private int cap;   
    private String ip;
    private Caminhao caminhao;
    
    public Lixeira(String ip, DatagramSocket ds){
      this.socket = ds;         
      this.ip = ip;
      this.cap = 0;
    }

    /**
     * Método que devolve o ip da lixeira
     * @return ip da lixeira
     */
    public String getIp(){
        return ip;
    }

    /**
     * @return  caminhao atribuido a lixeira
     */
    public Caminhao getCaminhao() {
        return caminhao;
    }

    /**
     * @param caminhao caminhão que será atribuido a lixeira
     */
    public void setCaminhao(Caminhao caminhao) {
        this.caminhao = caminhao;
    }
    /**
     * Método que limpa a lixeira
     */
    public void clear(){
        this.cap = 0;
    }
    /**
     * Método que seta uma capacidade a lixeira
     * @param s capacidade a ser setada
     */
    public void setCap(int s){
        this.cap = s;
    }
    /**
     * Método que retorna a capacidade da lixeira
     * @return a capacidade da lixeira
     */
    public int getCap(){
        return cap;
    }

    /**
     * @return the socket
     */
    public DatagramSocket getSocket() {
        return socket;
    }
}
