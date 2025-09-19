package br.com.wmtrading.todolist.task;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    @Autowired /* Gerencia o ciclo de vida da variável (Instancia) */
    private ITaskRepository taskRepository;

    /**
     * 
     * @param taskModel - Objeto serializado convertido do JSON para o registrar no banco de dados
     * @param request - Requisição HTTP com cabeçalho e corpo para ETL dos dados necessários
     * @return - Retorna a resposta baseado no status http e com os objeto, a tarefa criada, para desserialização (JSON)
     */
    @PostMapping("/register")
    public ResponseEntity createTask (@RequestBody TaskModel taskModel, HttpServletRequest request){
        if(this.taskRepository.findByTitle(taskModel.getTitle()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa já existe!");
        }
        
        var idUser = request.getAttribute("idUser"); /* Pega o id do usuário na requisição */
        taskModel.setIdUser((UUID)idUser); // Seta do id do usuário convertendo a variável para o tipo UUID (não vem do corpo da requisição assim)
        
        LocalDateTime currentDate = LocalDateTime.now();
        if(currentDate.isAfter(taskModel.getStartAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa com data posterior a data de início. A data de início deve ser maior que a atual!");
        }else if(currentDate.isAfter(taskModel.getEndAt())){ 
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa com data posterior a data de término. A data de término deve ser maior que a atual!");
        }else if(taskModel.getStartAt().isAfter(taskModel.getEndAt())){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("A data de início deve ser menor que a data de término!");
        }

        TaskModel taskCreated = this.taskRepository.save(taskModel); /* Salva o modelo recebido no corpo da requisição em JSON no repositório */
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    @PutMapping("/{id}")
    public ResponseEntity updateTask (@RequestBody TaskModel taskModel, HttpServletRequest request, @PathVariable UUID id){
        var taskTEMP = this.taskRepository.findById(id); // Pega a tarefa com o id passado no caminho da rota
        if(taskTEMP == null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa não existe!");
        }
        
        var idUser = request.getAttribute("idUser"); /* Pega o id do usuário na requisição */
        taskModel.setIdUser((UUID)idUser); // Seta do id do usuário convertendo a variável para o tipo UUID (não vem do corpo da requisição assim)

        TaskModel taskCreated = this.taskRepository.save(taskModel); /* Salva o modelo recebido no corpo da requisição em JSON no repositório */
        return ResponseEntity.status(HttpStatus.CREATED).body(taskCreated);
    }

    /**
     * 
     * @return - Retorna a resposta baseado no status http e com os objetos, a lista de tarefas no banco de dados, para desserialização (JSON)
     */
    @GetMapping("/getAllTasks")
    public ResponseEntity getAllTasks(){
        if(this.taskRepository.count() != 0){
            List<TaskModel> tasks = this.taskRepository.findAll();
            return ResponseEntity.status(HttpStatus.OK).body(tasks);

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefas NÃO existem no banco de dados!");
        }
    }

    /**
     * 
     * @return - Retorna a resposta baseado no status http e com os objetos, a lista de tarefas no banco de dados, para desserialização (JSON)
     */
    @GetMapping("/getAllTasksFromUser") // CONFIGURAR PARA BUSCAR TAREFAS RELACIONADAS AO USUÁRIO SOLICITANTE
    public ResponseEntity getAllTasksFromUser(@RequestBody HttpServletRequest request){
        var idUser = request.getAttribute("idUser"); /* Pega o id do usuário na requisição */
        List<TaskModel> tasksUser = this.taskRepository.findByIdUser((UUID) idUser); // Guarda a lista de tarefas do usuário buscadas pelo id fornecido pela requisição

        if(this.taskRepository.count() != 0){
            return ResponseEntity.status(HttpStatus.OK).body(tasksUser);

        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefas deste usuário NÃO existem no banco de dados!");
        }
    }

    /**
     * NÃO FUNCIONA!!!!!!
     * @param title - Título da tarefa que deseja buscar
     * @return - Retorna a resposta baseado no status http e com os objeto, a tarefa buscada no banco de dados, para desserialização (JSON)
     */
    @GetMapping("/getOneTaskFromUser") // CONFIGURAR PARA BUSCAR TAREFA RELACIONADA AO USUÁRIO SOLICITANTE
    public ResponseEntity getOneTask(@RequestBody String title){
        TaskModel taskTEMP = this.taskRepository.findByTitle(title);
        if (taskTEMP != null) {
            return ResponseEntity.status(HttpStatus.OK).body(taskTEMP);
        }else{
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Tarefa deste usuário NÃO existe!");
        }
    }
}