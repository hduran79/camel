package co.com.votre.camel.dto.product;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    
    private String mediaMin;
    private String mediaMax;


}
