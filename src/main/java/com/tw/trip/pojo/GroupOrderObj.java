package com.tw.trip.pojo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Base64;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupOrderObj implements Serializable {
    public static final long serialVersionUID = 36L;

    // ====== for data of group order page ======

    public GroupOrderObj(Integer tripId, String tripName, String tripContent, Integer tripOrderId,
                         Integer memberId, byte[] memberPic, String memberName, byte[] tripPic, Integer tourGroupId){

        this.tripId = tripId;
        this.tripName = tripName;
        this.tripContent = tripContent;
        this.tripOrderId = tripOrderId;
        this.memberId = memberId;
        this.memberName=memberName;
        memberPicBase64 = Base64.getEncoder().encodeToString(memberPic);
        tripPicBase64 = Base64.getEncoder().encodeToString(tripPic);
        this.tourGroupId = tourGroupId;

    }

    private String tripPicBase64;

    private Integer tripId;

    private String tripName;

    private String tripContent;

    private Integer tripOrderId;

    private Integer memberId;

    private String memberName;

    private String memberPicBase64;

    private Integer tourGroupId;

}
