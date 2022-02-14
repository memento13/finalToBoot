package kimsungsu.finalToBoot.entity.form;

import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
public class UserCreateForm {

    @NotNull
    @Email
    @NotEmpty
    private String email;
    @NotNull
    @NotEmpty
    private String name;
    @NotNull
    @NotEmpty
    private String password;


}
