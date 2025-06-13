package org.example.orchidbe.command.entities;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
    @Id
    @Column(name="role_id")
    private long roleId;

    @Column(name="role_name", nullable = false, unique = true)
    private String roleName;

    public long getId() {
        return roleId;
    }

    public void setId(long id) {
        this.roleId = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
