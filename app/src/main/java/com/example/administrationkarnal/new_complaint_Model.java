package com.example.administrationkarnal;

class new_complaint_Model
{
    String Category;
    String Description;
    String Status;
    String Date;
    String ComplaintId;
    String Address;
    String Name;
    String mobile;

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }



    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }
    public String getCategory()
    {
        return Category;
    }

    public void setCategory(String category)
    {
        Category = category;
    }

    public String getDescription()
    {
        return Description;
    }

    public void setDescription(String description)
    {
        Description = description;
    }

    public String getStatus()
    {
        return Status;
    }

    public void setStatus(String status)
    {
        Status = status;
    }

    public String getDate()
    {
        return Date;
    }

    public void setDate(String date)
    {
        Date = date;
    }

    public String getComplaintId()
    {
        return ComplaintId;
    }

    public void setComplaintId(String complaintId)
    {
        ComplaintId = complaintId;
    }
}
