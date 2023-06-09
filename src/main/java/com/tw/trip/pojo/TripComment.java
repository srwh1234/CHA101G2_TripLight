package com.tw.trip.pojo;

import com.tw.member.model.Member;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Base64;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name = "trip_comment" ,catalog = "cha101_g2")
public class TripComment implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer tripId;

    @Column(name = "member_id")
    private Integer memberId;

    private Integer rating;

    private String comment;

    private Integer status;

    @Transient
    private Timestamp postTime;

    private Integer editCount;

    @Transient
    private Timestamp lastEditTime;

    @ManyToOne
    @JoinColumn(name = "member_id", insertable = false, updatable = false)
    private Member member;

    @Transient
    private String memberPicBase64;

    @Transient
    private String memberName;

    private Integer tripOrderId;

    public TripComment(String memberName, byte[] memberPic, String comment, Timestamp postTime, int rating ){
        this.memberName = memberName;
        memberPicBase64 = Base64.getEncoder().encodeToString(memberPic);
        this.comment = comment;
        this.postTime = postTime;
        this.rating = rating;
    }


}
