package work.example.demo.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class RessourceNotFoundException extends RuntimeException{
    private  String ressourceName;
    private String fieldName;
    private Object fieldValue;
    public RessourceNotFoundException(String ressourceName, String fieldName, Object fieldValue) {
        super(String.format("%s not found with %s : '%s'",ressourceName,fieldName,fieldValue));
        this.ressourceName = ressourceName;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

}

