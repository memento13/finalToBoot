package kimsungsu.finalToBoot.entity;

import com.sun.istack.NotNull;
import kimsungsu.finalToBoot.entity.compositeKey.PartyMemberPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "party_members")
@Getter
@Setter
public class PartyMember extends BaseEntity{

    @EmbeddedId
    private PartyMemberPK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    @MapsId("partyId")
    @NotNull
    private Party party;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @MapsId("userId")
    @NotNull
    private User user;

    @NotNull
    private Integer grade;

}
