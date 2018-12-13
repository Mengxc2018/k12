package cn.k12soft.servo.domain;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import org.hibernate.annotations.DynamicUpdate;

@Entity
@DynamicUpdate
public class KlassFeed extends SchoolEntity {

  @Id
  @GeneratedValue
  private Long id;

  @ManyToOne
  private Klass klass;

  private String content;

  @ElementCollection
  private List<String> pictures;

  @ManyToOne
  private Actor createdBy;

  private Instant createdAt;

  @OneToMany(mappedBy = "feed", orphanRemoval = true)
  private List<KlassFeedComment> comments;

  @OneToMany(mappedBy = "feed", orphanRemoval = true)
  private List<KlassFeedLike> likes;

  private KlassFeed() {
  }

  public KlassFeed(Long id) {
    this.id = id;
  }

  public KlassFeed(Klass klass,
                   String content,
                   List<String> pictures,
                   Actor createdBy) {
    super(klass.getSchoolId());
    this.klass = klass;
    this.content = content;
    this.pictures = pictures;
    this.comments = new ArrayList<>();
    this.likes = new ArrayList<>();
    this.createdBy = createdBy;
    this.createdAt = Instant.now();
  }

  public Long getId() {
    return id;
  }

  public Klass getKlass() {
    return klass;
  }

  public Actor getCreatedBy() {
    return createdBy;
  }

  public String getContent() {
    return content;
  }

  public List<String> getPictures() {
    return pictures;
  }

  public Instant getCreatedAt() {
    return createdAt;
  }

  public List<KlassFeedComment> getComments() {
    return comments;
  }

  public List<KlassFeedLike> getLikes() {
    return likes;
  }
}
