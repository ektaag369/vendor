package com.example.vendor.VendorModule.DashBoard.Seeds;

import java.util.ArrayList;

public class SeedsModelClass {

    String SeedID;
    String BrandName;
    String SeedName;
    String SeedDescription;
    String MonthHarvesting;
    String YearHarvesting;
    String ItemWeight;
    String NetQuantity;
    String StockStatus;
    Double Price;
    String VendorID;
    String Variety;
    String ImageUrl;

    public SeedsModelClass(String seedID, String brandName, String seedName, String seedDescription, String monthHarvesting, String yearHarvesting, String itemWeight, String netQuantity, String stockStatus, Double price, String vendorID, String variety, String imageUrl) {
        SeedID = seedID;
        BrandName = brandName;
        SeedName = seedName;
        SeedDescription = seedDescription;
        MonthHarvesting = monthHarvesting;
        YearHarvesting = yearHarvesting;
        ItemWeight = itemWeight;
        NetQuantity = netQuantity;
        StockStatus = stockStatus;
        Price = price;
        VendorID = vendorID;
        Variety = variety;
        ImageUrl = imageUrl;
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

    public String getMonthHarvesting() {
        return MonthHarvesting;
    }

    public void setMonthHarvesting(String monthHarvesting) {
        MonthHarvesting = monthHarvesting;
    }

    public String getYearHarvesting() {
        return YearHarvesting;
    }

    public void setYearHarvesting(String yearHarvesting) {
        YearHarvesting = yearHarvesting;
    }

    public String getItemWeight() {
        return ItemWeight;
    }

    public void setItemWeight(String itemWeight) {
        ItemWeight = itemWeight;
    }

    public String getNetQuantity() {
        return NetQuantity;
    }

    public void setNetQuantity(String netQuantity) {
        NetQuantity = netQuantity;
    }

    public String getStockStatus() {
        return StockStatus;
    }

    public void setStockStatus(String stockStatus) {
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

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }
}
