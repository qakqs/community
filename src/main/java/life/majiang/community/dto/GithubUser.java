package life.majiang.community.dto;

/**
 * GithubUser
 */
public class GithubUser {

    private String name;
    private Long id;
    private String bio;

    public String getName(){
        return name;
    }
    public Long getId(){
        return id;
    }
    public String getBio(){
        return bio;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setId(Long id){
        this.id = id;
    }
    public void setBio(String bio){
        this.bio = bio;
    }

    @Override
    public String toString() {
        return "GithubUser{"+
                "name='" + name + '\''+
                ", id='" + id + 
                ", bio='" + bio + '\'' +
                '}';
    }
}