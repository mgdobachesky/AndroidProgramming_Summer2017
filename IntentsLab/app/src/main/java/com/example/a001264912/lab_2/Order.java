package com.example.a001264912.lab_2;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
	// Properties
	private String firstName;
	private String lastName;
	private String chocolateType;
	private int chocolateQuantity;
	private boolean expeditedShipping;
	
	// Constructors
	public Order() {}

	public Order(String firstName, String lastName, String chocolateType, int chocolateQuantity,
                 boolean expeditedShipping) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.chocolateType = chocolateType;
		this.chocolateQuantity = chocolateQuantity;
		this.expeditedShipping = expeditedShipping;
	}

	// Getters and Setters
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getChocolateType() {
		return chocolateType;
	}

	public void setChocolateType(String chocolateType) {
		this.chocolateType = chocolateType;
	}

	public int getChocolateQuantity() {
		return chocolateQuantity;
	}

	public void setChocolateQuantity(int chocolateQuantity) {
		this.chocolateQuantity = chocolateQuantity;
	}

	public boolean isExpeditedShipping() {
		return expeditedShipping;
	}

	public void setExpeditedShipping(boolean expeditedShipping) {
		this.expeditedShipping = expeditedShipping;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(this.firstName);
		dest.writeString(this.lastName);
		dest.writeString(this.chocolateType);
		dest.writeInt(this.chocolateQuantity);
		dest.writeByte(this.expeditedShipping ? (byte) 1 : (byte) 0);
	}

	protected Order(Parcel in) {
		this.firstName = in.readString();
		this.lastName = in.readString();
		this.chocolateType = in.readString();
		this.chocolateQuantity = in.readInt();
		this.expeditedShipping = in.readByte() != 0;
	}

	public static final Creator<Order> CREATOR = new Creator<Order>() {
		@Override
		public Order createFromParcel(Parcel source) {
			return new Order(source);
		}

		@Override
		public Order[] newArray(int size) {
			return new Order[size];
		}
	};
}
