package com.imooc.service.impl;

import com.imooc.mapper.StuMapper;
import com.imooc.pojo.Stu;
import com.imooc.service.StuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StuServiceImpl implements StuService {

    @Autowired
    private StuMapper stuMapper;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Stu getStuInfo(int id) {
        return stuMapper.selectByPrimaryKey(id);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void saveStu() {
        Stu stu = new Stu();
        stu.setAge(20);
        stu.setName("123");
        stuMapper.insert(stu);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public void updateStu(int id) {
        Stu stu = new Stu();
        stu.setId(id);
        stu.setName("123");
        stu.setAge(11);
        stuMapper.updateByPrimaryKey(stu);
    }

    @Override
    public void deleteStu(int id) {
        stuMapper.deleteByExample(id);
    }
}
