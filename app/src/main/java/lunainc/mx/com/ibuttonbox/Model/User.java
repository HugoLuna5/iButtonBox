package lunainc.mx.com.ibuttonbox.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {


    @SerializedName("id")
    @Expose
    private String uid;
    @SerializedName("name")
    @Expose
    private String nombre;
    @SerializedName("last_name_p")
    @Expose
    private String apellidoP;

    @SerializedName("last_name_m")
    @Expose
    private String apellidoM;

    private String numControl;

    @SerializedName("email")
    @Expose
    private String correo;

    @SerializedName("image")
    @Expose
    private String image;

    @SerializedName("thumb_image")
    @Expose
    private String thumb_image;

    @SerializedName("device_token")
    @Expose
    private String device_token;

    @SerializedName("id_type")
    @Expose
    private String type_account;

    @SerializedName("created_at")
    @Expose
    private String created_at;

    @SerializedName("status")
    @Expose
    private String success;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("token")
    @Expose
    private String token;


    public User() {
    }

    public User(String uid, String nombre, String apellidoP, String apellidoM, String numControl, String correo, String image, String thumb_image, String device_token, String type_account, String created_at, String success, String message, String token) {
        this.uid = uid;
        this.nombre = nombre;
        this.apellidoP = apellidoP;
        this.apellidoM = apellidoM;
        this.numControl = numControl;
        this.correo = correo;
        this.image = image;
        this.thumb_image = thumb_image;
        this.device_token = device_token;
        this.type_account = type_account;
        this.created_at = created_at;
        this.success = success;
        this.message = message;
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidoP() {
        return apellidoP;
    }

    public void setApellidoP(String apellidoP) {
        this.apellidoP = apellidoP;
    }

    public String getApellidoM() {
        return apellidoM;
    }

    public void setApellidoM(String apellidoM) {
        this.apellidoM = apellidoM;
    }

    public String getNumControl() {
        return numControl;
    }

    public void setNumControl(String numControl) {
        this.numControl = numControl;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    public void setThumb_image(String thumb_image) {
        this.thumb_image = thumb_image;
    }

    public String getDevice_token() {
        return device_token;
    }

    public void setDevice_token(String device_token) {
        this.device_token = device_token;
    }

    public String getType_account() {
        return type_account;
    }

    public void setType_account(String type_account) {
        this.type_account = type_account;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "User{" +
                "uid='" + uid + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellidoP='" + apellidoP + '\'' +
                ", apellidoM='" + apellidoM + '\'' +
                ", numControl='" + numControl + '\'' +
                ", correo='" + correo + '\'' +
                ", image='" + image + '\'' +
                ", thumb_image='" + thumb_image + '\'' +
                ", device_token='" + device_token + '\'' +
                ", type_account='" + type_account + '\'' +
                ", created_at='" + created_at + '\'' +
                ", success='" + success + '\'' +
                ", message='" + message + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}
