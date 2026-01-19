package com.vmsapi.model;

import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;

@Entity
@Table(name = "voters")
public class Voter {
    @Id
    @Column(name = "serial_no", unique = true, nullable = false)
    @JsonProperty("serial_no")
    private int serialNo;

    @Column(name = "epic_no")
    @JsonProperty("epic_no")
    private String epicNo;

    @Column(name = "house_seq")
    @JsonProperty("house_seq")
    private int houseSeq;

    @Column(name = "house_total")
    @JsonProperty("house_total")
    private int houseTotal;

    @Column(name = "incharge")
    @JsonProperty("incharge")
    private String incharge;

    @Column(name = "sex")
    @JsonProperty("sex")
    private String sex;

    @Column(name = "name")
    @JsonProperty("name")
    private String name;

    @Column(name = "door_no")
    @JsonProperty("door_no")
    private String doorNo;

    @Column(name = "available")
    @JsonProperty("available")
    private String available;

    @Column(name = "room_no")
    @JsonProperty("room_no")
    private String roomNo;

    @Column(name = "age")
    @JsonProperty("age")
    private int age;

    @Column(name = "contact_number")
    @JsonProperty("contact_number")
    private String contactNumber;

    public Voter() {}

    public Voter(int serialNo, String epicNo, int houseSeq, int houseTotal, String incharge, String sex, String name, String doorNo, String available, String roomNo, int age, String contactNumber) {
        this.serialNo = serialNo;
        this.epicNo = epicNo;
        this.houseSeq = houseSeq;
        this.houseTotal = houseTotal;
        this.incharge = incharge;
        this.sex = sex;
        this.name = name;
        this.doorNo = doorNo;
        this.available = available;
        this.roomNo = roomNo;
        this.age = age;
        this.contactNumber = contactNumber;
    }

    public int getSerialNo() { return serialNo; }
    public void setSerialNo(int serialNo) { this.serialNo = serialNo; }

    public String getEpicNo() { return epicNo; }
    public void setEpicNo(String epicNo) { this.epicNo = epicNo; }

    public int getHouseSeq() { return houseSeq; }
    public void setHouseSeq(int houseSeq) { this.houseSeq = houseSeq; }

    public int getHouseTotal() { return houseTotal; }
    public void setHouseTotal(int houseTotal) { this.houseTotal = houseTotal; }

    public String getIncharge() { return incharge; }
    public void setIncharge(String incharge) { this.incharge = incharge; }

    public String getSex() { return sex; }
    public void setSex(String sex) { this.sex = sex; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDoorNo() { return doorNo; }
    public void setDoorNo(String doorNo) { this.doorNo = doorNo; }

    public String getAvailable() { return available; }
    public void setAvailable(String available) { this.available = available; }

    public String getRoomNo() { return roomNo; }
    public void setRoomNo(String roomNo) { this.roomNo = roomNo; }

    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }
}
