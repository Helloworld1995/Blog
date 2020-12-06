package com.blog.po;



import javax.persistence.*;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 博客实体
 */
@Entity
@Table(name = "t_blog")
public class Blog implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String title;
    private String content;
    private String firstPicture;
    private String flag;
    private Integer views;
    @Transient
    private String tagIds;
    private String description;
    private boolean appreciation;
    private boolean shareStatement;
    private boolean commentable;
    private boolean published;
    private boolean recommend;
    @Transient
    private Integer blogsCount;
    @Transient
    private String createTimeFormat;
    @Transient
    private String updateTimeFormat;
    @Temporal(TemporalType.TIMESTAMP) //写入数据库的日期格式
    private Date createTime;

    @Temporal(TemporalType.TIMESTAMP) //写入数据库的日期格式
    private Date updateTime;
    @ManyToOne  //多对一
    private Type type;
    @ManyToMany(cascade = {CascadeType.PERSIST})
    private List<Tag> tags=new ArrayList<>();
    @ManyToOne()
    private User user;
    @OneToMany(mappedBy = "blog")
    private List<Comment> commentList=new ArrayList<>();

    public Blog() {
    }

    public String getTagIds() {
        return tagIds;
    }

    public Blog setTagIds(String tagIds) {
        this.tagIds = tagIds;
        return this;
    }

    public String getCreateTimeFormat() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.updateTimeFormat=df.format(this.createTime);
        return createTimeFormat;
    }

    public Blog setCreateTimeFormat(String createTimeFormat) {
        this.createTimeFormat = createTimeFormat;
        return this;
    }

    public String getUpdateTimeFormat() {
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        this.updateTimeFormat=df.format(this.updateTime);
        return updateTimeFormat;
    }

    public Blog setUpdateTimeFormat(String updateTimeFormat) {
        this.updateTimeFormat = updateTimeFormat;
        return this;
    }

    public Long getId() {
        return id;
    }

    public Blog setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public Blog setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getContent() {
        return content;
    }

    public Blog setContent(String content) {
        this.content = content;
        return this;
    }

    public String getFirstPicture() {
        return firstPicture;
    }

    public Blog setFirstPicture(String firstPicture) {
        this.firstPicture = firstPicture;
        return this;
    }

    public Integer getBlogsCount() {
        return blogsCount;
    }

    public Blog setBlogsCount(Integer blogsCount) {
        this.blogsCount = blogsCount;
        return this;
    }

    public String getFlag() {
        return flag;
    }

    public Blog setFlag(String flag) {
        this.flag = flag;
        return this;
    }

    public Integer getViews() {
        return views;
    }

    public Blog setViews(Integer views) {
        this.views = views;
        return this;
    }

    public boolean isAppreciation() {
        return appreciation;
    }

    public Blog setAppreciation(boolean appreciation) {
        this.appreciation = appreciation;
        return this;
    }

    public boolean isShareStatement() {
        return shareStatement;
    }

    public Blog setShareStatement(boolean shareStatement) {
        this.shareStatement = shareStatement;
        return this;
    }
    public String getDescription() {
        return description;
    }


    public Blog setDescription(String description) {
        this.description = description;
        return this;
    }

    public boolean isCommentable() {
        return commentable;
    }

    public Blog setCommentable(boolean commentable) {
        this.commentable = commentable;
        return this;
    }

    public boolean isPublished() {
        return published;
    }

    public Blog setPublished(boolean published) {
        this.published = published;
        return this;
    }

    public boolean isRecommend() {
        return recommend;
    }

    public Blog setRecommend(boolean recommend) {
        this.recommend = recommend;
        return this;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public Blog setCreateTime(Date createTime) {
        this.createTime = createTime;
        return this;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public Blog setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Type getType() {
        return type;
    }

    public Blog setType(Type type) {
        this.type = type;
        return this;
    }

    public List<Tag> getTags() {
        return tags;
    }

    public Blog setTags(List<Tag> tags) {
        this.tags = tags;
        return this;
    }

    public User getUser() {
        return user;
    }

    public Blog setUser(User user) {
        this.user = user;
        return this;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public Blog setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
        return this;
    }


    public void init(){
        this.tagIds=tagsToIds(tags);
    }

    private String tagsToIds(List<Tag> tags){
      if(!tags.isEmpty()){
          StringBuffer tagIds=new StringBuffer();
          for (Tag tag : tags) {
              tagIds.append(tag.getId());
              tagIds.append(",");
          }
          return tagIds.toString().substring(0,tagIds.length()-1);
      }else{
          return tagIds;
      }
    }

    public List<Long> getTagIdList() {
        List<Long> res=new ArrayList<>();
        if(this.tagIds==null||this.tagIds.equals("")||this.tagIds.equals("null")){
            return res;
        }
        String[] split = this.tagIds.split(",");
        for (String s : split) {
            if(!s.equals("null")) {
                res.add(Long.parseLong(s));
            }
        }
        return res;
    }
}
