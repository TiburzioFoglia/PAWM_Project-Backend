package it.unicam.cs.PAWNProjectBackend.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Collection;

@Data
@NoArgsConstructor
@Node
public class User {

    @Id
    private String userName;
    private String userFirstName;
    private String userLastName;
    private String userPassword;
    @Relationship(type = "Has_role",direction = Relationship.Direction.OUTGOING)
    private Collection<Role> roles;

    public User(String userName, String userFirstName, String userLastName, String userPassword, Collection<Role> roles) {
        this.userName = userName;
        this.userFirstName = userFirstName;
        this.userLastName = userLastName;
        this.userPassword = userPassword;
        this.roles = roles;
    }
}
