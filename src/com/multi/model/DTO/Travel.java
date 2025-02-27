package com.multi.model.DTO;

public class Travel {
    private int no;
    private String district;
    private String title;
    private String description;
    private String address;
    private String phone;
    private int count;

    public Travel() {
    }

    public Travel(int no, String district, String title, String description, String address, String phone, int count) {
        this.no = no;
        this.district = district;
        this.title = title;
        this.description = description;
        this.address = address;
        this.phone = phone;
        this.count = count;
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "Travel{" +
                "no=" + no +
                ", district='" + district + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", count=" + count +
                '}';
    }
}
