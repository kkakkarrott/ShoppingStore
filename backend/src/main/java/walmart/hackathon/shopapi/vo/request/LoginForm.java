package walmart.hackathon.shopapi.vo.request;


import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

@Data
public class LoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
