package kimsungsu.finalToBoot.entity.form;


import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;


@Data
public class UserCreateForm {


    @Email
    @NotBlank
    private String email;
    @NotBlank
    private String name;
    @NotBlank
    private String password;


}
