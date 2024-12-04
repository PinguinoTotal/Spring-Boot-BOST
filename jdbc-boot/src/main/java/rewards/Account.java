package rewards;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

//@Entity nos dice que es un objeto que sera guardado en la base de datos
@Entity
//Table es para saber como se va a llamar la base de datos
@Table(name = "T_ACCOUNT")
public class Account {
    //es el id que se utilizara en la base de datos
    @Id
    //column nos dice como se llamara la columna
    @Column(name = "ID")
    //como se debe generar el numero del id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String number;

    private String name;

    //el one to many define como seran las forenkeys
    //en este caso me parece que una cuenta puede tener varios beneficiados
    @OneToMany
    //el join colum es la tabla con la que se hara la union, una tabla intermedia
    @JoinColumn(name = "ACCOUNT_ID")
    private Set<Beneficiary> beneficiaries = new HashSet<Beneficiary>();

    private String creditCardNumber;

    //Contructores, Setters y Getters
    //ALT + INS


    public Account() {
    }

    public Account(String number, String name, Set<Beneficiary> beneficiaries, String creditCardNumber) {
        this.number = number;
        this.name = name;
        this.beneficiaries = beneficiaries;
        this.creditCardNumber = creditCardNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Beneficiary> getBeneficiaries() {
        return beneficiaries;
    }

    public void setBeneficiaries(Set<Beneficiary> beneficiaries) {
        this.beneficiaries = beneficiaries;
    }

    public String getCreditCardNumber() {
        return creditCardNumber;
    }

    public void setCreditCardNumber(String creditCardNumber) {
        this.creditCardNumber = creditCardNumber;
    }
}
