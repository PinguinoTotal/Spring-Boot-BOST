package rewards;

import org.springframework.data.repository.Repository;

public interface AccountRepository extends Repository<Account, Long> {

    //se puede dejar solo la implementacion del metodo, siempre que seamos cuidadosos
    //con la sintaxis, proque se le pone un texto que es comprensible para jpa
    //FIND BY CREDIT CARD NUMBER, es importante decir que se le pone el nombre
    //completo de la propiedad y no del nombre de como se llame en la tabla
    public Account findByCreditCardNumber(String creditCardNumber);
}
