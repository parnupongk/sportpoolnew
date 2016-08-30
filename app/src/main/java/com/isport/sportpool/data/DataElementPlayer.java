package com.isport.sportpool.data;

public class DataElementPlayer
{
	public String id = "";
	public String name = "";
	public String lname = "";
	public String fname = "";
	public String countryName = "";
	public String position = "";
	public String shirtNumber = "";
	public String dateBirth = "";
	public String hight = "";
	public String wight = "";

	
	public DataElementPlayer(String id,String name,String lname,String fname
			,String countryName,String position,String shirtNumber,String dateBirth,String hight,String wight)
	{
		this.id = id;
		this.name = name;
		this.lname = lname;
		this.fname = fname;
		this.countryName = countryName;
		this.position = position;
		this.shirtNumber = shirtNumber;
		this.dateBirth = dateBirth;
		this.hight = hight;
		this.wight = wight;
	}

}
