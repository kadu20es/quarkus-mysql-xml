package io.github.kadu20es.model;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "cliente")
public class Cliente extends PanacheEntity {

    @Column(name = "nome")
    public String nome;

    @Column(name = "email")
    public String email;

    @Column(name = "tipoPessoa")
    public String tipoPessoa;

    @Column(name = "processado")
    public Boolean processado;

    @ManyToMany
    @JoinTable(name = "cliente_endereco",
        joinColumns = @JoinColumn(name = "idCliente"),
        inverseJoinColumns = @JoinColumn(name = "idEndereco"))
    public List<Endereco> endereco;
}
