package br.com.wmtrading.todolist.user;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.wmtrading.todolist.user.model.UserModel;

public interface IUserRepository extends JpaRepository<UserModel, Object>{
    UserModel findByUsername(String username); /* Cria automaticamente um método uqe procura o usuário no repositório paelo username */
}
