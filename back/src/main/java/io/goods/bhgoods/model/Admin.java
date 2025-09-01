package io.goods.bhgoods.model;

import io.goods.bhgoods.enums.UserRole;
import jakarta.persistence.Entity;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

@Entity
@Table(name = "admin")
public class Admin extends User {
    
    public Admin() {}
    
    public Admin(String email, String senha) {
        super(email, senha, UserRole.ADMIN);
    }
    
    // @PrePersist ensures that even if role is not set, it will be ADMIN before saving to database
    // This is a safety mechanism in case someone creates Admin using default constructor
    @PrePersist
    private void setRole() {
        super.setRole(UserRole.ADMIN);
    }
}
