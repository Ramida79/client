package org.springframework.android.showcase.rest;



import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by PK on 2017-03-25.
 */


@JsonIgnoreProperties(ignoreUnknown = true)
public class Drawer_Container {

    /*
        id - cells id
        rfid - cells rfid
        owner - current cells owner
        x - position X
        y - position Y
        z - position Z
        sharedWith - array of usernames except owner
        lastAction - timestamp
        shortDescription - maximum 127 chars array
        longDescription - maximum 1023 chars array
    */

    @JsonProperty("id")
    private long id;
    @JsonProperty("rfid")
    private String rfid;
    @JsonProperty("owner")
    private String owner;

    @JsonProperty("x")
    private int x;
    @JsonProperty("y")
    private int y;
    @JsonProperty("z")
    private int z;

    @JsonProperty("sharedWith")
    private String sharedWith;

    @JsonProperty("lastAction")
    private String lastAction;

    @JsonProperty("shortDescription")
    private String shortDescription;

    @JsonProperty("longtDescription")
    private String longtDescription;



    public long getId()     {return this.id;}
    public void setId(long id) {         this.id = id;     }

    public void setRfid(String rfid) {
        this.rfid= rfid;
    }
    public String getRfid() {
        return this.rfid;
    }

    public void setOwner(String owner) {
        this.owner= owner;
    }
    public String getOwner() {
        return this.owner;
    }

    public int getX()     {return this.x;}
    public void setX(int x) {      this.x = x;     }
    public int getY()     {return this.y;}
    public void setY(int y) {      this.y = y;     }
    public int getZ()     {return this.z;}
    public void setZ(int z) {      this.z = z;     }

    public void setSharedWith(String sharedWith) {
        this.sharedWith= sharedWith;
    }
    public String getSharedWith() {
        return this.sharedWith;
    }

    public void setLastAction(String lastAction) {
        this.lastAction= lastAction;
    }
    public String getLastAction() {
        return this.lastAction;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription= shortDescription;
    }
    public String getShortDescription() {
        return this.shortDescription;
    }

    public void setLongtDescription(String longtDescription) {
        this.longtDescription= longtDescription;
    }
    public String getLongtDescription() {
        return this.longtDescription;
    }






    public String toString() {
        return "Id:[" + this.getId() + "] Subject:[" + this.getOwner() + "] Text:[" + this.getShortDescription() + "]";
    }


}
