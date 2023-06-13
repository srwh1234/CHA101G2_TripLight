package com.tw.contact.service;

import com.tw.contact.modelJPA.ChatRecord;
import com.tw.contact.modelJPA.dao.ChatRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ChatRecordServiceImpl implements ChatRecordService{

//    private ChatRecordRepository cRR;
//
//    @Autowired
//    public ChatRecordServiceImpl(ChatRecordRepository chatRecordRepository) {
//        cRR = chatRecordRepository;
//    }
//
//    @Override
//    public List<ChatRecord> findAll() {
//        return cRR.findAll();
//    }
//
//    @Override
//    public void update(ChatRecord chatRecord) {
//        cRR.save(chatRecord);
//    }
//
//    @Override
//    public void delete(int id) {
//        cRR.deleteById(id);
//    }
//
//    @Override
//    public List<ChatRecord> findByMember(int memberId) {
//        return cRR.findByMemberId(memberId);
//    }


}
