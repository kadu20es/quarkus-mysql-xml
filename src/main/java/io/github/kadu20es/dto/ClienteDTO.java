package io.github.kadu20es.dto;

import io.github.kadu20es.model.Endereco;
import jakarta.xml.bind.annotation.*;

import java.util.List;

@XmlRootElement(name = "cliente")
@XmlAccessorType(XmlAccessType.FIELD)
public class ClienteDTO {

    private Long id;
    private String nome;
    private String email;
    private String tipoPessoa;
    private Boolean processado;

    @XmlElementWrapper(name = "endereco")
    @XmlElement(name = "endereco")
    private List<EnderecoDTO> endereco;

    public ClienteDTO() {
    }

    public ClienteDTO(Long id, String nome, String email, String tipoPessoa, List<EnderecoDTO> endereco, Boolean processado) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.tipoPessoa = tipoPessoa;
        this.endereco = endereco;
        this.processado = processado;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTipoPessoa() {
        return tipoPessoa;
    }

    public void setTipoPessoa(String tipoPessoa) {
        this.tipoPessoa = tipoPessoa;
    }

    public List<EnderecoDTO> getEndereco() {
        return endereco;
    }

    public void setEndereco(List<EnderecoDTO> endereco) {
        this.endereco = endereco;
    }

    public Boolean getProcessado() { return processado; }

    public void setProcessado(Boolean processado) { this.processado = processado; }
}
