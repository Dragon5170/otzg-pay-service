package com.otzg.file.dao;

import com.otzg.file.entity.FileIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface FileIndexDao extends JpaRepository<FileIndex,Long> {

    @Transactional
    @Modifying
    @Query(value = "delete from FileIndex where id in ?1")
    void deleteByIds(List<Long> ids);

    @Query(value = "select f from FileIndex f where id in ?1")
    List<FileIndex> findByIds(List<Long> ids);

    @Query(value = "select f.path from FileIndex f ")
    List<String> findAllPath();
}