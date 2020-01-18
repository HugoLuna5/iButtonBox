package lunainc.mx.com.ibuttonbox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Group {


    @SerializedName("id")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("desc")
    @Expose
    private String desc;
    @SerializedName("id_creator")
    @Expose
    private String uid_creator;

    @SerializedName("created_at")
    @Expose
    private String created_at;
    @SerializedName("color")
    @Expose
    private String color;
    @SerializedName("code")
    @Expose
    private String code;
    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("statusResponse")
    @Expose
    private String statusResponse;

    @SerializedName("groups")
    @Expose
    private List<Group> groups = new ArrayList<Group>();


    public Group() {
    }

    public Group(String uid, String name, String desc, String uid_creator, String created_at, String color, String code, String status, String message, String statusResponse, List<Group> groups) {
        this.uid = uid;
        this.name = name;
        this.desc = desc;
        this.uid_creator = uid_creator;
        this.created_at = created_at;
        this.color = color;
        this.code = code;
        this.status = status;
        this.message = message;
        this.statusResponse = statusResponse;
        this.groups = groups;
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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String isStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    public List<Group> getGroups() {
        return groups;
    }

    public void setGroups(List<Group> groups) {
        this.groups = groups;
    }
}
