package lunainc.mx.com.ibuttonbox.Model;

public class Member {

    private String uid;
    private String uid_group;
    private String uid_user;
    private long join_at;

    public Member() {
    }

    public Member(String uid, String uid_group, String uid_user, long join_at) {
        this.uid = uid;
        this.uid_group = uid_group;
        this.uid_user = uid_user;
        this.join_at = join_at;
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

    public long getJoin_at() {
        return join_at;
    }

    public void setJoin_at(long join_at) {
        this.join_at = join_at;
    }
}
