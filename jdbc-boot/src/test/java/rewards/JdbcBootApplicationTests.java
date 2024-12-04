package rewards;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.assertj.core.api.Assertions.*;

//esta es la clase donde se realizan todos los test de la aplicacion
@SpringBootTest
public class JdbcBootApplicationTests {
	public static final String QUERY = "SELECT count(*) FROM T_ACCOUNT";

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Test
	public void testNumberOfAccount() {
		//jdbcTemplate hace la peticion a la base de datos
		long count = jdbcTemplate.queryForObject(QUERY, Long.class);
		//assert hace referencia a un valor que esperamos en las pruebas de testing
		assertThat(count).isEqualTo(21L);
	}

}