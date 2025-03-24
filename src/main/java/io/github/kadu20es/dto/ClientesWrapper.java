package io.github.kadu20es.dto;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.List;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClientesWrapper {

    @XmlElement(name = "cliente")
    private List<ClienteDTO> clientes;

    public ClientesWrapper() {
    }

    public ClientesWrapper(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }

    public List<ClienteDTO> getClientes() {
        return clientes;
    }

    public void setClientes(List<ClienteDTO> clientes) {
        this.clientes = clientes;
    }
}
