package kimsungsu.finalToBoot.entity;

import com.sun.istack.NotNull;
import kimsungsu.finalToBoot.entity.compositeKey.ProjectMemberPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "project_members")
@Getter
@Setter
public class ProjectMember extends BaseEntity{

    @EmbeddedId
    private ProjectMemberPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    @MapsId("partyId")
    @NotNull
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @MapsId("projectId")
    @NotNull
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @NotNull
    private User user;

}
