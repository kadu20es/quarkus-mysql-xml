package io.github.kadu20es.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "endereco")
public class Endereco extends PanacheEntity {

    @Column(name = "logradouro")
    public String logradouro;

    @Column(name = "numero")
    public String numero;

    @Column(name = "complemento")
    public String complemento;

    @Column(name = "bairro")
    public String bairro;

    @Column(name = "municipio")
    public String municipio;

    @Column(name = "uf")
    public String uf;

    @Column(name = "cep")
    public String cep;
}
