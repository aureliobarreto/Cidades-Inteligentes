/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.DatagramSocket;

/**
 *
 * @author Aurelio
 */
public class Transbordo {
    
    private DatagramSocket socket;
    private int cap;
    private String ip;
    
    public Transbordo(DatagramSocket s, String ip){
        this.socket = s;       
        this.ip = ip;
        this.cap = 0;
    }

    /**
     * @return o socket correspondente ao transbordo
     */
    public DatagramSocket getSocket() {
        return socket;
    }  

    /**
     * @return o ip do transbordo
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return capacidade do transbordo
     */
    public int getCap() {
        return cap;
    }
    /**
     * Método que limpa zera a capacidade do transbordo
     */
    public void clear(){
        this.setCap(0);
    }
    /**
     * Método que incrementa a capacidade do transbordo
     */
    public void addCam(){
        if(cap < 3){
        this.cap++;
        }
    }

    /**
     * @param cap a capacidade a ser setada
     */
    public void setCap(int cap) {
        this.cap = cap;
    }
}
