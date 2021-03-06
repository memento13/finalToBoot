package kimsungsu.finalToBoot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "parties")
@Getter
@Setter
public class Party extends BaseEntity{

    @Id
    private String id;
    @Column(unique = true)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "leader_id")
    @JsonIgnore // JSON Serializer Error 때문에 막음, 정보를 보여줄거면 dto로 따로 보여주는게 맞지.
    private User leader;

    @OneToMany(mappedBy = "party",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<PartyMember> partyMemberList = new ArrayList<>();

    @OneToMany(mappedBy = "party",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Project> projectList = new ArrayList<>();

    @OneToMany(mappedBy = "party",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<ProjectMember> projectMemberList  = new ArrayList<>();

    @OneToMany(mappedBy = "party",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Post> postList  = new ArrayList<>();
}
