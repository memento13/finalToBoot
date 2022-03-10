package kimsungsu.finalToBoot.entity.compositeKey;

import kimsungsu.finalToBoot.entity.Party;
import kimsungsu.finalToBoot.entity.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PartyMemberPK implements Serializable {

    private String partyId;
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PartyMemberPK)) return false;
        PartyMemberPK that = (PartyMemberPK) o;
        return getPartyId().equals(that.getPartyId()) &&
                getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPartyId(), getUserId());
    }
}
