package kimsungsu.finalToBoot.entity.compositeKey;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class LikePK implements Serializable {

    private String postId;
    private String userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikePK)) return false;
        LikePK likePK = (LikePK) o;
        return getPostId().equals(likePK.getPostId()) &&
                getUserId().equals(likePK.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPostId(), getUserId());
    }
}
