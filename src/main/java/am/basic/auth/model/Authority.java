package am.basic.auth.model;


import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Entity
public class Authority implements GrantedAuthority, Serializable {


    @Id
    private int id;

    private String name;

    @Override
    public String getAuthority() {
        return name;
    }
}
