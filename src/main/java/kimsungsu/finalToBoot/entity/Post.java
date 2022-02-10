package kimsungsu.finalToBoot.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post extends BaseEntity{
    @Id
    private String id;

    @NotNull
    private String title;

    @NotNull
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @NotNull
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id")
    @NotNull
    private Project project;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "party_id")
    @NotNull
    private Party party;

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Like> likeList  = new ArrayList<>();

    @OneToMany(mappedBy = "post",cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> commentList  = new ArrayList<>();
}
