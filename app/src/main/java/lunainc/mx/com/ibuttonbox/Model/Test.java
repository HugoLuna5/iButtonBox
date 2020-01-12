package lunainc.mx.com.ibuttonbox.Model;

public class Test {


    private String uid;
    private String uid_group;
    private String uid_creator;
    private String title;
    private String desc;
    private String type;
    private long time;


    public Test() {
    }


    public Test(String uid, String uid_group, String uid_creator, String title, String desc, String type, long time) {
        this.uid = uid;
        this.uid_group = uid_group;
        this.uid_creator = uid_creator;
        this.title = title;
        this.desc = desc;
        this.type = type;
        this.time = time;
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

    public String getUid_creator() {
        return uid_creator;
    }

    public void setUid_creator(String uid_creator) {
        this.uid_creator = uid_creator;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
