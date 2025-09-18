package br.com.wmtrading.todolist.user.model;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data /* Cria os getters e setters em tempo de execução da aplicação */
@Entity /* Indica que esta será uma tabela no banco de dados. */
@Table(name = "tb_users") /* Seta o nome dessa tabela no banco */
public class UserModel {
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;

    @Column(unique=true)
    private String username;
    private String name;
    private String password;
    private int age;
    private String email;

    @CreationTimestamp /* Assim que objeto for criado e seus atributos preenchidos, vai gerar automaticamente os dados de data e hora e vai salvar nessa variável abaixo */
    private LocalDateTime createdAt;
    
}
