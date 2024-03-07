package cl.francisco.reyes.registrousuario.dto.req;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Phones {
    @Id
    @Column(name = "phonesid")
    @JsonIgnore
    String phonesId;
    
    @Column(name = "number")
    String number;
    
    @Column(name = "citycode")
    @JsonProperty("citycode")
    String cityCode;
    
    @Column(name = "countrycode")
    @JsonProperty("countrycode")
    String countryCode;
}
