package com.tw.contact.model_1;

import java.util.List;

public interface chatRecord {
    public void insert(chatRecordVO cRVO);
    public void update(chatRecordVO qRVO);
    public void delete(int id);
    public List<chatRecordVO> getAll();

}
