package kimsungsu.finalToBoot.entity;

import com.sun.istack.NotNull;
import kimsungsu.finalToBoot.entity.compositeKey.LikePK;

import javax.persistence.*;

@Entity
@Table(name = "likes")
public class Like extends BaseEntity{

    @EmbeddedId
    private LikePK id;

    @MapsId("postId")
    @JoinColumn(name = "post_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private Post post;

    @MapsId("userId")
    @JoinColumn(name = "user_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @NotNull
    private User user;

}
