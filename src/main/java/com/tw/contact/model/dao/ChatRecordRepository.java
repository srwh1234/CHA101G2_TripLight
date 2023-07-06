package com.tw.contact.model.dao;

import com.tw.contact.model.PrivateChatRecord;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatRecordRepository extends MongoRepository<PrivateChatRecord, String> {
}
