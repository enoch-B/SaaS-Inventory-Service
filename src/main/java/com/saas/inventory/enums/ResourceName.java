package com.saas.inventory.enums;

import lombok.Getter;

@Getter
public enum ResourceName {

    /* Resource */
    GET_ALL_RESOURCES("Get All Resources"),
    GET_RESOURCES_BY_ROLE_NAME("Get Resources by Role"),
    GET_RESOURCE_BY_ID("Get Resource Details"),
    GET_RESOURCE_BY_NAME("Get Resource by Name"),
    GRANT_RESOURCE_ACCESS_TO_ROLE("Grant Resource Access to Role"),
    REVOKE_RESOURCE_ACCESS_FROM_ROLE("Revoke Resource Access from Role"),


    /* Disposal Collection Permission/ Disposable Asset */
    ADD_DISPOSAL_COLLECTION("Add Disposal Collection"),
    GET_ALL_DISPOSAL_COLLECTION("Get All Disposal Collections"),
    GET_DISPOSAL_COLLECTION_BY_ID("Get Disposal Collection by ID"),
    GET_DISPOSAL_COLLECTION_BY_DR_NO("Get Disposal Collection by DR No"),
    DELETE_DISPOSAL_COLLECTION("Delete Disposal Collection"),
    UPDATE_DISPOSAL_COLLECTION("Update Disposal Collection"),


    /* Fixed Asset Disposal Permission */
    ADD_FIXED_ASSET_DISPOSAL("Add Fixed Asset Disposal"),
    UPDATE_FIXED_ASSET_DISPOSAL("Update Fixed Asset Disposal"),
    GET_FIXED_ASSET_DISPOSAL_BY_ID("Get Fixed Asset Disposal By Id"),
    GET_ALL_FIXED_ASSET_DISPOSAL("Get All Fixed Asset Disposal"),
    GET_FIXED_ASSET_DISPOSAL_BY_DISPOSAL_NO("Get Fixed Asset Disposal By Disposal Number"),
    DELETE_FIXED_ASSET_DISPOSAL("Delete Fixed Asset Disposal"),

    /*Need Assessment Permission */
    GET_ALL_NEED_ASSESSMENT("Get All Need Assessments"),
    ADD_NEED_ASSESSMENT("Add Need Assessment"),
    GET_NEED_ASSESSMENT_BY_ID("Get Need Assessment By Id"),
    DELETE_NEED_ASSESSMENT("Delete Need Assessment"),
    UPDATE_NEED_ASSESSMENT("Update Need Assessment"),

    /* Fixed Asset Return Permission */
    ADD_FIXED_ASSET_RETURN("Add Fixed Asset Return"),
    GET_ALL_FIXED_ASSET_RETURN("Get All Fixed Asset Return"),
    GET_FIXED_ASSET_RETURN_BY_ID("Get Fixed Asset Return By Id"),
    DELETE_FIXED_ASSET_RETURN("Delete Fixed Asset Return"),
    UPDATE_FIXED_ASSET_RETURN("Update Fixed Asset Return"),

    /* Fixed Asset Transfer */
    ADD_FIXED_ASSET_TRANSFER("Add Fixed Asset Transfer"),
    GET_ALL_FIXED_ASSET_TRANSFER("Get All Fixed Asset Transfer"),
    GET_FIXED_ASSET_TRANSFER_BY_TRANSFER_NO("Get Fixed Asset Transfer by Transfer Number"),
    GET_FIXED_ASSET_TRANSFER_BY_ID("Get Fixed Asset Transfer By Id"),
    UPDATE_FIXED_ASSET_TRANSFER("Update Fixed Asset Transfer"),
    DELETE_FIXED_ASSET_TRANSFER("Delete Fixed Asset Transfer"),

    /*
     Inventory Count Resource
     */

    ADD_INVENTORY_COUNT("Add Inventory Count "),
    GET_INVENTORY_COUNT_BY_ID("Get Inventory Count By Id"),
    GET_ALL_INVENTORY_COUNT("Get All Inventory Count"),
    GET_INVENTORY_COUNT_BY_COUNT_NO("Get Inventory Count By Count No"),
    UPDATE_INVENTORY_COUNT("Update Inventory Count"),
    DELETE_INVENTORY_COUNT("Delete Inventory Count"),

    /*
      lOST Fixed Asset
     */

    ADD_LOST_FIXED_ASSET("Add Lost Fixed Asset"),
    GET_LOST_FIXED_ASSET_BY_ID("Get Lost fixed asset by id"),
    GET_LOST_FIXED_ASSET_BY_LOST_ITEM_NO("Get Lost Fixed Asset By Lost Item No"),
    UPDATE_LOST_FIXED_ASSET("Update Lost Fixed Asset"),
    DELETE_LOST_FIXED_ASSET("Delete Lost Fixed Asset"),
    GET_ALL_LOST_FIXED_ASSET("Get All Lost Fixed Asset"),
    DOWNLOAD_LOST_FIXED_ASSET_FILE("Download Lost Fixed Asset File"),

    /*
      Lost Stock Item
     */

    ADD_LOST_STOCK_ITEM("Add Lost stock item"),
    GET_ALL_LOST_STOCK_ITEM("Get All Lost Stock Item"),
    GET_LOST_STOCK_ITEM_BY_ID("Get Lost Stock Item By Id"),
    GET_LOST_STOCK_ITEM_BY_LOST_STOCK_ITEM_NO("Get Lost Stock Item By Lost Stock Item No"),
    UPDATE_LOST_STOCK_ITEM("Update the lost stock item "),
    DELETE_LOST_STOCK_ITEM("Delete Lost Stock Item"),
    /*
      Inventory Balance
     */
    ADD_INVENTORY_BALANCE("Add Inventory Balance"),
    UPDATE_INVENTORY_BALANCE("Update Inventory Balance"),
    GET_ALL_INVENTORY_BALANCE("Get all Inventory Balance"),
    GET_INVENTORY_BALANCE_BY_ID("Get Inventory Balance By Id"),
    DELETE_INVENTORY_BALANCE("Delete Inventory Balance"),

    /*
      Stock Disposal
     */
    GET_ALL_STOCK_DISPOSAL("Get All Stock Disposal"),
    GET_STOCK_DISPOSAL_BY_ID("Get Stock Disposal By Id"),
    GET_STOCK_DISPOSAL_BY_DISPOSAL_NO("Get Stock Disposal By Disposal Number"),
    UPDATE_STOCK_DISPOSAL("Update Stock Disposal"),
    DELETE_STOCK_DISPOSAL("Delete Stock Disposal"),
    ADD_STOCK_DISPOSAL("Add Stock Disposal"),
    ;

    private final String value;

    private ResourceName(String value) {
        this.value = value;
    }
}
