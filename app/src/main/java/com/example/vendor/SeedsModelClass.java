package com.example.vendor;

import java.util.ArrayList;

public class SeedsModelClass {

    String SeedID;
    String BrandName;
    String SeedName;
    String SeedDescription;
    String TimePeriod;
    Double ItemWeight;
    Integer NetQuantity;
    Boolean StockStatus;
    Double Price;
    String VendorID;
    String Variety;
    String SeedImg;

    public SeedsModelClass(String seedID, String brandName, String seedName, String seedDescription, String timePeriod, Double itemWeight, Integer netQuantity, Boolean stockStatus, Double price, String vendorID, String variety, String seedimg) {
        SeedID = seedID;
        BrandName = brandName;
        SeedName = seedName;
        SeedDescription = seedDescription;
        TimePeriod = timePeriod;
        ItemWeight = itemWeight;
        NetQuantity = netQuantity;
        StockStatus = stockStatus;
        Price = price;
        VendorID = vendorID;
        Variety=variety;
        SeedImg=seedimg;
    }

    public SeedsModelClass() {
    }

    public String getSeedID() {
        return SeedID;
    }

    public void setSeedID(String seedID) {
        SeedID = seedID;
    }

    public String getBrandName() {
        return BrandName;
    }

    public void setBrandName(String brandName) {
        BrandName = brandName;
    }

    public String getSeedName() {
        return SeedName;
    }

    public void setSeedName(String seedName) {
        SeedName = seedName;
    }

    public String getSeedDescription() {
        return SeedDescription;
    }

    public void setSeedDescription(String seedDescription) {
        SeedDescription = seedDescription;
    }

    public String getTimePeriod() {
        return TimePeriod;
    }

    public void setTimePeriod(String timePeriod) {
        TimePeriod = timePeriod;
    }

    public Double getItemWeight() {
        return ItemWeight;
    }

    public void setItemWeight(Double itemWeight) {
        ItemWeight = itemWeight;
    }

    public Integer getNetQuantity() {
        return NetQuantity;
    }

    public void setNetQuantity(Integer netQuantity) {
        NetQuantity = netQuantity;
    }

    public Boolean getStockStatus() {
        return StockStatus;
    }

    public void setStockStatus(Boolean stockStatus) {
        StockStatus = stockStatus;
    }

    public Double getPrice() {
        return Price;
    }

    public void setPrice(Double price) {
        Price = price;
    }

    public String getVendorID() {
        return VendorID;
    }

    public void setVendorID(String vendorID) {
        VendorID = vendorID;
    }

    public String getVariety() {
        return Variety;
    }

    public void setVariety(String variety) {
        Variety = variety;
    }

    public String getSeedImg() {
        return SeedImg;
    }

    public void setSeedImg(String seedImg) {
        SeedImg = seedImg;
    }
}
