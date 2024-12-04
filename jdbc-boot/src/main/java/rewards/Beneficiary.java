package rewards;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "T_ACCOUNT_BENEFICIARY")
public class Beneficiary {
    @Id
    @Column(name = "ID")
    private Long id;

    private String name;

    public Beneficiary() {
    }

    public Beneficiary(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
