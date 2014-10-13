package com.happyhours.model;

import com.google.gson.annotations.Expose;

public class DealOffers {


@Expose
private Integer id;
@Expose
private String offerName;

public Integer getId() {
return id;
}

public void setId(Integer id) {
this.id = id;
}

public String getOfferName() {
return offerName;
}

public void setOfferName(String offerName) {
this.offerName = offerName;
}

}
