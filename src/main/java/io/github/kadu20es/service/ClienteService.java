package io.github.kadu20es.service;

import io.github.kadu20es.dto.ClienteDTO;
import io.github.kadu20es.dto.ClientesWrapper;
import io.github.kadu20es.dto.EnderecoDTO;
import io.github.kadu20es.model.Cliente;
import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.Marshaller;
import org.eclipse.microprofile.config.inject.ConfigProperty;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClienteService {

    @Inject
    @ConfigProperty(name = "file.output")
    String xmlOutputPath;

    @PersistenceContext
    EntityManager entityManager;

    @Scheduled(every = "1m")
    @Transactional
    public void processarClientesAlterados() throws Exception {
        LocalDateTime umMinutoAtras = LocalDateTime.now().minusMinutes(1);
        LocalDateTime agora = LocalDateTime.now();

        // Busca as alterações de banco que não foram processadas.
        List<Cliente> clientesAlterados = entityManager.createQuery(
                "SELECT c FROM Cliente c WHERE c.processado = false", Cliente.class)
                .getResultList();

        if (!clientesAlterados.isEmpty()) {
            System.out.println(clientesAlterados.size() + "Novos clientes encontrados ou clientes alterados. Gerando XML");
            gerarXmlClientes(clientesAlterados);
            //marcarClientesProcessados(clientesAlterados);
        }

        System.out.println("Nenhum novo cliente no último 1 minuto");

    }

    private void gerarXmlClientes(List<Cliente> clientesAlterados) throws  Exception {

        //List<Cliente> clientes = Cliente.listAll();


        List<ClienteDTO> clientesDto = clientesAlterados.stream()
            .map(cliente -> {
                List<EnderecoDTO> enderecosDTO = cliente.endereco.stream()
                    .map(end -> new EnderecoDTO(
                        end.id,
                        end.logradouro,
                        end.numero,
                        end.complemento,
                        end.bairro,
                        end.municipio,
                        end.uf,
                        end.cep)
                    ).toList();

                return new ClienteDTO(
                                    cliente.id,
                                    cliente.nome,
                                    cliente.email,
                                    cliente.tipoPessoa,
                        enderecosDTO,
                        cliente.processado);
        }).toList();

        ClientesWrapper wrapper = new ClientesWrapper(clientesDto);

        JAXBContext jaxbContext = JAXBContext.newInstance(ClientesWrapper.class);
        Marshaller marshaller = jaxbContext.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

        //File file = new File(xmlOutputPath);
        String userHome = System.getProperty("user.home");
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd-MM-yyyy_HHmmss");
        String data = LocalDateTime.now().format(fmt);
        String arquivo = "/saida/saida_" + data + ".xml";

        File file = new File(userHome+arquivo);
        marshaller.marshal(wrapper, file);

        marcarClientesProcessados(clientesDto);
        System.out.println("Gerando arquivo XML com " + clientesAlterados.size() + " clientes em " + file.getAbsolutePath());
    }

    @Transactional
    public void marcarClientesProcessados(List<ClienteDTO> clientes) {
        for (ClienteDTO clienteDto : clientes) {
            Cliente cliente = entityManager.find(Cliente.class, clienteDto.getId());
            if (cliente != null) {
                cliente.processado = true;
                entityManager.merge(cliente);
            }
        }
        entityManager.flush();
        System.out.println("Clientes marcados como processados");
    }


}
