//package com.tw.contact.modelJPA;
//
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Data;
//import lombok.NoArgsConstructor;
//
//import java.util.List;
//
//@NoArgsConstructor
//@AllArgsConstructor
//@Data //@Getter @Setter @ToString @EqualsAndHashCode @RequiredArgsConstructor
//@Entity
//@Table(name="faq_type")
//public class FaqType {
//
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name="id")
//    private int id;
//
//    @OneToMany(mappedBy = "faqTypeId")
//    private List<Faq> faqs;
//
//    @Column(name="name")
//    private int name;
//
//
//}
