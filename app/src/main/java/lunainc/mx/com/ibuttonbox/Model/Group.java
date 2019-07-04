package lunainc.mx.com.ibuttonbox.Model;

public class Group {


    private String uid;
    private String name;
    private String desc;
    private String uid_creator;
    private long created_at;
    private String color;
    private boolean status;


    public Group() {
    }


    public Group(String uid, String name, String desc, String uid_creator, long created_at, String color, boolean status) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
        this.uid_creator = uid_creator;
        this.created_at = created_at;
        this.color = color;
        this.status = status;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUid_creator() {
        return uid_creator;
    }

    public void setUid_creator(String uid_creator) {
        this.uid_creator = uid_creator;
    }

    public long getCreated_at() {
        return created_at;
    }

    public void setCreated_at(long created_at) {
        this.created_at = created_at;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
