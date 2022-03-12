package kimsungsu.finalToBoot.entity;

import com.sun.istack.NotNull;
import kimsungsu.finalToBoot.entity.compositeKey.PartyMemberPK;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "party_members")
@Getter
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


    public PartyMember() {
//        this.id = new PartyMemberPK();
    }

    public PartyMember(Party party, User user, Integer grade) {
//        this.id = new PartyMemberPK();
//        this.id.setPartyId(party.getId());
//        this.id.setUserId(user.getId());
        this.id = new PartyMemberPK(party.getId(),user.getId());
        this.party = party;
        this.user = user;
        this.grade = grade;
    }

    public void setParty(Party party) {
        this.party = party;
        this.id.setPartyId(party.getId());
    }

    public void setUser(User user) {
        this.user = user;
        this.id.setUserId(user.getId());
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}


