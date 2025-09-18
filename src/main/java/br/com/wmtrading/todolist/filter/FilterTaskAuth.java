package br.com.wmtrading.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.wmtrading.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component /* O Springboot vai gerenciar esta classe  */
public class FilterTaskAuth extends OncePerRequestFilter{
    @Autowired
    private IUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //Pegar a autenticação (usuário e senha)
        var authorization = request.getHeader("Authorization"); //Pega o tipo de autorização e os campos
        var authEncoded = authorization.substring("Basic".length()).trim();
        byte[] authDecode = Base64.getDecoder().decode(authEncoded); //Decodifica a senha em bytes
        String authString = new String(authDecode); //Passa ela para String
        String[] credentials = authString.split(":"); //Separa o username e a senha pelo :
        String username = credentials[0]; //Guarda o Username
        String password = credentials[1]; //Guarda a senha
        //Validar usuário
        if(this.userRepository.findByUsername(username) == null){
            response.sendError(401); //Se não achar o username, o usuári não está autorizado a entrar
        }else{
            BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            //Validar senha

            //Segue viagem
        
            filterChain.doFilter(request, response);
        }
        
    }
    
}
