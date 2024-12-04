package rewards;

//tomamos los valores que se encuntran en el archivo properties

//esta va a ser una clase que ocupemos como propiedades
//como si la estuvieramos poniendo en el application.properties
//y haciendo que lo reconozca como una clase de configuracion poniendole
//@EnableConfigurationProperties(RewardsRecipientProperties.class)

import org.springframework.boot.context.properties.ConfigurationProperties;

//esto se logra con la anotation @ConfigurationProperties
//el prefix es lo que va antes de lo que qremos
@ConfigurationProperties(prefix = "rewards.recipient")
public class RewardsRecipientProperties {

    private String name;
    private int age;
    private String gender;
    private String hobby;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobby() {
        return hobby;
    }

    public void setHobby(String hobby) {
        this.hobby = hobby;
    }
}
