package br.com.wmtrading.todolist.task;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name="tb_tasks")
public class TaskModel {
    @Id
    @GeneratedValue(generator="UUID")
    private UUID id;
    @Column(length = 50) //limita o titulo a 50 caracteres 
    private String title;
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime endAt;
    private String priority;

    private UUID idUser;

    @CreationTimestamp /* Assim que objeto for criado e seus atributos preenchidos, vai gerar automaticamente os dados de data e hora e vai salvar nessa variável abaixo */
    private LocalDateTime createdAt;
}
