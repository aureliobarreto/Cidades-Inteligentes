/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.net.Socket;

/**
 *
 * @author Aurelio
 */
public class Caminhao {
    private Socket socket;
    private int rota, cap;
    private String ip;
    private boolean estado;
    
    
    
    public Caminhao(Socket s, String ip){
        this.socket = s;
        this.ip = ip;
        this.rota = 0;
        this.cap = 0;
        this.estado = true;
    }

    /**
     * @return o socket correspondente ao caminhão
     */
    public Socket getSocket() {
        return socket;
    }

    /**
     * @return o ip do caminhão
     */
    public String getIp() {
        return ip;
    }

    /**
     * @return quantidade de lixeiras atribuidas a rota do caminhão
     */
    public int getRota() {
        return rota;
    }
    /**
     * Incrementa uma lixeira a rota do caminhão
     */
    public void atribui(){
        this.rota++;
    }
    /**
     * Decrementa um lixeira da rota do caminhão
     */
    public void desvincula(){
        this.rota--;
    }

    /**
     * @return capacidade do caminhão
     */
    public int getCap() {
        return cap;
    }

    /**
     * Incrementa a capacidade do caminhão
     */
    public void addCap() {
    this.cap++;
    }
    /**
     * Limpa o caminhão
     */
    public void clear(){
        this.cap = 0;
        this.rota = 0;
    }

    /**
     * @return the estado
     */
    public boolean isEstado() {
        return estado;
    }

    /**
     * @param estado the estado to set
     */
    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
