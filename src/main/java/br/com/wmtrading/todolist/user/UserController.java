package br.com.wmtrading.todolist.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.wmtrading.todolist.user.model.UserModel;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired /* Gerencia o ciclo de vida da variável (Instancia) */
    private IUserRepository userRepository;


    @PostMapping("/register")
    public ResponseEntity registerUser(@RequestBody UserModel userModel){
        if(this.userRepository.findByUsername(userModel.getUsername()) != null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Usuário já existe!");
        }
        
        var passwordHashred = BCrypt.withDefaults().hashToString(12, userModel.getPassword().toCharArray()); /* Passa para um Array de charpara poder criptografar a senha */
        userModel.setPassword(passwordHashred); // Seta a senha criptografada no objeto

        UserModel userCreated = this.userRepository.save(userModel); /* Salva o modelo recebido no corpo da requisição em JSON no repositório */
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreated);
    }
}
