package rewards;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

//esta es la clase principal de la aplicacion o por dond entra, por ende tiene
//@SpringBootApplication
@SpringBootApplication
//añadiendo la clase que tiene las propiedades
@EnableConfigurationProperties(RewardsRecipientProperties.class)
public class JdbcBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(JdbcBootApplication.class, args);
	}


	//un bean es un componente que se crea y puede ser usado en todo el programa
	@Bean
	//commandLineRunner es un metodo que permite ejecutar tareas especificas
	//el JdbcTemplate es una clase de Spring que simplifica la interacción con bases de datos JDBC.
	//Permite ejecutar consultas SQL y mapear resultados de forma sencilla.
	CommandLineRunner commandLineRunner(JdbcTemplate jdbcTemplate){
		//un query que cuenta todo lo de la tabla T_ACCOUNT
		String QUERY = "SELECT count(*) FROM T_ACCOUNT";
		//jdbcTemplate.queryForObject(QUERY, Long.class) ejecuta la consulta SQL definida en QUERY y devuelve el
		// resultado como un objeto de tipo Long
		return args -> System.out.println("Hello, there are "
				+ jdbcTemplate.queryForObject(QUERY, Long.class)
				+ " accounts");
	}

	@Bean
	public  CommandLineRunner commandLineRunner2(RewardsRecipientProperties rewardsRecipientProperties){
		return  args -> System.out.println("Recipient name: " + rewardsRecipientProperties.getName());
	}
}
