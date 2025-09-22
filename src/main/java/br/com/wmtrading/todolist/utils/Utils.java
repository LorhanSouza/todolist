package br.com.wmtrading.todolist.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class Utils {
    /* Pega as propriedades e atributos não nulos e os copia para o objeto de destino
     * A ideia é filtrar os campos nulos com o método getNullPropertyNames passando o obsjeto de origem (o modelo da requisição, o que vem em JSON)
     * e copiar os campos já preenchidos de forma que altere apenas o desejado
     */
    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
    }

    public static String[] getNullPropertyNames(Object source){
        /* BeanWrapper - Interface que fornece uma  forma para acessar as propriedades de um objeto
         * BeanWarpperImpl - Implementação dessa interface
         */
        final BeanWrapper src = new BeanWrapperImpl(source);
        PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<>();

        /* Varre o array de propriedades para pegar dentro dos objetos os nomes dessas propriedades */
        for(PropertyDescriptor pd:pds){
            Object srcValue = src.getPropertyValue(pd.getName()); //Pega o nome da propriedade
            if(srcValue == null){ //Testa se o campo é nulo
                emptyNames.add(pd.getName()); // Guarda dentro do Hash se for
            }
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }
}
