package br.com.wmtrading.todolist.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired /* Gerencia o ciclo de vida da variável (Instancia) */
    private ITaskRepository taskRepository;


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody TaskModel taskModel){
        if(this.taskRepository.findByTitle(taskModel.getTitle()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa já existe!");
        }

        TaskModel taskCreated = this.taskRepository.save(taskModel); /* Salva o modelo recebido no corpo da requisição em JSON no repositório */
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @GetMapping
    public ResponseEntity getOneTask(@RequestBody String title){
        TaskModel taskTEMP = this.taskRepository.findByTitle(title);
        if (taskTEMP != null) {
            return ResponseEntity.status(HttpStatus.OK).body(taskTEMP);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa NÃO existe!");
        }
    }
}