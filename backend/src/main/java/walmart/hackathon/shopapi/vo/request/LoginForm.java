package walmart.hackathon.shopapi.vo.request;


import org.hibernate.validator.constraints.NotBlank;

import lombok.Data;

/**
 * Created By Zhu Lin on 1/1/2019.
 */
@Data
public class LoginForm {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
