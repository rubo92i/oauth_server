package am.basic.notificator.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
 public class Authority implements GrantedAuthority, Serializable {


     private int id;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
