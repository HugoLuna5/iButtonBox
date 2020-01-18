package lunainc.mx.com.ibuttonbox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Member {

    @SerializedName("id")
    @Expose
    private String uid;
    @SerializedName("id_group")
    @Expose
    private String uid_group;
    @SerializedName("id_user")
    @Expose
    private String uid_user;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("created_at")
    @Expose
    private String join_at;

    @SerializedName("members")
    @Expose
    private List<Member> members = new ArrayList<Member>();

    @SerializedName("user")
    @Expose
    private User user;

    public Member() {
    }


    public Member(String uid, String uid_group, String uid_user, String message, String status, String join_at, List<Member> members, User user) {
        this.uid = uid;
        this.uid_group = uid_group;
        this.uid_user = uid_user;
        this.message = message;
        this.status = status;
        this.join_at = join_at;
        this.members = members;
        this.user = user;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid_group() {
        return uid_group;
    }

    public void setUid_group(String uid_group) {
        this.uid_group = uid_group;
    }

    public String getUid_user() {
        return uid_user;
    }

    public void setUid_user(String uid_user) {
        this.uid_user = uid_user;
    }

    public String getJoin_at() {
        return join_at;
    }

    public void setJoin_at(String join_at) {
        this.join_at = join_at;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Member> getMembers() {
        return members;
    }

    public void setMembers(List<Member> members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
