package kimsungsu.finalToBoot.entity.compositeKey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class ProjectMemberPK implements Serializable {

    private String partyId;
    private String projectId;
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProjectMemberPK)) return false;
        ProjectMemberPK that = (ProjectMemberPK) o;
        return getPartyId().equals(that.getPartyId()) &&
                getProjectId().equals(that.getProjectId()) &&
                getUserId().equals(that.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPartyId(), getProjectId(), getUserId());
    }
}
